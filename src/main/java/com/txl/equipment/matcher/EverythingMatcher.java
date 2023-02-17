package com.txl.equipment.matcher;

import com.txl.equipment.job.JobKey;
import com.txl.equipment.key.Key;
import com.txl.equipment.trigger.TriggerKey;

/**
 * Created by TangXiangLin on 2023-02-15 13:35
 * 名称和组名都相同的匹配器
 */
public class EverythingMatcher<T extends Key<?>> implements Matcher<T> {

    private static final long serialVersionUID = 202300056681974058L;

    protected EverythingMatcher() {
    }

    public static EverythingMatcher<JobKey> allJobs() {
        return new EverythingMatcher<JobKey>();
    }

    public static EverythingMatcher<TriggerKey> allTriggers() {
        return new EverythingMatcher<TriggerKey>();
    }

    public boolean isMatch(T key) {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        return obj.getClass().equals(getClass());
    }

    @Override
    public int hashCode() {
        return getClass().getName().hashCode();
    }
}
