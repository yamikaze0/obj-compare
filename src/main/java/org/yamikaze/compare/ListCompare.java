package org.yamikaze.compare;

import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.SizeDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:31
 */
public class ListCompare extends AbstractCompare<List<?>>{

    @Override
    public void compareObj(List<?> expect, List<?> actual, CompareContext<List<?>> context) {
        if (!context.isStrictMode() && InternalCompUtils.isEmpty(expect) && InternalCompUtils.isEmpty(actual)) {
            return;
        }

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

        for (int index = 0; index < expectSize; index++) {
            Object newExpect = expect.get(index);
            Object newActual = actual.get(index);

            if (context.getRecycleChecker().isRecycle(newExpect, newActual)) {
                continue;
            }

            context.getRecycleChecker().addRecycle(newExpect, newActual);

            CompareContext<Object> nc = context.clone(newExpect, newActual);
            nc.setPath(nc.getNPath(index));
            CompareFactory.getCompare(Object.class).compareObj(nc);
        }

    }
}
