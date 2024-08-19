package com.rain.common.exception.handler;


import cn.hutool.core.util.StrUtil;
import com.kingdee.bos.webapi.common.exception.WebApiInvokeException;
import com.kingdee.bos.webapi.common.exception.WebApiResponseValidationException;
import com.rain.common.exception.BizException;
import com.rain.domain.vo.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果不能区分具体的异常类型 默认走
     *
     * @param throwable Throwable类是Java语言中所有错误和异常的超类
     * @return R
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(Throwable throwable) {
        log.error("", throwable);
        return R.error("系统出现未知异常,请联系管理员。");
    }

    /**
     * 应用程序可能捕获的异常
     *
     * @param e Exception
     * @return R
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(Exception e) {
        log.error("", e);
        return R.error("系统出现未知异常,请联系管理员。");
    }

    /**
     * 应用程序运行中可能捕获的异常
     *
     * @param e RuntimeException 运行在异常
     * @return R
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(RuntimeException e) {
        log.error("", e);
        return R.error("系统出现未知异常:\n" + e.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param e 业务异常 {@link  BizException}
     * @return R
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(BizException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * spring  处理器找不到异常
     *
     * @param e NoHandlerFoundException
     * @return R
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(NoHandlerFoundException e) {
        String errorMessage = String.format("不存在该请求路径,请求路径:'%s',请求方法:'%s'", e.getRequestURL(), e.getHttpMethod());
        return R.error(HttpStatus.NOT_FOUND, errorMessage);
    }

    /**
     * 金蝶webApi调用异常
     *
     * @param e WebApiInvokeException
     * @return R
     */
    @ExceptionHandler(value = WebApiInvokeException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(WebApiInvokeException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 金蝶webApi 操作失败异常
     *
     * @param e WebApiResponseValidationException
     * @return R
     */
    @ExceptionHandler(value = WebApiResponseValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(WebApiResponseValidationException e) {
        String message = e.getMessage();
        return R.error(e.getCode(), message, e.getWebApiResp());
    }

    /**
     * 非法参数异常校验
     *
     * @param e {@link  IllegalArgumentException}
     * @return R
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(IllegalArgumentException e) {
        String message = e.getMessage();
        String errorMessage = "非法参数异常" + (StringUtils.isNotBlank(message) ? ",信息:" + message : "!");
        return R.error(errorMessage);
    }

    /**
     * 不支持的HTTP请求方法
     *
     * @param e       异常
     * @param request 请求
     * @return R
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> exceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return R.error(StrUtil.format("请求方法不支持'{}'请求", e.getMethod()));
    }


    /**
     * 处理MVC 报错
     *
     * @param e HttpMessageNotReadableException
     * @return AjaxResult
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<?> exceptionHandler(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException异常:", e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求体消息不可读");
    }


    /**
     * 处理 sql  异常
     *
     * @param e SQL...Exception
     * @return R
     */
    @ExceptionHandler(value = {
            SQLException.class,
            SQLSyntaxErrorException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<?> exceptionHandlerSqlException(Exception e) {
        log.error("sql异常:", e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "数据库访问出现异常,请联系管理员!");
    }


    /**
     * 当ResourceHttpRequestHandler找不到资源时引发的异常
     *
     * @param e NoResourceFoundException
     * @return R
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(NoResourceFoundException e) {
        HttpMethod httpMethod = e.getHttpMethod();
        String resourcePath = e.getResourcePath();
        String errorMessage = StrUtil.format("没有该静态资源:{},请求方法:{}", resourcePath, httpMethod.toString());
        return R.error(HttpStatus.NOT_FOUND.value(), errorMessage);
    }


    /**
     * 参数类型转换异常
     *
     * @param e MethodArgumentConversionNotSupportedException
     * @return AjaxResult
     */
    @ExceptionHandler(value = MethodArgumentConversionNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public R<?> exceptionHandler(MethodArgumentConversionNotSupportedException e) {
        log.error("error:", e);
        String message = StrUtil.format("参数:[{}]类型转换错误,值无法转换为相对的类型!", e.getName());
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }


    /**
     * 控制器入参必填校验异常处理
     *
     * @param e e
     * @return obj
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public R<?> exceptionHandler(MissingServletRequestParameterException e) {
        String message = String.format("缺少必填参数:'%s',类型:'%s'", e.getParameterName(), e.getParameterType());
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    /**
     * 请求处理方法的参数验证失败时被抛出
     *
     * @param e HandlerMethodValidationException
     * @return R
     */
    @ExceptionHandler(value = HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<?> exceptionHandler(HandlerMethodValidationException e) {
        // 参数验证失败，返回错误信息
        List<? extends MessageSourceResolvable> allErrors = e.getAllErrors();
        String errorMessage = allErrors.stream()
                .filter(Objects::nonNull)
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(",", "[", "]"));
        return R.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    /**
     * bean 的验证异常或者 bean 的属性验证异常时 抛出
     * 这个异常通常在验证阶段出现，表示一个对象的属性值违反了某个约束。
     *
     * @param e ConstraintViolationException
     * @return R
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<?> exceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String message = constraintViolations.stream()
                .filter(Objects::nonNull)
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(",", "[", "]"));
        return R.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 这个异常通常在 Bean Validation 的配置阶段出现，表示约束声明不正确或不符合要求
     *
     * @param e ConstraintDeclarationException
     * @return R
     */
    @ExceptionHandler(value = ConstraintDeclarationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<?> exceptionHandler(ConstraintDeclarationException e) {
        log.error("", e);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统出现异常,请联系管理员!");
    }
}
