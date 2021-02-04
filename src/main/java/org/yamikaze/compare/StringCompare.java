package org.yamikaze.compare;

import org.yamikaze.compare.diff.NotEqualsDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:53
 */
public class StringCompare extends AbstractCompare<String> {

    @Override
    public void compareObj(String expect, String actual, CompareContext<String> context) {
        if (!context.isStrictMode() && InternalCompUtils.isBlank(expect, actual)) {
            return;
        }

        boolean result = Objects.equals(expect, actual);
        if (!result) {
            context.addDiff(new NotEqualsDifference(context.getPath(), expect, actual));
        }
    }
}

