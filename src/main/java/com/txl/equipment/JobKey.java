package com.txl.equipment;

/**
 * Created by TangXiangLin on 2023-02-13 11:14
 * 触发器的唯一任务详述标识
 * 1. 唯一标识由名称 + 组, 并且同一组中的名称是唯一的。
 * 2. 未指定组时，按默认的组名称，即：DEFAULT
 */
public final class JobKey extends Key<JobKey> {

    private static final long serialVersionUID = -6073883950062574010L;

    public JobKey(String name) {
        super(name, null);
    }

    public JobKey(String name, String group) {
        super(name, group);
    }

    public static JobKey jobKey(String name) {
        return new JobKey(name, null);
    }

    public static JobKey jobKey(String name, String group) {
        return new JobKey(name, group);
    }
}
