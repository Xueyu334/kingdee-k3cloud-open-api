package com.kingdee.bos.webapi.config;

import com.kingdee.bos.webapi.common.utils.CfgUtilExt;
import com.kingdee.bos.webapi.common.utils.WebApiHelper;
import com.kingdee.bos.webapi.common.utils.WebApiHttpHelper;
import com.kingdee.bos.webapi.config.properties.WebApiProperties;
import com.kingdee.bos.webapi.entity.AppCfg;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.bos.webapi.utils.PrintUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * 金蝶云星空配置
 *
 * @author xueyu
 * @see WebApiProperties
 * @see CfgUtilExt
 * @see PrintUtils
 * @see WebApiHelper
 */
@Slf4j
@Configuration
public class WebApiConfig {

    private static final String WEB_API_PROPERTIES_PREFIX = "kingdee.k3cloud.web-api";

    /**
     * 手动创建并绑定金蝶云星空 Web API 配置，避免 {@link WebApiProperties} 依赖 Spring 注解。
     *
     * @param environment Spring 环境配置
     * @return 已绑定的 Web API 配置
     */
    @Bean
    public WebApiProperties webApiProperties(Environment environment) {
        WebApiProperties webApiProperties = new WebApiProperties();
        Binder binder = Binder.get(environment);
        Bindable<WebApiProperties> target = Bindable.ofInstance(webApiProperties);
        binder.bind(WEB_API_PROPERTIES_PREFIX, target);
        return webApiProperties;
    }


    /**
     * 配置云星空 OpenApi 客户端
     *
     * @param webApiProperties 配置参数
     * @return {@link  K3CloudApi}
     */
    @Bean(value = "k3CloudApiClient")
    public K3CloudApi k3CloudApiClient(WebApiProperties webApiProperties) {
        log.info("开始配置金蝶云星空WebApi-AppClient==》");
        String serverUrl = webApiProperties.getServerUrl();
        if (StringUtils.isBlank(serverUrl)) {
            throw new IllegalArgumentException("云星空的服务URL不能为空!");
        }
        String acctId = webApiProperties.getAcctId();
        if (StringUtils.isBlank(acctId)) {
            throw new IllegalArgumentException("云星空的账套ID不能为空!");
        }
        String userName = webApiProperties.getUserName();
        String appId = webApiProperties.getAppId();
        if (StringUtils.isBlank(appId)) {
            throw new IllegalArgumentException("请填写云星空的授权应用APPID");
        }
        String appSec = webApiProperties.getAppSec();
        if (StringUtils.isBlank(appSec)) {
            throw new IllegalArgumentException("请填写云星空的授权应用密钥");
        }
        int lcId = webApiProperties.getLcId();
        String orgNum = webApiProperties.getOrgNum();
        int connectTimeout = webApiProperties.getConnectTimeout();
        int requestTimeout = webApiProperties.getRequestTimeout();
        int stockTimeout = webApiProperties.getStockTimeout();
        String proxy = webApiProperties.getProxy();
        //设置全局默认的 appCfg
        AppCfg appCfg = CfgUtilExt.builder()
                .serverUrl(serverUrl)
                .acctId(acctId)
                .userName(userName)
                .appId(appId)
                .appSecret(appSec)
                .lcId(lcId)
                .orgNum(orgNum)
                .connectTimeout(connectTimeout)
                .requestTimeout(requestTimeout)
                .stockTimeout(stockTimeout)
                .proxy(proxy)
                .build();
        CfgUtilExt.setAppCfgToCfgUtil(appCfg);
        //设置是否打印执行路径
        boolean printExecuteUrl = webApiProperties.isPrintExecuteUrl();
        PrintUtils.setPrint(printExecuteUrl);
        return new K3CloudApi();
    }

    /**
     * 创建并配置金蝶云星空WebApi的HTTP请求辅助工具Bean。
     * 该方法基于提供的配置参数构建WebApiHttpHelper实例，用于处理与金蝶云星空WebApi的HTTP通信。
     * 该Bean在容器销毁时会自动调用close方法以释放相关资源。
     *
     * @param webApiProperties 金蝶云星空的WebApi配置属性，包含服务器URL、账套ID、应用认证信息等必要参数
     * @return 配置完成的WebApiHttpHelper实例，可用于执行WebApi的HTTP请求操作
     */
    @Bean(destroyMethod = "close")
    public WebApiHttpHelper k3CloudWebApiHttpHelper(WebApiProperties webApiProperties) {
        return WebApiHttpHelper.of(webApiProperties);
    }

    /**
     * 创建并配置金蝶云星空WebApi的辅助工具Bean。
     * 该方法基于已配置的K3CloudApi客户端实例，构建WebApiHelper对象，用于简化对金蝶云星空WebApi的调用操作。
     *
     * @param k3CloudApi 已配置的金蝶云星空OpenApi客户端实例，提供与金蝶云星空服务交互的基础能力
     * @return 配置完成的WebApiHelper实例，可用于执行金蝶云星空WebApi的各种业务操作
     */
    @Bean
    public WebApiHelper k3CloudWebApiHelper(K3CloudApi k3CloudApi) {
        return WebApiHelper.of(k3CloudApi);
    }

}
