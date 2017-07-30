package de.zlvp.gui;

import static java.lang.String.format;
import static javax.swing.DropMode.ON;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.google.common.eventbus.Subscribe;

import de.zlvp.Client;
import de.zlvp.Events;
import de.zlvp.Events.Aktualisieren;
import de.zlvp.entity.AbstractEntity;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Person;
import de.zlvp.ui.InternalFrame;
import de.zlvp.ui.JTreeTransferHandler;
import de.zlvp.ui.TreeData;
import de.zlvp.ui.TreePopup;

public class HauptFenster extends InternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;

    private JPanel jPanel2;

    private JButton jButtonAktualisieren;

    private JPanel jPanel;

    private JSplitPane jSplitPane;

    private JScrollPane jScrollPane;

    private JPanel jPanel4;

    private JTree jTree;

    private final int jahrId;

    public HauptFenster(int jahrId) {
        this.jahrId = jahrId;

        Events.bus().register(this);

        initialize();
        setUp();
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        try {
            setMaximum(true);
        } catch (PropertyVetoException e1) {
            throw new RuntimeException(e1.getMessage(), e1);
        }
    }

    private void initialize() {
        this.setSize(800, 600);
        this.setTitle("Hauptfenster");
        this.setContentPane(getJContentPane());
        this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                Events.get().fireDisableMenuItems();
            }
        });
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
            jContentPane.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
            jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.add(getJButtonAktualisieren(), null);
        }
        return jPanel2;
    }

    private JButton getJButtonAktualisieren() {
        if (jButtonAktualisieren == null) {
            jButtonAktualisieren = new JButton();
            jButtonAktualisieren.setText("Aktualisieren");
            jButtonAktualisieren.addActionListener(e -> aktualisieren());
        }
        return jButtonAktualisieren;
    }

    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
        }
        return jPanel;
    }

    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setLeftComponent(getJScrollPane());
            jSplitPane.setRightComponent(getJPanel4());
        }
        return jSplitPane;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTree());
        }
        return jScrollPane;
    }

    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            jPanel4 = new JPanel();
        }
        return jPanel4;
    }

    private JTree getJTree() {
        if (jTree == null) {
            jTree = new JTree();
            jTree.setModel(new DefaultTreeModel(null));
            jTree.getSelectionModel().setSelectionMode(SINGLE_TREE_SELECTION);
            jTree.setComponentPopupMenu(new TreePopup());
            jTree.setCellRenderer(new DefaultTreeCellRenderer() {
                private static final long serialVersionUID = 1L;

                @Override
                public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                        boolean leaf, int row, boolean hasFocus) {
                    DefaultTreeCellRenderer c = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree,
                            value, sel, expanded, leaf, row, hasFocus);
                    Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
                    if (userObject instanceof AbstractEntity) {
                        c.setText(((AbstractEntity) userObject).getBezeichnung());
                        if (userObject instanceof Person) {
                            c.setToolTipText(
                                    format("<html>%s, %s<br>%s<br>%s %s</html>", ((Person) userObject).getName(),
                                            ((Person) userObject).getVorname(), ((Person) userObject).getStrasse(),
                                            ((Person) userObject).getPlz(), ((Person) userObject).getOrt()));
                        } else {
                            c.setToolTipText(null);
                        }
                    } else {
                        c.setText(userObject.toString());
                    }

                    return c;
                }
            });

            jTree.addTreeSelectionListener(e -> {
                TreePath selectionPath = jTree.getSelectionPath();
                if (selectionPath != null) {
                    Object lastPathComponent = selectionPath.getLastPathComponent();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) lastPathComponent;
                    Object userObject = node.getUserObject();

                    int anzahlElemente = selectionPath.getPath().length;

                    if (anzahlElemente == 1) {
                        Events.get().fireJahrSelected((Jahr) userObject);
                    }

                    if (anzahlElemente == 2) {
                        Events.get().fireLagerSelected((Lager) userObject);

                        SwingWorker<TPLager, Void> sw = new SwingWorker<TPLager, Void>() {
                            @Override
                            protected TPLager doInBackground() throws Exception {
                                return new TPLager((Lager) userObject);
                            }

                            @Override
                            protected void done() {
                                try {
                                    getJSplitPane().setRightComponent(get());
                                } catch (InterruptedException | ExecutionException e) {
                                    throw new RuntimeException(e.getMessage(), e);
                                }
                            }
                        };
                        sw.execute();

                    }

                    if (anzahlElemente == 3) {
                        Events.get().fireGruppeSelected((Gruppe) userObject);

                        DefaultMutableTreeNode lagerNode = (DefaultMutableTreeNode) selectionPath.getParentPath()
                                .getLastPathComponent();
                        Lager lager = (Lager) lagerNode.getUserObject();

                        getJSplitPane().setRightComponent(new TPGruppe(lager, ((Gruppe) userObject)));
                    }

                    if (anzahlElemente == 5) {
                        DefaultMutableTreeNode lagerNode = (DefaultMutableTreeNode) selectionPath
                                .getLastPathComponent();
                        Person person = (Person) lagerNode.getUserObject();

                        Events.get().firePersonSelected(person);
                    } else {
                        // getFensterKlasse().disableMenuItems();
                    }
                }

            });

            jTree.setDragEnabled(true);
            jTree.setDropMode(ON);
            jTree.setTransferHandler(new JTreeTransferHandler());
            aktualisieren();
        }

        ToolTipManager.sharedInstance().registerComponent(jTree);
        return jTree;
    }

    private void expandNodes(JTree tree, int level) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        DefaultMutableTreeNode currentNode = root.getNextNode();
        do {
            if (currentNode.getLevel() == level) {
                tree.expandPath(new TreePath(currentNode.getPath()));
            }
            currentNode = currentNode.getNextNode();
        } while (currentNode != null);
    }

    private void aktualisieren() {
        SwingWorker<Jahr, List<Jahr>> worker = new SwingWorker<Jahr, List<Jahr>>() {
            @Override
            protected Jahr doInBackground() throws Exception {
                return Client.get().getJahr(jahrId);
            }

            @Override
            protected void done() {
                try {
                    TreePath selectionPath = getJTree().getSelectionModel().getSelectionPath();
                    getJTree().setModel(new DefaultTreeModel(new TreeData().getTreeModel(get())));
                    TreePath newSelectionPath = findNewSelectionPath(selectionPath);
                    getJTree().setSelectionPath(newSelectionPath);
                    if (selectionPath == null) {
                        expandNodes(getJTree(), 2);
                    }
                    getJSplitPane().setDividerLocation(0.15);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }

        };
        worker.execute();
    }

    private TreePath findNewSelectionPath(TreePath oldSelectionPath) {
        TreePath newSelectionPath = null;

        if (oldSelectionPath != null) {
            Object[] oldPathComponents = oldSelectionPath.getPath();
            Object[] newPathComponents = new Object[oldPathComponents.length];

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) getJTree().getModel().getRoot();

            // Set the root
            if (oldPathComponents[0].equals(node)) {
                newPathComponents[0] = node;
            }

            // Set the rest of the path components
            for (int n = 1; n < oldPathComponents.length; n++) {
                for (int k = 0; k < node.getChildCount(); k++) {
                    if (oldPathComponents[n].equals(node.getChildAt(k))) {
                        newPathComponents[n] = node.getChildAt(k);
                        node = (DefaultMutableTreeNode) node.getChildAt(k);
                        break;
                    }
                }
            }

            // Make sure that the last path component exists
            if (newPathComponents[newPathComponents.length - 1] != null) {
                newSelectionPath = new TreePath(newPathComponents);
            }
        }
        return newSelectionPath;
    }

    @Subscribe
    public void aktualisieren(Aktualisieren a) {
        aktualisieren();
    }
}
