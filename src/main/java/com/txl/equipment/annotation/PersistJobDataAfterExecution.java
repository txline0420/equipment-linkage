package com.txl.equipment.annotation;

import java.lang.annotation.*;

/**
 * Created by TangXiangLin on 2023-02-21 17:21
 * 执行后保持作业任务数据
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PersistJobDataAfterExecution {
}
