package com.kingdee.bos.webapi.config;

import cn.hutool.core.lang.Assert;
import com.kingdee.bos.webapi.common.utils.CfgUtilExt;
import com.kingdee.bos.webapi.common.utils.WebApiHelper;
import com.kingdee.bos.webapi.config.properties.WebApiProperties;
import com.kingdee.bos.webapi.entity.AppCfg;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.bos.webapi.utils.PrintUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
@EnableConfigurationProperties(WebApiProperties.class)
public class WebApiConfig {

    /**
     * 配置云星空OpenApi客户端
     *
     * @param webApiProperties 配置参数
     * @return {@link  K3CloudApi}
     */
    @Bean(value = "k3CloudApiClient")
    public K3CloudApi k3CloudApiClient(WebApiProperties webApiProperties) {
        log.info("开始配置金蝶云星空WebApi-AppClient==》");
        String serverUrl = webApiProperties.getServerUrl();
        Assert.notBlank(serverUrl, () -> new IllegalArgumentException("云星空的服务URL不能为空!"));
        String acctId = webApiProperties.getAcctId();
        Assert.notBlank(acctId, () -> new IllegalArgumentException("云星空的账套ID不能为空!"));
        String userName = webApiProperties.getUserName();
        String appId = webApiProperties.getAppId();
        Assert.notBlank(appId, () -> new IllegalArgumentException("请填写云星空的授权应用APPID"));
        String appSec = webApiProperties.getAppSec();
        Assert.notBlank(appSec, () -> new IllegalArgumentException("请填写云星空的授权应用密钥"));
        int lcId = webApiProperties.getLcId();
        String orgNum = webApiProperties.getOrgNum();
        int connectTimeout = webApiProperties.getConnectTimeout();
        int requestTimeout = webApiProperties.getRequestTimeout();
        int stockTimeout = webApiProperties.getStockTimeout();
        String proxy = webApiProperties.getProxy();
        //设置全局默认的appCfg
        AppCfg appCfg = CfgUtilExt.buildAppCfg(serverUrl, acctId, userName, appId, appSec, lcId, orgNum, connectTimeout, requestTimeout, stockTimeout, proxy);
        CfgUtilExt.setAppCfgToCfgUtil(appCfg);
        //设置是否打印执行路径
        boolean printExecuteUrl = webApiProperties.isPrintExecuteUrl();
        PrintUtils.setPrint(printExecuteUrl);
        return new K3CloudApi();
    }

    /**
     * 云星空api调用-包装
     *
     * @param k3CloudApi 云星空OpenApi客户端
     * @return {@link  WebApiHelper}
     */
    @Bean
    public WebApiHelper k3CloudWebApiHelper(K3CloudApi k3CloudApi) {
        return WebApiHelper.of(k3CloudApi);
    }

}
