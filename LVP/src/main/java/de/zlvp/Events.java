package de.zlvp;

import com.google.common.eventbus.EventBus;

import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Person;

public class Events {

    private static EventBus bus = new EventBus();

    private static Events INSTANCE = new Events();

    public static EventBus bus() {
        return bus;
    }

    public static Events get() {
        return INSTANCE;
    }

    public void fireAktualisieren() {
        bus.post(new Aktualisieren() {
        });
    }

    public void fireLagerSelected(Lager lager) {
        bus.post((LagerSelected) () -> lager);
    }

    public void fireGruppeSelected(Gruppe gruppe) {
        bus.post((GruppeSelected) () -> gruppe);
    }

    public void firePersonSelected(Person person) {
        bus.post((PersonSelected) () -> person);
    }

    public void fireJahrSelected(Jahr jahr) {
        bus.post((JahrSelected) () -> jahr);
    }

    public void fireDisableMenuItems() {
        bus.post(new DisableMenuItems() {
        });
    }

    public void fireLoginSuccessfull(String username) {
        bus.post((LoginSuccessfull) () -> username);
    }

    public static interface Aktualisieren {
    }

    @FunctionalInterface
    public static interface LagerSelected {
        Lager get();
    }

    @FunctionalInterface
    public static interface GruppeSelected {
        Gruppe get();
    }

    @FunctionalInterface
    public static interface PersonSelected {
        Person get();
    }

    @FunctionalInterface
    public static interface JahrSelected {
        Jahr get();
    }

    public static interface DisableMenuItems {
    }

    @FunctionalInterface
    public static interface LoginSuccessfull {
        String getUsername();
    }

}
