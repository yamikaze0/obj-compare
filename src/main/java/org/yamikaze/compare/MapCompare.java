package org.yamikaze.compare;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 22:53
 */
public class MapCompare extends AbstractCompare<Map> {

    @Override
    public void compareObj(Map expectObject, Map compareObject, CompareContext<Map> context) {

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

        Set set = expectObject.keySet();
        for (Object objKey : set) {
            if (isIgnoreFields(context, objKey)) {
                context.getResult().addSkipField(prefix(context.getComparePath()) + objKey);
                continue;
            }

            Object objValue = expectObject.get(objKey);
            Object compareValue = compareObject.get(objKey);
            CompareContext<Object> newContext = context.clone(objValue, compareValue);
            newContext.setComparePath(prefix(context.getComparePath()) + objKey);
            CompareFactory.getCompare(Object.class).compareObj(newContext);
        }
    }

    private boolean isIgnoreFields(CompareContext<?> context, Object key) {
        List<IgnoreField> ignoreFields = context.getAllIgnoreFields();
        if (ignoreFields == null || ignoreFields.isEmpty()) {
            return false;
        }

        NamedType namedType = new NamedType(key.toString(), key.getClass());
        for (IgnoreField ignoreField : ignoreFields) {
            if (ignoreField.ignored(context, namedType)) {
                return true;
            }
        }

        return false;
    }
}
