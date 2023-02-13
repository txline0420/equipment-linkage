package com.txl.equipment.trigger;

import com.txl.equipment.exception.JobExecutionException;
import com.txl.equipment.job.JobExecutionContext;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by TangXiangLin on 2023-02-13 14:43
 * 可使用的触发器
 */
public interface OperableTrigger extends MutableTrigger {

    /** 下一次的触发器的更新 */
    void triggered(Calendar calendar);

    /** 调度器添加到触发器后，计算首次触发的时间 */
    Date computeFirstFireTime(Calendar calendar);

    CompletedExecutionInstruction executionComplete(JobExecutionContext context, JobExecutionException result);
}
