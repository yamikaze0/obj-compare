package org.yamikaze.compare;

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

    public AttributesNamedTypeCompare(String name, Class type) {
        super(name, type);
    }

    @Override
    public void compareObj(String expectObject, String compareObject, CompareContext<String> context) {
        if (!context.isStrictMode()) {
            if (isBlank(expectObject) && isBlank(compareObject)) {
                return;
            }
        }

        boolean finished = false;
        if (expectObject == null || compareObject == null) {
            finished = true;
            context.addFailItem(new HasNullFailItem(context.generatePrefix(), expectObject, compareObject));
        }

        if (finished) {
            return;
        }

        // fast compare.
        if (Objects.equals(expectObject, compareObject)) {
            return;
        }


        boolean hasError = false;
        Map<String, String> expectAttributeMap = parseAttribute(expectObject);
        Map<String, String> compareAttributeMap = parseAttribute(compareObject);

        AttributeCompareFailItem failItem = new AttributeCompareFailItem(context.generatePrefix());
        DifferenceFailItem differenceFailItem = new DifferenceFailItem();

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
                NotEqualsFailItem item = new NotEqualsFailItem(context.generatePrefix() + "." + key);
                item.setExpectVal(expectVal);
                item.setCompareVal(compareVal);
                failItem.addNotEqualsFailItem(item);
            }
        }

        differenceFailItem.getCompareKeys().addAll(compareKeys);
        failItem.setDifferenceFailItem(differenceFailItem);

        if (hasError) {
            context.addFailItem(failItem);
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
