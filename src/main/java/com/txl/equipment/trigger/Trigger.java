package com.txl.equipment.trigger;

import com.txl.equipment.job.JobDataMap;
import com.txl.equipment.job.JobKey;
import com.txl.equipment.schedule.ScheduleBuilder;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by TangXiangLin on 2023-02-13 10:39
 * 触发器
 */
public interface Trigger extends Serializable, Cloneable, Comparable<Trigger>{

    long serialVersionUID = -3904243490805975570L;

    /** 触发器状态 */
    enum TriggerState { NONE, NORMAL, PAUSED, COMPLETE, ERROR, BLOCKED }

    /** 触发器已完成的执行指令 */
    enum CompletedExecutionInstruction { NOOP, RE_EXECUTE_JOB, SET_TRIGGER_COMPLETE, DELETE_TRIGGER,
        SET_ALL_JOB_TRIGGERS_COMPLETE, SET_TRIGGER_ERROR, SET_ALL_JOB_TRIGGERS_ERROR }

    /** 触发器适配策略 */
    int MISFIRE_INSTRUCTION_SMART_POLICY = 0;
    int MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY = -1;

    /** 触发器的优先级默认值 */
    int DEFAULT_PRIORITY = 5;

    /** 触发器的唯一标识 */
    TriggerKey getKey();

    /** 触发器关联任务的唯一详述标识 */
    JobKey getJobKey();

    /** 触发器的描述 */
    String getDescription();

    /** 触发器关联的日历名称 */
    String getCalendarName();

    /** 触发器关联的任务数据字符标识map集 */
    JobDataMap getJobDataMap();

    /** 获取触发器的优先级, 默认值: 5 */
    int getPriority();

    /** 触发器是否再次触发 */
    boolean mayFireAgain();

    /** 触发器触发的开始时间 */
    Date getStartTime();

    /** 触发器结束的时间 */
    Date getEndTime();

    /** 触发器下一次的触发时间 */
    Date getNextFireTime();

    /** 触发器上一次的触发时间 */
    Date getPreviousFireTime();

    /** 触发器跟离下一次触发的时间 */
    Date getFireTimeAfter(Date afterTime);

    /** 触发器跟离最后一次触发的时间 */
    Date getFinalFireTime();

    /** 触发器适配策略值 */
    int getMisfireInstruction();

    /** 触发器构建 */
    TriggerBuilder<? extends Trigger> getTriggerBuilder();

    /** 调度器构建 */
    ScheduleBuilder<? extends Trigger> getScheduleBuilder();

    /** 触发器的同等性 */
    boolean equals(Object other);

    /** 根据按键的自然(即字母)顺序比较触发器 */
    int compareTo(Trigger other);

    /** 触发器的比较器,用于比较下一次的触发*/
    class TriggerTimeComparator implements Comparator<Trigger>, Serializable {
        private static final long serialVersionUID = -3904243490805975570L;

        public static int compare(Date nextFireTime1, int priority1, TriggerKey key1, Date nextFireTime2, int priority2, TriggerKey key2) {
            if (nextFireTime1 != null || nextFireTime2 != null) {
                if (nextFireTime1 == null) {
                    return 1;
                }

                if (nextFireTime2 == null) {
                    return -1;
                }

                if(nextFireTime1.before(nextFireTime2)) {
                    return -1;
                }

                if(nextFireTime1.after(nextFireTime2)) {
                    return 1;
                }
            }

            int comp = priority2 - priority1;
            if (comp != 0) {
                return comp;
            }

            return key1.compareTo(key2);
        }

        public int compare(Trigger t1, Trigger t2) {
            return compare(t1.getNextFireTime(), t1.getPriority(), t1.getKey(), t2.getNextFireTime(), t2.getPriority(), t2.getKey());
        }
    }
}
