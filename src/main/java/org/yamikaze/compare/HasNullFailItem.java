package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:18
 */
public class HasNullFailItem extends NotEqualsFailItem {

    public HasNullFailItem(String path) {
        super(path);
    }

    public HasNullFailItem(String path, Object expectVal, Object compareVal) {
        super(path, expectVal, compareVal);
    }

    @Override
    public String toString() {
        return "对象不一致，比较路径 " + getPath() + ", 期望值为 " + cast(getExpectVal()) + ", 实际值为 " + cast(getCompareVal());
    }

    private String cast(Object val) {

        if (val == null) {
            return "is null";
        }

        return "is not null";
    }
}
