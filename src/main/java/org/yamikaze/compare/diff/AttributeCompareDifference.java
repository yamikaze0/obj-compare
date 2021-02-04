package org.yamikaze.compare.diff;

import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:44
 */
public class AttributeCompareDifference extends AbstractDifference {

    private List<NotEqualsDifference> notEqualsFailItems = new ArrayList<>();

    private DifferenceDissmilarity differenceFailItem;

    public AttributeCompareDifference(String path) {
        super(path);
    }

    public void addNotEqualsFailItem(NotEqualsDifference notEqualsFailItem) {
        notEqualsFailItems.add(notEqualsFailItem);
    }

    public List<NotEqualsDifference> getNotEqualsFailItems() {
        return notEqualsFailItems;
    }

    public void setNotEqualsFailItems(List<NotEqualsDifference> notEqualsFailItems) {
        this.notEqualsFailItems = notEqualsFailItems;
    }

    public DifferenceDissmilarity getDifferenceFailItem() {
        return differenceFailItem;
    }

    public void setDifferenceFailItem(DifferenceDissmilarity differenceFailItem) {
        this.differenceFailItem = differenceFailItem;
    }


    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("属性比较失败 : path = ").append(path);
        InternalCompUtils.appendNewLine(sb);
        InternalCompUtils.appendTab(sb);

        if (!notEqualsFailItems.isEmpty()) {
            InternalCompUtils.appendTab(sb);
            sb.append("** 不相等的属性项 : ");
            for (NotEqualsDifference failItem : notEqualsFailItems) {
                InternalCompUtils.appendNewLine(sb);
                InternalCompUtils.appendTab(sb);
                InternalCompUtils.appendTab(sb);
                InternalCompUtils.appendTab(sb);
                sb.append(failItem);
            }
        }

        boolean hasDifferenceFailItem = differenceFailItem != null
                && (differenceFailItem.getCompareKeys().size() != 0 || differenceFailItem.getExpectKeys().size() != 0);
        if (hasDifferenceFailItem) {
            InternalCompUtils.appendNewLine(sb);
            InternalCompUtils.appendTab(sb);
            InternalCompUtils.appendTab(sb);
            sb.append("** 属性项补集 : ");
            sb.append(differenceFailItem);
        }

        return sb.toString();
    }
}
