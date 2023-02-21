package com.txl.equipment.job;

import com.txl.equipment.annotation.DisallowConcurrentExecution;
import com.txl.equipment.annotation.PersistJobDataAfterExecution;
import com.txl.equipment.exception.LinkageIllegalArgumentException;
import com.txl.equipment.schedule.Scheduler;
import com.txl.equipment.utils.ClassUtils;

import java.io.Serializable;

/**
 * Created by TangXiangLin on 2023-02-21 16:59
 * 作业任务的实现
 */
public class JobDetailImpl implements Cloneable, Serializable,JobDetail {

    private static final long serialVersionUID = -6069784757781506897L;

    /** 作业任务名称 */
    private String name;
    /** 作业任务组名*/
    private String group = Scheduler.DEFAULT_GROUP;
    /** 作业任务描述 */
    private String description;
    /** 作业任务实例 */
    private Class<? extends Job> jobClass;
    /** 作业任务参数 */
    private JobDataMap jobDataMap;
    /** 作业任务生命周期 */
    private boolean durability = false;
    /** 作业任务恢复 */
    private boolean shouldRecover = false;
    /** 作业任务唯一标识 */
    private transient JobKey key = null;

    // 1. Constructor
    public JobDetailImpl() {
    }

    public JobDetailImpl(String name, Class<? extends Job> jobClass) {
        this(name, null, jobClass);
    }

    public JobDetailImpl(String name, String group, Class<? extends Job> jobClass) {
        setName(name);
        setGroup(group);
        setJobClass(jobClass);
    }

    public JobDetailImpl(String name, String group, Class<? extends Job> jobClass,
                         boolean durability, boolean recover) {
        setName(name);
        setGroup(group);
        setJobClass(jobClass);
        setDurability(durability);
        setRequestsRecovery(recover);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() == 0) {
            throw new LinkageIllegalArgumentException("Job name cannot be empty.");
        }
        this.name = name;
        this.key = null;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        if (group != null && group.trim().length() == 0) {
            throw new LinkageIllegalArgumentException(
                    "Group name cannot be empty.");
        }
        if (group == null) {
            group = Scheduler.DEFAULT_GROUP;
        }
        this.group = group;
        this.key = null;
    }

    public String getFullName() {
        return group + "." + name;
    }

    public JobKey getKey() {
        if(key == null) {
            if(getName() == null)
                return null;
            key = new JobKey(getName(), getGroup());
        }
        return key;
    }

    public void setKey(JobKey key) {
        if(key == null)
            throw new LinkageIllegalArgumentException("Key cannot be null!");
        setName(key.getName());
        setGroup(key.getGroup());
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        if (jobClass == null) {
            throw new LinkageIllegalArgumentException("Job class cannot be null.");
        }
        if (!Job.class.isAssignableFrom(jobClass)) {
            throw new LinkageIllegalArgumentException(
                    "Job class must implement the Job interface.");
        }
        this.jobClass = jobClass;
    }

    public JobDataMap getJobDataMap() {
        if (jobDataMap == null) {
            jobDataMap = new JobDataMap();
        }
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    public void setDurability(boolean durability) {
        this.durability = durability;
    }

    public void setRequestsRecovery(boolean shouldRecover) {
        this.shouldRecover = shouldRecover;
    }

    public boolean isDurable() {
        return durability;
    }

    public boolean isPersistJobDataAfterExecution() {
        return ClassUtils.isAnnotationPresent(jobClass, PersistJobDataAfterExecution.class);
    }

    public boolean isConcurrentExectionDisallowed() {
        return ClassUtils.isAnnotationPresent(jobClass, DisallowConcurrentExecution.class);
    }

    public boolean requestsRecovery() {
        return shouldRecover;
    }

    @Override
    public String toString() {
        return "JobDetail '" + getFullName() + "':  jobClass: '"
                + ((getJobClass() == null) ? null : getJobClass().getName())
                + " concurrentExectionDisallowed: " + isConcurrentExectionDisallowed()
                + " persistJobDataAfterExecution: " + isPersistJobDataAfterExecution()
                + " isDurable: " + isDurable() + " requestsRecovers: " + requestsRecovery();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JobDetail)) {
            return false;
        }

        JobDetail other = (JobDetail) obj;

        if(other.getKey() == null || getKey() == null)
            return false;

        if (!other.getKey().equals(getKey())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        JobKey key = getKey();
        return key == null ? 0 : getKey().hashCode();
    }

    @Override
    public Object clone() {
        JobDetailImpl copy;
        try {
            copy = (JobDetailImpl) super.clone();
            if (jobDataMap != null) {
                copy.jobDataMap = (JobDataMap) jobDataMap.clone();
            }
        } catch (CloneNotSupportedException ex) {
            throw new IncompatibleClassChangeError("Not Cloneable.");
        }

        return copy;
    }

    public JobBuilder getJobBuilder() {
        JobBuilder b = JobBuilder.newJob()
                .ofType(getJobClass())
                .requestRecovery(requestsRecovery())
                .storeDurably(isDurable())
                .usingJobData(getJobDataMap())
                .withDescription(getDescription())
                .withIdentity(getKey());
        return b;
    }

}
