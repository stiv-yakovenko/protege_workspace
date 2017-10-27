package org.protege.editor.core.log;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 04/11/15
 */
public class LogRecordModel extends AbstractListModel<LogRecordElement> {

    private final List<LogRecordElement> logRecordList = new ArrayList<>();

    public void clear() {
        logRecordList.clear();
        fireContentsChanged(this, 0, 0);
    }

    @Override
    public int getSize() {
        return logRecordList.size();
    }

    @Override
    public LogRecordElement getElementAt(int index) {
        return logRecordList.get(index);
    }

    public void append(LogRecord record) {
        logRecordList.add(new LogRecordElement(record));
        int lastIndex = logRecordList.size() - 1;
        fireIntervalAdded(this, lastIndex, lastIndex);
    }
}
