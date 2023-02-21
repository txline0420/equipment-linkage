package com.txl.equipment.trigger;

import com.txl.equipment.calendar.Calendar;
import com.txl.equipment.exception.JobExecutionException;
import com.txl.equipment.exception.LinkageIllegalArgumentException;
import com.txl.equipment.exception.SchedulerException;
import com.txl.equipment.job.JobDataMap;
import com.txl.equipment.job.JobExecutionContext;
import com.txl.equipment.job.JobKey;
import com.txl.equipment.schedule.ScheduleBuilder;
import com.txl.equipment.schedule.Scheduler;

import java.util.Date;


/**
 * Created by TangXiangLin on 2023-02-20 16:08
 * 抽象的触发器
 * 1. 每一个触发器都由名称和组名作为唯一标识.
 * 2. 多个触发器可以同时指向一个作业任务，单个触发器只能指抽一个作业任务.
 * 3. 触发器可以使用JobDataMap向作业任务传递相关的参数。
 */
public abstract class AbstractTrigger<T extends Trigger>  implements OperableTrigger{

    private static final long serialVersionUID = -3904243490805975570L;

    /** 触发器名称 */
    protected String name;

    /** 触发器组名，有默认值  */
    private String group = Scheduler.DEFAULT_GROUP;

    /** 作业任务名称 */
    private String jobName;

    /** 作业任务组名，有默认值 */
    private String jobGroup = Scheduler.DEFAULT_GROUP;

    /** 触发器描述 */
    private String description;

    /** 触发器向作业任务传递的参数 */
    private JobDataMap jobDataMap;

    /** 仍在此处以实现串行化向后兼容性 */
    @SuppressWarnings("unused")
    private boolean volatility = false;

    /** 获取日历名称 */
    private String calendarName = null;

    /** 获取触发器实例id */
    private String fireInstanceId = null;

    /** 触发器适配策略 */
    private int misfireInstruction = MISFIRE_INSTRUCTION_SMART_POLICY;

    /** 触发器的优先级默认值 */
    private int priority = DEFAULT_PRIORITY;

    /** 触发器的唯一标识 */
    private transient TriggerKey key = null;

    // 1. Constructors
    public AbstractTrigger() {
    }

    public AbstractTrigger(String name) {
        setName(name);
        setGroup(null);
    }

    public AbstractTrigger(String name, String group) {
        setName(name);
        setGroup(group);
    }

    public AbstractTrigger(String name, String group, String jobName, String jobGroup) {
       setName(name);
       setGroup(group);
       setJobName(jobName);
       setJobGroup(jobGroup);
    }


    // 2. Setter
    public void setName(String name) {
       if(name == null || name.trim().length() == 0){
           throw new LinkageIllegalArgumentException(
                   "Trigger name cannot be null or empty.");
       }
       this.name = name;
       this.key = null;
    }

    public void setGroup(String group) {
        if (group != null && group.trim().length() == 0) {
            throw new LinkageIllegalArgumentException(
                    "Group name cannot be an empty string.");
        }

        if(group == null) {
            group = Scheduler.DEFAULT_GROUP;
        }

        this.group = group;
        this.key = null;
    }

    public void setJobName(String jobName) {
        if (jobName == null || jobName.trim().length() == 0) {
            throw new LinkageIllegalArgumentException(
                    "Job name cannot be null or empty.");
        }

        this.jobName = jobName;
    }

    public void setJobGroup(String jobGroup) {
        if (jobGroup != null && jobGroup.trim().length() == 0) {
            throw new LinkageIllegalArgumentException(
                    "Group name cannot be null or empty.");
        }

        if(jobGroup == null) {
            jobGroup = Scheduler.DEFAULT_GROUP;
        }

        this.jobGroup = jobGroup;
    }

    public void setVolatility(boolean volatility) {
        this.volatility = volatility;
    }

    public void setFireInstanceId(String fireInstanceId) {
        this.fireInstanceId = fireInstanceId;
    }

    public void setKey(TriggerKey key) {
        setName(key.getName());
        setGroup(key.getGroup());
        this.key = key;
    }

    public void setJobKey(JobKey key) {
        setJobName(key.getName());
        setJobGroup(key.getGroup());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public void setMisfireInstruction(int misfireInstruction) {
        if (!validateMisfireInstruction(misfireInstruction)) {
            throw new LinkageIllegalArgumentException(
                    "The misfire instruction code is invalid for this type of trigger.");
        }
        this.misfireInstruction = misfireInstruction;
    }


    // 3. Getter
    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public boolean isVolatility() {
        return volatility;
    }

    public String getFireInstanceId() {
        return fireInstanceId;
    }

    /** 指令执行已完成 */
    public CompletedExecutionInstruction executionComplete(JobExecutionContext context, JobExecutionException result) {

        /* 再次触发 */
        if (result != null && result.refireImmediately()) {
            return CompletedExecutionInstruction.RE_EXECUTE_JOB;
        }

        /* 再次调度 */
        if (result != null && result.unscheduleFiringTrigger()) {
            return CompletedExecutionInstruction.SET_TRIGGER_COMPLETE;
        }

        /* 所有触发器触发并调度完毕 */
        if (result != null && result.unscheduleAllTriggers()) {
            return CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE;
        }

        /* 再次触发 */
        if (!mayFireAgain()) {
            return CompletedExecutionInstruction.DELETE_TRIGGER;
        }

        return CompletedExecutionInstruction.NOOP;
    }



    public TriggerKey getKey() {
        if(key == null) {
            if(getName() == null)
                return null;
            key = new TriggerKey(getName(), getGroup());
        }

        return key;
    }

    public JobKey getJobKey() {
        if(getJobName() == null)
            return null;

        return new JobKey(getJobName(), getJobGroup());
    }

    public String getFullJobName() {
        return jobGroup + "." + jobName;
    }

    public String getDescription() {
        return description;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public JobDataMap getJobDataMap() {
        if (jobDataMap == null) {
            jobDataMap = new JobDataMap();
        }
        return jobDataMap;
    }

    public int getPriority() {
        return priority;
    }


    public int getMisfireInstruction() {
        return misfireInstruction;
    }

    public String getFullName() {
        return group + "." + name;
    }

    // 4. abstract
    /** 当调度器决定"触发"触发器（执行关联的作业）时调用 */
    public abstract void triggered(Calendar calendar);

    /** 由调度器在触发器首次添加到调度器时调用，以便使触发器基于任何相关日历计算其第一次激发时间 */
    public abstract Date computeFirstFireTime(Calendar calendar);

    /** 是否再次触发 */
    public abstract boolean mayFireAgain();

    /** 获取触发器生命周期-开始时间 */
    public abstract Date getStartTime();

    /** 设置触发器生命周期-开始时间 */
    public abstract void setStartTime(Date startTime);

    /** 获取触发器生命周期-结束时间 */
    public abstract Date getEndTime();

    /** 设置触发器生命周期-结束时间 */
    public abstract void setEndTime(Date endTime);

    /** 返回触发器计划触发的下一个时间。
     * 1. 如果触发器不会再次触发，则返回null.
     * 2. 如果为触发器计算到下一次触发的时间已经到达，但调度器尚未能够触发触发器（这可能是由于缺少资源，例如线程），则返回的时间可能是过去的时间.
     */
    public abstract Date getNextFireTime();

    /** 返回触发器上次触发的时间，如果触发器尚未触发，则返回null。 */
    public abstract Date getPreviousFireTime();

    /** 返回给定时间之后触发器将触发的下一个时间。如果触发器在给定时间后不会触发，则返回null。 */
    public abstract Date getFireTimeAfter(Date afterTime);

    /** 返回触发器将触发的最后时间，如果触发器无限期重复，则返回null。 */
    public abstract Date getFinalFireTime();

    /** 校验触发指令 */
    protected abstract boolean validateMisfireInstruction(int candidateMisfireInstruction);

    /** 更新触发器的状态 */
    public abstract void updateAfterMisfire(Calendar cal);

    /** 根据日历关联触发器，并更新触发器的更新状态（应更新状态，以便在给定日历的新设置的情况下，下次触发时间是合适的）*/
    public abstract void updateWithNewCalendar(Calendar cal, long misfireThreshold);

    /** 获取调度器的构造者 */
    public abstract ScheduleBuilder<? extends Trigger> getScheduleBuilder();


    // 5。General method
    /** 获取触发器的构造者 */
    public TriggerBuilder<? extends Trigger> getTriggerBuilder() {
        return TriggerBuilder.newTrigger()
                .forJob(getJobKey())
                .modifiedByCalendar(getCalendarName())
                .usingJobData(getJobDataMap())
                .withDescription(getDescription())
                .endAt(getEndTime())
                .withIdentity(getKey())
                .withPriority(getPriority())
                .startAt(getStartTime())
                .withSchedule(getScheduleBuilder());
    }

    /** 触发器指令校验 */
    public void validate() throws SchedulerException {
        if (name == null) {
            throw new SchedulerException("Trigger's name cannot be null");
        }

        if (group == null) {
            throw new SchedulerException("Trigger's group cannot be null");
        }

        if (jobName == null) {
            throw new SchedulerException(
                    "Trigger's related Job's name cannot be null");
        }

        if (jobGroup == null) {
            throw new SchedulerException(
                    "Trigger's related Job's group cannot be null");
        }
    }


    // 6. Override
    @Override
    public String toString() {
        return "Trigger '" + getFullName() + "':  triggerClass: '"
                + getClass().getName() + " calendar: '" + getCalendarName()
                + "' misfireInstruction: " + getMisfireInstruction()
                + " nextFireTime: " + getNextFireTime();
    }

    public int compareTo(Trigger other) {

        if(other.getKey() == null && getKey() == null)
            return 0;
        if(other.getKey() == null)
            return -1;
        if(getKey() == null)
            return 1;

        return getKey().compareTo(other.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Trigger))
            return false;

        Trigger other = (Trigger)o;

        return !(other.getKey() == null || getKey() == null) && getKey().equals(other.getKey());

    }

    @Override
    public int hashCode() {
        if(getKey() == null)
            return super.hashCode();

        return getKey().hashCode();
    }

    @Override
    public Object clone() {
        AbstractTrigger<?> copy;
        try {
            copy = (AbstractTrigger<?>) super.clone();

            // Shallow copy the jobDataMap.  Note that this means that if a user
            // modifies a value object in this map from the cloned Trigger
            // they will also be modifying this Trigger.
            if (jobDataMap != null) {
                copy.jobDataMap = (JobDataMap)jobDataMap.clone();
            }

        } catch (CloneNotSupportedException ex) {
            throw new IncompatibleClassChangeError("Not Cloneable.");
        }
        return copy;
    }

}
