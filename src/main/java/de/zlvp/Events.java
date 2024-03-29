package de.zlvp;

import com.google.common.eventbus.EventBus;

import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Person;
import de.zlvp.entity.Teilnehmer;

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

    public void fireLeiterSaved(Leiter leiter, Gruppe srcGruppe, Gruppe destGruppe) {
        bus.post(new LeiterSaved() {
            @Override
            public Gruppe srcGruppe() {
                return srcGruppe;
            }

            @Override
            public Leiter get() {
                return leiter;
            }

            @Override
            public Gruppe destGruppe() {
                return destGruppe;
            }

            @Override
            public String toString() {
                return prettyPrint();
            }
        });
    }

    public void fireTeilnehmerSaved(Teilnehmer teilnehmer, Gruppe srcGruppe, Gruppe destGruppe) {
        bus.post(new TeilnehmerSaved() {
            @Override
            public Teilnehmer get() {
                return teilnehmer;
            }

            @Override
            public Gruppe srcGruppe() {
                return srcGruppe;
            }

            @Override
            public Gruppe destGruppe() {
                return destGruppe;
            }

            @Override
            public String toString() {
                return prettyPrint();
            }
        });
    }

    public void fireGruppeSaved(Gruppe gruppe, Lager srcLager, Lager destLager) {
        bus.post(new GruppeSaved() {

            @Override
            public Gruppe get() {
                return gruppe;
            }

            @Override
            public Lager srcLager() {
                return srcLager;
            }

            @Override
            public Lager destLager() {
                return destLager;
            }

            @Override
            public String toString() {
                return prettyPrint();
            }
        });
    }

    public void fireLagerSaved(Lager lager) {
        bus.post((LagerSaved) () -> lager);
    }

    public void fireLagerRenamed(Lager lager) {
        bus.post((LagerRenamed) () -> lager);
    }

    public  interface Aktualisieren {
    }

    public  interface LeiterSaved {
        Leiter get();

        Gruppe srcGruppe();

        Gruppe destGruppe();

        default String prettyPrint() {
            return String.format("Leiter: %s von %s nach %s", get(), srcGruppe(), destGruppe());
        };
    }

    public  interface TeilnehmerSaved {
        Teilnehmer get();

        Gruppe srcGruppe();

        Gruppe destGruppe();

        default String prettyPrint() {
            return String.format("Teilnehmer: %s von %s nach %s", get(), srcGruppe(), destGruppe());
        };
    }

    public  interface GruppeSaved {
        Gruppe get();

        Lager srcLager();

        Lager destLager();

        default String prettyPrint() {
            return String.format("Gruppe: %s von %s nach %s", get(), srcLager(), destLager());
        };
    }

    public  interface LagerSaved {
        Lager get();
    }

    public  interface LagerRenamed {
        Lager get();
    }

    @FunctionalInterface
    public  interface LagerSelected {
        Lager get();
    }

    @FunctionalInterface
    public  interface GruppeSelected {
        Gruppe get();
    }

    @FunctionalInterface
    public  interface PersonSelected {
        Person get();
    }

    @FunctionalInterface
    public  interface JahrSelected {
        Jahr get();
    }

    public  interface DisableMenuItems {
    }

    @FunctionalInterface
    public  interface LoginSuccessfull {
        String getUsername();
    }

}
