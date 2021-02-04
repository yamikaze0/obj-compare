package org.yamikaze.compare;

import org.yamikaze.compare.diff.NotEqualsDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:22
 */
public class BooleanCompare extends AbstractCompare<Boolean> {

    @Override
    public void compareObj(Boolean expect, Boolean actual, CompareContext<Boolean> context) {
        if (!context.isStrictMode() && InternalCompUtils.isFalse(expect, actual)) {
            return;
        }

        boolean compareResult = Objects.equals(expect, actual);
        if (!compareResult) {
            context.addDiff(new NotEqualsDifference(context.getPath(), expect, actual));
        }
    }
}
