package com.DATN.PhanAnhSuCoDoThi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "spamCheckExecutor")
    public Executor spamCheckExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // Số lượng thread mặc định chạy chạy đồng thời
        executor.setMaxPoolSize(10); // Số lượng thread tối đa khi hàng đợi đầy
        executor.setQueueCapacity(100); // Kích thước hàng đợi tối đa
        executor.setThreadNamePrefix("SpamCheck-");
        executor.initialize();
        return executor;
    }
}
