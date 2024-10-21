package com.rain.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author xueyu
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {


    /**
     * 默认线程池配置
     *
     * @return {@linkplain  ThreadPoolTaskExecutor}
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        log.info("default-threadPoolTaskExecutor-config-start...");
        //在程序刚启动的时候 并不会根据核心线程数创建线程 而是按需创建，只会在任务到达时会创建线程 最终会保留核心线程数的线程一直存在 即便是他们是空闲的
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //指定线程池中的最大线程数。当核心线程数已满，且任务队列也已满时，新任务会创建新的线程，直到线程数达到最大值。
        threadPoolTaskExecutor.setMaxPoolSize(200);
        //指定线程池中的核心线程数。核心线程会一直存活，即使它们处于空闲状态。
        threadPoolTaskExecutor.setCorePoolSize(5);
        //指定用于保存等待执行任务的队列的容量。当核心线程都在执行任务且任务队列已满时，新任务会根据线程池的策略被拒绝。
        threadPoolTaskExecutor.setQueueCapacity(500);
        //指定非核心线程的闲置超时时间。当线程池中的线程数量大于核心线程数时，闲置的线程在这个时间后会被回收。
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        threadPoolTaskExecutor.setThreadNamePrefix("my-customer-thread-");
        // 指定当任务被拒绝时的处理策略。主要有四种策略：AbortPolicy、CallerRunsPolicy、DiscardPolicy 和 DiscardOldestPolicy。
        ThreadPoolExecutor.CallerRunsPolicy rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        threadPoolTaskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        //指定是否在关闭线程池时等待所有任务完成。如果设置为 true，那么线程池在关闭时会等待所有任务执行完成后再继续关闭。
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //在 waitForTasksToCompleteOnShutdown 为 true 时，指定等待所有任务完成的最长时间。超过此时间后，强制关闭线程池。
        threadPoolTaskExecutor.setAwaitTerminationSeconds(30);
        return threadPoolTaskExecutor;
    }


    /**
     * 配置给{@link org.springframework.scheduling.annotation.Async}使用的异步线程池
     *
     * @return ThreadPoolTaskExecutor
     * @see AsyncConfig
     */
    @Bean(name = "threadPoolTaskExecutorAsync")
    public ThreadPoolTaskExecutor threadPoolTaskExecutorAsync() {
        //在程序刚启动的时候 并不会根据核心线程数创建线程 而是按需创建，只会在任务到达时会创建线程 最终会保留核心线程数的线程一直存在 即便是他们是空闲的
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //指定线程池中的最大线程数。当核心线程数已满，且任务队列也已满时，新任务会创建新的线程，直到线程数达到最大值。
        threadPoolTaskExecutor.setMaxPoolSize(200);
        //指定线程池中的核心线程数。核心线程会一直存活，即使它们处于空闲状态。
        threadPoolTaskExecutor.setCorePoolSize(5);
        //指定用于保存等待执行任务的队列的容量。当核心线程都在执行任务且任务队列已满时，新任务会根据线程池的策略被拒绝。
        threadPoolTaskExecutor.setQueueCapacity(500);
        //指定非核心线程的闲置超时时间。当线程池中的线程数量大于核心线程数时，闲置的线程在这个时间后会被回收。
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        threadPoolTaskExecutor.setThreadNamePrefix("async-thread-");
        // 指定当任务被拒绝时的处理策略。主要有四种策略：AbortPolicy、CallerRunsPolicy、DiscardPolicy 和 DiscardOldestPolicy。
        ThreadPoolExecutor.CallerRunsPolicy rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        threadPoolTaskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        //指定是否在关闭线程池时等待所有任务完成。如果设置为 true，那么线程池在关闭时会等待所有任务执行完成后再继续关闭。
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //在 waitForTasksToCompleteOnShutdown 为 true 时，指定等待所有任务完成的最长时间。超过此时间后，强制关闭线程池。
        threadPoolTaskExecutor.setAwaitTerminationSeconds(30);
        return threadPoolTaskExecutor;
    }
}
