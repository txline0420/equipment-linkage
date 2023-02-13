package com.txl.equipment.job;

import com.txl.equipment.schedule.Scheduler;

/**
 * Created by TangXiangLin on 2023-02-13 14:53
 * 包含环境信息的任务上下文
 */
public interface JobExecutionContext {

    Scheduler getScheduler();

}
