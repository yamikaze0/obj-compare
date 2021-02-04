package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:23
 */
public class CompareObjectUtils {

    private static final List<String> DEFAULT_SKIP_LIST = Collections.emptyList();

    public static CompareResult compareOld(Object expect, Object actual, List<String> skipField, boolean mode) {
        CompareContext<Object> compare = new CompareContext<>();
        compare.setType(Object.class);
        compare.setExpect(expect);
        compare.setActual(actual);
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

    public static CompareResult compareOld(Object expect, Object actual) {
        return compareOld(expect, actual, DEFAULT_SKIP_LIST, false);
    }

    public static CompareResult compareOld(Object expect, Object actual, List<String> skipField) {
        return compareOld(expect, actual, skipField, false);
    }

    public static CompareResult compareOld(Object expect, Object actual, boolean mode) {
        return compareOld(expect, actual, DEFAULT_SKIP_LIST, mode);
    }


    public static CompareResult compare(Object expect, Object actual, List<IgnoreField> ignoreFields, boolean mode) {
        CompareContext<Object> compare = new CompareContext<>();
        compare.setType(Object.class);
        compare.setExpect(expect);
        compare.setActual(actual);
        compare.setStrictMode(mode);
        compare.setResult(new CompareResult());
        compare.setIgnoreFields(ignoreFields);
        CompareFactory.getCompare(Object.class).compareObj(compare);

        return compare.getResult();
    }

    public static CompareResult compare(Object expect, Object actual) {
        return compare(expect, actual, null, false);
    }

    public static CompareResult compare(Object expect, Object actual, List<IgnoreField> ignoreFields) {
        return compare(expect, actual, ignoreFields, false);
    }

    public static CompareResult compare(Object expect, Object actual, boolean mode) {
        return compare(expect, actual, null, mode);
    }
}


