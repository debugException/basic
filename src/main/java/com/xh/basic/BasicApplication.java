package com.xh.basic;

import com.xh.basic.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@SpringBootApplication
public class BasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

    @EnableAsync
    @Configuration
    class TaskPoolConfig{

        @Bean("taskExecutor")
        public Executor taskExecutor(){
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            //核心线程数
            executor.setCorePoolSize(10);
            //最大线程数
            executor.setMaxPoolSize(20);
            //缓存队列
            executor.setQueueCapacity(200);
            //允许线程空闲时间
            executor.setKeepAliveSeconds(60);
            //线程池名的前缀
            executor.setThreadNamePrefix("taskExecutor-");
            //线程池对拒绝任务的处理策略
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的bean
            executor.setWaitForTasksToCompleteOnShutdown(true);
            //设置线程池中任务的等待时间
            executor.setAwaitTerminationSeconds(60);
            return executor;
        }
    }


}
