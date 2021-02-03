package org.yamikaze.compare;

import org.yamikaze.compare.diff.NotEqualsDissmilarity;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:53
 */
public class StringCompare extends AbstractCompare<String> {

    @Override
    public void compareObj(String expectObject, String compareObject, CompareContext<String> context) {
        if (!context.isStrictMode()) {
            if (isBlank(expectObject) && isBlank(compareObject)) {
                return;
            }
        }

        boolean result = Objects.equals(expectObject, compareObject);
        if (!result) {
            context.addDiff(new NotEqualsDissmilarity(context.generatePrefix(), expectObject, compareObject));
        }
    }
}

