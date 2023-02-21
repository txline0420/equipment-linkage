package com.txl.equipment.job;

import com.txl.equipment.exception.JobExecutionException;

/**
 * Created by TangXiangLin on 2023-02-21 15:47
 * 作业任务
 */
public interface Job {
    /** 执行动作 */
    void execute(JobExecutionContext context) throws JobExecutionException;
}
