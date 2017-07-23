package de.zlvp.ui;

import static javax.swing.SwingUtilities.isLeftMouseButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class DualListField<F, T> extends JPanel {

    private static final long serialVersionUID = 1L;

    final private JList<F> jListFrom;
    final private JList<T> jListTo;
    private JPanel jPanel1 = new JPanel();
    private JScrollPane jScrollPaneFrom = new JScrollPane();
    private JScrollPane jScrollPaneTo = new JScrollPane();
    private JSplitPane jSplitPaneButtons = new JSplitPane();
    private JButton jButtonAdd = new JButton();
    private JButton jButtonRemove = new JButton();

    private final ElementAddedCallback<F> addedCallback;
    private final ElementRemovedCallback<T> removedCallback;

    public DualListField(JList<F> jListFrom, JList<T> jListTo, ElementAddedCallback<F> addedCallback,
            ElementRemovedCallback<T> removedCallback) {
        this.jListFrom = jListFrom;
        this.jListTo = jListTo;
        this.addedCallback = addedCallback;
        this.removedCallback = removedCallback;

        setLayout(new java.awt.GridLayout(1, 3));

        jScrollPaneFrom.setViewportView(jListFrom);

        add(jScrollPaneFrom);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jSplitPaneButtons.setOrientation(JSplitPane.VERTICAL_SPLIT);

        jButtonAdd.setText(">>");
        jSplitPaneButtons.setTopComponent(jButtonAdd);

        jButtonRemove.setText("<<");
        jSplitPaneButtons.setBottomComponent(jButtonRemove);

        jPanel1.add(jSplitPaneButtons, new java.awt.GridBagConstraints());
        add(jPanel1);

        jScrollPaneTo.setViewportView(jListTo);

        add(jScrollPaneTo);

        jListTo.getModel().addListDataListener(new ListDataAddedListener() {

            @Override
            public void intervalAdded(ListDataEvent e) {
                DefaultListModel<T> newValueModel = (DefaultListModel<T>) e.getSource();
                DefaultListModel<F> fromModel = (DefaultListModel<F>) jListFrom.getModel();

                Enumeration<T> newElements = newValueModel.elements();
                while (newElements.hasMoreElements()) {
                    T to = newElements.nextElement();
                    Enumeration<F> elements = fromModel.elements();
                    while (elements.hasMoreElements()) {
                        F from = elements.nextElement();
                        if (to != null && to.equals(from)) {
                            fromModel.removeElement(from);
                        }
                    }
                }
            }
        });

        jListFrom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e) && e.getClickCount() == 2) {
                    add();
                }
            }
        });
        jButtonAdd.addActionListener(e -> {
            add();
        });

        jButtonRemove.addActionListener(e -> {
            remove();
        });
        jListTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e) && e.getClickCount() == 2) {
                    remove();
                }
            }
        });
    }

    private void remove() {
        T selectedValue = jListTo.getSelectedValue();
        if (selectedValue != null) {
            DefaultListModel<F> m = (DefaultListModel<F>) jListFrom.getModel();
            m.insertElementAt((F) selectedValue, 0);
        }
        DefaultListModel<T> model = (DefaultListModel<T>) jListTo.getModel();
        model.removeElement(selectedValue);
        removedCallback.removed(selectedValue);
    }

    private void add() {
        F selectedValue = jListFrom.getSelectedValue();
        if (selectedValue != null) {
            DefaultListModel<T> m = (DefaultListModel<T>) jListTo.getModel();
            m.insertElementAt((T) selectedValue, 0);
        }
        DefaultListModel<F> model = (DefaultListModel<F>) jListFrom.getModel();
        model.removeElement(selectedValue);
        addedCallback.added(selectedValue);
    }

    @FunctionalInterface
    public static interface ElementAddedCallback<E> {
        void added(E from);
    }

    @FunctionalInterface
    public static interface ElementRemovedCallback<E> {
        void removed(E to);
    }

    private static abstract class ListDataAddedListener implements ListDataListener {

        @Override
        public void intervalRemoved(ListDataEvent e) {
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
        }

    }

}