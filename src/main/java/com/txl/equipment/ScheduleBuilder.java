package com.txl.equipment;

/**
 * Created by TangXiangLin on 2023-02-13 13:59
 */
public abstract class ScheduleBuilder<T extends Trigger> {

    protected abstract MutableTrigger build();

}
