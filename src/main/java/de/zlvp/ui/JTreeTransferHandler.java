package de.zlvp.ui;

import static de.zlvp.Client.get;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.zlvp.Events;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Person;
import de.zlvp.entity.Teilnehmer;

public class JTreeTransferHandler extends TransferHandler {
    private static final long serialVersionUID = 1L;

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    DataFlavor treeNodeFlavor;
    DataFlavor listFlavor;
    final DataFlavor[] flavors;

    public JTreeTransferHandler() {
        treeNodeFlavor = createDataFlavor(DefaultMutableTreeNode.class);
        listFlavor = createDataFlavor(Object.class);
        flavors = new DataFlavor[] { treeNodeFlavor, listFlavor };
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        support.setShowDropLocation(true);
        if (support.isDataFlavorSupported(treeNodeFlavor)) {
            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            JTree tree = (JTree) support.getComponent();

            if (dl.getPath() == null) {
                return false;
            }

            DefaultMutableTreeNode sourceModel = (DefaultMutableTreeNode) tree.getSelectionPath()
                    .getLastPathComponent();
            DefaultMutableTreeNode targetModel = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();

            if (sourceModel.getUserObject() instanceof Gruppe && targetModel.getUserObject() instanceof Lager) {
                return true;
            }

            if (sourceModel.getUserObject() instanceof Teilnehmer && targetModel.getUserObject().equals("Teilnehmer")) {
                if (((DefaultMutableTreeNode) targetModel.getParent()).getUserObject()
                        .equals(((DefaultMutableTreeNode) sourceModel.getParent().getParent()).getUserObject())) {
                    // drop auf sich selber wenn gruppen gleich sind. Dann
                    return false;
                }
                return true;
            }

            if (sourceModel.getUserObject() instanceof Leiter && targetModel.getUserObject().equals("Leiter")) {
                if (((DefaultMutableTreeNode) targetModel.getParent()).getUserObject()
                        .equals(((DefaultMutableTreeNode) sourceModel.getParent().getParent()).getUserObject())) {
                    // drop auf sich selber wenn gruppen gleich sind. Dann
                    return false;
                }
                return true;
            }
        } else if (support.isDataFlavorSupported(listFlavor)) {
            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            if (dl != null && dl.getPath() != null) {
                try {
                    Transferable t = support.getTransferable();
                    Person person = (Person) t.getTransferData(listFlavor);
                    DefaultMutableTreeNode targetModel = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();

                    if (targetModel.getUserObject().equals("Teilnehmer") || targetModel.getUserObject().equals("Leiter")) {
                        @SuppressWarnings("unchecked") Enumeration<DefaultMutableTreeNode> children = targetModel.children();
                        while (children.hasMoreElements()) {
                            DefaultMutableTreeNode node = children.nextElement();
                            if (node.getUserObject().equals(person)) {
                                return false;
                            }
                        }
                        return true;
                    }
                } catch (Throwable t) {
                    throw new RuntimeException(t.getMessage(), t);
                }
            }
        }
        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            return new NodesTransferable((DefaultMutableTreeNode) path.getLastPathComponent());
        }
        return null;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            Transferable t = support.getTransferable();
            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();
            Object dropUserObject = dropNode.getUserObject();

            if (t.isDataFlavorSupported(treeNodeFlavor)) {
                DefaultMutableTreeNode srcNode = (DefaultMutableTreeNode) t.getTransferData(treeNodeFlavor);
                if (dropUserObject instanceof Lager) {
                    Lager lager = (Lager) dropUserObject;
                    Gruppe gruppe = (Gruppe) srcNode.getUserObject();

                    get().verschiebeGruppe(gruppe.getId(), gruppe.getLager().getId(), lager.getId(),
                            c -> Events.get().fireGruppeSaved(gruppe, gruppe.getLager(), lager));
                } else if ((dropUserObject instanceof String
                        && ("Leiter".equals(dropUserObject) || "Teilnehmer".equals(dropUserObject)))
                        && srcNode.getUserObject() instanceof Person) {

                    Person person = (Person) srcNode.getUserObject();
                    Gruppe srcGruppe = (Gruppe) ((DefaultMutableTreeNode) srcNode.getParent().getParent())
                            .getUserObject();
                    Gruppe destGruppe = (Gruppe) ((DefaultMutableTreeNode) dropNode.getParent()).getUserObject();

                    if ("Leiter".equals(dropUserObject) && person instanceof Leiter) {
                        get().verschiebeLeiter(person.getId(), srcGruppe.getId(), destGruppe.getId(),
                                c -> Events.get().fireLeiterSaved((Leiter) person, srcGruppe, destGruppe));
                    } else if ("Teilnehmer".equals(dropUserObject) && person instanceof Teilnehmer) {
                        get().verschiebeTeilnehmer(person.getId(), srcGruppe.getId(), destGruppe.getId(),
                                c -> Events.get().fireTeilnehmerSaved((Teilnehmer) person, srcGruppe, destGruppe));
                    }
                }
            } else if (t.isDataFlavorSupported(listFlavor)) {
                Object objectFromList = t.getTransferData(listFlavor);
                if (dropUserObject instanceof String) {
                    Person person = (Person) objectFromList;
                    Gruppe gruppe = (Gruppe) ((DefaultMutableTreeNode) dropNode.getParent()).getUserObject();

                    if ("Leiter".equals(dropUserObject)) {
                        get().speichereLeiter(true, person.getId(), gruppe.getId(), c -> {
                            Leiter leiter = new Leiter(person);
                            leiter.setGruppe(gruppe);
                            Events.get().fireLeiterSaved(leiter, null, gruppe);
                        });
                    } else if ("Teilnehmer".equals(dropUserObject)) {
                        get().speichereTeilnehmer(true, person.getId(), gruppe.getId(), c -> {
                            Teilnehmer teilnehmer = new Teilnehmer(person);
                            teilnehmer.setGruppe(gruppe);
                            Events.get().fireTeilnehmerSaved(teilnehmer, null, gruppe);
                        });
                    }
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException(t.getMessage(), t);
        }

        return true;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

    public class NodesTransferable implements Transferable {
        DefaultMutableTreeNode node;

        public NodesTransferable(DefaultMutableTreeNode nodes) {
            this.node = nodes;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return node;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { treeNodeFlavor };
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return treeNodeFlavor.equals(flavor);
        }
    }

    private DataFlavor createDataFlavor(Class<?> clazz) {
        String mimeType = String.format("%s;class=\"%s\"", DataFlavor.javaJVMLocalObjectMimeType, clazz.getName());
        try {
            return new DataFlavor(mimeType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
