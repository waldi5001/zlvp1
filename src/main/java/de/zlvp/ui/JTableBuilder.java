package de.zlvp.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;

import de.javasoft.swing.JYTable;
import de.javasoft.swing.JYTableHeader;
import de.javasoft.swing.jytable.renderer.CellLayoutHint;
import de.javasoft.swing.jytable.sort.JYTableSortController;
import de.javasoft.swing.table.ObjectTableCellEditor;
import de.zlvp.controller.AsyncCallback;

public class JTableBuilder<E> {

    private final List<Column<?>> columns = new ArrayList<>();

    private JTable table;

    private List<E> data;

    private final Set<E> changedData = new HashSet<>();

    private ObjectGetter<E> mapper;
    private final Loader<E> loader;
    private ObjectSetter<E> objectSetter;
    private Saver<E> saver;
    private Deleter<E> deleter;
    private boolean silentSaveOnClick = false;

    public static <E> JTableBuilder<E> get(Class<E> clazz, Loader<E> loader) {
        return new JTableBuilder<>(loader);
    }

    private JTableBuilder(Loader<E> loader) {
        this.loader = loader;
    }

    public void refresh() {
        loader.get(result -> {
            this.table.setRowSorter(null);
            this.table.setModel(new TableModel<>(changedData, columns, data = result, mapper, objectSetter,
                    silentSaveOnClick, saver));
            setColumns();
        });
    }

    public void save() {
        for (E d : changedData) {
            saver.save(d, asyncCallback -> {
                changedData.clear();
                refresh();
            });
        }
    }

    public JTable build() {
        this.table = new JYTable();
        JYTableHeader header = (JYTableHeader) table.getTableHeader();
        CellLayoutHint hint = header.getCellLayoutHint();

        header.setCellLayoutHint(
                new CellLayoutHint(hint.sortMarkerPosition, SwingConstants.CENTER, hint.verticalAlignment));

        for (int i = 0; i < columns.size(); i++) {
            Column<?> column = columns.get(i);
            if (column.isMultiline()) {
                this.table.setRowHeight(this.table.getRowHeight() * 10);
                break;
            }
        }

        refresh();

        return this.table;
    }

    public JTableBuilder<E> addColumn(Column<?> column) {
        this.columns.add(column);
        return this;
    }

    public JTableBuilder<E> get(ObjectGetter<E> mapper) {
        this.mapper = mapper;
        return this;
    }

    public JTableBuilder<E> set(ObjectSetter<E> objectSetter) {
        this.objectSetter = objectSetter;
        return this;
    }

    public JTableBuilder<E> save(Saver<E> saver) {
        this.saver = saver;
        return this;
    }

    public JTableBuilder<E> delete(Deleter<E> deleter) {
        this.deleter = deleter;
        return this;
    }

    public E getSelectedValue() {
        if (table.getSelectedRow() == -1) {
            return null;
        }
        return data.get(table.convertRowIndexToModel(table.getSelectedRow()));
    }

    public void deleteSelectedRows() {
        List<E> dataToDelete = new ArrayList<>();
        for (int i : table.getSelectedRows()) {
            dataToDelete.add(data.get(table.convertRowIndexToModel(i)));
        }
        deleter.delete(dataToDelete, result -> refresh());
    }

    private void setColumns() {
        for (int i = 0; i < columns.size(); i++) {
            Column<?> column = columns.get(i);
            Integer preferredWidth = column.getPreferredWidth();
            Integer width = column.getWidth();

            TableColumn tableColumn = this.table.getColumnModel().getColumn(i);
            if (column.getCellComboBox() != null) {
                tableColumn.setCellEditor(new DefaultCellEditor(column.getCellComboBox()));
            }

            if (column.getClazz() == Date.class) {
                tableColumn.setCellEditor(new DateFormateTableCellEditor());
                tableColumn.setCellRenderer(new DateTableCellRenderer());
            }

            if (column.getSortOrder() != null) {
                JYTableSortController<? extends javax.swing.table.TableModel> rowSorter = (JYTableSortController<? extends javax.swing.table.TableModel>) this.table
                        .getRowSorter();
                rowSorter.setSortOrder(i, column.getSortOrder());
                rowSorter.sort();
            }

            if (column.isMultiline()) {
                tableColumn.setCellEditor(new MultlilineTableCell.MultlineCellEditor());
                tableColumn.setCellRenderer(new MultlilineTableCell.MultlineCellRenderer());
            }

            if (preferredWidth != null) {
                tableColumn.setPreferredWidth(preferredWidth);
            }

            if (width != null) {
                tableColumn.setMaxWidth(width);
            }
        }
    }

    public JTableBuilder<E> silentSaveOnClick() {
        silentSaveOnClick = true;
        return this;
    }

    private static class TableModel<T> extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final List<T> data;
        private final ObjectGetter<T> mapper;
        private final ObjectSetter<T> setter;
        private final List<Column<?>> columns;
        private final Set<T> changedData;
        private final boolean silentSaveOnClick;
        private final Saver<T> saver;

        public TableModel(Set<T> changedData, List<Column<?>> columns, List<T> data, ObjectGetter<T> mapper,
                ObjectSetter<T> setter, boolean silentSaveOnClick, Saver<T> saver) {
            this.columns = columns;
            this.data = data;
            this.mapper = mapper;
            this.setter = setter;
            this.changedData = changedData;
            this.silentSaveOnClick = silentSaveOnClick;
            this.saver = saver;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return mapper.get(data.get(rowIndex), columnIndex);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            T d = data.get(rowIndex);
            setter.set(d, aValue, columnIndex);

            if (silentSaveOnClick) {
                saver.save(d, asyncCallback -> {

                });
            } else {
                changedData.add(d);
            }
        }

        @Override
        public String getColumnName(int column) {
            return columns.get(column).getName();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columns.get(columnIndex).getClazz();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return setter != null && columns.get(columnIndex).isEditable();
        }

    }

    public static class ColumnBuilder<T> {

        private Column<T> column;

        public static <T> ColumnBuilder<T> get(Class<T> clazz) {
            return new ColumnBuilder<>(clazz);
        }

        private ColumnBuilder(Class<T> clazz) {
            this.column = new Column<>(clazz);
        }

        public ColumnBuilder<T> add(String name) {
            column.setName(name);
            return this;
        }

        @SuppressWarnings("rawtypes")
        public ColumnBuilder<T> add(JComboBox cellComboBox) {
            column.setCellComboBox(cellComboBox);
            return this;
        }

        public ColumnBuilder<T> preferredWidth(int preferredWidth) {
            column.setPreferredWidth(preferredWidth);
            return this;
        }

        public ColumnBuilder<T> width(int width) {
            column.setWidth(width);
            return this;
        }

        public ColumnBuilder<T> editable(boolean editable) {
            column.setEditable(editable);
            return this;
        }

        public ColumnBuilder<T> multiline() {
            column.multiline();
            return this;
        }

        public ColumnBuilder<T> asc() {
            column.asc();
            return this;
        }

        public ColumnBuilder<T> desc() {
            column.desc();
            return this;
        }

        public Column<T> build() {
            return column;
        }

    }

    @SuppressWarnings("rawtypes")
    private static class Column<T> {
        private String name;
        private Integer preferredWidth;
        private Integer width;

        private JComboBox cellComboBox;
        private boolean editable = true;
        private boolean multiline = false;
        private Class<T> clazz;

        private SortOrder sortOrder = null;

        public Column(Class<T> clazz) {
            this.clazz = clazz;
        }

        public Integer getPreferredWidth() {
            return preferredWidth;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public JComboBox getCellComboBox() {
            return cellComboBox;
        }

        public void setCellComboBox(JComboBox cellComboBox) {
            this.cellComboBox = cellComboBox;
        }

        public Class<T> getClazz() {
            return clazz;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public void setPreferredWidth(Integer preferredWidth) {
            this.preferredWidth = preferredWidth;
        }

        public void multiline() {
            this.multiline = true;
        }

        public boolean isMultiline() {
            return multiline;
        }

        public SortOrder getSortOrder() {
            return sortOrder;
        }

        public void asc() {
            sortOrder = SortOrder.ASCENDING;
        }

        public void desc() {
            sortOrder = SortOrder.DESCENDING;
        }

    }

    @FunctionalInterface
    public static interface Loader<T> {
        void get(AsyncCallback<List<T>> result);
    }

    @FunctionalInterface
    public static interface ObjectGetter<T> {
        Object get(T object, int column);
    }

    @FunctionalInterface
    public static interface ObjectSetter<T> {
        void set(T object, Object objectToSet, int column);
    }

    @FunctionalInterface
    public static interface Saver<T> {
        void save(T toSave, AsyncCallback<Void> asyncCallback);
    }

    @FunctionalInterface
    public static interface Deleter<T> {
        void delete(List<T> data, AsyncCallback<Void> asyncCallback);
    }

    public static class Columns {
        public static Column<Boolean> CHECK = ColumnBuilder.get(Boolean.class).add("").desc().width(150).build();
        public static Column<String> WOCHENTAG = ColumnBuilder.get(String.class).add("Tag").editable(false).width(50)
                .build();
    }

    private static class DoublClickTableCellEditor extends ObjectTableCellEditor {

        private static final long serialVersionUID = 1L;

        public DoublClickTableCellEditor(TableCellEditor arg0) {
            super(arg0);
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent) anEvent).getClickCount() >= 2;
            }
            return false;
        }
    }

    private static class DateFormateTableCellEditor extends DefaultCellEditor {

        private static final long serialVersionUID = 1L;

        private final JFormattedTextField textField;

        public DateFormateTableCellEditor() {
            super(new JCheckBox());
            this.textField = new JFormattedTextField(
                    new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
        }

        private MaskFormatter createMaskFormatter() {
            try {
                MaskFormatter mf = new MaskFormatter("##.##.##");
                mf.setValueContainsLiteralCharacters(false);
                return mf;
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            textField.setValue(value);
            return textField;
        }

        @Override
        public Object getCellEditorValue() {
            String text = textField.getText();
            if (!text.contains(".")) {
                MaskFormatter maskFormatter = createMaskFormatter();
                try {
                    String formatted = maskFormatter.valueToString(text);
                    return new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN))
                            .stringToValue(formatted);
                } catch (ParseException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            return textField.getValue();
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent) anEvent).getClickCount() >= 2;
            }
            return false;
        }
    }

    private static class DateTableCellRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        protected void setValue(Object value) {
            try {
                setText(new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN))
                        .valueToString(value));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

}
