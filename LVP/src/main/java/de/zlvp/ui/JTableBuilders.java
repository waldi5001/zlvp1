package de.zlvp.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.zlvp.Client;
import de.zlvp.entity.Anrede;
import de.zlvp.entity.Essen;
import de.zlvp.entity.Funktion;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Lagerinfo;
import de.zlvp.entity.Lagerort;
import de.zlvp.entity.Legenda;
import de.zlvp.entity.Legendatyp;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Materialwart;
import de.zlvp.entity.Programm;
import de.zlvp.entity.Stab;
import de.zlvp.entity.Teilnehmer;
import de.zlvp.entity.Zelt;
import de.zlvp.ui.JTableBuilder.ColumnBuilder;
import de.zlvp.ui.JTableBuilder.Columns;
import de.zlvp.ui.JTableBuilder.Loader;

public class JTableBuilders {

    public static JTableBuilder<Stab> stab(Lager lager, Loader<Stab> loader) {
        List<Funktion> allFunktion = Client.get().getAllFunktion();
        allFunktion.add(0, new Funktion(null, null));

        return JTableBuilder.get(Stab.class, () -> loader.get())//
                .set((person, val, index) -> {
                    if (index == 0) {
                        person.setName((String) val);
                    } else if (index == 1) {
                        person.setVorname((String) val);
                    } else if (index == 2) {
                        person.setStrasse((String) val);
                    } else if (index == 3) {
                        person.setPlz((String) val);
                    } else if (index == 4) {
                        person.setOrt((String) val);
                    } else if (index == 5) {
                        person.setGebDat((Date) val);
                    } else if (index == 6) {
                        person.setFunktion((Funktion) val);
                    }
                })//
                .get((person, index) -> {
                    if (index == 0) {
                        return person.getName();
                    } else if (index == 1) {
                        return person.getVorname();
                    } else if (index == 2) {
                        return person.getStrasse();
                    } else if (index == 3) {
                        return person.getPlz();
                    } else if (index == 4) {
                        return person.getOrt();
                    } else if (index == 5) {
                        return person.getGebDat();
                    } else if (index == 6) {
                        return person.getFunktion();
                    }
                    return null;
                })
                .save(s -> Client.get().speichereStab(s.getId(), s.getOriginalId(), s.getGeschlecht(), s.getVorname(),
                        s.getName(), s.getStrasse(), s.getPlz(), s.getOrt(), s.getGebDat(), s.getTelNr(), s.getEmail(),
                        s.getHandy(), s.getTelNr(), s.getFunktion() != null ? s.getFunktion().getId() : null,
                        lager.getId()))//
                .addColumn(ColumnBuilder.get(String.class).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).add("Geburtsdatum").build())//
                .addColumn(
                        ColumnBuilder
                                .get(Funktion.class).add("Funktion").add(JComboBoxBuilder
                                        .get(Funktion.class, () -> allFunktion).map(f -> f.getBezeichnung()).build())
                                .desc().build());
    }

    public static JTableBuilder<Materialwart> materialwart(Lager lager, Loader<Materialwart> loader) {
        return JTableBuilder.get(Materialwart.class, () -> loader.get())//
                .set((person, val, index) -> {
                    if (index == 0) {
                        person.setLager((boolean) val == true ? lager : null);
                    } else if (index == 1) {
                        person.setName((String) val);
                    } else if (index == 2) {
                        person.setVorname((String) val);
                    } else if (index == 3) {
                        person.setStrasse((String) val);
                    } else if (index == 4) {
                        person.setPlz((String) val);
                    } else if (index == 5) {
                        person.setOrt((String) val);
                    } else if (index == 6) {
                        person.setGebDat((Date) val);
                    }
                })//
                .get((person, index) -> {
                    if (index == 0) {
                        return person.getLager() != null;
                    } else if (index == 1) {
                        return person.getName();
                    } else if (index == 2) {
                        return person.getVorname();
                    } else if (index == 3) {
                        return person.getStrasse();
                    } else if (index == 4) {
                        return person.getPlz();
                    } else if (index == 5) {
                        return person.getOrt();
                    } else if (index == 6) {
                        return person.getGebDat();
                    }
                    return null;
                })
                .save(mw -> Client.get().speichereMaterialwart(mw.getId(), mw.getOriginalId(), mw.getGeschlecht(),
                        mw.getVorname(), mw.getName(), mw.getStrasse(), mw.getPlz(), mw.getOrt(), mw.getGebDat(),
                        mw.getTelNr(), mw.getEmail(), mw.getHandy(), mw.getTelNr(),
                        mw.getLager() != null ? mw.getLager().getId() : null))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).add("Geburtsdatum").build());//
    }

    public static JTableBuilder<Zelt> zelt(Lager lager, Loader<Zelt> loader) {
        return JTableBuilder.get(Zelt.class, () -> loader.get())//
                .set((zelt, val, index) -> {
                    if (index == 0) {
                        if ((boolean) val) {
                            zelt.getLager().add(lager);
                        } else {
                            zelt.getLager().clear();
                        }
                    }
                })//
                .get((zelt, index) -> {
                    if (index == 0) {
                        return !zelt.getLager().isEmpty();
                    } else if (index == 1) {
                        return zelt.getBezeichnung();
                    }
                    return null;
                })
                .save(zelt -> Client.get().speichereZeltZuLager(zelt.getId(), zelt.getOriginalId(),
                        zelt.getLager().isEmpty() ? null : lager.getId()))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Bezeichnung").build());//
    }

    public static JTableBuilder<Zelt> zelt(Gruppe gruppe, Loader<Zelt> loader) {
        return JTableBuilder.get(Zelt.class, () -> loader.get())//
                .set((zelt, val, index) -> {
                    if (index == 0) {
                        if ((boolean) val) {
                            zelt.getGruppe().add(gruppe);
                        } else {
                            zelt.getGruppe().clear();
                        }
                    }
                })//
                .get((zelt, index) -> {
                    if (index == 0) {
                        return !zelt.getGruppe().isEmpty();
                    } else if (index == 1) {
                        return zelt.getBezeichnung();
                    }
                    return null;
                })
                .save(zelt -> Client.get().speichereZeltZuGruppe(zelt.getId(), zelt.getOriginalId(),
                        zelt.getGruppe().isEmpty() ? null : gruppe.getOriginalId()))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Bezeichnung").build());//
    }

    public static JTableBuilder<Gruppe> gruppe(Lager lager, Loader<Gruppe> loader) {
        return JTableBuilder.get(Gruppe.class, () -> loader.get())//
                .set((gruppe, val, index) -> {
                    if (index == 0) {
                        if ((boolean) val) {
                            gruppe.setLager(lager);
                        } else {
                            gruppe.setLager(null);
                        }
                    } else if (index == 1) {
                        gruppe.setName((String) val);
                    }
                })//
                .get((gruppe, index) -> {
                    if (index == 0) {
                        return gruppe.getLager() != null;
                    } else if (index == 1) {
                        return gruppe.getBezeichnung();
                    }
                    return null;
                })
                .save(gruppe -> Client.get().speichereGruppe(gruppe.getId(), gruppe.getOriginalId(),
                        gruppe.getLager() != null ? gruppe.getLager().getId() : null, gruppe.getName(),
                        gruppe.getSchlachtruf()))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).add("Bezeichnung").build());//
    }

    public static JTableBuilder<Leiter> leiter(Gruppe gruppe, Loader<Leiter> loader) {
        return JTableBuilder.get(Leiter.class, () -> loader.get())//
                .set((person, val, index) -> {
                    if (index == 0) {
                        person.setGruppe((boolean) val == true ? gruppe : null);
                    } else if (index == 1) {
                        person.setName((String) val);
                    } else if (index == 2) {
                        person.setVorname((String) val);
                    } else if (index == 3) {
                        person.setStrasse((String) val);
                    } else if (index == 4) {
                        person.setPlz((String) val);
                    } else if (index == 5) {
                        person.setOrt((String) val);
                    } else if (index == 6) {
                        person.setGebDat((Date) val);
                    }
                })//
                .get((person, index) -> {
                    if (index == 0) {
                        return person.getGruppe() != null;
                    } else if (index == 1) {
                        return person.getName();
                    } else if (index == 2) {
                        return person.getVorname();
                    } else if (index == 3) {
                        return person.getStrasse();
                    } else if (index == 4) {
                        return person.getPlz();
                    } else if (index == 5) {
                        return person.getOrt();
                    } else if (index == 6) {
                        return person.getGebDat();
                    }
                    return null;
                })
                .save(le -> Client.get().speichereLeiter(le.getId(), le.getOriginalId(), le.getGeschlecht(),
                        le.getVorname(), le.getName(), le.getStrasse(), le.getPlz(), le.getOrt(), le.getGebDat(),
                        le.getTelNr(), le.getEmail(), le.getHandy(), le.getTelNr(),
                        le.getGruppe() != null ? le.getGruppe().getOriginalId() : null))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).add("Geburtsdatum").build());//

    }

    public static JTableBuilder<Teilnehmer> teilnehmer(Gruppe gruppe, Loader<Teilnehmer> loader) {
        return JTableBuilder.get(Teilnehmer.class, () -> loader.get())//
                .set((person, val, index) -> {
                    if (index == 0) {
                        person.setGruppe((boolean) val == true ? gruppe : null);
                    } else if (index == 1) {
                        person.setName((String) val);
                    } else if (index == 2) {
                        person.setVorname((String) val);
                    } else if (index == 3) {
                        person.setStrasse((String) val);
                    } else if (index == 4) {
                        person.setPlz((String) val);
                    } else if (index == 5) {
                        person.setOrt((String) val);
                    } else if (index == 6) {
                        person.setGebDat((Date) val);
                    }
                })//
                .get((person, index) -> {
                    if (index == 0) {
                        return person.getGruppe() != null;
                    } else if (index == 1) {
                        return person.getName();
                    } else if (index == 2) {
                        return person.getVorname();
                    } else if (index == 3) {
                        return person.getStrasse();
                    } else if (index == 4) {
                        return person.getPlz();
                    } else if (index == 5) {
                        return person.getOrt();
                    } else if (index == 6) {
                        return person.getGebDat();
                    }
                    return null;
                })
                .save(te -> Client.get().speichereTeilnehmer(te.getId(), te.getOriginalId(), te.getGeschlecht(),
                        te.getVorname(), te.getName(), te.getStrasse(), te.getPlz(), te.getOrt(), te.getGebDat(),
                        te.getTelNr(), te.getEmail(), te.getHandy(), te.getTelNr(),
                        te.getGruppe() != null ? te.getGruppe().getOriginalId() : null))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).add("Geburtsdatum").build());//
    }

    public static JTableBuilder<Programm> programm(Lager lager, Loader<Programm> loader) {
        return JTableBuilder.get(Programm.class, () -> loader.get())//
                .set((programm, val, index) -> {
                    if (index == 0) {
                        programm.setDatum((Date) val);
                    } else if (index == 2) {
                        programm.setMorgen((String) val);
                    } else if (index == 3) {
                        programm.setMittag((String) val);
                    } else if (index == 4) {
                        programm.setAbend((String) val);
                    }
                })//
                .get((programm, index) -> {
                    if (index == 0) {
                        return programm.getDatum();
                    } else if (index == 1) {
                        return getWochentag(programm.getDatum());
                    } else if (index == 2) {
                        return programm.getMorgen();
                    } else if (index == 3) {
                        return programm.getMittag();
                    } else if (index == 4) {
                        return programm.getAbend();
                    }
                    return null;
                })
                .save(p -> Client.get().speichereProgramm(lager.getId(), p.getId(), p.getDatum(), p.getMorgen(),
                        p.getMittag(), p.getAbend()))//
                .delete(p -> Client.get().speichereProgramm(null, p.getId(), null, null, null, null))//
                .addColumn(ColumnBuilder.get(Date.class).add("Datum").width(100).build())//
                .addColumn(Columns.WOCHENTAG)//
                .addColumn(ColumnBuilder.get(String.class).add("Morgen").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Mittag").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Abend").multiline().build());//
    }

    public static JTableBuilder<Essen> essen(Lager lager, Loader<Essen> loader) {
        return JTableBuilder.get(Essen.class, () -> loader.get())//
                .set((essen, val, index) -> {
                    if (index == 0) {
                        essen.setDatum((Date) val);
                    } else if (index == 2) {
                        essen.setMorgen((String) val);
                    } else if (index == 3) {
                        essen.setMittag((String) val);
                    } else if (index == 4) {
                        essen.setAbend((String) val);
                    }
                })//
                .get((essen, index) -> {
                    if (index == 0) {
                        return essen.getDatum();
                    } else if (index == 1) {
                        return getWochentag(essen.getDatum());
                    } else if (index == 2) {
                        return essen.getMorgen();
                    } else if (index == 3) {
                        return essen.getMittag();
                    } else if (index == 4) {
                        return essen.getAbend();
                    }
                    return null;
                })
                .save(e -> Client.get().speichereEssen(lager.getId(), e.getId(), e.getDatum(), e.getMorgen(),
                        e.getMittag(), e.getAbend()))//
                .delete(e -> Client.get().speichereEssen(null, e.getId(), null, null, null, null))//
                .addColumn(ColumnBuilder.get(Date.class).add("Datum").width(100).build())//
                .addColumn(Columns.WOCHENTAG)//
                .addColumn(ColumnBuilder.get(String.class).add("Morgen").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Mittag").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Abend").multiline().build());//
    }

    private static String getWochentag(Date datum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.GERMAN);
    }

    public static JTableBuilder<Legenda> legenda(Lagerort lagerort, Loader<Legenda> loader) {
        return JTableBuilder.get(Legenda.class, () -> loader.get())//
                .set((person, val, index) -> {
                    if (index == 0) {
                        person.setLegendaTyp((Legendatyp) val);
                    } else if (index == 1) {
                        person.setAnrede((Anrede) val);
                    } else if (index == 2) {
                        person.setFirma((String) val);
                    } else if (index == 3) {
                        person.setName((String) val);
                    } else if (index == 4) {
                        person.setVorname((String) val);
                    } else if (index == 5) {
                        person.setStrasse((String) val);
                    } else if (index == 6) {
                        person.setPlz((String) val);
                    } else if (index == 7) {
                        person.setOrt((String) val);
                    } else if (index == 8) {
                        person.setHandy((String) val);
                    } else if (index == 9) {
                        person.setEmail((String) val);
                    } else if (index == 10) {
                        person.setFax((String) val);
                    } else if (index == 11) {
                        person.setBemerkung((String) val);
                    }
                })//
                .get((person, index) -> {
                    if (index == 0) {
                        return person.getLegendaTyp();
                    } else if (index == 1) {
                        return person.getAnrede();
                    } else if (index == 2) {
                        return person.getFirma();
                    } else if (index == 3) {
                        return person.getName();
                    } else if (index == 4) {
                        return person.getVorname();
                    } else if (index == 5) {
                        return person.getStrasse();
                    } else if (index == 6) {
                        return person.getPlz();
                    } else if (index == 7) {
                        return person.getOrt();
                    } else if (index == 8) {
                        return person.getHandy();
                    } else if (index == 9) {
                        return person.getEmail();
                    } else if (index == 10) {
                        return person.getFax();
                    } else if (index == 11) {
                        return person.getBemerkung();
                    }
                    return null;
                })
                .save(lg -> Client.get().speichereLegenda(lg.getId(), lagerort.getId(), lg.getName(), lg.getVorname(),
                        lg.getFirma(), lg.getStrasse(), lg.getPlz(), lg.getOrt(), lg.getLegendaTyp().getId(),
                        lg.getAnrede().getId(), lg.getStrasse(), lg.getFax(), lg.getHandy(), lg.getEmail(),
                        lg.getBemerkung()))//
                .addColumn(ColumnBuilder.get(Legendatyp.class)
                        .add(JComboBoxBuilder.get(Legendatyp.class, () -> Client.get().getAllLegendatyp()).build())
                        .add("Typ").desc().build())//
                .addColumn(ColumnBuilder.get(Anrede.class)
                        .add(JComboBoxBuilder.get(Anrede.class, () -> Client.get().getAllAnrede()).build())
                        .add("Anrede").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Firma").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Strasse").build())//
                .addColumn(ColumnBuilder.get(String.class).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Ort").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Handy").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Email").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Fax").build())//
                .addColumn(ColumnBuilder.get(String.class).multiline().add("Bemerkung").build());//
    }

    public static JTableBuilder<Lagerinfo> lagerinfo(Loader<Lagerinfo> loader) {
        return JTableBuilder.get(Lagerinfo.class, () -> loader.get())//
                .set((li, val, index) -> {
                    if (index == 0) {
                        li.setChecked((boolean) val);
                    } else if (index == 1) {
                        li.setName((String) val);
                    } else if (index == 2) {
                        li.setVorname((String) val);
                    } else if (index == 3) {
                        li.setStrasse((String) val);
                    } else if (index == 4) {
                        li.setPlz((String) val);
                    } else if (index == 5) {
                        li.setOrt((String) val);
                    } else if (index == 6) {
                        li.setGebDat((Date) val);
                    }
                })//
                .get((li, index) -> {
                    if (index == 0) {
                        return li.isChecked();
                    } else if (index == 1) {
                        return li.getName();
                    } else if (index == 2) {
                        return li.getVorname();
                    } else if (index == 3) {
                        return li.getStrasse();
                    } else if (index == 4) {
                        return li.getPlz();
                    } else if (index == 5) {
                        return li.getOrt();
                    } else if (index == 6) {
                        return li.getGebDat();
                    }
                    return null;
                })
                .save(li -> Client.get().speichereLagerinfo(li.getId(), li.getOriginalId(), li.getGeschlecht(),
                        li.getVorname(), li.getName(), li.getStrasse(), li.getPlz(), li.getOrt(), li.getGebDat(),
                        li.getTelNr(), li.getEmail(), li.getHandy(), li.getTelNr(), li.isChecked()))
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).add("Geburtsdatum").build());// ;
    }
}
