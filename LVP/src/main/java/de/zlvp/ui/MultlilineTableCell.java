package de.zlvp.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MultlilineTableCell {

    public static class MultlineCellEditor extends AbstractCellEditor implements TableCellEditor {
        private static final long serialVersionUID = 1L;

        private final JTextArea textArea = new JTextArea();

        public MultlineCellEditor() {
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            textArea.setText((String) value);
            return new JScrollPane(textArea);
        }

        @Override
        public Object getCellEditorValue() {
            return textArea.getText();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            if (e instanceof MouseEvent) {
                return ((MouseEvent) e).getClickCount() >= 2;
            }
            return false;
        }
    }

    public static class MultlineCellRenderer extends JScrollPane implements TableCellRenderer {

        private static final long serialVersionUID = 1L;

        private final JTextArea textArea = new JTextArea();

        public MultlineCellRenderer() {
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            getViewport().add(textArea);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
                textArea.setForeground(table.getSelectionForeground());
                textArea.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
                textArea.setBackground(table.getBackground());
            }

            textArea.setText((String) value);
            textArea.setCaretPosition(0);
            return this;
        }
    }

}