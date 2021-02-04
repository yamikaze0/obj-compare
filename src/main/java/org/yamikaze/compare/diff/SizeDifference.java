package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:19
 */
public class SizeDifference extends NotEqualsDifference {

    public SizeDifference(String path) {
        super(path);
    }

    public SizeDifference(String path, int expectValSize, int compareValSize) {
        super(path, expectValSize, compareValSize);
    }

    @Override
    public String toString() {
        return "集合类数量比较失败，比较路径 " + path + ", 期望大小为 [" + cast(getExpect()) + "], 实际大小为 [" + cast(getActual()) + "]";
    }

    private int cast(Object val) {
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }

        return 0;
    }
}
