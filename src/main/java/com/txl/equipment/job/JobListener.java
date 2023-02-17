package com.txl.equipment.job;

import com.txl.equipment.exception.JobExecutionException;

/**
 * Created by TangXiangLin on 2023-02-15 10:43
 * 作业任务监听器
 * 1. 当作业任务执行时，需要得到通知的类来实现的该接口
 */
public interface JobListener {

    /** 获取监听器的名称 */
    String gerName();

    /** 当作业任务，被触发器触发后，并即将被执行 */
    void jobToBeExecuted(JobExecutionContext context);

    /** 当作业任务，被触发器触发后，并否决执行 */
    void jobExecutionVetoed(JobExecutionContext context);

    /** 当作业任务执行完毕后，并且在触发器再次触发前执行 */
    void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException);

}
