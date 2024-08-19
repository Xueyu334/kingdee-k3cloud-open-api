package com.rain.web.servlet.interceptor;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.cors.CorsUtils.isPreFlightRequest;

/**
 * 不拦截预检查请求的拦截器
 *
 * @author xueyu
 */
public abstract class AbstractNoCorsRequestInterceptor implements HandlerInterceptor {


    @Override
    public final boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        if (!isPreFlightRequest(request)) {
            return preHandleNoCors(request, response, handler);
        }
        return true;
    }


    @Override
    public final void postHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, ModelAndView modelAndView) {
        if (!isPreFlightRequest(request)) {
            postHandleNoCors(request, response, handler);
        }
    }


    @Override
    public final void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) {
        if (!isPreFlightRequest(request)) {
            afterCompletionNoCors(request, response, handler, ex);
        }
    }

    /**
     * 在请求处理之前执行
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return true 继续执行 false 不执行
     */
    protected abstract boolean preHandleNoCors(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 在请求处理之后，视图渲染之前执行
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     */
    protected abstract void postHandleNoCors(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 在请求完成之后执行
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @param ex       异常
     */
    protected abstract void afterCompletionNoCors(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);


}
