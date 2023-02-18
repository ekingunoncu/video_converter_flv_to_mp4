package com.ekingunoncu.converter.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configuration class for Spring's asynchronous method execution capability.
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig {

    /**
     * Creates a thread pool executor for executing tasks asynchronously, such as
     * file conversions.
     * 
     * @return A thread pool executor with the specified core and maximum pool
     *         sizes, queue capacity, thread name prefix, and rejected execution
     *         handler.
     */
    @Bean(name = "fileConversionExecutor")
    public Executor fileConversionExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Set the core pool size to 4 threads.
        executor.setCorePoolSize(4);

        // Set the maximum pool size to 8 threads.
        executor.setMaxPoolSize(8);

        // Set the queue capacity to 100 tasks.
        executor.setQueueCapacity(100);

        // Set the thread name prefix to "file-conversion-".
        executor.setThreadNamePrefix("file-conversion-");

        // Initialize the executor.
        executor.initialize();

        return executor;
    }
}
