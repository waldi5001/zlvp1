package de.zlvp.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import de.zlvp.Events;

public class JListTransferHandler extends TransferHandler {
    private static final long serialVersionUID = 1L;

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    DataFlavor listFlavor;
    final DataFlavor[] flavors;

    public JListTransferHandler() {
        listFlavor = createDataFlavor(Object.class);
        flavors = new DataFlavor[] { listFlavor };
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<?> tree = (JList<?>) c;
        Object selctedObject = tree.getSelectedValue();
        if (selctedObject != null) {
            return new JListTransferabel(selctedObject);
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
        return false;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

    public class JListTransferabel implements Transferable {
        Object object;

        public JListTransferabel(Object object) {
            this.object = object;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return object;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return listFlavor.equals(flavor);
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
