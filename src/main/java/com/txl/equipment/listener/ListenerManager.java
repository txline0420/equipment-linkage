package com.txl.equipment.listener;

import com.txl.equipment.job.JobKey;
import com.txl.equipment.job.JobListener;
import com.txl.equipment.matcher.Matcher;
import com.txl.equipment.schedule.SchedulerListener;
import com.txl.equipment.trigger.TriggerKey;
import com.txl.equipment.trigger.TriggerListener;

import java.util.List;

/**
 * Created by TangXiangLin on 2023-02-13 17:02
 * 监听器管理
 * 1. JobListener接口提供作业执行的通知。
 * 2. TriggerListener接口提供触发器的通知。
 * 3. SchedulerListener接口提供调度器事件和错误的通知。
 */
public interface ListenerManager {

    /** 将给定的JobListener添加到Scheduler，并注册它以接收所有作业的事件。
     * 默认使用EverythingMatcher匹配器 */
    void addJobListener(JobListener jobListener);

    /** 将给定的JobListener添加到Scheduler，并注册它以接收所有作业的事件。
     * 使用指定的匹配器 */
    void addJobListener(JobListener jobListener, Matcher<JobKey> matcher);

    /** 将给定的JobListener添加到Scheduler，并注册它以接收所有作业的事件。
     * 使用指定的匹配器 */
    void addJobListener(JobListener jobListener, Matcher<JobKey> ... matchers);

    /** 将给定的JobListener添加到Scheduler，并注册它以接收所有作业的事件。
     * 使用指定的匹配器 */
    void addJobListener(JobListener jobListener, List<Matcher<JobKey>> matchers);

    /** 将给定的匹配器添加到匹配器集合当中 */
    boolean addJobListenerMatcher(String listenerName, Matcher<JobKey> matcher);

    /** 将给定的匹配器从到匹配器集合当中移除 */
    boolean removeJobListenerMatcher(String listenerName, Matcher<JobKey> matcher);

    /** 将给定的一组匹配器添加到匹配器集合当中  */
    boolean setJobListenerMatchers(String listenerName, List<Matcher<JobKey>> matchers);

    /** 根据名称获取匹配器集合 */
    List<Matcher<JobKey>> getJobListenerMatchers(String listenerName);

    /** 根据名称移除作业任务监听器 */
    boolean removeJobListener(String name);

    /** 获取所有作业任务监听器 */
    List<JobListener> getJobListeners();

    /** 根据名称获取作业任务监听器 */
    JobListener getJobListener(String name);

    /** 将给定的TriggerListener添加到Scheduler，并注册它以接收所有触发器的事件，
     * 默认使用EverythingMatcher匹配器 */
    void addTriggerListener(TriggerListener triggerListener);

    /** 将给定的TriggerListener添加到Scheduler，并注册它以接收所有触发器的事件。
     * 使用指定的匹配器 */
    void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> matcher);

    /** 将给定的TriggerListener添加到Scheduler，并注册它以接收所有触发器的事件。
     * 使用指定的匹配器 */
    void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> ... matchers);

    /** 将给定的TriggerListener添加到Scheduler，并注册它以接收所有触发器的事件。
     * 使用指定的匹配器 */
    void addTriggerListener(TriggerListener triggerListener, List<Matcher<TriggerKey>> matchers);

    /** 将给定的触发器匹配器添加到匹配器集合当中 */
    boolean addTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher);

    /** 将给定的匹配器从到匹配器集合当中移除 */
    boolean removeTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher);

    /** 将给定的一组匹配器添加到匹配器集合当中  */
    boolean setTriggerListenerMatchers(String listenerName, List<Matcher<TriggerKey>> matchers);

    /** 根据名称获取匹配器集合 */
    List<Matcher<TriggerKey>> getTriggerListenerMatchers( String listenerName);

    /** 根据名称移除作业触发器监听器 */
    boolean removeTriggerListener(String name);

    /** 获取所有触发器监听器 */
    List<TriggerListener> getTriggerListeners();

    /** 根据名称获取触发器监听器 */
    TriggerListener getTriggerListener(String name);

    /** 将调度器监听器注册到Scheduler中 */
    void addSchedulerListener(SchedulerListener schedulerListener);

    /** 从Scheduler中移除调度器监听器 */
    boolean removeSchedulerListener(SchedulerListener schedulerListener);

    /** 获取所有调度器监控器 */
    List<SchedulerListener> getSchedulerListeners();
}
