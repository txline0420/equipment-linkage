package com.txl.equipment.schedule;

import com.txl.equipment.trigger.MutableTrigger;
import com.txl.equipment.trigger.Trigger;

/**
 * Created by TangXiangLin on 2023-02-13 13:59
 */
public abstract class ScheduleBuilder<T extends Trigger> {

    protected abstract MutableTrigger build();

}
