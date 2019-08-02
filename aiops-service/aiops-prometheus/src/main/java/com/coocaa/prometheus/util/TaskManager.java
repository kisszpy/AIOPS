package com.coocaa.prometheus.util;

import com.coocaa.core.tool.utils.SpringUtil;
import com.coocaa.prometheus.rabbitMQ.TimingDataSender;
import com.coocaa.prometheus.service.PromQLService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: intelligent_maintenance
 * @description: 定时任务管理
 * @author: dongyang_wu
 * @create: 2019-08-01 13:55
 */
@Component("TaskManager")
public class TaskManager implements DisposableBean {
    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>(16);
    private final Map<Integer, AtomicInteger> taskErrorTimes = new ConcurrentHashMap<>(16);
    @Autowired
    private TaskScheduler taskScheduler;
    private volatile static PromQLService promQLService;
    private volatile static TimingDataSender timingDataSender;
    private volatile Integer notifyNumber = 5;

    public void addCronTask(Integer taskId, Runnable task, String cronExpression) {
        if (this.scheduledTasks.containsKey(taskId)) {
            removeCronTask(taskId);
        }
        ScheduledFuture<?> future = taskScheduler.schedule(task, new CronTrigger(cronExpression));
        this.scheduledTasks.put(taskId, future);
    }

    @Transactional
    public synchronized boolean removeCronTask(Integer taskId) {
        if (this.scheduledTasks.containsKey(taskId)) {
            ScheduledFuture<?> scheduledTask = this.scheduledTasks.remove(taskId);
            if (scheduledTask != null && !scheduledTask.isCancelled()) {
                scheduledTask.cancel(true);
                System.out.println("停止定时任务");
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        for (ScheduledFuture<?> scheduledTask : this.scheduledTasks.values()) {
            scheduledTask.cancel(true);
        }
        this.scheduledTasks.clear();
    }

    public synchronized Boolean isOverErrorTimes(Integer taskId) {
        taskErrorTimes.putIfAbsent(taskId, new AtomicInteger(0));
        return taskErrorTimes.get(taskId).getAndIncrement() > notifyNumber;
    }

    public synchronized void deleteErrorTimesMap(Integer taskId) {
        taskErrorTimes.remove(taskId);
    }

    public static PromQLService getPromQLService() {
        //第一重判断
        if (promQLService == null) {
            //锁定代码块
            synchronized (PromQLService.class) {
                //第二重判断
                if (promQLService == null) {
                    promQLService = SpringUtil.getBean("PromQLService");
                }
            }
        }
        return promQLService;
    }

    public static TimingDataSender getTimingDataSender() {
        //第一重判断
        if (timingDataSender == null) {
            //锁定代码块
            synchronized (TimingDataSender.class) {
                //第二重判断
                if (timingDataSender == null) {
                    timingDataSender = SpringUtil.getBean("TimingDataSender");
                }
            }
        }
        return timingDataSender;
    }
}