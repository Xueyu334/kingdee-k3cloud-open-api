package com.kingdee.bos.webapi.common.exception;

import lombok.Getter;

import java.io.Serial;


/**
 * WebApiInvokeException 表示在调用 Web API 过程中发生的异常。
 * 该异常独立于应用层业务异常，用于封装与 Web API 交互时出现的错误，例如网络问题、API 响应错误或业务逻辑校验失败。
 * 异常可以包含错误码和详细消息，便于在异常处理时识别错误类型和原因。
 * 通过提供多个构造方法，支持不同场景下的异常创建，包括仅消息、消息与原因、错误码与消息、以及错误码、消息与原因的组合。
 * 此异常通常用于需要区分业务异常和 Web API 调用异常的系统中，以提供更精确的错误处理机制。
 *
 * @author xueyu
 */
@Getter
public class WebApiInvokeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8379537900238479658L;

    /**
     * 异常对应的错误码，默认值为 500。
     */
    protected final int code;

    public WebApiInvokeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WebApiInvokeException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public WebApiInvokeException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public WebApiInvokeException(String message) {
        super(message);
        this.code = 500;
    }

    @Override
    public String toString() {
        return super.toString() + ", code=" + code;
    }


}
