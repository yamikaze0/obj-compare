package org.yamikaze.compare;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:03
 */
public class IgnoreField {

    /**
     * The ignore field name.
     */
    private String fieldName;

    /**
     * If field not full-match field name, but it's ends with field.
     */
    private boolean applyPath;

    /**
     * Which type is ignored.
     *
     * If ignoreType is Object.class, it's means any type that fields matched is ignored.
     */
    private Class<?> ignoreType;

    public IgnoreField() {
    }

    public IgnoreField(String fieldName) {
        this.fieldName = fieldName;
        this.applyPath = true;
        this.ignoreType = Object.class;
    }

    public Class<?> getIgnoreType() {
        return ignoreType;
    }

    public void setIgnoreType(Class ignoreType) {
        this.ignoreType = ignoreType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean getApplyPath() {
        return applyPath;
    }

    public void setApplyPath(boolean applyPath) {
        this.applyPath = applyPath;
    }

    public boolean ignored(CompareContext<?> context, NamedType namedType) {
        if (Objects.equals(fieldName, namedType.getName()) && ignoreType.isAssignableFrom(namedType.getType())) {
            return true;
        }

        if (!applyPath) {
            return false;
        }

        return Objects.equals(fieldName, prefix(context.getComparePath()) + namedType.getName()) && ignoreType.isAssignableFrom(namedType.getType());

    }

    protected String prefix(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        return str + ".";
    }
}
