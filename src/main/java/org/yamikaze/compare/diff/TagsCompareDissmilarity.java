package org.yamikaze.compare.diff;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-19 10:16
 */
public class TagsCompareDissmilarity extends AbstractCompareDissmilarity {

    private DifferenceDissmilarity differenceFailItem;

    public TagsCompareDissmilarity(String path) {
        super(path);
    }

    public DifferenceDissmilarity getDifferenceFailItem() {
        return differenceFailItem;
    }

    public void setDifferenceFailItem(DifferenceDissmilarity differenceFailItem) {
        this.differenceFailItem = differenceFailItem;
    }

    @Override
    public String toString() {
        return "标签对比不一致 " + differenceFailItem;
    }
}
