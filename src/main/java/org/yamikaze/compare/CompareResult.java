package org.yamikaze.compare;

import org.yamikaze.compare.diff.Difference;

import java.util.ArrayList;
import java.util.List;

import static org.yamikaze.compare.utils.InternalCompUtils.appendNewLine;
import static org.yamikaze.compare.utils.InternalCompUtils.appendTab;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 14:57
 */
public class CompareResult {

    /**
     * Compare result.
     */
    private boolean isSame = false;

    /**
     * The compare skip fields.
     */
    private final List<String> skipFields = new ArrayList<>(16);

    /**
     * The compare difference items.
     */
    private final List<Difference> differences = new ArrayList<>(16);

    /**
     * Some tip messages in compare process.
     */
    private final List<String> cmpMessages = new ArrayList<>(32);

    public List<Difference> getDifferences() {
        return differences;
    }

    public boolean isSame() {
        return isSame || differences.isEmpty();
    }

    public void setSame(boolean same) {
        this.isSame = same;
    }

    public List<String> getSkipFields() {
        return skipFields;
    }

    public List<String> getCmpMessages() {
        return cmpMessages;
    }

    public void addSkipField(String field) {
        if (skipFields.contains(field)) {
            return;
        }

        skipFields.add(field);
    }

    public void addDiff(Difference diff) {
        differences.add(diff);
    }

    public void addCmpMsg(String msg) {
        this.cmpMessages.add(msg);
    }


    @Override
    public String toString() {
        isSame = differences.isEmpty();
        StringBuilder sb = new StringBuilder(512);
        sb.append("compare result is ").append(isSame);

        if (skipFields.size() > 0) {
            appendNewLine(sb);
            layoutMessage(sb, "compare skipFields are ", skipFields);
        }

        if (cmpMessages.size() > 0) {
            appendNewLine(sb);
            layoutMessage(sb, "messages: ", cmpMessages);
        }

        if (differences.size() > 0) {
            appendNewLine(sb);
            layoutMessage(sb, "compare differences are ", differences);
        }
        return sb.toString();
    }

    private void layoutMessage(StringBuilder sb, String prefix, List<?> messages) {
        appendNewLine(sb);
        sb.append(prefix);
        int index = 0;
        for (Object msg : messages) {
            appendNewLine(sb);
            appendTab(sb);
            sb.append(++index).append(":").append(msg);
        }
    }
}
