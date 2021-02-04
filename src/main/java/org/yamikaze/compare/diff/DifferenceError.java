package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:38
 */
public class DifferenceError implements Difference {

    private final Exception exception;

    public DifferenceError(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String getMessage() {
        return "比较发生异常 {" +
                "exception=" + exception +
                '}';
    }
}
