package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微博认证信息
 */
@Getter
@Setter
@ToString
public class WeiboAuthInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 微博URL
     */
    @JsonProperty("WeiboUrl")
    private String weiboUrl;

    /**
     * 网络ID
     */
    @JsonProperty("NetWorkID")
    private String netWorkID;

    /**
     * 公司网络ID
     */
    @JsonProperty("CompanyNetworkID")
    private String companyNetworkID;

    /**
     * 账户
     */
    @JsonProperty("Account")
    private String account;

    /**
     * Token Key
     */
    @JsonProperty("TokenKey")
    private String tokenKey;

    /**
     * Token Secret
     */
    @JsonProperty("TokenSecret")
    private String tokenSecret;

    /**
     * 验证信息
     */
    @JsonProperty("Verify")
    private String verify;

    /**
     * 回调URL
     */
    @JsonProperty("CallbackUrl")
    private String callbackUrl;

    /**
     * 用户ID
     */
    @JsonProperty("UserId")
    private String userId;
}
