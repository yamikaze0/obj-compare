package org.yamikaze.compare;

import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:31
 */
public class ListCompare extends AbstractCompare<List>{

    @Override
    public void compareObj(List expectObject, List compareObject, CompareContext<List> context) {
        if (!context.isStrictMode()) {
            if (isEmpty(expectObject) && isEmpty(compareObject)) {
                return;
            }
        }

        if (expectObject == null || compareObject == null) {
            context.addFailItem(new HasNullFailItem(context.generatePrefix(), expectObject, compareObject));
            return;
        }

        int size1 = expectObject.size();
        int size2 = compareObject.size();
        if (size1 != size2) {
            context.addFailItem(new SizeCompareFailItem(context.generatePrefix(), size1, size2));
            return;
        }

        for (int index = 0; index < size1; index++) {
            Object item1 = expectObject.get(index);
            Object item2 = compareObject.get(index);
            CompareContext<Object> context1 = context.clone(item1, item2);
            context1.setComparePath(prefix(context.getComparePath()) + index);
            CompareFactory.getCompare(Object.class).compareObj(context1);
        }

    }
}
