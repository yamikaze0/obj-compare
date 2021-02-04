package org.yamikaze.compare;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:56
 */
public class NamedType {

    /**
     * Name
     */
    private String name;

    /**
     * Type
     */
    private Class<?> type;

    public NamedType(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NamedType)) {
            return false;
        }

        NamedType nt = (NamedType)obj;
        return Objects.equals(nt.name, this.name) && nt.type == this.type;
    }
}
