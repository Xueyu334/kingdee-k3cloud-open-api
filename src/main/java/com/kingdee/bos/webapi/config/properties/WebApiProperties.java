package com.kingdee.bos.webapi.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 配置用于与Web API交互的相关属性。
 *
 * @author xueyu
 */
@Getter
@Setter
@RequiredArgsConstructor
public class WebApiProperties {

    /**
     * 服务Url地址
     */
    private String serverUrl;

    /**
     * 第三方系统登录授权的账套ID
     */
    private String acctId;

    /**
     * 第三方系统登录授权的用户
     */
    private String userName;

    /**
     * 第三方系统登录授权的应用ID
     */
    private String appId;

    /**
     * 第三方系统登录授权的应用密钥
     */
    private String appSec;

    /**
     * 账套语系，默认2052(中文简体)
     */
    private int lcId = 2052;

    /**
     * 组织编码，启用多组织时配置对应的组织编码才有效
     */
    private String orgNum;

    /**
     * 允许最大连接延时  单位秒
     */
    private int connectTimeout = 120;

    /**
     * 允许最大读取延时 单位秒
     */
    private int requestTimeout = 120;

    /**
     * 套接字超时 单位秒
     */
    private int stockTimeout = 180;

    /**
     * 若使用代理，配置此参数
     */
    private String proxy;

    /**
     * 是否打印执行地址
     */
    private boolean isPrintExecuteUrl = false;


    public void setProxy(String proxy) {
        if (StringUtils.isBlank(proxy)) {
            this.proxy = null;
        } else {
            this.proxy = proxy;
        }
    }
}
