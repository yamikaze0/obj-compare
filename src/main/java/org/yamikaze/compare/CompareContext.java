package org.yamikaze.compare;

import org.yamikaze.compare.diff.Difference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:49
 */
public class CompareContext<T> {

    /**
     * Compare result.
     */
    private CompareResult result;

    /**
     * Expect Object
     */
    private Object expect;

    /**
     * Compare Object
     */
    private Object actual;

    /**
     * The compare path. such as obj.field.subField.
     */
    private String path = "";

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

    /**
     * Current compare depth.
     */
    private int depth;

    /**
     * Recycle checker
     */
    private RecycleChecker recycleChecker = new RecycleChecker();

    private static final List<IgnoreField> DEFAULT_IGNORES = new ArrayList<>(16);

    static {
        // fix inner classes
        DEFAULT_IGNORES.add(new IgnoreField("this$0"));
    }


    public List<IgnoreField> getIgnoreFields() {
        return ignoreFields;
    }

    public List<IgnoreField> getAllIgnoreFields() {
        List<IgnoreField> ifs = new ArrayList<>(16);
        if (ignoreFields != null && !ignoreFields.isEmpty()) {
            ifs.addAll(ignoreFields);
        }

        ifs.addAll(DEFAULT_IGNORES);
        return ifs;
    }

    public RecycleChecker getRecycleChecker() {
        return recycleChecker;
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

    public Object getExpect() {
        return expect;
    }

    public void setExpect(Object expect) {
        this.expect = expect;
    }

    public Object getActual() {
        return actual;
    }

    public void setActual(Object actual) {
        this.actual = actual;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public int getDepth() {
        return depth;
    }

    public void addDiff(Difference diff) {
        result.addFailItem(diff);
    }

    /**
     * Build next path.
     *
     * @param name next name or index.
     * @return     next path.
     */
    public String getNPath(Object name) {
        if (path == null || path.length() == 0) {
            return String.valueOf(name);
        }

        return path + "." + name;
    }

    public CompareContext<Object> clone(Object expect, Object actual) {
        CompareContext<Object> context = new CompareContext<>();
        context.setExpect(expect);
        context.setActual(actual);
        context.setStrictMode(strictMode);
        context.setResult(result);
        context.setIgnoreFields(ignoreFields);
        context.setType(Object.class);
        context.depth = this.depth + 1;
        context.recycleChecker = recycleChecker;
        return context;
    }

    void decr() {
        this.depth--;
    }

}
