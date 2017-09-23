package de.zlvp.entity;

import java.util.ArrayList;
import java.util.List;

public class Gruppe extends AbstractEntity {

    public Gruppe(Integer id, String name, String schlachtruf) {
        setId(id);
        this.name = name;
        this.schlachtruf = schlachtruf;
    }

    public Gruppe(Gruppe g) {
        this(g.getId(), g.getName(), g.getSchlachtruf());
    }

    private List<Leiter> leiter;
    private List<Teilnehmer> teilnehmer;

    private String name;
    private String schlachtruf;

    private Lager lager;

    private boolean checked = false;

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public String getBezeichnung() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchlachtruf() {
        return schlachtruf;
    }

    public void setSchlachtruf(String schlachtruf) {
        this.schlachtruf = schlachtruf;
    }

    public List<Leiter> getLeiter() {
        if (leiter == null) {
            leiter = new ArrayList<>();
        }
        return leiter;
    }

    public List<Teilnehmer> getTeilnehmer() {
        if (teilnehmer == null) {
            teilnehmer = new ArrayList<>();
        }
        return teilnehmer;
    }

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }

}
