package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:50
 */
public class DifferenceFailItem implements CompareFailItem {

    private List<String> expectKeys = new ArrayList<>();

    private List<String> compareKeys = new ArrayList<>();

    public void addExpectKey(String expectKey) {
        expectKeys.add(expectKey);
    }

    public void addCompareKey(String compareKey) {
        compareKeys.add(compareKey);
    }

    public List<String> getExpectKeys() {
        return expectKeys;
    }

    public List<String> getCompareKeys() {
        return compareKeys;
    }

    @Override
    public String toString() {
        return "集合Key包含不一致, 期望对象补集为 " + cast(expectKeys) + ", 实际对象补集为 " + cast(compareKeys);
    }

    private String cast(List<String> val) {
        return String.valueOf(val);
    }
}
