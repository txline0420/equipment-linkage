package com.txl.equipment.trigger;

import com.txl.equipment.calendar.Calendar;
import com.txl.equipment.exception.LinkageIllegalArgumentException;
import com.txl.equipment.exception.SchedulerException;
import com.txl.equipment.schedule.ScheduleBuilder;
import com.txl.equipment.schedule.SimpleScheduleBuilder;


import java.util.Date;

/**
 * Created by TangXiangLin on 2023-02-21 13:59
 * 简单的触发器
 * 1. 在指定的时间点触发作业任务
 * 2. 可以设置重复的次数
 */
public class SimpleTriggerImpl extends AbstractTrigger<SimpleTrigger> implements SimpleTrigger,CoreTrigger{
    protected static final long serialVersionUID = -3735980074222850397L;
    /** 向天再借100年　*/
    protected static final int YEAR_TO_GIVEUP_SCHEDULING_AT = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) + 100;
    /** 触发器的生命周期 - 开始时间 */
    protected Date startTime = null;
    /** 触发器的生命周期 - 结束时间 */
    protected Date endTime = null;
    /** 触发器下一次触发的时间 */
    protected Date nextFireTime = null;
    /** 触发器上一次触发的时间 */
    protected Date previousFireTime = null;
    /** 重复次数 */
    protected int repeatCount = 0;
    /** 重复间隔 */
    protected long repeatInterval = 0;
    /** 已触发的次数 */
    protected int timesTriggered = 0;
    /** 是否完成触发 */
    protected boolean complete = false;

    // 1. Constructor
    public SimpleTriggerImpl() {
    }

    // 2. Implements
    /** 获取触发器的生命周期 - 开始时间 */
    public Date getStartTime() {
        return startTime;
    }

    /** 设置触发器的生命周期 - 开始时间 */
    public void setStartTime(Date startTime) {
        if (startTime == null) {
            throw new LinkageIllegalArgumentException("Start time cannot be null");
        }
        Date eTime = getEndTime();
        if (eTime != null && startTime != null && eTime.before(startTime)) {
            throw new LinkageIllegalArgumentException(
                    "End time cannot be before start time");
        }
        this.startTime = startTime;
    }

    /** 获取触发器的生命周期 - 结束时间 */
    public Date getEndTime() {
        return endTime;
    }

    /** 设置触发器的生命周期 - 结束时间 */
    public void setEndTime(Date endTime) {
        Date sTime = getStartTime();
        if (sTime != null && endTime != null && sTime.after(endTime)) {
            throw new LinkageIllegalArgumentException(
                    "End time cannot be before start time");
        }
        this.endTime = endTime;
    }

    /** 获取重复的次数 */
    public int getRepeatCount() {
        return repeatCount;
    }

    /** 设置重复的次数 */
    public void setRepeatCount(int repeatCount) {
        if (repeatCount < 0 && repeatCount != REPEAT_INDEFINITELY) {
            throw new LinkageIllegalArgumentException(
                    "Repeat count must be >= 0, use the "
                            + "constant REPEAT_INDEFINITELY for infinite.");
        }
        this.repeatCount = repeatCount;
    }

    /** 获取重复间隔 */
    public long getRepeatInterval() {
        return repeatInterval;
    }

    /** 设置重复间隔 */
    public void setRepeatInterval(long repeatInterval) {
        if (repeatInterval < 0) {
            throw new LinkageIllegalArgumentException(
                    "Repeat interval must be >= 0");
        }
        this.repeatInterval = repeatInterval;
    }

    /** 获取已触发的次数 */
    public int getTimesTriggered() {
        return timesTriggered;
    }

    /** 设置已触发的次数 */
    public void setTimesTriggered(int timesTriggered) {
        this.timesTriggered = timesTriggered;
    }

    /** 获取触发器下一次触发的时间 */
    public Date getNextFireTime() {
        return nextFireTime;
    }

    /** 设置触发器下一次触发的时间 */
    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    /** 获取触发器上一次触发的时间 */
    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    /** 设置触发器上一次触发的时间 */
    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    /** 返回给定时间之后触发器将激发的下一个时间。如果触发器在给定时间后不会触发，则返回null。 */
    public Date getFireTimeAfter(Date afterTime) {
        if (complete) {
            return null;
        }
        if ((timesTriggered > repeatCount) && (repeatCount != REPEAT_INDEFINITELY)) {
            return null;
        }
        if (afterTime == null) {
            afterTime = new Date();
        }
        if (repeatCount == 0 && afterTime.compareTo(getStartTime()) >= 0) {
            return null;
        }
        long startMillis = getStartTime().getTime();
        long afterMillis = afterTime.getTime();
        long endMillis = (getEndTime() == null) ? Long.MAX_VALUE : getEndTime().getTime();
        if (endMillis <= afterMillis) {
            return null;
        }
        if (afterMillis < startMillis) {
            return new Date(startMillis);
        }
        long numberOfTimesExecuted = ((afterMillis - startMillis) / repeatInterval) + 1;
        if ((numberOfTimesExecuted > repeatCount) &&
                (repeatCount != REPEAT_INDEFINITELY)) {
            return null;
        }
        Date time = new Date(startMillis + (numberOfTimesExecuted * repeatInterval));
        if (endMillis <= time.getTime()) {
            return null;
        }
        return time;
    }

    /** 返回给定时间之前触发器将激发的下一个时间。如果触发器在给定时间后不会触发，则返回null。 */
    public Date getFireTimeBefore(Date end) {
        if (end.getTime() < getStartTime().getTime()) {
            return null;
        }
        int numFires = computeNumTimesFiredBetween(getStartTime(), end);
        return new Date(getStartTime().getTime() + (numFires * repeatInterval));
    }

    /** 时间比较 */
    public int computeNumTimesFiredBetween(Date start, Date end) {
        if(repeatInterval < 1) {
            return 0;
        }
        long time = end.getTime() - start.getTime();
        return (int) (time / repeatInterval);
    }

    /** 返回触发器将激发的最后时间，如果repeatCount为REPEAT_INDEFINITELY，则返回null。 */
    public Date getFinalFireTime() {
        if (repeatCount == 0) {
            return startTime;
        }
        if (repeatCount == REPEAT_INDEFINITELY) {
            return (getEndTime() == null) ? null : getFireTimeBefore(getEndTime());
        }
        long lastTrigger = startTime.getTime() + (repeatCount * repeatInterval);
        if ((getEndTime() == null) || (lastTrigger < getEndTime().getTime())) {
            return new Date(lastTrigger);
        } else {
            return getFireTimeBefore(getEndTime());
        }
    }

    /** 验证触发器适配策略 */
    protected boolean validateMisfireInstruction(int misfireInstruction) {
        if (misfireInstruction < MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY) {
            return false;
        }
        return misfireInstruction <= MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT;
    }

    /** 调度器决定执行关联的作业时调用，并通知触发器的下一次触发（如果有）提供更新 */
    public void triggered(Calendar calendar) {
        timesTriggered++;
        previousFireTime = nextFireTime;
        nextFireTime = getFireTimeAfter(nextFireTime);

        while (nextFireTime != null && calendar != null
                && !calendar.isTimeIncluded(nextFireTime.getTime())) {

            nextFireTime = getFireTimeAfter(nextFireTime);

            if(nextFireTime == null)
                break;

            //avoid infinite loop
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(nextFireTime);
            if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
                nextFireTime = null;
            }
        }
    }

    /** 由调度程序在触发器首次添加到调度程序时调用，以便使触发器基于任何相关日历计算其第一次激发时间 */
    public Date computeFirstFireTime(Calendar calendar) {
        nextFireTime = getStartTime();
        while (nextFireTime != null && calendar != null
                && !calendar.isTimeIncluded(nextFireTime.getTime())) {
            nextFireTime = getFireTimeAfter(nextFireTime);
            if(nextFireTime == null) break;
            //avoid infinite loop
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(nextFireTime);
            if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
                return null;
            }
        }
        return nextFireTime;
    }

    /** 触发器是否再次触发 */
    public boolean mayFireAgain() {
        return (getNextFireTime() != null);
    }

    /** 复写校验 */
    public void validate() throws SchedulerException {
        super.validate();
        if (repeatCount != 0 && repeatInterval < 1) {
            throw new SchedulerException("Repeat Interval cannot be zero.");
        }
    }

    /** 扩展点 */
    public boolean hasAdditionalProperties() {
        return false;
    }

    /** 根据日历关联触发器，并更新触发器的更新状态（应更新状态，以便在给定日历的新设置的情况下，下次触发时间是合适的）*/
    public void updateWithNewCalendar(com.txl.equipment.calendar.Calendar calendar, long misfireThreshold) {
        nextFireTime = getFireTimeAfter(previousFireTime);
        if (nextFireTime == null || calendar == null) {
            return;
        }
        Date now = new Date();
        while (nextFireTime != null && !calendar.isTimeIncluded(nextFireTime.getTime())) {
            nextFireTime = getFireTimeAfter(nextFireTime);
            if(nextFireTime == null) break;
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(nextFireTime);
            if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
                nextFireTime = null;
            }
            if(nextFireTime != null && nextFireTime.before(now)) {
                long diff = now.getTime() - nextFireTime.getTime();
                if(diff >= misfireThreshold) {
                    nextFireTime = getFireTimeAfter(nextFireTime);
                }
            }
        }
    }

    /** 更新触发器的状态 */
    public void updateAfterMisfire(Calendar cal) {
        int instr = getMisfireInstruction();

        if(instr == Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY)
            return;

        if (instr == Trigger.MISFIRE_INSTRUCTION_SMART_POLICY) {
            if (getRepeatCount() == 0) {
                instr = MISFIRE_INSTRUCTION_FIRE_NOW;
            } else if (getRepeatCount() == REPEAT_INDEFINITELY) {
                instr = MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT;
            } else {
                // if (getRepeatCount() > 0)
                instr = MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT;
            }
        } else if (instr == MISFIRE_INSTRUCTION_FIRE_NOW && getRepeatCount() != 0) {
            instr = MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT;
        }

        if (instr == MISFIRE_INSTRUCTION_FIRE_NOW) {
            setNextFireTime(new Date());
        } else if (instr == MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT) {
            Date newFireTime = getFireTimeAfter(new Date());
            while (newFireTime != null && cal != null
                    && !cal.isTimeIncluded(newFireTime.getTime())) {
                newFireTime = getFireTimeAfter(newFireTime);

                if(newFireTime == null)
                    break;

                //avoid infinite loop
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(newFireTime);
                if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    newFireTime = null;
                }
            }
            setNextFireTime(newFireTime);
        } else if (instr == MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT) {
            Date newFireTime = getFireTimeAfter(new Date());
            while (newFireTime != null && cal != null
                    && !cal.isTimeIncluded(newFireTime.getTime())) {
                newFireTime = getFireTimeAfter(newFireTime);

                if(newFireTime == null)
                    break;

                //avoid infinite loop
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTime(newFireTime);
                if (c.get(java.util.Calendar.YEAR) > YEAR_TO_GIVEUP_SCHEDULING_AT) {
                    newFireTime = null;
                }
            }
            if (newFireTime != null) {
                int timesMissed = computeNumTimesFiredBetween(nextFireTime,
                        newFireTime);
                setTimesTriggered(getTimesTriggered() + timesMissed);
            }

            setNextFireTime(newFireTime);
        } else if (instr == MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT) {
            Date newFireTime = new Date();
            if (repeatCount != 0 && repeatCount != REPEAT_INDEFINITELY) {
                setRepeatCount(getRepeatCount() - getTimesTriggered());
                setTimesTriggered(0);
            }

            if (getEndTime() != null && getEndTime().before(newFireTime)) {
                setNextFireTime(null); // We are past the end time
            } else {
                setStartTime(newFireTime);
                setNextFireTime(newFireTime);
            }
        } else if (instr == MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT) {
            Date newFireTime = new Date();

            int timesMissed = computeNumTimesFiredBetween(nextFireTime,
                    newFireTime);

            if (repeatCount != 0 && repeatCount != REPEAT_INDEFINITELY) {
                int remainingCount = getRepeatCount()
                        - (getTimesTriggered() + timesMissed);
                if (remainingCount <= 0) {
                    remainingCount = 0;
                }
                setRepeatCount(remainingCount);
                setTimesTriggered(0);
            }

            if (getEndTime() != null && getEndTime().before(newFireTime)) {
                setNextFireTime(null); // We are past the end time
            } else {
                setStartTime(newFireTime);
                setNextFireTime(newFireTime);
            }
        }
    }


    /** 获取简单调度器 */
    public ScheduleBuilder<? extends Trigger> getScheduleBuilder() {
        SimpleScheduleBuilder sb = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(getRepeatInterval())
                .withRepeatCount(getRepeatCount());
        switch(getMisfireInstruction()) {
            case MISFIRE_INSTRUCTION_FIRE_NOW : sb.withMisfireHandlingInstructionFireNow();
                break;
            case MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT : sb.withMisfireHandlingInstructionNextWithExistingCount();
                break;
            case MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT : sb.withMisfireHandlingInstructionNextWithRemainingCount();
                break;
            case MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT : sb.withMisfireHandlingInstructionNowWithExistingCount();
                break;
            case MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT : sb.withMisfireHandlingInstructionNowWithRemainingCount();
                break;
        }
        return sb;
    }


}
