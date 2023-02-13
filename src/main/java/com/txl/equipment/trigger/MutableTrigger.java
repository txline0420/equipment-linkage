package com.txl.equipment.trigger;

import com.txl.equipment.job.JobDataMap;
import com.txl.equipment.job.JobKey;

import java.util.Date;

/**
 * Created by TangXiangLin on 2023-02-13 14:10
 * 可变触发器
 */
public interface MutableTrigger extends Trigger {

    /** 设置触发器的唯一标识 */
    void setKey(TriggerKey key);

    /** 设置触发器关联任务的唯一标识 */
    void setJobKey(JobKey key);

    /** 设置触发器的描述信息 */
    void setDescription(String description);

    /** 设置触发器关联的日历名称 */
    void setCalendarName(String calendarName);

    /** 设置触发器关联的任务数据字符标识map集 */
    void setJobDataMap(JobDataMap jobDataMap);

    /** 设置触发器的优先级 */
    void setPriority(int priority);

    /** 设置触发器触发的开始时间 */
    void setStartTime(Date startTime);

    /** 设置触发器结束的时间 */
    void setEndTime(Date endTime);

    /** 设置触发器适配策略值 */
    void setMisfireInstruction(int misfireInstruction);

    Object clone();
}
