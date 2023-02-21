package com.txl.equipment.exception;

/**
 * Created by TangXiangLin on 2023-02-13 14:58
 * 任务作业异常类
 */
public class JobExecutionException extends SchedulerException{

    private static final long serialVersionUID = 1326342535829043325L;

    /** 是否再次触发 */
    protected boolean refire = false;

    /** 是否再次调度 */
    protected boolean unscheduleTrigg = false;

    /** 所有触发器是否触发并调度完毕 */
    protected boolean unscheduleAllTriggs = false;

    public JobExecutionException() {
    }

    public JobExecutionException(Throwable cause) {
        super(cause);
    }

    public JobExecutionException(String msg) {
        super(msg);
    }

    public JobExecutionException(boolean refireImmediately) {
        refire = refireImmediately;
    }

    public JobExecutionException(Throwable cause, boolean refireImmediately) {
        super(cause);

        refire = refireImmediately;
    }

    public JobExecutionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JobExecutionException(String msg, Throwable cause, boolean refireImmediately) {
        super(msg, cause);
        refire = refireImmediately;
    }


    public JobExecutionException(String msg, boolean refireImmediately) {
        super(msg);

        refire = refireImmediately;
    }

    /* 是否再次触发 */
    public boolean refireImmediately() {
        return refire;
    }

    /** 再次调度 */
    public boolean unscheduleFiringTrigger() {
        return unscheduleTrigg;
    }

    /** 所有触发器是否触发并调度完毕 */
    public boolean unscheduleAllTriggers() {
        return unscheduleAllTriggs;
    }

    public void setRefireImmediately(boolean refire) {
        this.refire = refire;
    }

    public void setUnscheduleFiringTrigger(boolean unscheduleTrigg) {
        this.unscheduleTrigg = unscheduleTrigg;
    }

    public void setUnscheduleAllTriggers(boolean unscheduleAllTriggs) {
        this.unscheduleAllTriggs = unscheduleAllTriggs;
    }


}
