package com.txl.equipment.trigger;

/**
 * Created by TangXiangLin on 2023-02-20 15:01
 * 简单的触发器
 * 1. 基于固定时间点，以及可以重复间隔触发的触发器
 */
public interface SimpleTrigger extends Trigger{

    public static final long serialVersionUID = -3735980074222850397L;

    /** 当触发器发生误触发时，调试器立即启动调度 */
    public static final int MISFIRE_INSTRUCTION_FIRE_NOW = 1;

    /** 若现在时间在触发器的触发在开始时间 - 结束时间之间，则立即启动调度，并且重复计数保持原样 */
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT = 2;

    /** 若现在时间在触发器的触发在开始时间 - 结束时间之间，则立即启动调度，并且重复计数设置为未错过任何触发时的重复计数 */
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT = 3;


    /** 当触发器发生误触发时，调试器安排到下一个时间点启动调度，并且重复计数设置为未错过任何触发时的重复计数 */
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT = 4;

    /** 当触发器发生误触发时，调试器立重新调度到现在之后的下一个计划时间,并且重复计数保持不变 */
    public static final int MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT = 5;

    /** 触发器永久触发 */
    public static final int REPEAT_INDEFINITELY = -1;

    /** 获取重复触发的次数 */
    int getRepeatCount();

    /** 获取到下次重复触发的时间(毫秒) */
    long getRepeatInterval();

    /** 获取已触发的次数 */
    int getTimesTriggered();

    /** 触发器的构造者 */
    TriggerBuilder<? extends Trigger> getTriggerBuilder();
}
