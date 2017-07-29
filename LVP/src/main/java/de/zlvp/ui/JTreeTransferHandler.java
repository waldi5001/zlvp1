package de.zlvp.ui;

import static java.lang.String.format;
import static javax.swing.JOptionPane.YES_OPTION;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.zlvp.Client;
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

            // Drop auf sich selber
            if (sourceModel.getParent().equals(targetModel)) {
                return false;
            }

            if (sourceModel.getUserObject() instanceof Gruppe && targetModel.getUserObject() instanceof Lager) {
                return true;
            }

            if (sourceModel.getUserObject() instanceof Teilnehmer && targetModel.getUserObject().equals("Teilnehmer")) {
                return true;
            }

            if (sourceModel.getUserObject() instanceof Leiter && (targetModel.getUserObject().equals("Leiter"))) {
                return true;
            }
        } else if (support.isDataFlavorSupported(listFlavor)) {
            JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
            try {
                Transferable t = support.getTransferable();
                Person person = (Person) t.getTransferData(listFlavor);
                DefaultMutableTreeNode targetModel = (DefaultMutableTreeNode) dl.getPath().getLastPathComponent();

                if (targetModel.getUserObject().equals("Teilnehmer") || targetModel.getUserObject().equals("Leiter")) {
                    @SuppressWarnings("unchecked")
                    Enumeration<DefaultMutableTreeNode> children = targetModel.children();
                    while (children.hasMoreElements()) {
                        DefaultMutableTreeNode node = children.nextElement();
                        if (node.getUserObject().equals(person)) {
                            return false;
                        }
                    }
                    return true;
                }
            } catch (UnsupportedFlavorException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (java.io.IOException e) {
                throw new RuntimeException(e.getMessage(), e);
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
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == MOVE) {
            Events.get().fireAktualisieren();
        }
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

                    int selectedOption = JOptionPane
                            .showConfirmDialog(
                                    null, format("Gruppe \"%s\" nach Lager \"%s\" verschieben?",
                                            gruppe.getBezeichnung(), lager.getBezeichnung()),
                                    "Gruppe verschieben", JOptionPane.YES_NO_OPTION);

                    if (selectedOption == YES_OPTION) {
                        Client.get().verschiebeGruppe(gruppe.getId(), ((Lager) dropUserObject).getId());
                    }
                } else if ((dropUserObject instanceof String
                        && ("Leiter".equals(dropUserObject) || "Teilnehmer".equals(dropUserObject)))
                        && srcNode.getUserObject() instanceof Person) {

                    Person person = (Person) srcNode.getUserObject();
                    Gruppe srcGruppe = (Gruppe) ((DefaultMutableTreeNode) srcNode.getParent().getParent())
                            .getUserObject();
                    Gruppe gruppe = (Gruppe) ((DefaultMutableTreeNode) dropNode.getParent()).getUserObject();

                    int selectedOption = JOptionPane.showConfirmDialog(null,
                            format("%s \"%s\" von \"%s\" nach \"%s\" verschieben?", person.getClass().getSimpleName(),
                                    person.getBezeichnung(), srcGruppe.getBezeichnung(), gruppe.getBezeichnung()),
                            "Gruppe verschieben", JOptionPane.YES_NO_OPTION);

                    if (selectedOption == YES_OPTION) {
                        if ("Leiter".equals(dropUserObject) && person instanceof Leiter) {
                            Client.get().verschiebeLeiter(person.getId(), gruppe.getOriginalId());
                        } else if ("Teilnehmer".equals(dropUserObject) && person instanceof Teilnehmer) {
                            Client.get().verschiebeTeilnehmer(person.getId(), gruppe.getOriginalId());
                        }
                    }
                }
            } else if (t.isDataFlavorSupported(listFlavor)) {
                Object objectFromList = t.getTransferData(listFlavor);
                if (dropUserObject instanceof String) {
                    Person person = (Person) objectFromList;
                    Gruppe gruppe = (Gruppe) ((DefaultMutableTreeNode) dropNode.getParent()).getUserObject();

                    if ("Leiter".equals(dropUserObject)) {
                        Client.get().speichereLeiter(null, person.getId(), person.getGeschlecht(), person.getVorname(),
                                person.getName(), person.getStrasse(), person.getPlz(), person.getOrt(),
                                person.getGebDat(), person.getTelNr(), person.getEmail(), person.getHandy(),
                                person.getNottel(), gruppe.getOriginalId());
                    } else if ("Teilnehmer".equals(dropUserObject)) {
                        Client.get().speichereTeilnehmer(null, person.getId(), person.getGeschlecht(),
                                person.getVorname(), person.getName(), person.getStrasse(), person.getPlz(),
                                person.getOrt(), person.getGebDat(), person.getTelNr(), person.getEmail(),
                                person.getHandy(), person.getNottel(), gruppe.getOriginalId());
                    }
                }
            }
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e.getMessage(), e);
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
