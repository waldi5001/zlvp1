package de.zlvp.ui;

import javax.swing.tree.DefaultMutableTreeNode;

import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Teilnehmer;

public class TreeData {

    public DefaultMutableTreeNode getTreeModel(Jahr jahr) {
        DefaultMutableTreeNode root = new UserObjectEqualMutableTreeNode(jahr);
        for (Lager lager : jahr.getLager()) {
            DefaultMutableTreeNode lagerLeaf = new UserObjectEqualMutableTreeNode(lager);
            root.add(lagerLeaf);
            for (Gruppe gruppe : lager.getGruppe()) {
                DefaultMutableTreeNode gruppeLeaf = new UserObjectEqualMutableTreeNode(gruppe);
                lagerLeaf.add(gruppeLeaf);

                DefaultMutableTreeNode LEITER = new UserObjectEqualMutableTreeNode("Leiter");
                DefaultMutableTreeNode TEILNEHMER = new UserObjectEqualMutableTreeNode("Teilnehmer");

                gruppeLeaf.add(LEITER);
                gruppeLeaf.add(TEILNEHMER);

                for (Teilnehmer t : gruppe.getTeilnehmer()) {
                    TEILNEHMER.add(new UserObjectEqualMutableTreeNode(t));
                }
                for (Leiter l : gruppe.getLeiter()) {
                    LEITER.add(new UserObjectEqualMutableTreeNode(l));
                }
            }
        }
        return root;
    }

    private static class UserObjectEqualMutableTreeNode extends DefaultMutableTreeNode {

        private static final long serialVersionUID = 1L;

        public UserObjectEqualMutableTreeNode(Object userObject) {
            super(userObject, true);
        }

        @Override
        public boolean equals(Object obj) {
            return this.getUserObject().equals(((UserObjectEqualMutableTreeNode) obj).getUserObject());
        }

    }

}
