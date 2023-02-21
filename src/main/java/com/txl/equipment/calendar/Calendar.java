package com.txl.equipment.calendar;

import java.io.Serializable;

/**
 * Created by TangXiangLin on 2023-02-21 14:38
 * 日历接口
 * 1. 定义触发器可能（不会）触发的时间间隔。
 * 2. 日历不定义实际的触发时间，而是用于限制触发器在必要时按正常的计划触发。
 * 3. 大多数日历默认包含所有时间，并允许用户指定要排除的时间。
 */
public interface Calendar extends Serializable,Cloneable {

    int MONTH = 0;

    /** 设置日历 */
    void setBaseCalendar(Calendar baseCalendar);

    /** 获取日历 */
    Calendar getBaseCalendar();

    /** 确定日历是否”包含“给定时间（以毫秒为单位） */
    boolean isTimeIncluded(long timeStamp);

    /** 确定给定时间之后日历"包含"的下一个时间（以毫秒为单位） */
    long getNextIncludedTime(long timeStamp);

    /** 返回其创建者（如果有）给日历实例的描述,如果未设置描述，则为null。 */
    String getDescription();

    /** 设置日历的描述 */
    void setDescription(String description);

    Object clone();
}
