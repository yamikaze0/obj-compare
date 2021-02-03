package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2021/2/4 1:34 上午
 */
public class TypeAssigner {

    /**
     * Check class is assignable target class.
     */
    private final boolean applyAssignable;

    /**
     * Source class.
     */
    private final Class<?> source;

    public TypeAssigner(boolean applyAssignable, Class<?> source) {
        this.applyAssignable = applyAssignable;
        this.source = source;
    }

    public boolean checkCast(Class<?> target) {
        if (applyAssignable) {
            return source.isAssignableFrom(target);
        }

        return source == target;
    }
}
