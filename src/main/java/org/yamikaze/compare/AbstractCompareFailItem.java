package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:10
 */
public abstract class AbstractCompareFailItem implements CompareFailItem {

    private String path;

    public AbstractCompareFailItem(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
