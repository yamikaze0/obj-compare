package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:18
 */
public class NullOfOneObject extends NotEqualsDifference {

    public NullOfOneObject(String path) {
        super(path);
    }

    public NullOfOneObject(String path, Object expectVal, Object compareVal) {
        super(path, expectVal, compareVal);
    }

    @Override
    public String toString() {
        return "对象不一致，比较路径 " + path + ", 期望值为 " + cast(getExpect()) + ", 实际值为 " + cast(getActual());
    }

    private String cast(Object val) {

        if (val == null) {
            return "is null";
        }

        return "is not null";
    }
}
