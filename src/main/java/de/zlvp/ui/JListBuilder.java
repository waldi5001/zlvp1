package de.zlvp.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import de.zlvp.controller.AsyncCallback;

public class JListBuilder<E> {

    private JList<E> jlist;
    private ValueGetter<E> mapper;
    private Loader<E> loader;

    public static <E> JListBuilder<E> get(Class<E> clazz, Loader<E> loader) {
        return new JListBuilder<>(loader);
    }

    private JListBuilder(Loader<E> loader) {
        this.loader = loader;
    }

    public JListBuilder<E> multiselect() {
        this.jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return this;
    }

    public JListBuilder<E> map(ValueGetter<E> mapper) {
        this.mapper = mapper;
        return this;
    }

    public JList<E> build() {
        this.jlist = new JList<>(new DefaultListModel<>());
        this.jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.jlist.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                DefaultListCellRenderer c = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value,
                        index, isSelected, cellHasFocus);
                if (mapper != null) {
                    c.setText(mapper.get((E) value));
                } else {
                    c.setText(value.toString());
                }
                return c;
            }
        });
        refresh();
        return jlist;
    }

    @FunctionalInterface
    public static interface Loader<T> {
        void get(AsyncCallback<List<T>> asyncCallback);
    }

    @FunctionalInterface
    public static interface ValueGetter<T> {
        String get(T object);
    }

    public void refresh() {
        loader.get(asyncCallback -> {
            DefaultListModel<E> listModel = (DefaultListModel<E>) jlist.getModel();
            listModel.clear();
            for (E e : asyncCallback) {
                listModel.addElement(e);
            }
        });
    }
}
