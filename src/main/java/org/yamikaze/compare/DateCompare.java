package org.yamikaze.compare;

import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.NotEqualsDissmilarity;

import java.util.Date;
import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-05-15 16:58
 */
public class DateCompare extends AbstractCompare<Date>{

    @Override
    public void compareObj(Date expectObject, Date compareObject, CompareContext<Date> context) {
        if (expectObject == null || compareObject == null) {
            context.addDiff(new NullOfOneObject(context.generatePrefix(), expectObject, compareObject));
            return;
        }

        boolean result = Objects.equals(expectObject, compareObject);
        if (!result) {
            context.addDiff(new NotEqualsDissmilarity(context.generatePrefix(), expectObject, compareObject));
        }
    }
}
