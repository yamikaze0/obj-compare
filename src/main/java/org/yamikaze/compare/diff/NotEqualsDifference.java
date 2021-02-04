package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:44
 */
public class NotEqualsDifference extends AbstractDifference {

    private Object expect;

    private Object actual;

    public NotEqualsDifference(String path) {
        super(path);
    }

    public NotEqualsDifference(String path, Object expect, Object actual) {
        super(path);
        this.expect = expect;
        this.actual = actual;
    }

    public Object getExpect() {
        return expect;
    }

    public void setExpect(Object expect) {
        this.expect = expect;
    }

    public Object getActual() {
        return actual;
    }

    public void setActual(Object actual) {
        this.actual = actual;
    }

    @Override
    public String getMessage() {
        return "对象不一致，比较路径 " + path + ", 期望值为 [" + cast(expect) + "], 实际值为 [" + cast(actual) + "]";
    }

    private String cast(Object val) {
        return String.valueOf(val);
    }
}
