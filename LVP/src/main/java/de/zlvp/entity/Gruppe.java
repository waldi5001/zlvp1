package de.zlvp.entity;

import java.util.ArrayList;
import java.util.List;

public class Gruppe extends AbstractEntity {

    public Gruppe(Integer id, Integer gruppeId, String name, String schlachtruf) {
        setId(id);
        setOriginalId(gruppeId);
        this.name = name;
        this.schlachtruf = schlachtruf;
    }

    public Gruppe(Gruppe g) {
        this(g.getId(), g.getOriginalId(), g.getName(), g.getSchlachtruf());
    }

    private List<Leiter> leiter;
    private List<Teilnehmer> teilnehmer;

    private String name;
    private String schlachtruf;

    private Lager lager;

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
