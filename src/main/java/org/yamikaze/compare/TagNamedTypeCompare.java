package org.yamikaze.compare;

import org.yamikaze.compare.diff.DifferenceDissmilarity;
import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.TagsCompareDifference;
import org.yamikaze.compare.utils.InternalCompUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:27
 */
public class TagNamedTypeCompare extends AbstractNamedTypeCompare<String> {

    private static final String VALUE_SEPARATOR = ",";

    public TagNamedTypeCompare(String name, Class<?> type) {
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
        Set<String> expectTags = parseTags(expect);
        Set<String> compareTags = parseTags(actual);

        TagsCompareDifference failItem = new TagsCompareDifference(context.getPath());
        DifferenceDissmilarity differenceFailItem = new DifferenceDissmilarity();

        for (String key : expectTags) {

            if (!compareTags.remove(key)) {
                hasError = true;
                differenceFailItem.addExpectKey(key);
            }
        }

        differenceFailItem.getCompareKeys().addAll(compareTags);
        failItem.setDifferenceFailItem(differenceFailItem);

        if (hasError) {
            context.addDiff(failItem);
        }

    }

    private Set<String> parseTags(String attributes) {
        String[] expectTags = attributes.split(VALUE_SEPARATOR);

        Set<String> tags = new HashSet<>(16);

        for (String expectTag : expectTags) {
            if (expectTag == null || expectTag.trim().length() == 0) {
                continue;
            }

            tags.add(expectTag.trim());
        }

        return tags;
    }
}
