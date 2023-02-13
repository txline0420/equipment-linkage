package com.txl.equipment.key;

import com.txl.equipment.key.DirtyFlagMap;

import java.io.Serializable;

/**
 * Created by TangXiangLin on 2023-02-13 11:29
 * 字符类型的脏污标识
 */
public class StringKeyDirtyFlagMap extends DirtyFlagMap<String, Object> {
    static final long serialVersionUID = -9076749120524952280L;

    /**
     * @deprecated JDBCJobStores no longer prune out transient data.  If you
     * include non-Serializable values in the Map, you will now get an
     * exception when attempting to store it in a database.
     */
    private boolean allowsTransientData = false;

    public StringKeyDirtyFlagMap() {
        super();
    }

    public StringKeyDirtyFlagMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringKeyDirtyFlagMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return getWrappedMap().hashCode();
    }

    /**
     * Get a copy of the Map's String keys in an array of Strings.
     */
    public String[] getKeys() {
        return keySet().toArray(new String[size()]);
    }

    /**
     * Tell the <code>StringKeyDirtyFlagMap</code> that it should
     * allow non-<code>Serializable</code> values.  Enforces that the Map
     * doesn't already include transient data.
     *
     * @deprecated JDBCJobStores no longer prune out transient data.  If you
     * include non-Serializable values in the Map, you will now get an
     * exception when attempting to store it in a database.
     */
    public void setAllowsTransientData(boolean allowsTransientData) {

        if (containsTransientData() && !allowsTransientData) {
            throw new IllegalStateException(
                    "Cannot set property 'allowsTransientData' to 'false' "
                            + "when data map contains non-serializable objects.");
        }

        this.allowsTransientData = allowsTransientData;
    }

    /**
     * Whether the <code>StringKeyDirtyFlagMap</code> allows
     * non-<code>Serializable</code> values.
     *
     * @deprecated JDBCJobStores no longer prune out transient data.  If you
     * include non-Serializable values in the Map, you will now get an
     * exception when attempting to store it in a database.
     */
    public boolean getAllowsTransientData() {
        return allowsTransientData;
    }

    /**
     * Determine whether any values in this Map do not implement
     * <code>Serializable</code>.  Always returns false if this Map
     * is flagged to not allow transient data.
     *
     * @deprecated JDBCJobStores no longer prune out transient data.  If you
     * include non-Serializable values in the Map, you will now get an
     * exception when attempting to store it in a database.
     */
    public boolean containsTransientData() {
        if (!getAllowsTransientData()) { // short circuit...
            return false;
        }

        String[] keys = getKeys();
        for (int i = 0; i < keys.length; i++) {
            Object o = super.get(keys[i]);
            if (!(o instanceof Serializable)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes any data values in the map that are non-Serializable.  Does
     * nothing if this Map does not allow transient data.
     *
     * @deprecated JDBCJobStores no longer prune out transient data.  If you
     * include non-Serializable values in the Map, you will now get an
     * exception when attempting to store it in a database.
     */
    public void removeTransientData() {
        if (!getAllowsTransientData()) { // short circuit...
            return;
        }

        String[] keys = getKeys();
        for (int i = 0; i < keys.length; i++) {
            Object o = super.get(keys[i]);
            if (!(o instanceof Serializable)) {
                remove(keys[i]);
            }
        }
    }


    public void put(String key, int value) {
        super.put(key, Integer.valueOf(value));
    }

    public void put(String key, long value) {
        super.put(key, Long.valueOf(value));
    }

    public void put(String key, float value) {
        super.put(key, Float.valueOf(value));
    }

    public void put(String key, double value) {
        super.put(key, Double.valueOf(value));
    }

    public void put(String key, boolean value) {
        super.put(key, Boolean.valueOf(value));
    }

    public void put(String key, char value) {
        super.put(key, Character.valueOf(value));
    }

    public void put(String key, String value) {
        super.put(key, value);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put((String)key, value);
    }

    public int getInt(String key) {
        Object obj = get(key);

        try {
            if(obj instanceof Integer)
                return ((Integer) obj).intValue();
            return Integer.parseInt((String)obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not an Integer.");
        }
    }

    public long getLong(String key) {
        Object obj = get(key);

        try {
            if(obj instanceof Long)
                return ((Long) obj).longValue();
            return Long.parseLong((String)obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Long.");
        }
    }

    public float getFloat(String key) {
        Object obj = get(key);

        try {
            if(obj instanceof Float)
                return ((Float) obj).floatValue();
            return Float.parseFloat((String)obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Float.");
        }
    }

    public double getDouble(String key) {
        Object obj = get(key);

        try {
            if(obj instanceof Double)
                return ((Double) obj).doubleValue();
            return Double.parseDouble((String)obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Double.");
        }
    }

    public boolean getBoolean(String key) {
        Object obj = get(key);

        try {
            if(obj instanceof Boolean)
                return ((Boolean) obj).booleanValue();
            return Boolean.parseBoolean((String)obj);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Boolean.");
        }
    }

    public char getChar(String key) {
        Object obj = get(key);

        try {
            if(obj instanceof Character)
                return ((Character) obj).charValue();
            return ((String)obj).charAt(0);
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Character.");
        }
    }

    public String getString(String key) {
        Object obj = get(key);

        try {
            return (String) obj;
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a String.");
        }
    }
}
