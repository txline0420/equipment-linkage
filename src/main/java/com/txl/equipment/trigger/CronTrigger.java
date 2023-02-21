package com.txl.equipment.trigger;

import java.util.TimeZone;

/**
 * Created by TangXiangLin on 2023-02-20 15:54
 * Cron触发器
 */
public interface CronTrigger extends Trigger {

    public static final long serialVersionUID = -8644953146451592766L;

    /** 当触发器发生误触发时，调试器立即启动调度 */
    public static final int MISFIRE_INSTRUCTION_FIRE_ONCE_NOW = 1;

    /** 当触发器发生误触发时，调试器将其下次启动时间更新为当前时间之后的计划中的下一个时间 */
    public static final int MISFIRE_INSTRUCTION_DO_NOTHING = 2;

    /** 获取Cron表达式　*/
    String getCronExpression();

    /** 获取时区 */
    TimeZone getTimeZone();

    /** 获取表达式的摘要 */
    String getExpressionSummary();

    /** 获取触发器的构造者 */
    TriggerBuilder<CronTrigger> getTriggerBuilder();

}
