package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:44
 */
public class AttributeCompareFailItem extends AbstractCompareFailItem {

    private List<NotEqualsFailItem> notEqualsFailItems = new ArrayList<>();

    private DifferenceFailItem differenceFailItem;

    public AttributeCompareFailItem(String path) {
        super(path);
    }

    public void addNotEqualsFailItem(NotEqualsFailItem notEqualsFailItem) {
        notEqualsFailItems.add(notEqualsFailItem);
    }

    public List<NotEqualsFailItem> getNotEqualsFailItems() {
        return notEqualsFailItems;
    }

    public void setNotEqualsFailItems(List<NotEqualsFailItem> notEqualsFailItems) {
        this.notEqualsFailItems = notEqualsFailItems;
    }

    public DifferenceFailItem getDifferenceFailItem() {
        return differenceFailItem;
    }

    public void setDifferenceFailItem(DifferenceFailItem differenceFailItem) {
        this.differenceFailItem = differenceFailItem;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("属性比较失败 : path = ").append(getPath());
        CompareUtils.appendNewLine(sb);
        CompareUtils.appendTab(sb);

        if (!notEqualsFailItems.isEmpty()) {
            CompareUtils.appendTab(sb);
            sb.append("** 不相等的属性项 : ");
            for (NotEqualsFailItem failItem : notEqualsFailItems) {
                CompareUtils.appendNewLine(sb);
                CompareUtils.appendTab(sb);
                CompareUtils.appendTab(sb);
                CompareUtils.appendTab(sb);
                sb.append(failItem);
            }
        }

        boolean hasDifferenceFailItem = differenceFailItem != null
                && (differenceFailItem.getCompareKeys().size() != 0 || differenceFailItem.getExpectKeys().size() != 0);
        if (hasDifferenceFailItem) {
            CompareUtils.appendNewLine(sb);
            CompareUtils.appendTab(sb);
            CompareUtils.appendTab(sb);
            sb.append("** 属性项补集 : ");
            sb.append(differenceFailItem);
        }

        return sb.toString();
    }
}
