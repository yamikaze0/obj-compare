package org.yamikaze.compare;

import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.SizeDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 22:53
 */
public class MapCompare extends AbstractCompare<Map<?, ?>> {

    @Override
    public void compareObj(Map<?, ?> expect, Map<?, ?> actual, CompareContext<Map<?, ?>> context) {
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

        Set<?> set = expect.keySet();
        for (Object objKey : set) {
            if (isIgnoreFields(context, objKey)) {
                context.getResult().addSkipField(context.getNPath(objKey));
                continue;
            }

            Object objValue = expect.get(objKey);
            Object compareValue = actual.get(objKey);
            CompareContext<Object> newContext = context.clone(objValue, compareValue);
            newContext.setPath(context.getNPath(objKey));
            CompareFactory.getCompare(Object.class).compareObj(newContext);
        }
    }

    private boolean isIgnoreFields(CompareContext<?> context, Object key) {
        NamedType namedType = new NamedType(key.toString(), key.getClass());
        return super.ignored(context, namedType);
    }
}
