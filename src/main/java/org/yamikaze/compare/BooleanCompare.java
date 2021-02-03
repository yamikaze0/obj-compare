package org.yamikaze.compare;

import org.yamikaze.compare.diff.NotEqualsDissmilarity;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:22
 */
public class BooleanCompare extends AbstractCompare<Boolean> {

    @Override
    public void compareObj(Boolean expectObject, Boolean compareObject, CompareContext<Boolean> context) {
        if (!context.isStrictMode()) {
            if (isFalse(expectObject) && isFalse(compareObject)) {
                return;
            }
        }

        boolean compareResult = Objects.equals(expectObject, compareObject);
        if (!compareResult) {
            context.addDiff(new NotEqualsDissmilarity(context.generatePrefix(), expectObject, compareObject));
        }
    }

    private boolean isFalse(Boolean obj) {
        return obj == null || !obj;
    }
}
