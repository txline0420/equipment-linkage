package com.txl.equipment;

/**
 * Created by TangXiangLin on 2023-02-13 11:06
 * 触发器的唯一标识
 * 1. 唯一标识由名称 + 组, 并且同一组中的名称是唯一的。
 * 2. 未指定组时，按默认的组名称，即：DEFAULT
 */
public final class TriggerKey extends Key<TriggerKey> {

    private static final long serialVersionUID = 8070357886703449660L;

    public TriggerKey(String name) {
        super(name, null);
    }

    public TriggerKey(String name, String group) {
        super(name, group);
    }

    public static TriggerKey triggerKey(String name) {
        return new TriggerKey(name, null);
    }

    public static TriggerKey triggerKey(String name, String group) {
        return new TriggerKey(name, group);
    }
}
