package com.txl.equipment.job;

import com.txl.equipment.key.Key;

/**
 * Created by TangXiangLin on 2023-02-21 16:04
 * 作业任务构造者
 */
public class JobBuilder {

    /** 作业任务唯一标识 */
    protected JobKey key;
    /** 作业任务描述 */
    protected String description;
    /** 作业任务实例 */
    protected Class<? extends Job> jobClass;
    /** 作业任务生命周期 */
    protected boolean durability;
    /** 作业任务恢复 */
    protected boolean shouldRecover;
    /** 作业任务参数 */
    protected JobDataMap jobDataMap = new JobDataMap();

    // 1. Constructor
    protected JobBuilder() {
    }

    // 2. Builder
    public static JobBuilder newJob() {
        return new JobBuilder();
    }

    public static JobBuilder newJob(Class <? extends Job> jobClass) {
        JobBuilder b = new JobBuilder();
        b.ofType(jobClass);
        return b;
    }

    public JobDetail build() {
        JobDetailImpl job = new JobDetailImpl();
        job.setJobClass(jobClass);
        job.setDescription(description);
        if(key == null)
            key = new JobKey(Key.createUniqueName(null), null);
        job.setKey(key);
        job.setDurability(durability);
        job.setRequestsRecovery(shouldRecover);
        if(!jobDataMap.isEmpty())
            job.setJobDataMap(jobDataMap);
        return job;
    }

    /** 作业任务的唯一标识 */
    public JobBuilder withIdentity(String name) {
        key = new JobKey(name, null);
        return this;
    }

    /** 作业任务的唯一标识 */
    public JobBuilder withIdentity(String name, String group) {
        key = new JobKey(name, group);
        return this;
    }

    /** 作业任务的唯一标识 */
    public JobBuilder withIdentity(JobKey jobKey) {
        this.key = jobKey;
        return this;
    }

    /** 作业任务描述 */
    public JobBuilder withDescription(String jobDescription) {
        this.description = jobDescription;
        return this;
    }

    /** 任业任务实例 */
    public JobBuilder ofType(Class <? extends Job> jobClazz) {
        this.jobClass = jobClazz;
        return this;
    }

    /** 是否恢复请求 */
    public JobBuilder requestRecovery() {
        this.shouldRecover = true;
        return this;
    }

    /** 是否恢复请求 */
    public JobBuilder requestRecovery(boolean jobShouldRecover) {
        this.shouldRecover = jobShouldRecover;
        return this;
    }

    /** 作业任务被孤立后是否保持存储 */
    public JobBuilder storeDurably() {
        return storeDurably(true);
    }

    public JobBuilder storeDurably(boolean jobDurability) {
        this.durability = jobDurability;
        return this;
    }

    /** 添加作业任务的参数 */
    public JobBuilder usingJobData(String dataKey, String value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder usingJobData(String dataKey, Integer value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder usingJobData(String dataKey, Long value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder usingJobData(String dataKey, Float value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder usingJobData(String dataKey, Double value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder usingJobData(String dataKey, Boolean value) {
        jobDataMap.put(dataKey, value);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder usingJobData(JobDataMap newJobDataMap) {
        jobDataMap.putAll(newJobDataMap);
        return this;
    }

    /** 添加作业任务参数 */
    public JobBuilder setJobData(JobDataMap newJobDataMap) {
        jobDataMap = newJobDataMap;
        return this;
    }

}
