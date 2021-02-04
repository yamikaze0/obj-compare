package org.yamikaze.compare;

import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.NotEqualsDifference;

import java.util.Date;
import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-05-15 16:58
 */
public class DateCompare extends AbstractCompare<Date>{

    @Override
    public void compareObj(Date expect, Date actual, CompareContext<Date> context) {
        if (expect == null || actual == null) {
            context.addDiff(new NullOfOneObject(context.getPath(), expect, actual));
            return;
        }

        boolean result = Objects.equals(expect, actual);
        if (!result) {
            context.addDiff(new NotEqualsDifference(context.getPath(), expect, actual));
        }
    }
}
