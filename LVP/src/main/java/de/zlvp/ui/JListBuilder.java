package de.zlvp.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class JListBuilder<E> {

    private JList<E> jlist;
    private ValueGetter<E> mapper;
    private Loader<E> loader;

    public static <E> JListBuilder<E> get(Class<E> clazz, Loader<E> loader) {
        return new JListBuilder<E>(loader);
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
        this.jlist = new JList<E>();
        this.jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.jlist.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                DefaultListCellRenderer c = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value,
                        index, isSelected, cellHasFocus);
                c.setText(mapper.get((E) value));
                return c;
            }
        });
        refresh();
        return jlist;
    }

    @FunctionalInterface
    public static interface Loader<T> {
        List<T> get();
    }

    @FunctionalInterface
    public static interface ValueGetter<T> {
        String get(T object);
    }

    public void refresh() {
        DefaultListModel<E> listModel = new DefaultListModel<E>();
        for (E e : loader.get()) {
            listModel.addElement(e);
        }
        this.jlist.setModel(listModel);
    }
}
