package com.txl.equipment.exception;

/**
 * Created by TangXiangLin on 2023-02-13 14:58
 * 任务作业异常类
 */
public class JobExecutionException extends SchedulerException{

    private static final long serialVersionUID = 1326342535829043325L;

    private boolean refire = false;

    private boolean unscheduleTrigg = false;

    private boolean unscheduleAllTriggs = false;

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

    public JobExecutionException(String msg, Throwable cause,
                                 boolean refireImmediately) {
        super(msg, cause);
        refire = refireImmediately;
    }


    public JobExecutionException(String msg, boolean refireImmediately) {
        super(msg);

        refire = refireImmediately;
    }

    public void setRefireImmediately(boolean refire) {
        this.refire = refire;
    }

    public boolean refireImmediately() {
        return refire;
    }

    public void setUnscheduleFiringTrigger(boolean unscheduleTrigg) {
        this.unscheduleTrigg = unscheduleTrigg;
    }

    public boolean unscheduleFiringTrigger() {
        return unscheduleTrigg;
    }

    public void setUnscheduleAllTriggers(boolean unscheduleAllTriggs) {
        this.unscheduleAllTriggs = unscheduleAllTriggs;
    }

    public boolean unscheduleAllTriggers() {
        return unscheduleAllTriggs;
    }
}
