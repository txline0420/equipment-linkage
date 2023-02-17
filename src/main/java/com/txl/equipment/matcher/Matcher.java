package com.txl.equipment.matcher;

import com.txl.equipment.key.Key;

import java.io.Serializable;

/**
 * Created by TangXiangLin on 2023-02-15 10:47
 * 匹配器
 */
public interface Matcher<T extends Key<?>> extends Serializable {

    boolean isMatch(T key);

    public int hashCode();

    public boolean equals(Object obj);

}
