package org.yamikaze.compare;

import java.util.regex.Pattern;

/**
 *
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 15:05
 */
public class IgnorePatternField extends IgnoreField {

    private final Pattern pattern;

    public IgnorePatternField(String fieldName) {
        super(fieldName);
        pattern = Pattern.compile(getName());
    }

    @Override
    public boolean ignored(CompareContext<?> context, NamedType field) {
        if (!checkType(field.getType())) {
            return false;
        }

        return pattern.matcher(field.getName()).find();

    }
}
