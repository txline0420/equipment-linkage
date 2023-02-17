package com.txl.equipment.trigger;

import com.txl.equipment.job.JobExecutionContext;

/**
 * Created by TangXiangLin on 2023-02-15 11:55
 * 触发器监听器
 * 1. 当触发器触发时，希望得到通知的类来实现的接口
 */
public interface TriggerListener {

    /** 获取监听器的名称 */
    String gerName();

    /** 当触发器已激发，并且其关联的JobDetail即将执行时，由调度器调用 */
    void triggerFired(Trigger trigger, JobExecutionContext context);

    /** 当触发器已激发，并且其关联的JobDetail即将执行时，由调度器调用
     * 如果实现否决执行（通过返回true），则不会调用作业的execute方法
     */
    boolean vetoJobExecution(Trigger trigger, JobExecutionContext context);

    /** 当触发器失灵 */
    void triggerMisfired(Trigger trigger);

    /** 当触发器触发时，由调度器调用，与其关联JobDetail已被执行，并且的Trigger（xx）方法已被调用 */
    void triggerComplete(Trigger trigger, JobExecutionContext context,
                         Trigger.CompletedExecutionInstruction triggerInstructionCode);

}
