package org.yamikaze.compare;

import org.yamikaze.compare.diff.AttributeCompareDifference;
import org.yamikaze.compare.diff.DifferenceDissmilarity;
import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.NotEqualsDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:27
 */
public class AttributesNamedTypeCompare extends AbstractNamedTypeCompare<String> {

    private static final String KEY_SEPARATOR = ";";
    private static final String VALUE_SEPARATOR = ":";

    public AttributesNamedTypeCompare(String name, Class<?> type) {
        super(name, type);
    }

    @Override
    public void compareObj(String expect, String actual, CompareContext<String> context) {
        if (!context.isStrictMode() && InternalCompUtils.isBlank(expect, actual)) {
            return;
        }

        boolean finished = false;
        if (expect == null || actual == null) {
            finished = true;
            context.addDiff(new NullOfOneObject(context.getPath(), expect, actual));
        }

        if (finished) {
            return;
        }

        // fast compare.
        if (Objects.equals(expect, actual)) {
            return;
        }


        boolean hasError = false;
        Map<String, String> expectAttributeMap = parseAttribute(expect);
        Map<String, String> compareAttributeMap = parseAttribute(actual);

        AttributeCompareDifference failItem = new AttributeCompareDifference(context.getPath());
        DifferenceDissmilarity differenceFailItem = new DifferenceDissmilarity();

        Set<String> keys = expectAttributeMap.keySet();
        Set<String> compareKeys = compareAttributeMap.keySet();

        for (String key : keys) {
            String expectVal = expectAttributeMap.get(key);
            String compareVal = compareAttributeMap.get(key);

            if (compareVal == null) {
                hasError = true;
                differenceFailItem.addExpectKey(key);
                continue;
            }

            compareKeys.remove(key);

            if (!Objects.equals(expectVal, compareVal)) {
                hasError = true;
                NotEqualsDifference item = new NotEqualsDifference(context.getPath() + "." + key);
                item.setExpect(expectVal);
                item.setActual(compareVal);
                failItem.addNotEqualsFailItem(item);
            }
        }

        differenceFailItem.getCompareKeys().addAll(compareKeys);
        failItem.setDifferenceFailItem(differenceFailItem);

        if (hasError) {
            context.addDiff(failItem);
        }

    }

    private Map<String, String> parseAttribute(String attributes) {
        String[] expectAttributes = attributes.split(KEY_SEPARATOR);

        Map<String, String> attributesMap = new HashMap<>(16);

        for (String expectAttribute : expectAttributes) {
            String[] split = expectAttribute.split(VALUE_SEPARATOR);
            // ignore non-standard attributes.
            if (split.length != 2) {
                continue;
            }

            attributesMap.put(split[0], split[1]);
        }

        return attributesMap;
    }
}
