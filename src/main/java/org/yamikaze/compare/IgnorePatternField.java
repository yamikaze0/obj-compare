package org.yamikaze.compare;

import java.util.regex.Pattern;

/**
 *
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:05
 */
public class IgnorePatternField extends IgnoreField {

    private Pattern pattern;

    public IgnorePatternField(String fieldName) {
        super(fieldName);
    }

    @Override
    public boolean ignored(CompareContext<?> context, NamedType field) {
        if (!getIgnoreType().isAssignableFrom(field.getType())) {
            return false;
        }

        if (pattern == null) {
            pattern = Pattern.compile(getFieldName());
        }

        return pattern.matcher(field.getName()).find();

    }
}
