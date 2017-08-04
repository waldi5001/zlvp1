package de.zlvp.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import de.zlvp.controller.AsyncCallback;

public class JComboBoxBuilder<E> {

    private JComboBox<E> jCombobox;
    private ValueGetter<E> mapper;
    final private Loader<E> loader;

    public static <E> JComboBoxBuilder<E> get(Class<E> clazz, Loader<E> loader) {
        return new JComboBoxBuilder<>(loader);
    }

    private JComboBoxBuilder(Loader<E> loader) {
        this.loader = loader;
    }

    public JComboBoxBuilder<E> map(ValueGetter<E> mapper) {
        this.mapper = mapper;
        return this;
    }

    public JComboBox<E> build() {
        this.jCombobox = new JComboBox<>(new DefaultComboBoxModel<E>());
        this.jCombobox.setRenderer(new BasicComboBoxRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                BasicComboBoxRenderer c = (BasicComboBoxRenderer) super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                if (value != null) {
                    if (mapper != null) {
                        c.setText(mapper.get((E) value));
                    } else {
                        c.setText(value.toString());
                    }
                }
                return c;
            }
        });

        refresh();
        return jCombobox;
    }

    public void refresh() {
        jCombobox.setModel(new DefaultComboBoxModel<>());
        loader.get(result -> {
            for (E e : result) {
                this.jCombobox.addItem(e);
            }
        });
    }

    @FunctionalInterface
    public static interface ValueGetter<T> {
        String get(T object);
    }

    @FunctionalInterface
    public static interface Loader<T> {
        void get(AsyncCallback<List<T>> result);
    }

}
