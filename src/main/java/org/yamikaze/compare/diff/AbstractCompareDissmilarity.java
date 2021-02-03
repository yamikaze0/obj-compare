package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:10
 */
public abstract class AbstractCompareDissmilarity implements Difference {

    private String path;

    public AbstractCompareDissmilarity(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
