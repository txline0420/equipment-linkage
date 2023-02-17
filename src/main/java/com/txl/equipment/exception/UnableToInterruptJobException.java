package com.txl.equipment.exception;

/**
 * Created by TangXiangLin on 2023-02-14 12:40
 * 无法中断作业任务异常
 */
public class UnableToInterruptJobException extends SchedulerException{
    private static final long serialVersionUID = -490863760696463776L;
    public UnableToInterruptJobException(String msg) {
        super(msg);
    }
    public UnableToInterruptJobException(Throwable cause) {
        super(cause);
    }
}
