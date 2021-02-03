package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 16:19
 */
public class SizeCompareDissmilarity extends NotEqualsDissmilarity {

    public SizeCompareDissmilarity(String path) {
        super(path);
    }

    public SizeCompareDissmilarity(String path, int expectValSize, int compareValSize) {
        super(path, expectValSize, compareValSize);
    }

    @Override
    public String toString() {
        return "集合类数量比较失败，比较路径 " + getPath() + ", 期望大小为 [" + cast(getExpectVal()) + "], 实际大小为 [" + cast(getCompareVal()) + "]";
    }

    private int cast(Object val) {
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }

        return 0;
    }
}
