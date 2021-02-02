package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:23
 */
public class ObjectEqualsUtils {

    private static final List<String> DEFAULT_SKIP_LIST = Collections.emptyList();

    public static CompareResult objEquals(Object expectObject, Object compareObject, List<String> skipField, boolean mode) {
        CompareContext<Object> compare = new CompareContext<>();
        compare.setType(Object.class);
        compare.setComparePath("");
        compare.setExpectObject(expectObject);
        compare.setCompareObject(compareObject);
        compare.setStrictMode(mode);
        compare.setResult(new CompareResult());

        if (skipField != null && !skipField.isEmpty()) {
            List<IgnoreField> ignoreFields = new ArrayList<>(skipField.size());
            skipField.forEach(p -> ignoreFields.add(new IgnoreField(p)));
            compare.setIgnoreFields(ignoreFields);
        }

        CompareFactory.getCompare(Object.class).compareObj(compare);

        return compare.getResult();
    }

    public static CompareResult objEquals(Object expectObject, Object compareObject) {
        return objEquals(expectObject, compareObject, DEFAULT_SKIP_LIST, false);
    }

    public static CompareResult objEquals(Object expectObject, Object compareObject, List<String> skipField) {
        return objEquals(expectObject, compareObject, skipField, false);
    }

    public static CompareResult objEquals(Object expectObject, Object compareObject, boolean mode) {
        return objEquals(expectObject, compareObject, DEFAULT_SKIP_LIST, mode);
    }


    public static CompareResult compare(Object expectObject, Object compareObject, List<IgnoreField> ignoreFields, boolean mode) {
        CompareContext<Object> compare = new CompareContext<>();
        compare.setType(Object.class);
        compare.setComparePath("");
        compare.setExpectObject(expectObject);
        compare.setCompareObject(compareObject);
        compare.setStrictMode(mode);
        compare.setResult(new CompareResult());
        compare.setIgnoreFields(ignoreFields);
        CompareFactory.getCompare(Object.class).compareObj(compare);

        return compare.getResult();
    }

    public static CompareResult compare(Object expectObject, Object compareObject) {
        return compare(expectObject, compareObject, null, false);
    }

    public static CompareResult compare(Object expectObject, Object compareObject, List<IgnoreField> ignoreFields) {
        return compare(expectObject, compareObject, ignoreFields, false);
    }

    public static CompareResult compare(Object expectObject, Object compareObject, boolean mode) {
        return compare(expectObject, compareObject, null, mode);
    }
}


