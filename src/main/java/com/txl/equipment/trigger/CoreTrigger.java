package com.txl.equipment.trigger;

/**
 * Created by TangXiangLin on 2023-02-20 15:50
 * 核心接口
 * 1. 保留内部接口以实现向后兼容性
 */
public interface CoreTrigger extends Trigger{

    public boolean hasAdditionalProperties();
}
