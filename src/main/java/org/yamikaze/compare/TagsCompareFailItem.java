package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-19 10:16
 */
public class TagsCompareFailItem extends AbstractCompareFailItem {

    private DifferenceFailItem differenceFailItem;

    public TagsCompareFailItem(String path) {
        super(path);
    }

    public DifferenceFailItem getDifferenceFailItem() {
        return differenceFailItem;
    }

    public void setDifferenceFailItem(DifferenceFailItem differenceFailItem) {
        this.differenceFailItem = differenceFailItem;
    }

    @Override
    public String toString() {
        return "标签对比不一致 " + differenceFailItem;
    }
}
