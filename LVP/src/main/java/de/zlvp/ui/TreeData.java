package de.zlvp.ui;

import javax.swing.tree.DefaultMutableTreeNode;

import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Teilnehmer;

public class TreeData {

    public DefaultMutableTreeNode getTreeModel(Jahr jahr) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(jahr);
        for (Lager lager : jahr.getLager()) {
            DefaultMutableTreeNode lagerLeaf = new DefaultMutableTreeNode(lager);
            root.add(lagerLeaf);
            for (Gruppe gruppe : lager.getGruppe()) {
                DefaultMutableTreeNode gruppeLeaf = new DefaultMutableTreeNode(gruppe);
                lagerLeaf.add(gruppeLeaf);

                DefaultMutableTreeNode LEITER = new DefaultMutableTreeNode("Leiter");
                DefaultMutableTreeNode TEILNEHMER = new DefaultMutableTreeNode("Teilnehmer");

                gruppeLeaf.add(LEITER);
                gruppeLeaf.add(TEILNEHMER);

                for (Teilnehmer t : gruppe.getTeilnehmer()) {
                    TEILNEHMER.add(new DefaultMutableTreeNode(t));
                }
                for (Leiter l : gruppe.getLeiter()) {
                    LEITER.add(new DefaultMutableTreeNode(l));
                }
            }
        }
        return root;
    }

}
