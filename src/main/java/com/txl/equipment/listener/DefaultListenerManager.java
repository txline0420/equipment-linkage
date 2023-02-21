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
    }

    public void addJobListener(JobListener jobListener, Matcher<JobKey> matcher) {
        if(jobListener.gerName() == null || jobListener.gerName().length() == 0){
            throw new LinkageIllegalArgumentException("Add JobListener To Schedule Name Cannot Be Empty.");
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
            logger.info("Add JobListener To Schedule Success.");
        }

    }

    public void addJobListener(JobListener jobListener, Matcher<JobKey>... matchers) {
        addJobListener(jobListener,Arrays.asList(matchers));
    }

    public void addJobListener(JobListener jobListener, List<Matcher<JobKey>> matchers) {
        if(jobListener.gerName() == null || jobListener.gerName().length() == 0){
            throw new LinkageIllegalArgumentException("Add JobListener To Schedule Name Cannot Be Empty.");
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
            logger.info("Add JobListener To Schedule Success.");
        }

    }

    public boolean addJobListenerMatcher(String listenerName, Matcher<JobKey> matcher) {
        if(matcher == null){
            throw new LinkageIllegalArgumentException("Add Matcher JobKey is not acceptable Null.");
        }

        synchronized (globalJobListeners) {
            List<Matcher<JobKey>> matchers = globalJobListenersMatchers.get(listenerName);
            if(matchers == null){
                logger.error("Add Matcher JobKey To ListenerManager Failed.");
                return false;
            }
            matchers.add(matcher);
            logger.info("Add Matcher JobKey To ListenerManager Success.");
            return true;
        }
    }

    public boolean removeJobListenerMatcher(String listenerName, Matcher<JobKey> matcher) {
        if(matcher == null){
            throw new LinkageIllegalArgumentException("Remove Matcher JobKey is not acceptable Null.");
        }
        synchronized (globalJobListeners) {
            List<Matcher<JobKey>> matchers = globalJobListenersMatchers.get(listenerName);
            if(matchers == null){
                logger.error("Remove Matcher JobKey From ListenerManager Failed.");
                return false;
            }
            logger.info("Remove Matcher JobKey From ListenerManager Success.");
            return matchers.remove(matcher);
        }
    }

    public List<Matcher<JobKey>> getJobListenerMatchers(String listenerName) {
        synchronized (globalJobListeners) {
            List<Matcher<JobKey>> matchers = globalJobListenersMatchers.get(listenerName);
            if(matchers == null){
                logger.error("Get Matcher JobKey From ListenerManager is Null.");
                return null;
            }
            logger.info("Get Matcher JobKey From ListenerManager is {}",matchers.size());
            return Collections.unmodifiableList(matchers);
        }
    }

    public boolean setJobListenerMatchers(String listenerName, List<Matcher<JobKey>> matchers) {
        if(matchers == null){
            throw new IllegalArgumentException("Set Matcher JobKey To ListenerManager is not acceptable Null.");
        }

        synchronized (globalJobListeners) {
            List<Matcher<JobKey>> oldMatchers = globalJobListenersMatchers.get(listenerName);
            if(oldMatchers == null){
                logger.error("Set Matcher JobKey To ListenerManager is Failed.");
                return false;
            }
            logger.info("Set Matcher JobKey To ListenerManager is success.");
            globalJobListenersMatchers.put(listenerName, matchers);
            return true;
        }
    }

    public boolean removeJobListener(String name) {
        synchronized (globalJobListeners) {
            return (globalJobListeners.remove(name) != null);
        }
    }

    public List<JobListener> getJobListeners() {
        synchronized (globalJobListeners) {
            return Collections.unmodifiableList(new LinkedList<JobListener>(globalJobListeners.values()));
        }
    }

    public JobListener getJobListener(String name) {
        synchronized (globalJobListeners) {
            return globalJobListeners.get(name);
        }
    }

    public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> ... matchers) {
        addTriggerListener(triggerListener, Arrays.asList(matchers));
    }

    public void addTriggerListener(TriggerListener triggerListener, List<Matcher<TriggerKey>> matchers) {
        if (triggerListener.gerName() == null
                || triggerListener.gerName().length() == 0) {
            throw new IllegalArgumentException(
                    "Add TriggerListener To ListenerManager name cannot be empty.");
        }

        synchronized (globalTriggerListeners) {
            globalTriggerListeners.put(triggerListener.gerName(), triggerListener);

            LinkedList<Matcher<TriggerKey>> matchersL = new  LinkedList<Matcher<TriggerKey>>();
            if(matchers != null && matchers.size() > 0) {
                matchersL.addAll(matchers);
            } else {
                matchersL.add(EverythingMatcher.allTriggers());
            }
            globalTriggerListenersMatchers.put(triggerListener.gerName(), matchersL);
            logger.info("Add TriggerListener To ListenerManager success.");
        }
    }

    public void addTriggerListener(TriggerListener triggerListener) {
        addTriggerListener(triggerListener, EverythingMatcher.allTriggers());
    }

    public void addTriggerListener(TriggerListener triggerListener, Matcher<TriggerKey> matcher) {
        if(matcher == null)
            throw new LinkageIllegalArgumentException(
                    "Add TriggerListener && matcher To ListenerManager not acceptable matcher is Null.");

        if (triggerListener.gerName() == null
                || triggerListener.gerName().length() == 0) {
            throw new LinkageIllegalArgumentException(
                    "Add TriggerListener To ListenerManager name cannot be empty.");
        }

        synchronized (globalTriggerListeners) {
            globalTriggerListeners.put(triggerListener.gerName(), triggerListener);
            List<Matcher<TriggerKey>> matchers = new LinkedList<Matcher<TriggerKey>>();
            matchers.add(matcher);
            globalTriggerListenersMatchers.put(triggerListener.gerName(), matchers);
            logger.info("Add TriggerListener && matcher To ListenerManager success.");
        }
    }

    public boolean addTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher) {
        if(matcher == null){
            throw new LinkageIllegalArgumentException(
                    "Add TriggerListener && matcher To ListenerManager not acceptable matcher is Null.");
        }

        synchronized (globalTriggerListeners) {
            List<Matcher<TriggerKey>> matchers = globalTriggerListenersMatchers.get(listenerName);
            if(matchers == null){
                logger.error("Add TriggerListener && matcher To ListenerManager Failed.");
                return false;
            }
            matchers.add(matcher);
            logger.info("Add TriggerListener && matcher To ListenerManager Success.");
            return true;
        }
    }

    public boolean removeTriggerListenerMatcher(String listenerName, Matcher<TriggerKey> matcher) {
        if(matcher == null){
            throw new LinkageIllegalArgumentException(
                    "Remove TriggerListener && matcher From ListenerManager not acceptable matcher is Null.");
        }


        synchronized (globalTriggerListeners) {
            List<Matcher<TriggerKey>> matchers = globalTriggerListenersMatchers.get(listenerName);
            if(matchers == null){
                logger.error("Remove TriggerListener && matcher From ListenerManager Failed.");
                return false;
            }
            logger.info("Remove TriggerListener && matcher From ListenerManager Success.");
            return matchers.remove(matcher);
        }
    }

    public List<Matcher<TriggerKey>> getTriggerListenerMatchers(String listenerName) {
        synchronized (globalTriggerListeners) {
            List<Matcher<TriggerKey>> matchers = globalTriggerListenersMatchers.get(listenerName);
            if(matchers == null){
                logger.error("Get TriggerListenerMatchers From ListenerManager is Null.");
                return null;
            }
            logger.error("Get TriggerListenerMatchers From ListenerManager is {}.",matchers.size());
            return Collections.unmodifiableList(matchers);
        }
    }

    public boolean setTriggerListenerMatchers(String listenerName, List<Matcher<TriggerKey>> matchers)  {
        if(matchers == null){
            throw new LinkageIllegalArgumentException(
                    "Set TriggerListenerMatchers To ListenerManager not acceptable matcher is Null.");
        }


        synchronized (globalTriggerListeners) {
            List<Matcher<TriggerKey>> oldMatchers = globalTriggerListenersMatchers.get(listenerName);
            if(oldMatchers == null){
                logger.error("Set TriggerListenerMatchers To ListenerManager Failed.");
                return false;
            }
            globalTriggerListenersMatchers.put(listenerName, matchers);
            logger.info("Set TriggerListenerMatchers To ListenerManager Success.");
            return true;
        }
    }

    public boolean removeTriggerListener(String name) {
        synchronized (globalTriggerListeners) {
            return (globalTriggerListeners.remove(name) != null);
        }
    }


    public List<TriggerListener> getTriggerListeners() {
        synchronized (globalTriggerListeners) {
            return Collections.unmodifiableList(new LinkedList<TriggerListener>(globalTriggerListeners.values()));
        }
    }

    public TriggerListener getTriggerListener(String name) {
        synchronized (globalTriggerListeners) {
            return globalTriggerListeners.get(name);
        }
    }


    public void addSchedulerListener(SchedulerListener schedulerListener) {
        synchronized (schedulerListeners) {
            schedulerListeners.add(schedulerListener);
        }
    }

    public boolean removeSchedulerListener(SchedulerListener schedulerListener) {
        synchronized (schedulerListeners) {
            return schedulerListeners.remove(schedulerListener);
        }
    }

    public List<SchedulerListener> getSchedulerListeners() {
        synchronized (schedulerListeners) {
            return Collections.unmodifiableList(new ArrayList<SchedulerListener>(schedulerListeners));
        }
    }
}
