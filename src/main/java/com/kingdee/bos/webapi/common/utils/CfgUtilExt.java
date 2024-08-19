package com.kingdee.bos.webapi.common.utils;

import com.kingdee.bos.webapi.entity.AppCfg;
import com.kingdee.bos.webapi.sdk.HttpRequester;
import com.kingdee.bos.webapi.utils.CfgUtil;
import com.kingdee.bos.webapi.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;


/**
 * 金蝶web api的封装有些困扰
 * 在最终http请求阶段获取proxy 还是会调用{@link CfgUtil#getAppDefaultCfg()}方法
 * 所以增加该扩展 避免读取配置文件
 *
 * @author xueyu
 * @see HttpRequester#post()
 * @see HttpUtils#getProxy()
 * @see CfgUtil#getAppDefaultCfg()
 */
public class CfgUtilExt {

    /**
     * 将 {@link AppCfg} 实例设置为 {@link CfgUtil}。
     *
     * @param appCfg 要设置的 {@link AppCfg} 实例
     */
    public static void setAppCfgToCfgUtil(AppCfg appCfg) {
        try {
            Field field = CfgUtil.class.getDeclaredField("instance");
            //将字段的访问权限设为true
            field.setAccessible(true);
            field.set(null, appCfg);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用提供的配置参数构建并返回一个 {@link AppCfg} 实例。
     *
     * @param serverUrl      要设置在 {@link AppCfg} 实例中的服务器 URL。
     * @param acctId         要设置在 {@link AppCfg} 实例中的账套 ID。
     * @param userName       要设置在 {@link AppCfg} 实例中的用户名。
     * @param appId          要设置在 {@link AppCfg} 实例中的应用程序 ID。
     * @param appSec         要设置在 {@link AppCfg} 实例中的应用程序密钥。
     * @param lcId           要设置在 {@link AppCfg} 实例中的多语言代码。
     * @param orgNum         要设置在 {@link AppCfg} 实例中的组织编号。
     * @param connectTimeout 要设置在 {@link AppCfg} 实例中的连接超时时间（秒）。
     * @param requestTimeout 要设置在 {@link AppCfg} 实例中的请求超时时间（秒）。
     * @param stockTimeout   要设置在 {@link AppCfg} 实例中的库存超时时间（秒）。
     * @param proxy          要设置在 {@link AppCfg} 实例中的代理信息。
     * @return 使用提供参数初始化的 {@link AppCfg} 实例。
     */
    public static AppCfg buildAppCfg(String serverUrl, String acctId, String userName,
                                     String appId, String appSec, int lcId,
                                     String orgNum, int connectTimeout, int requestTimeout,
                                     int stockTimeout, String proxy) {
        AppCfg appCfg = new AppCfg();
        appCfg.setServerUrl(serverUrl);
        appCfg.setdCID(acctId);
        appCfg.setUserName(userName);
        appCfg.setAppId(appId);
        appCfg.setAppSecret(appSec);
        appCfg.setlCID(lcId);
        appCfg.setOrgNum(orgNum);
        appCfg.setConnectTimeout(connectTimeout);
        appCfg.setRequestTimeout(requestTimeout);
        appCfg.setStockTimeout(stockTimeout);
        if (StringUtils.isNotBlank(proxy)) {
            appCfg.setProxy(proxy);
        }
        return appCfg;
    }


}
