package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:35
 */
public class TypeCompareFailItem extends NotEqualsFailItem {

    public TypeCompareFailItem(String path) {
        super(path);
    }

    public TypeCompareFailItem(String path, Object expectVal, Object compareVal) {
        super(path, expectVal, compareVal);
    }

    @Override
    public String toString() {
        return "类型比较失败，比较路径 " + getPath() + ", 期望类型为 [" + cast(getExpectVal()) + "], 实际类型为 [" + cast(getCompareVal()) + "]";
    }

    private String cast(Object val) {
        if (val instanceof Class) {
            return ((Class) val).getSimpleName();
        }

        return "unknown type";
    }
}
