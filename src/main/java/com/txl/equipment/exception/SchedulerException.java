package com.txl.equipment.exception;

/**
 * Created by TangXiangLin on 2023-02-13 14:59
 * 调度器异常处理类
 */
public class SchedulerException extends Exception{

    private static final long serialVersionUID = 174841398690789156L;

    public SchedulerException() {
        super();
    }

    public SchedulerException(String msg) {
        super(msg);
    }

    public SchedulerException(Throwable cause) {
        super(cause);
    }

    public SchedulerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public Throwable getUnderlyingException() {
        return super.getCause();
    }

    @Override
    public String toString() {
        Throwable cause = getUnderlyingException();
        if (cause == null || cause == this) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }
    }
}
