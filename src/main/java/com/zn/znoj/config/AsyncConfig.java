package com.zn.znoj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/5/13
 */

@Configuration
public class AsyncConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4);         // 核心线程数
        executor.setMaxPoolSize(16);         // 最大线程数（不建议超20）
        executor.setQueueCapacity(10);       // 队列太大容易堆积任务
        executor.setKeepAliveSeconds(30);    // 线程空闲超时释放
        executor.setThreadNamePrefix("Judge-Async-");

        // 由提交任务的线程自己运行该任务，起到“削峰”的作用.
        // 不丢任务，但会拖慢调用者速度
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());


        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();

        System.out.println("@Async 判题线程池配置成功：核心线程4，最大线程16，队列10");
        return executor;
    }
}