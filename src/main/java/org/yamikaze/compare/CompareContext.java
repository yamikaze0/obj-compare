package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:49
 */
public class CompareContext<T> {

    private static final String COMPARE_FAIL = "compare fail";

    /**
     * Compare result.
     */
    private CompareResult result;

    /**
     * Expect Object
     */
    private Object expectObject;

    /**
     * Compare Object
     */
    private Object compareObject;

    /**
     * The compare path. such as obj.field.subField.
     */
    private String comparePath;

    /**
     * The compare class type.
     */
    private Class<T> type;

    /**
     * The mode.
     */
    private boolean strictMode;

    /**
     * The compare ignore fields.
     */
    private List<IgnoreField> ignoreFields;

    private static List<IgnoreField> systemIgnoreFields = new ArrayList<>(16);

    static {
        // fix inner classes
        systemIgnoreFields.add(new IgnoreField("this$0"));
    }


    public List<IgnoreField> getIgnoreFields() {
        return ignoreFields;
    }

    public List<IgnoreField> getAllIgnoreFields() {
        List<IgnoreField> ifs = new ArrayList<>(16);
        if (ignoreFields != null && !ignoreFields.isEmpty()) {
            ifs.addAll(ignoreFields);
        }

        ifs.addAll(systemIgnoreFields);
        return ifs;
    }

    public void setIgnoreFields(List<IgnoreField> ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    public boolean isStrictMode() {
        return strictMode;
    }

    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }

    public CompareResult getResult() {
        return result;
    }

    public void setResult(CompareResult result) {
        this.result = result;
    }

    public Object getExpectObject() {
        return expectObject;
    }

    public void setExpectObject(Object expectObject) {
        this.expectObject = expectObject;
    }

    public Object getCompareObject() {
        return compareObject;
    }

    public void setCompareObject(Object compareObject) {
        this.compareObject = compareObject;
    }

    public String getComparePath() {
        return comparePath;
    }

    public void setComparePath(String comparePath) {
        this.comparePath = comparePath;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void addFailItem(CompareFailItem failItem) {
        result.addFailItem(failItem);
    }

    public String generatePrefix() {
        if (CompareUtils.isBlank(comparePath)) {
            return "";
        }

        return comparePath;
    }

    public CompareContext<Object> clone(Object obj, Object compare) {
        CompareContext<Object> context1 = new CompareContext<>();
        context1.setExpectObject(obj);
        context1.setCompareObject(compare);
        context1.setStrictMode(this.isStrictMode());
        context1.setResult(this.getResult());
        context1.setIgnoreFields(this.getIgnoreFields());
        context1.setType(Object.class);
        return context1;
    }


}
