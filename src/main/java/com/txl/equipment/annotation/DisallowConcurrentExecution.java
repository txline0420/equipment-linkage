package com.txl.equipment.annotation;

import java.lang.annotation.*;

/**
 * Created by TangXiangLin on 2023-02-21 17:25
 * 不允许执行并发
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DisallowConcurrentExecution {
}
