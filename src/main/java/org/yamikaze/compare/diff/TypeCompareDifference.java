package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:35
 */
public class TypeCompareDifference extends NotEqualsDifference {

    public TypeCompareDifference(String path) {
        super(path);
    }

    public TypeCompareDifference(String path, Object expectVal, Object compareVal) {
        super(path, expectVal, compareVal);
    }

    @Override
    public String toString() {
        return "类型比较失败，比较路径 " + path + ", 期望类型为 [" + cast(getExpect()) + "], 实际类型为 [" + cast(getActual()) + "]";
    }

    private String cast(Object val) {
        if (val instanceof Class) {
            return ((Class<?>) val).getSimpleName();
        }

        return "unknown type";
    }
}
