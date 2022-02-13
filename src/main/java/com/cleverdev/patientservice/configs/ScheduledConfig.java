package com.cleverdev.patientservice.configs;

import com.cleverdev.patientservice.scheduled.ImportDataProcessing;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Configuration
@AllArgsConstructor
public class ScheduledConfig {

    private final ImportDataProcessing importDataProcessing;

    private ThreadPoolTaskScheduler getThreadPoolTaskScheduler(String name, Integer poolSize) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(poolSize);
        threadPoolTaskScheduler.setThreadNamePrefix(name);
        return threadPoolTaskScheduler;
    }

    @Bean
    public ThreadPoolTaskScheduler importDataThreadPool() {
        return getThreadPoolTaskScheduler("importDataPool", 2);
    }

    @PostConstruct
    public void configureSchedule() {
//        importDataThreadPool().schedule(importDataProcessing, new PeriodicTrigger(2, TimeUnit.HOURS));
        importDataThreadPool().schedule(importDataProcessing, new CronTrigger("0 0 0/2 ? * * "));
    }
}
