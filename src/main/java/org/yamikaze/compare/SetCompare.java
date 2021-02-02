package org.yamikaze.compare;

import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 22:53
 */
public class SetCompare extends AbstractCompare<Set> {

    @Override
    public void compareObj(Set expectObject, Set compareObject, CompareContext<Set> context) {

        boolean objIsEmpty = isEmpty(expectObject);
        boolean compareIsEmpty = isEmpty(compareObject);

        if (!context.isStrictMode()) {
            if (objIsEmpty && compareIsEmpty) {
                return;
            }
        }

        boolean objIsNull = expectObject == null;
        boolean compareIsNull = compareObject == null;

        //有一个为空
        if (objIsNull || compareIsNull) {
            context.addFailItem(new HasNullFailItem(context.generatePrefix(), expectObject, compareObject));
            return;
        }

        int objSize = expectObject.size();
        int compareSize = compareObject.size();

        if (objSize != compareSize) {
            context.addFailItem(new SizeCompareFailItem(context.generatePrefix(), objSize, compareSize));
            return;
        }

        for (Object setKey : expectObject) {
            if (compareObject.contains(setKey)) {
                continue;
            }

            context.addFailItem(new NotEqualsFailItem(context.generatePrefix() + "." + setKey, setKey, null));
        }
    }
}
