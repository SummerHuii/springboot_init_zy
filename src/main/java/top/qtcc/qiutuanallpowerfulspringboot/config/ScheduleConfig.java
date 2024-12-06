package top.qtcc.qiutuanallpowerfulspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * 定时任务配置
 *
 * @author qiutuan
 * @date 2024/12/07
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Resource(name = "scheduledExecutor")
    private Executor scheduledExecutor;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 设置定时任务的线程池
        taskRegistrar.setScheduler(scheduledExecutor);
    }
} 