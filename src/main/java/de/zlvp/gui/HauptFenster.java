package de.zlvp.gui;

import static java.lang.String.format;
import static javax.swing.DropMode.ON;
import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.google.common.eventbus.Subscribe;

import de.zlvp.Client;
import de.zlvp.Events;
import de.zlvp.Events.Aktualisieren;
import de.zlvp.Events.GruppeSaved;
import de.zlvp.Events.LagerSaved;
import de.zlvp.Events.LeiterSaved;
import de.zlvp.Events.TeilnehmerSaved;
import de.zlvp.entity.AbstractEntity;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Person;
import de.zlvp.ui.AbstractJInternalFrame;
import de.zlvp.ui.JTreeTransferHandler;
import de.zlvp.ui.TreeData;
import de.zlvp.ui.TreeData.UserObjectEqualMutableTreeNode;
import de.zlvp.ui.TreePopup;

public class HauptFenster extends AbstractJInternalFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane;

    private JPanel jPanel2;

    private JButton jButtonAktualisieren;

    private JPanel jPanel;

    private JSplitPane jSplitPane;

    private JScrollPane jScrollPane;

    private JTree jTree;

    private final int jahrId;

    public HauptFenster(int jahrId) {
        this.jahrId = jahrId;

        Events.bus().register(this);

        initialize();
        setup();
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
            String key = "Aktualisieren";
            AbstractAction action = new AbstractAction(key) {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent evt) {
                    aktualisieren();
                }

            };
            jButtonAktualisieren = new JButton(action);
            jButtonAktualisieren.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), key);
            jButtonAktualisieren.getActionMap().put(key, action);
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
            jSplitPane.setRightComponent(new JPanel());
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

    private JTree getJTree() {
        if (jTree == null) {
            jTree = new JTree();
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
                        getJSplitPane().setRightComponent(new TPLager((Lager) userObject));
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

    private void aktualisieren() {
        Client.get().getJahr(jahrId, result -> {
            TreePath selectionPath = getJTree().getSelectionModel().getSelectionPath();
            getJTree().setModel(new DefaultTreeModel(new TreeData().getTreeModel(result)));
            expandNodes(getJTree(), 2);
            TreePath newSelectionPath = findNewSelectionPath(selectionPath);
            getJTree().setSelectionPath(newSelectionPath);
            getJSplitPane().setDividerLocation(0.15);
            setVisible(true);
        });
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

    @Subscribe
    public void aktualisieren(LeiterSaved event) {
        DefaultTreeModel model = (DefaultTreeModel) getJTree().getModel();
        DefaultMutableTreeNode leiterNode = null;

        if (event.destGruppe() != null) {
            leiterNode = getTeilnehmerOrLeiterNode(event.destGruppe(), "Leiter");
            model.insertNodeInto(new UserObjectEqualMutableTreeNode(event.get()), leiterNode,
                    leiterNode.getChildCount());
        }
        if (event.srcGruppe() != null) {
            leiterNode = getTeilnehmerOrLeiterNode(event.srcGruppe(), "Leiter");
            for (int i = 0; i < leiterNode.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) leiterNode.getChildAt(i);
                if (child.getUserObject().equals(event.get())) {
                    model.removeNodeFromParent(child);
                }
            }
        }
        getJTree().expandPath(new TreePath(leiterNode.getPath()));
    }

    @Subscribe
    public void aktualisieren(TeilnehmerSaved event) {
        DefaultTreeModel model = (DefaultTreeModel) getJTree().getModel();
        DefaultMutableTreeNode teilnehmerNode = null;

        if (event.destGruppe() != null) {
            teilnehmerNode = getTeilnehmerOrLeiterNode(event.destGruppe(), "Teilnehmer");
            model.insertNodeInto(new UserObjectEqualMutableTreeNode(event.get()), teilnehmerNode,
                    teilnehmerNode.getChildCount());
        }
        if (event.srcGruppe() != null) {
            teilnehmerNode = getTeilnehmerOrLeiterNode(event.srcGruppe(), "Teilnehmer");
            for (int i = 0; i < teilnehmerNode.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) teilnehmerNode.getChildAt(i);
                if (child.getUserObject().equals(event.get())) {
                    model.removeNodeFromParent(child);
                }
            }
        }
        getJTree().expandPath(new TreePath(teilnehmerNode.getPath()));
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void aktualisieren(GruppeSaved event) {

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getJTree().getModel().getRoot();
        Enumeration<DefaultMutableTreeNode> breadthFirstEnumeration = root.breadthFirstEnumeration();

        DefaultMutableTreeNode gruppeNode = null;
        DefaultMutableTreeNode targetNode = null;

        while (breadthFirstEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = breadthFirstEnumeration.nextElement();
            if (node.getUserObject().equals(event.get())) {
                ((DefaultTreeModel) getJTree().getModel()).nodeChanged(node);
            }
            if (event.srcLager() != null && node.getUserObject().equals(event.srcLager())) {
                Enumeration<DefaultMutableTreeNode> children = node.children();
                while (children.hasMoreElements()) {
                    DefaultMutableTreeNode child = children.nextElement();
                    if (child.getUserObject().equals(event.get())) {
                        gruppeNode = child;
                    }
                }
            }
            if (event.destLager() != null && node.getUserObject().equals(event.destLager())) {
                targetNode = node;
            }
        }
        if (gruppeNode != null) {
            ((DefaultTreeModel) getJTree().getModel()).removeNodeFromParent(gruppeNode);
            ((DefaultTreeModel) getJTree().getModel()).insertNodeInto(gruppeNode, targetNode,
                    targetNode.getChildCount());
            getJTree().expandPath(new TreePath(gruppeNode.getPath()));
        } else if (targetNode != null) {
            DefaultMutableTreeNode gruppeLeaf = new UserObjectEqualMutableTreeNode(event.get());
            // Sollte in TreeData zentralisiert werden.
            gruppeLeaf.add(new UserObjectEqualMutableTreeNode("Leiter"));
            gruppeLeaf.add(new UserObjectEqualMutableTreeNode("Teilnehmer"));
            ((DefaultTreeModel) getJTree().getModel()).insertNodeInto(gruppeLeaf, targetNode,
                    targetNode.getChildCount());
            getJTree().expandPath(new TreePath(gruppeLeaf.getPath()));
        }
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void aktualisieren(LagerSaved event) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getJTree().getModel().getRoot();
        Enumeration<DefaultMutableTreeNode> breadthFirstEnumeration = root.breadthFirstEnumeration();
        while (breadthFirstEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = breadthFirstEnumeration.nextElement();
            if (node.getUserObject().equals(event.get())) {
                ((DefaultTreeModel) getJTree().getModel()).nodeChanged(node);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private DefaultMutableTreeNode getTeilnehmerOrLeiterNode(Gruppe gruppe, String bezeichnung) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getJTree().getModel().getRoot();
        Enumeration<DefaultMutableTreeNode> breadthFirstEnumeration = root.breadthFirstEnumeration();
        while (breadthFirstEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode gruppeNode = breadthFirstEnumeration.nextElement();
            if (gruppeNode.getUserObject().equals(gruppe)) {
                for (int i = 0; i < gruppeNode.getChildCount(); i++) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) gruppeNode.getChildAt(i);
                    if (child.getUserObject().equals(bezeichnung)) {
                        return child;
                    }
                }
            }
        }
        return null;
    }
}
