package com.txl.equipment.trigger;

import com.txl.equipment.exception.LinkageIllegalArgumentException;
import com.txl.equipment.job.JobDataMap;
import com.txl.equipment.job.JobDetail;
import com.txl.equipment.job.JobKey;
import com.txl.equipment.key.Key;
import com.txl.equipment.schedule.ScheduleBuilder;
import com.txl.equipment.schedule.SimpleScheduleBuilder;

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
    /** 调度器构建者 */
    protected ScheduleBuilder<?> scheduleBuilder = null;

    /** 构建方法私有化 */
    private TriggerBuilder() {
    }

    /** 构建触发器的构造者 */
    public static TriggerBuilder<Trigger> newTrigger() {
        return new TriggerBuilder<Trigger>();
    }

    @SuppressWarnings("unchecked")
    public T build(){
        if(scheduleBuilder == null){
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        }
        MutableTrigger trig = scheduleBuilder.build();

        trig.setCalendarName(calendarName);
        trig.setDescription(description);
        trig.setStartTime(startTime);
        trig.setEndTime(endTime);
        if(key == null)
            key = new TriggerKey(Key.createUniqueName(null), null);
        trig.setKey(key);
        if(jobKey != null)
            trig.setJobKey(jobKey);
        trig.setPriority(priority);

        if(!jobDataMap.isEmpty())
            trig.setJobDataMap(jobDataMap);

        return (T) trig;
    }

    /** 添加唯一标识 */
    public TriggerBuilder<T> withIdentity(String name) {
        key = new TriggerKey(name, null);
        return this;
    }

    /** 添加唯一标识 */
    public TriggerBuilder<T> withIdentity(String name, String group) {
        key = new TriggerKey(name, group);
        return this;
    }

    /** 添加唯一标识 */
    public TriggerBuilder<T> withIdentity(TriggerKey triggerKey) {
        this.key = triggerKey;
        return this;
    }

    /** 添加触发器描述 */
    public TriggerBuilder<T> withDescription(String triggerDescription) {
        this.description = triggerDescription;
        return this;
    }

    /** 设置优先级 */
    public TriggerBuilder<T> withPriority(int triggerPriority) {
        this.priority = triggerPriority;
        return this;
    }

    /** 触发器日历调整 */
    public TriggerBuilder<T> modifiedByCalendar(String calName) {
        this.calendarName = calName;
        return this;
    }

    /** 触发器开始时间 */
    public TriggerBuilder<T> startAt(Date triggerStartTime) {
        this.startTime = triggerStartTime;
        return this;
    }

    /** 触发器从现在作为开始时间 */
    public TriggerBuilder<T> startNow() {
        this.startTime = new Date();
        return this;
    }

    /** 触发器结束时间 */
    public TriggerBuilder<T> endAt(Date triggerEndTime) {
        this.endTime = triggerEndTime;
        return this;
    }

    /** 添加调度器 */
    @SuppressWarnings("unchecked")
    public <SBT extends T> TriggerBuilder<SBT> withSchedule(ScheduleBuilder<SBT> schedBuilder) {
        this.scheduleBuilder = schedBuilder;
        return (TriggerBuilder<SBT>) this;
    }

    /** 触发器添加作业任务 */
    public TriggerBuilder<T> forJob(JobKey keyOfJobToFire) {
        this.jobKey = keyOfJobToFire;
        return this;
    }

    /** 触发器添加作业任务 */
    public TriggerBuilder<T> forJob(String jobName) {
        this.jobKey = new JobKey(jobName, null);
        return this;
    }

    /** 触发器添加作业任务 */
    public TriggerBuilder<T> forJob(String jobName, String jobGroup) {
        this.jobKey = new JobKey(jobName, jobGroup);
        return this;
    }

    /** 触发器添加作业任务 */
    public TriggerBuilder<T> forJob(JobDetail jobDetail) {
        JobKey k = jobDetail.getKey();
        if(k.getName() == null)
            throw new LinkageIllegalArgumentException(
                    "The given job has not yet had a name assigned to it.");
        this.jobKey = k;
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(String dataKey, String value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(String dataKey, Integer value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(String dataKey, Long value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(String dataKey, Float value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(String dataKey, Double value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(String dataKey, Boolean value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 将参数据添加到触发器中 */
    public TriggerBuilder<T> usingJobData(JobDataMap newJobDataMap) {
        for(String dataKey: jobDataMap.keySet()) {
            newJobDataMap.put(dataKey, jobDataMap.get(dataKey));
        }
        jobDataMap = newJobDataMap;
        return this;
    }
}
