package com.rain.common.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author xueyu
 */
@Getter
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3523268774413159621L;
    /**
     * 业务异常-错误码
     */
    protected final int code;


    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BizException(CharSequence format, Object... args) {
        this(StrUtil.format(format, args));
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BizException(Throwable cause) {
        super(cause);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }


    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return "BizException{" +
                "code=" + code +
                '}';
    }
}
