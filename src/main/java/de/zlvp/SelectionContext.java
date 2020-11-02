package de.zlvp;

import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Person;

public class SelectionContext {

    private static final SelectionContext INSTANCE = new SelectionContext();

    private Lager lager;
    private Gruppe gruppe;
    private Jahr jahr;
    private Person person;

    private SelectionContext() {
    }

    public static SelectionContext get() {
        return INSTANCE;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
        this.gruppe = null;
        this.jahr = lager.getJahr();
        this.person = null;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
        this.lager = gruppe.getLager();
        this.jahr = gruppe.getLager().getJahr();
        this.person = null;
    }

    public void setJahr(Jahr jahr) {
        this.jahr = jahr;
        this.gruppe = null;
        this.lager = null;
        this.person = null;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.gruppe = null;
        this.jahr = null;
        this.lager = null;
    }

    public Lager getLager() {
        return lager;
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public Jahr getJahr() {
        return jahr;
    }

    public Person getPerson() {
        return person;
    }

}
