package com.txl.equipment.trigger;

import com.txl.equipment.job.JobDataMap;
import com.txl.equipment.job.JobKey;

import java.util.Date;

/**
 * Created by TangXiangLin on 2023-02-13 13:41
 * 触发器的构造者
 */
public class TriggerBuilder<T extends Trigger> {
    /** 触发器唯一标识 */
    protected TriggerKey key;
    /** 触发器描述 */
    protected String description;
    /** 触发器开始触发的时间 */
    protected Date startTime = new Date();
    /** 触发器结束触发的时间 */
    protected Date endTime;
    /** 触发器的优先级默认值: 5 */
    protected int priority = Trigger.DEFAULT_PRIORITY;
    /** 触发器关联的日历名称 */
    protected String calendarName;
    /** 触发器关联任务的唯一详述标识  */
    protected JobKey jobKey;
    protected JobDataMap jobDataMap = new JobDataMap();

    /** 构建方法私有化 */
    private TriggerBuilder() {
    }

    /** 构建触发器的构造者 */
    public static TriggerBuilder<Trigger> buildTrigger() {
        return new TriggerBuilder<Trigger>();
    }



}
