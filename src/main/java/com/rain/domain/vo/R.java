package com.rain.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

@Setter
@Getter
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8842496838604486962L;

    /**
     * code
     * 业务异常码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 业务数据
     */
    private T data;

    public R() {
    }

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public R(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param data 业务数据
     * @param <T>  业务数据类型
     * @return 成功的R包装
     */
    public static <T> R<T> ok(T data) {
        R<T> success = new R<>();
        success.setCode(200);
        success.setMessage("success");
        success.setData(data);
        return success;
    }

    /**
     * 成功
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     业务数据类型
     * @return 成功的R包装
     */
    public static <T> R<T> ok(String message, T data) {
        R<T> success = new R<>();
        success.setCode(200);
        success.setMessage(message);
        success.setData(data);
        return success;
    }

    /**
     * 成功
     *
     * @param message 成功响应消息
     * @param <T>     数据类型
     * @return 成功响应的 R包装
     */
    public static <T> R<T> success(String message) {
        R<T> success = new R<>();
        success.setCode(200);
        success.setMessage(message);
        return success;
    }


    /**
     * 成功
     *
     * @param <T> 数据类型
     * @return 成功响应的 R包装
     */
    public static <T> R<T> success() {
        R<T> success = new R<>();
        success.setCode(200);
        success.setMessage("操作成功");
        return success;
    }

    /**
     * 错误
     *
     * @param code    业务定义码
     * @param message 消息
     * @param <T>     业务类型
     * @return 错误的R包装
     */
    public static <T> R<T> error(int code, String message) {
        R<T> error = new R<>();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }


    /**
     * 默认的500 错误
     *
     * @param message 消息
     * @param <T>     业务类型
     * @return 错误的R包装
     */
    public static <T> R<T> error(String message) {
        R<T> error = new R<>();
        error.setCode(500);
        error.setMessage(message);
        return error;
    }

    /**
     * 错误
     *
     * @param code    spring http status 枚举
     * @param message 消息
     * @param data    错误信息数据 帮助开发者进一步分析错误
     * @param <T>     数据类型
     * @return 错误的R包装
     */
    public static <T> R<T> error(int code, String message, T data) {
        R<T> error = new R<>();
        error.setCode(code);
        error.setMessage(message);
        error.setData(data);
        return error;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", R.class.getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("data=" + data)
                .toString();
    }

}
