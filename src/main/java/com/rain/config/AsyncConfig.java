package com.rain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 用于Spring应用中异步方法执行的配置类。
 * 启用Spring的异步处理能力，并通过实现{@link AsyncConfigurer}接口提供异步任务执行的自定义配置。
 * <p>
 * 该配置与名为"threadPoolTaskExecutorAsync"的专用线程池执行器Bean集成，以管理异步方法的执行。
 * 同时定义了一个自定义的异常处理器，用于记录异步方法执行期间发生的未捕获异常。
 * <p>
 * 类通过{@code @EnableAsync}注解激活Spring的异步方法执行功能，并通过{@code @Configuration}注解表明其包含Spring Bean定义和配置设置。
 * <p>
 * 异步执行器通过依赖注入获取，该配置确保所有异步操作均使用指定的线程池进行执行。
 * 未捕获的异常将通过自定义的处理器进行日志记录，以便于监控和调试。
 *
 * @author xueyu
 */
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 专用于记录异步任务执行过程中产生的未捕获异常的日志记录器。
     * 该日志记录器通过指定"Async"名称进行初始化，确保所有异步相关的日志信息能够被统一分类和管理。
     * 在异步配置类中，此日志记录器被用于自定义的异步未捕获异常处理器中，以便在异步方法执行出现异常时，
     * 能够将异常信息、相关方法及参数记录到专门的异步日志通道中，便于后续的监控、调试和问题追踪。
     * 通过使用独立的日志记录器，可以使得异步任务的异常日志与应用程序的其他日志分离，提高日志的可读性和维护性。
     */
    private static final Logger ASYNC_LOGGER = LoggerFactory.getLogger("Async");

    /**
     * 用于异步任务执行的线程池任务执行器实例。
     * 该执行器通过依赖注入方式获取，专门用于处理Spring异步方法调用。
     * 执行器配置了核心线程数、最大线程数、任务队列容量及线程空闲超时时间等参数，
     * 并设置了线程名称前缀以便于日志追踪。
     * 当任务被拒绝时，采用调用者运行策略，确保任务不会丢失。
     * 在应用关闭时，执行器会等待所有任务完成，并设置了最长等待时间。
     * 此执行器作为异步配置的一部分，为整个应用提供统一的异步任务执行能力。
     */
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 设置异步任务执行所需的线程池任务执行器。
     * 通过Spring的依赖注入机制，将指定名称的线程池Bean注入到当前配置类中。
     *
     * @param threadPoolTaskExecutor 要注入的线程池任务执行器实例，该实例通过@Qualifier注解指定为"threadPoolTaskExecutorAsync"，
     *                               确保注入的是专用于异步方法执行的线程池配置。
     */
    @Autowired
    public void setThreadPoolTaskExecutor(@Qualifier(value = "threadPoolTaskExecutorAsync") ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    /**
     * 获取用于执行异步方法的线程池执行器。
     * 此方法返回已配置的线程池任务执行器实例，该执行器专门用于处理Spring框架中的异步任务。
     * 执行器提供了线程管理、任务队列及拒绝策略等核心功能，确保异步方法能够高效、可靠地运行。
     *
     * @return 配置好的线程池任务执行器，用于异步任务的调度与执行
     */
    @Override
    public Executor getAsyncExecutor() {
        return threadPoolTaskExecutor;
    }

    /**
     * 获取异步方法执行过程中未捕获异常的自定义处理器。
     * 该方法返回一个实现了{@link AsyncUncaughtExceptionHandler}接口的处理器实例，
     * 用于处理异步任务执行时抛出的未捕获异常。处理器会将异常信息、触发异常的方法以及方法参数
     * 记录到名为"Async"的专用日志记录器中，以便于监控和问题排查。
     * 通过此自定义处理器，可以确保异步执行过程中的异常不会丢失，并能被统一记录和管理。
     *
     * @return 用于处理异步未捕获异常的自定义异常处理器实例
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> ASYNC_LOGGER.error("异步任务执行出现异常,方法:{}", method, ex);
    }

}
