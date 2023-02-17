package com.txl.equipment.listener;

import com.txl.equipment.exception.LinkageIllegalArgumentException;
import com.txl.equipment.job.JobKey;
import com.txl.equipment.job.JobListener;
import com.txl.equipment.matcher.EverythingMatcher;
import com.txl.equipment.matcher.Matcher;
import com.txl.equipment.schedule.SchedulerListener;
import com.txl.equipment.trigger.TriggerKey;
import com.txl.equipment.trigger.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by TangXiangLin on 2023-02-15 12:05
 * 缺省的监听器管理实现
 */
public class DefaultListenerManager implements ListenerManager {

    private static final Logger logger = LoggerFactory.getLogger(DefaultListenerManager.class);

    protected final Map<String, JobListener> globalJobListeners = new LinkedHashMap<String, JobListener>(10);

    protected final Map<String, TriggerListener> globalTriggerListeners = new LinkedHashMap<String, TriggerListener>(10);

    protected final Map<String, List<Matcher<JobKey>>> globalJobListenersMatchers = new LinkedHashMap<String, List<Matcher<JobKey>>>(10);

    protected final Map<String, List<Matcher<TriggerKey>>> globalTriggerListenersMatchers = new LinkedHashMap<String, List<Matcher<TriggerKey>>>(10);

    protected final ArrayList<SchedulerListener> schedulerListeners = new ArrayList<SchedulerListener>(10);


    public void addJobListener(JobListener jobListener) {
        addJobListener(jobListener, EverythingMatcher.allJobs());
        logger.info("JobListener added Schedule.");
    }

    public void addJobListener(JobListener jobListener, Matcher<JobKey> matcher) {
        if(jobListener.gerName() == null || jobListener.gerName().length() == 0){
            throw new LinkageIllegalArgumentException("JobListener name cannot be empty.");
        }

        synchronized (globalJobListeners){
            globalJobListeners.put(jobListener.gerName(),jobListener);
            LinkedList<Matcher<JobKey>> matchersL = new LinkedList<Matcher<JobKey>>();
            if(matcher != null){
                matchersL.add(matcher);
            } else {
              matchersL.add(EverythingMatcher.allJobs());
            }
            globalJobListenersMatchers.put(jobListener.gerName(), matchersL);
        }

    }

    public void addJobListener(JobListener jobListener, Matcher<JobKey>... matchers) {
        addJobListener(jobListener,Arrays.asList(matchers));
    }

    public void addJobListener(JobListener jobListener, List<Matcher<JobKey>> matchers) {
        if(jobListener.gerName() == null || jobListener.gerName().length() == 0){
            throw new LinkageIllegalArgumentException("JobListener name cannot be empty.");
        }

        synchronized (globalJobListeners) {
            globalJobListeners.put(jobListener.gerName(), jobListener);
            LinkedList<Matcher<JobKey>> matchersL = new  LinkedList<Matcher<JobKey>>();
            if(matchers != null && matchers.size() > 0) {
                matchersL.addAll(matchers);
            } else {
                matchersL.add(EverythingMatcher.allJobs());
            }
            globalJobListenersMatchers.put(jobListener.gerName(), matchersL);
        }

    }

    public boolean addJobListenerMatcher(String listenerName, Matcher<JobKey> matcher) {
        return false;
    }

    public boolean removeJobListenerMatcher(String listenerName, Matcher<JobKey> matcher) {
        return false;
    }

    public boolean setJobListenerMatchers(String listenerName, List<Matcher<JobKey>> matchers) {
        return false;
    }

    public List<Matcher<JobKey>> getJobListenerMatchers(String listenerName) {
        return null;
    }

    public boolean removeJobListener(String name) {
        return false;
    }

    public List<JobListener> getJobListeners() {
        return null;
    }

    public JobListener getJobListener(String name) {
        return null;
    }

    public void addTriggerListener(TriggerListener triggerListener) {

    }

    public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> matcher) {

    }

    public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey>... matchers) {

    }

    public void addTriggerListener(TriggerListener triggerListener, List<Matcher<TriggerKey>> matchers) {

    }

    public boolean addTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher) {
        return false;
    }

    public boolean removeTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher) {
        return false;
    }

    public boolean setTriggerListenerMatchers(String listenerName, List<Matcher<TriggerKey>> matchers) {
        return false;
    }

    public List<Matcher<TriggerKey>> getTriggerListenerMatchers(String listenerName) {
        return null;
    }

    public boolean removeTriggerListener(String name) {
        return false;
    }

    public List<TriggerListener> getTriggerListeners() {
        return null;
    }

    public TriggerListener getTriggerListener(String name) {
        return null;
    }

    public void addSchedulerListener(SchedulerListener schedulerListener) {

    }

    public boolean removeSchedulerListener(SchedulerListener schedulerListener) {
        return false;
    }

    public List<SchedulerListener> getSchedulerListeners() {
        return null;
    }
}
