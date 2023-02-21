package com.txl.equipment.job;

import java.io.Serializable;

/**
 * Created by TangXiangLin on 2023-02-13 17:13
 * 作业任务详细描述
 */
public interface JobDetail extends Serializable,Cloneable {

    /** 获取作业任务唯一标识 */
    JobKey getKey();

    /** 获取作业任务描述 */
    String getDescription();

    /** 获取作业任务的实例 */
    Class<? extends Job> getJobClass();

    /** 获取作业任务参数 */
    JobDataMap getJobDataMap();

    /** 没有触发器指向的作业任务，是否处理孤立状态 */
    boolean isDurable();

    /** 执行后是否保持数据 */
    boolean isPersistJobDataAfterExecution();

    /** 是否允许并发执行 */
    boolean isConcurrentExectionDisallowed();

    /** 是否恢复请求 */
    boolean requestsRecovery();

    Object clone();

    JobBuilder getJobBuilder();
}
