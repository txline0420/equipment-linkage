package com.txl.equipment.schedule;

import com.txl.equipment.exception.SchedulerException;
import com.txl.equipment.job.JobDetail;
import com.txl.equipment.job.JobExecutionContext;
import com.txl.equipment.job.JobFactory;
import com.txl.equipment.key.Key;
import com.txl.equipment.listener.ListenerManager;
import com.txl.equipment.trigger.Trigger;
import com.txl.equipment.trigger.TriggerKey;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by TangXiangLin on 2023-02-13 15:05
 * 调度器
 * 1. 调度器中维护着"任务描述"、"触发器"的注册、注销。
 * 2. 调度器负责相关的触发器触发后，相关的任务执行。
 * 3。调度器实例SchedulerFactory由产生。已经创建的调度器实例可以创建他的工厂找着并使用。
 * 4。调度器实例创建完毕后，处理"stand-by"(待机)模式,需要启动start()方法，才能激活并开始工作。
 * 5. 作业任务则由客户端定义并实现任务接口来创建。创建以后可以通过触发器或调度器进行注册。
 * 6。触发器和作业任务由名称+组作为唯一标识，当触发器根据设定的条件满足以后，由调度器统一调度任务的执行。
 * 7。JobListener接口提供作业执行的通知。
 * 8。TriggerListener接口提供触发触发的通知。
 * 9。SchedulerListener接口提供调度器事件和错误的通知。
 * 10。监听器可以通过ListenerManager接口与本地调度器关联。
 */
public interface Scheduler {

    /** 组的默认名称 */
    String DEFAULT_GROUP = Key.DEFAULT_GROUP;

    /** 调度器内部使用的常量组名称 - 恢复组名 */
    String DEFAULT_RECOVERY_GROUP = "RECOVERING_JOBS";

    /** 调度器内部使用的常量组名称 - 失败组名  */
    String DEFAULT_FAIL_OVER_GROUP = "FAILED_OVER_JOBS";

    /** 调度器内部使用的常量组名称 - 从触发器到任务恢复的名称  */
    String FAILED_JOB_ORIGINAL_TRIGGER_NAME =  "QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME";

    /** 调度器内部使用的常量组名称 - 从触发器到任务恢复的组名  */
    String FAILED_JOB_ORIGINAL_TRIGGER_GROUP =  "QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP";

    /** 用于在调度程序实例失败后恢复作业时，从恢复触发器的数据映射中检索原始触发器的启动时间 */
    String FAILED_JOB_ORIGINAL_TRIGGER_FIRETIME_IN_MILLISECONDS =
            "QRTZ_FAILED_JOB_ORIG_TRIGGER_FIRETIME_IN_MILLISECONDS_AS_STRING";

    /** 用于在调度程序实例失败后恢复作业时，从恢复触发器的数据映射中检索原始触发器的计划启动时间 */
    String FAILED_JOB_ORIGINAL_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS =
            "QRTZ_FAILED_JOB_ORIG_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS_AS_STRING";


    /** 获取调度器的名称 */
    String getSchedulerName() throws SchedulerException;

    /** 获取调度器的实例ID */
    String getSchedulerInstanceId() throws SchedulerException;

    /** 获取调度器的上下文对象 */
    SchedulerContext getContext() throws SchedulerException;

    /** 调度器状态 - 启动 */
    void start() throws SchedulerException;

    /** 调度器状态 - 延时启动 */
    void startDelayed(int seconds) throws SchedulerException;

    /** 调度器状态 - 是否已启动 */
    boolean isStarted() throws SchedulerException;

    /** 调度器状态 - 待机 */
    void standby() throws SchedulerException;

    /** 调度器状态 - 是否待机 */
    boolean isInStandbyMode() throws SchedulerException;

    /** 调度器状态 - 停止 */
    void shutdown() throws SchedulerException;

    /** 调度器状态 - 延时停止 */
    void shutdown(boolean waitForJobsToComplete) throws SchedulerException;

    /** 调度器状态 - 是否停止 */
    boolean isShutdown() throws SchedulerException;

    /** 获取调度器原数据 */
    SchedulerMetaData getMetaData() throws SchedulerException;

    /** 获取调度器正在作业的任务实例 */
    List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException;

    /** 获取任务创建的工厂 */
    void setJobFactory(JobFactory factory) throws SchedulerException;

    /** 获取监控器管理者，通过该引用可以注册侦听器 */
    ListenerManager getListenerManager()  throws SchedulerException;

    /** 将给定的JobDetail添加到Scheduler，并将给定的触发器与其关联。
     * 如果给定的触发器没有引用任何作业，那么它将被设置为引用与它一起传递到此方法中的作业 */
    Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException;

    /** 触发器设置给定触发器 */
    Date scheduleJob(Trigger trigger) throws SchedulerException;

    /** 为所有给定作业计划，使用触发器集 */
    void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException;

    /** 为所有给定作业，使用触发器集 */
    void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace) throws SchedulerException;

    /** 从调度器中删除指定的触发器 */
    boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException;

    /** 从调度器中删除指定的触发器集 */
    boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException;



}
