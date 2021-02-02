package org.yamikaze.compare;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:18
 */
public abstract class AbstractNamedTypeCompare<T> extends AbstractCompare<T> {

    /**
     * supported compare fields name
     */
    private String name;

    /**
     * supported compare fields type.
     *
     * Object.class mean any type.
     */
    private Class<?> type;

    public AbstractNamedTypeCompare(String name) {
        this(name, Object.class);
    }

    public AbstractNamedTypeCompare(String name, Class type) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("name must not be blank");
        }

        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }

        this.name = name;
        this.type = type;
    }

    protected boolean isMatched(String field, Class type) {
        return Objects.equals(field, name)
                && (type == this.type || this.type.isAssignableFrom(type));

    }
}
