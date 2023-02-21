package com.txl.equipment.schedule;

import com.txl.equipment.date.DateBuilder;
import com.txl.equipment.exception.LinkageIllegalArgumentException;
import com.txl.equipment.trigger.MutableTrigger;
import com.txl.equipment.trigger.SimpleTrigger;
import com.txl.equipment.trigger.SimpleTriggerImpl;
import com.txl.equipment.trigger.Trigger;

/**
 * Created by TangXiangLin on 2023-02-20 14:43
 * 简单的调度器构造者
 * 1. 构建出一个基于固定间隔触发器进行调度的调度器
 */
public class SimpleScheduleBuilder extends ScheduleBuilder<SimpleTrigger>{

    /** 节隔时间 */
    private long interval = 0;
    /** 重试次数 */
    private int repeatCount = 0;
    /** 触发器适配策略 */
    private int misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY;

    // 1. Constructor
    protected SimpleScheduleBuilder() {
    }

    // 2. Builder
    public static SimpleScheduleBuilder simpleSchedule() {
        return new SimpleScheduleBuilder();
    }

    /** 永久重复 */
    public SimpleScheduleBuilder repeatForever() {
        this.repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
        return this;
    }

    /** 重复次数 */
    public SimpleScheduleBuilder withRepeatCount(int triggerRepeatCount) {
        this.repeatCount = triggerRepeatCount;
        return this;
    }

    /** 小时重复 */
    public static SimpleScheduleBuilder repeatHourlyForever() {
        return simpleSchedule()
                .withIntervalInHours(1)
                .repeatForever();
    }

    /** 小时重复 */
    public static SimpleScheduleBuilder repeatHourlyForever(int hours) {
        return simpleSchedule()
                .withIntervalInHours(hours)
                .repeatForever();
    }

    /** 分钟重复 */
    public static SimpleScheduleBuilder repeatMinutelyForever() {
        return simpleSchedule()
                .withIntervalInMinutes(1)
                .repeatForever();
    }

    /** 间隔分钟 */
    public SimpleScheduleBuilder withIntervalInMinutes(int intervalInMinutes) {
        this.interval = intervalInMinutes * DateBuilder.MILLISECONDS_IN_MINUTE;
        return this;
    }

    public static SimpleScheduleBuilder repeatMinutelyForTotalCount(int count) {
        if(count < 1)
            throw new LinkageIllegalArgumentException(
                    "Total count of firings must be at least one! Given count: " + count);
        return simpleSchedule()
                .withIntervalInMinutes(1)
                .withRepeatCount(count - 1);
    }

    /** 间隔分钟 */
    public static SimpleScheduleBuilder repeatMinutelyForTotalCount(int count, int minutes) {
        if(count < 1)
            throw new LinkageIllegalArgumentException(
                    "Total count of firings must be at least one! Given count: " + count);
        return simpleSchedule()
                .withIntervalInMinutes(minutes)
                .withRepeatCount(count - 1);
    }

    /** 间隔秒 */
    public static SimpleScheduleBuilder repeatSecondlyForTotalCount(int count) {
        if(count < 1)
            throw new LinkageIllegalArgumentException(
                    "Total count of firings must be at least one! Given count: " + count);
        return simpleSchedule()
                .withIntervalInSeconds(1)
                .withRepeatCount(count - 1);
    }

    /** 间隔秒 */
    public static SimpleScheduleBuilder repeatSecondlyForTotalCount(int count, int seconds) {
        if(count < 1)
            throw new LinkageIllegalArgumentException(
                    "Total count of firings must be at least one! Given count: " + count);
        return simpleSchedule()
                .withIntervalInSeconds(seconds)
                .withRepeatCount(count - 1);
    }

    /** 间隔时 */
    public static SimpleScheduleBuilder repeatHourlyForTotalCount(int count) {
        if(count < 1)
            throw new LinkageIllegalArgumentException(
                    "Total count of firings must be at least one! Given count: " + count);
        return simpleSchedule()
                .withIntervalInHours(1)
                .withRepeatCount(count - 1);
    }

    /** 间隔时 */
    public static SimpleScheduleBuilder repeatHourlyForTotalCount(int count, int hours) {
        if(count < 1)
            throw new LinkageIllegalArgumentException(
                    "Total count of firings must be at least one! Given count: " + count);
        return simpleSchedule()
                .withIntervalInHours(hours)
                .withRepeatCount(count - 1);
    }

    /** 间隔毫秒 */
    public SimpleScheduleBuilder withIntervalInMilliseconds(long intervalInMillis) {
        this.interval = intervalInMillis;
        return this;
    }

    /** 间隔秒 */
    public SimpleScheduleBuilder withIntervalInSeconds(int intervalInSeconds) {
        this.interval = intervalInSeconds * 1000L;
        return this;
    }

    /** 小时 */
    public SimpleScheduleBuilder withIntervalInHours(int intervalInHours) {
        this.interval = intervalInHours * DateBuilder.MILLISECONDS_IN_HOUR;
        return this;
    }

    /** 触发器适配策略 */
    public SimpleScheduleBuilder withMisfireHandlingInstructionIgnoreMisfires() {
        misfireInstruction = Trigger.MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY;
        return this;
    }

    /** 触发器适配策略 */
    public SimpleScheduleBuilder withMisfireHandlingInstructionFireNow() {
        misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW;
        return this;
    }

    /** 触发器适配策略 */
    public SimpleScheduleBuilder withMisfireHandlingInstructionNextWithExistingCount() {
        misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT;
        return this;
    }

    /** 触发器适配策略 */
    public SimpleScheduleBuilder withMisfireHandlingInstructionNextWithRemainingCount() {
        misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT;
        return this;
    }

    /** 触发器适配策略 */
    public SimpleScheduleBuilder withMisfireHandlingInstructionNowWithExistingCount() {
        misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT;
        return this;
    }

    /** 触发器适配策略 */
    public SimpleScheduleBuilder withMisfireHandlingInstructionNowWithRemainingCount() {
        misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT;
        return this;
    }

    @Override
    public MutableTrigger build() {
        SimpleTriggerImpl st = new SimpleTriggerImpl();
        st.setRepeatInterval(interval);
        st.setRepeatCount(repeatCount);
        st.setMisfireInstruction(misfireInstruction);
        return st;
    }
}
