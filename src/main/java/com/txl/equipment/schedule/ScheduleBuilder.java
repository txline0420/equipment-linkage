package com.txl.equipment.schedule;

import com.txl.equipment.trigger.MutableTrigger;
import com.txl.equipment.trigger.Trigger;

/**
 * Created by TangXiangLin on 2023-02-13 13:59
 * 抽象的调度器构造者
 */
public abstract class ScheduleBuilder<T extends Trigger> {

    /** 抽象的多变调度器构造者 */
    public abstract MutableTrigger build();

}
