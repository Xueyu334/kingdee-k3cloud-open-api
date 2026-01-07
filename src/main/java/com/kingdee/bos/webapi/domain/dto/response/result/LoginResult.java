package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录结果
 *
 * @author xueyu
 */
@Getter
@Setter
@ToString
public class LoginResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应消息
     */
    @JsonProperty("Message")
    private String message;

    /**
     * 消息代码
     */
    @JsonProperty("MessageCode")
    private String messageCode;

    /**
     * 登录结果类型，1表示成功
     */
    @JsonProperty("LoginResultType")
    private Integer loginResultType;

    /**
     * 上下文信息，包含登录后的各种会话和环境参数
     */
    @JsonProperty("Context")
    private LoginResultContext context;

    /**
     * KDSVC会话ID
     */
    @JsonProperty("KDSVCSessionId")
    private String kdsvcSessionId;

    /**
     * 表单ID
     */
    @JsonProperty("FormId")
    private String formId;

    /**
     * 重定向表单参数
     */
    @JsonProperty("RedirectFormParam")
    private String redirectFormParam;

    /**
     * 表单输入对象
     */
    @JsonProperty("FormInputObject")
    private Object formInputObject;

    /**
     * 错误堆栈跟踪信息，登录失败时可能包含
     */
    @JsonProperty("ErrorStackTrace")
    private String errorStackTrace;

    /**
     * 语言ID
     */
    @JsonProperty("Lcid")
    private Integer lcid;

    /**
     * 访问令牌
     */
    @JsonProperty("AccessToken")
    private String accessToken;

    /**
     * 自定义参数，可扩展的配置信息
     */
    @JsonProperty("CustomParam")
    private LoginResultCustomParam customParam;


    /**
     * Kd访问结果
     */
    @JsonProperty("KdAccessResult")
    private Object kdAccessResult;

    /**
     * API调用是否成功
     */
    @JsonProperty("IsSuccessByAPI")
    private Boolean isSuccessByAPI;

    /**
     * 判断登录是否成功
     *
     * @return true 成功 false 失败
     */
    public boolean isLoginSuccess() {
        return loginResultType != null && loginResultType == 1 && kdsvcSessionId != null && !kdsvcSessionId.isEmpty();
    }
}
