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
    private String name;

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

    /**
     * If applyAssignable is true, will ignore all type that is sub type of ignoreType.
     */
    private boolean applyAssignable = true;

    protected TypeAssigner assigner;

    public IgnoreField() {
    }

    public IgnoreField(String fieldName) {
        this.name = fieldName;
        this.applyPath = true;
        this.ignoreType = Object.class;
    }

    public IgnoreField(String fieldName, Class<?> ignoreType) {
        this.name = fieldName;
        this.applyPath = true;
        this.ignoreType = ignoreType;
    }

    public void setApplyAssignable(boolean applyAssignable) {
        this.applyAssignable = applyAssignable;
    }

    public void setIgnoreType(Class<?> ignoreType) {
        this.ignoreType = ignoreType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setApplyPath(boolean applyPath) {
        this.applyPath = applyPath;
    }

    public boolean ignored(CompareContext<?> context, NamedType namedType) {
        if (Objects.equals(name, namedType.getName()) && checkType(namedType.getType())) {
            return true;
        }

        if (!applyPath) {
            return false;
        }

        return Objects.equals(name, context.getNPath(namedType.getName())) && checkType(namedType.getType());

    }

    protected boolean checkType(Class<?> type) {
        init();
        return assigner.checkCast(type);
    }

    private void init() {
        if (assigner == null) {
            assigner = new TypeAssigner(applyAssignable, ignoreType);
        }
    }
}
