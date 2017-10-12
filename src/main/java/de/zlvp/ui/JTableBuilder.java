package de.zlvp.ui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.table.DatePickerCellEditor;
import org.jfree.ui.DateCellRenderer;

import de.javasoft.swing.JYTable;
import de.javasoft.swing.JYTableHeader;
import de.javasoft.swing.jytable.renderer.CellLayoutHint;
import de.javasoft.swing.jytable.sort.JYTableSortController;
import de.zlvp.controller.AsyncCallback;

public class JTableBuilder<E> {

    private final List<Column<?>> columns = new ArrayList<>();

    private JTable table;

    private List<E> data;

    private ObjectGetter<E> mapper;
    private final Loader<E> loader;
    private ObjectSetter<E> objectSetter;
    private Saver<E> saver;
    private Deleter<E> deleter;

    public static <E> JTableBuilder<E> get(Class<E> clazz, Loader<E> loader) {
        return new JTableBuilder<>(loader);
    }

    private JTableBuilder(Loader<E> loader) {
        this.loader = loader;
    }

    public void refresh() {
        loader.get(result -> {
            this.table.setRowSorter(null);
            this.table.setModel(new TableModel<>(columns, data = result, mapper, objectSetter, saver));
            setColumns();
        });
    }

    public JTable buildAndLoad() {
        JTable table = build();
        refresh();
        return table;
    }

    public JTable build() {
        this.table = new JYTable();
        this.table.setCellSelectionEnabled(true);
        this.table.setSurrendersFocusOnKeystroke(true);
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
                DatePickerCellEditor dateCellEditor = new DatePickerCellEditor(
                        DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN));
                tableColumn.setCellEditor(dateCellEditor);
                tableColumn.setCellRenderer(
                        new DateCellRenderer(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN)));
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

    private static class TableModel<T> extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private final List<T> data;
        private final ObjectGetter<T> mapper;
        private final ObjectSetter<T> setter;
        private final List<Column<?>> columns;
        private final Saver<T> saver;

        public TableModel(List<Column<?>> columns, List<T> data, ObjectGetter<T> mapper, ObjectSetter<T> setter,
                Saver<T> saver) {
            this.columns = columns;
            this.data = data;
            this.mapper = mapper;
            this.setter = setter;
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

            saver.save(d, asyncCallback -> {

            });
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

}
