package org.yamikaze.compare;

import org.yamikaze.compare.diff.Difference;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:38
 */
public class CompareErrorItem implements Difference {

    private Exception exception;

    public CompareErrorItem(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String toString() {
        return "比较发生异常 {" +
                "exception=" + exception +
                '}';
    }
}
