package com.kingdee.bos.webapi.common.exception;

import com.rain.common.exception.BizException;

import java.io.Serial;


public class WebApiInvokeException extends BizException {

    @Serial
    private static final long serialVersionUID = 8379537900238479658L;

    public WebApiInvokeException(int code, String message) {
        super(code, message);
    }

    public WebApiInvokeException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public WebApiInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebApiInvokeException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
