package org.yamikaze.compare;

import org.yamikaze.compare.diff.NotEqualsDifference;
import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.SizeDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 22:53
 */
public class SetCompare extends AbstractCompare<Set<?>> {

    @Override
    public void compareObj(Set<?> expect, Set<?> actual, CompareContext<Set<?>> context) {
        if (!context.isStrictMode() && InternalCompUtils.isEmpty(expect) && InternalCompUtils.isEmpty(actual)) {
            return;
        }

        //有一个为空
        if (expect == null || actual == null) {
            context.addDiff(new NullOfOneObject(context.getPath(), expect, actual));
            return;
        }

        int expectSize = expect.size();
        int actualSize = actual.size();

        if (expectSize != actualSize) {
            context.addDiff(new SizeDifference(context.getPath(), expectSize, actualSize));
            return;
        }

        for (Object setKey : expect) {
            if (actual.contains(setKey)) {
                continue;
            }

            context.addDiff(new NotEqualsDifference(context.getNPath(setKey), setKey, null));
        }
    }
}
