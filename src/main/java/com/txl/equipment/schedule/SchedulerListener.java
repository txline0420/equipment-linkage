package com.txl.equipment.schedule;

import com.txl.equipment.exception.SchedulerException;
import com.txl.equipment.job.JobDetail;
import com.txl.equipment.job.JobKey;
import com.txl.equipment.trigger.Trigger;
import com.txl.equipment.trigger.TriggerKey;

/**
 * Created by TangXiangLin on 2023-02-15 12:00
 * 调度器监听器
 * 1. 由希望通知主要调度程序事件的类来实现的接口
 */
public interface SchedulerListener {

    /** 调度器即将调度作业任务 */
    void jobScheduled(Trigger trigger);

    /** 调度器未调度作业任务 */
    void jobUnscheduled(TriggerKey triggerKey);

    /** 触发器结束触发作用 */
    void triggerFinalized(Trigger trigger);

    /** 触发器暂停触发 */
    void triggerPaused(TriggerKey triggerKey);

    /** 一组触发器暂停触发 */
    void triggersPaused(String triggerGroup);

    /** 触发器恢复触发 */
    void triggerResumed(TriggerKey triggerKey);

    /** 一组触发器恢复触发 */
    void triggersResumed(String triggerGroup);

    /** 已增加作业任务 */
    void jobAdded(JobDetail jobDetail);

    /** 已移除作业任务 */
    void jobDeleted(JobKey jobKey);

    /** 已暂停作业任务 */
    void jobPaused(JobKey jobKey);

    /** 已暂停一组作业任务 */
    void jobsPaused(String jobGroup);

    /** 已恢复作业任务 */
    void jobResumed(JobKey jobKey);

    /** 已恢复一组作业任务 */
    void jobsResumed(String jobGroup);

    /** 调度器中发生严重错误 */
    void schedulerError(String msg, SchedulerException cause);

    /** 调度器置于"待机"模式 */
    void schedulerInStandbyMode();

    /** 调度器开始调度 */
    void schedulerStarted();

    /** 调度器正在调度 */
    void schedulerStarting();

    /** 调度器已完成调度 */
    void schedulerShutdown();

    /** 调度器即将完成调度 */
    void schedulerShuttingdown();

    /** 调度器已清除任务、触发器、日历等资源 */
    void schedulingDataCleared();
}

