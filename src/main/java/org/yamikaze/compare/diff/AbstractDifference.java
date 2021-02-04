package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:10
 */
public abstract class AbstractDifference implements Difference {

    /**
     * Compare path.
     */
    protected final String path;

    public AbstractDifference(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + getMessage();
    }
}
