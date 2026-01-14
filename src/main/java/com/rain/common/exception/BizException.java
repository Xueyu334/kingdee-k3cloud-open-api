package com.rain.common.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * BizException 表示业务逻辑执行过程中发生的异常。
 * 该异常继承自 RuntimeException，用于封装业务层面的错误，例如数据校验失败、业务规则违反或操作不允许等场景。
 * 异常包含一个业务错误码（code），用于标识具体的异常类型，便于系统间错误传递、前端错误展示或日志分析。
 * 错误码提供了一种标准化的方式来区分不同的业务异常，而异常消息则提供详细的错误描述，辅助问题定位和调试。
 * 通过提供多个构造方法，支持不同场景下的异常创建，包括仅消息、消息与原因、错误码与消息、以及错误码、消息与原因的组合。
 * 此异常通常作为业务层异常的基类，子类可以扩展以表示更具体的业务异常类型，实现更精确的错误处理机制。
 * 默认错误码为 500，表示服务器内部错误，但可以通过构造方法指定自定义错误码以适应特定业务需求。
 *
 * @author xueyu
 */
@Getter
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3523268774413159621L;

    /**
     * 异常对应的业务错误码，用于标识具体的业务异常类型。
     * 错误码通常用于系统间错误传递或前端错误展示，便于快速定位问题。
     * 默认值为500，表示服务器内部错误。
     */
    protected final int code;


    /**
     * 通过指定的错误码和错误消息构造业务异常实例。
     * 此构造方法用于创建包含自定义错误码和详细描述的业务异常，适用于需要明确标识异常类型并提供具体错误信息的场景。
     * 错误码用于区分不同的业务异常类型，便于系统间错误传递或前端错误展示；错误消息则提供异常的具体描述，辅助问题定位。
     *
     * @param code    异常对应的业务错误码，用于标识具体的业务异常类型
     * @param message 异常的详细描述信息，说明异常发生的原因或上下文
     */
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = 500;
    }


    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
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
        this.code = 500;
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
