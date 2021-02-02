package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:44
 */
public class NotEqualsFailItem extends AbstractCompareFailItem {

    private Object expectVal;

    private Object compareVal;

    public NotEqualsFailItem(String path) {
        super(path);
    }

    public NotEqualsFailItem(String path, Object expectVal, Object compareVal) {
        super(path);
        this.expectVal = expectVal;
        this.compareVal = compareVal;
    }

    public Object getExpectVal() {
        return expectVal;
    }

    public void setExpectVal(Object expectVal) {
        this.expectVal = expectVal;
    }

    public Object getCompareVal() {
        return compareVal;
    }

    public void setCompareVal(Object compareVal) {
        this.compareVal = compareVal;
    }

    @Override
    public String toString() {
        return "对象不一致，比较路径 " + getPath() + ", 期望值为 [" + cast(getExpectVal()) + "], 实际值为 [" + cast(getCompareVal()) + "]";
    }

    private String cast(Object val) {
        return String.valueOf(val);
    }
}
