package de.zlvp.ui;

import static de.zlvp.Client.get;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.zlvp.Events;
import de.zlvp.entity.Anrede;
import de.zlvp.entity.Essen;
import de.zlvp.entity.Funktion;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Lagerinfo;
import de.zlvp.entity.Legenda;
import de.zlvp.entity.Legendatyp;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Materialwart;
import de.zlvp.entity.Person;
import de.zlvp.entity.Programm;
import de.zlvp.entity.Schaden;
import de.zlvp.entity.Stab;
import de.zlvp.entity.Teilnehmer;
import de.zlvp.entity.Waehrung;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltverleih;
import de.zlvp.ui.JTableBuilder.ColumnBuilder;
import de.zlvp.ui.JTableBuilder.Columns;
import de.zlvp.ui.JTableBuilder.Loader;

public class JTableBuilders {

    @FunctionalInterface
    public static interface Callback<T> {
        T get();
    }

    @FunctionalInterface
    public static interface SelectionCallback {
        boolean isSelected();
    }

    @FunctionalInterface
    private static interface Comparator<S, T> {
        boolean isEqual(S s, T t);
    }

    public static JTableBuilder<Stab> stab(Callback<Lager> lagerCallback, Loader<Person> loaderPerson,
            Loader<Stab> loaderStab) {
        return getWithCopy(Person.class, Stab.class, loaderPerson, loaderStab, (s, t) -> s.getId().equals(t.getId()))//
                .set((person, val, index) -> {
                    if (index == 6) {
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
                        return person.getFunktion() != null ? person.getFunktion().getBezeichnung() : null;
                    }
                    return null;
                })
                // s.getFunktion() == Funktion.REMOVE && s.getLager() == null =
                // Es wird ein leerer Eintrag bei der Funktion ausgewählt obwohl
                // die Person noch kein Stab war.
                .save((s, cb) -> get().speichereStab(s.getId(), s.getFunktion(),
                        s.getFunktion() == Funktion.REMOVE
                                ? s.getLager() == null ? lagerCallback.get().getId() : s.getLager().getId()
                                : lagerCallback.get().getId(),
                        speichernCallback -> {
                            s.setLager(speichernCallback);
                            cb.get(null);
                        }))//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).editable(false).add("Geburtsdatum").build())
                .addColumn(ColumnBuilder.get(Funktion.class).add("Funktion")
                        .add(JComboBoxBuilder.get(Funktion.class, allFunktion -> {
                            allFunktion.get(asList(Funktion.values()));
                        }).map(Funktion::getBezeichnung).build()).desc().build());
    }

    public static JTableBuilder<Materialwart> materialwart(Callback<Lager> lagerCallback, Loader<Person> loaderPerson,
            Loader<Materialwart> allMateriealwart) {
        return getWithCopy(Person.class, Materialwart.class, loaderPerson, allMateriealwart,
                (s, t) -> s.getId().equals(t.getId()))//
                        .set((person, val, index) -> {
                            if (index == 0) {
                                person.setChecked((boolean) val);
                            }
                        })//
                        .get((person, index) -> {
                            if (index == 0) {
                                return person.isChecked();
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
                        .save((mw, cb) -> get().speichereMaterialwart(mw.isChecked(), mw.getId(),
                                mw.isChecked() ? lagerCallback.get().getId() : mw.getLager().getId(),
                                gespeichertCallback -> {
                                    mw.setLager(gespeichertCallback);
                                    cb.get(null);
                                }))//
                        .addColumn(Columns.CHECK)//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Nachname").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Vorname").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Straße").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("PLZ").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Ort").build())//
                        .addColumn(ColumnBuilder.get(Date.class).editable(false).add("Geburtsdatum").build());//
    }

    public static JTableBuilder<Zelt> zelteVonLager(Callback<Lager> lagerCallback, Loader<Zelt> allZelt,
            Loader<Zelt> allZeltFromLager) {
        return getWithCopy(Zelt.class, Zelt.class, allZelt, allZeltFromLager,
                (s, t) -> s.getBezeichnung().equals(t.getBezeichnung()))//
                        .set((zelt, val, index) -> {
                            if (index == 0) {
                                zelt.setChecked((boolean) val);
                            }
                        })//
                        .get((zelt, index) -> {
                            if (index == 0) {
                                return zelt.isChecked();
                            } else if (index == 1) {
                                return zelt.getBezeichnung();
                            }
                            return null;
                        })
                        .save((zelt, cb) -> get().speichereZeltZuLager(zelt.isChecked(), zelt.getId(),
                                zelt.isChecked() ? lagerCallback.get().getId() : zelt.getLager().getId(),
                                gespeichertCallback -> {
                                    zelt.setLager(gespeichertCallback);
                                    cb.get(null);
                                }))//
                        .addColumn(Columns.CHECK)//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Bezeichnung").build());//
    }

    public static JTableBuilder<Zelt> zelt(Loader<Zelt> allZelt) {
        return JTableBuilder.get(Zelt.class, allZelt)//
                .set((zelt, val, index) -> {
                    if (index == 1) {
                        zelt.setAngeschafft((Date) val);
                    } else if (index == 2) {
                        zelt.setPreis((Double) val);
                    } else if (index == 3) {
                        zelt.setWaehrung((Waehrung) val);
                    }
                })//
                .get((zelt, index) -> {
                    if (index == 0) {
                        return zelt.getBezeichnung();
                    } else if (index == 1) {
                        return zelt.getAngeschafft();
                    } else if (index == 2) {
                        return zelt.getPreis();
                    } else if (index == 3) {
                        return zelt.getWaehrung() != null ? zelt.getWaehrung().getBezeichnung() : null;
                    }
                    return null;
                })
                .save((zeltToSave, asyncCallback) -> get().speichereZelt(zeltToSave.getId(),
                        zeltToSave.getBezeichnung(), zeltToSave.getAngeschafft(), zeltToSave.getPreis(),
                        zeltToSave.getWaehrung(), asyncCallback))//
                .addColumn(ColumnBuilder.get(String.class).add("Bezeichnung").editable(false).build())
                .addColumn(ColumnBuilder.get(Date.class).add("Angeschafft").build())
                .addColumn(ColumnBuilder.get(Double.class).add("Preis").build()).addColumn(ColumnBuilder
                        .get(String.class).add("Währung").add(JComboBoxBuilder.get(Waehrung.class, result -> {
                            result.get(asList(Waehrung.values()));
                        }).map(w -> w.getBezeichnung()).build()).build());//
    }

    public static JTableBuilder<Zelt> zelteVonGruppe(Callback<Gruppe> gruppeCallback, Loader<Zelt> allZeltFromLager,
            Loader<Zelt> allFromGruppe) {
        return getWithCopy(Zelt.class, Zelt.class, allZeltFromLager, allFromGruppe,
                (s, t) -> s.getBezeichnung().equals(t.getBezeichnung()))//
                        .set((zelt, val, index) -> {
                            if (index == 0) {
                                zelt.setChecked((boolean) val);
                            }
                        })//
                        .get((zelt, index) -> {
                            if (index == 0) {
                                return zelt.isChecked();
                            } else if (index == 1) {
                                return zelt.getBezeichnung();
                            }
                            return null;
                        })
                        .save((zelt, cb) -> get().speichereZeltZuGruppe(zelt.isChecked(), zelt.getId(),
                                zelt.isChecked() ? gruppeCallback.get().getId() : zelt.getGruppe().getId(),
                                gespeichertCallback -> {
                                    zelt.setGruppe(gespeichertCallback);
                                    cb.get(null);
                                }))//
                        .addColumn(Columns.CHECK)//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Bezeichnung").build());//
    }

    public static JTableBuilder<Gruppe> gruppe(Callback<Lager> lagerCallback, SelectionCallback selectionCallback,
            Loader<Gruppe> gruppeLoader) {
        return getWithCopy(Gruppe.class, Gruppe.class, cb -> {
            if (selectionCallback.isSelected()) {
                get().getAllGruppen(cb);
            } else {
                get().getAllUnassignedGruppen(cb);
            }
        }, gruppeLoader, (s, t) -> s.getId().equals(t.getId()))//
                .set((gruppe, val, index) -> {
                    if (index == 0) {
                        gruppe.setChecked((boolean) val);
                        // Kann man das nicht unten beim Save machen?
                        gruppe.setLager(lagerCallback.get());
                    }
                })//
                .get((gruppe, index) -> {
                    if (index == 0) {
                        return gruppe.isChecked();
                    } else if (index == 1) {
                        return gruppe.getBezeichnung();
                    }
                    return null;
                })
                .save((gruppe, cb) -> get().speichereGruppe(gruppe.isChecked(), gruppe.getId(),
                        gruppe.isChecked() ? lagerCallback.get().getId() : gruppe.getLager().getId(), gruppe.getName(),
                        gruppe.getSchlachtruf(), gespeichertCallback -> {
                            Events.get().fireGruppeSaved(gespeichertCallback,
                                    gruppe.isChecked() ? null : gruppe.getLager(),
                                    gruppe.isChecked() ? lagerCallback.get() : null);
                            cb.get(null);
                        }))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Bezeichnung").build());//
    }

    public static JTableBuilder<Leiter> leiter(Callback<Gruppe> gruppeCallback, Loader<Person> loaderPerson,
            Loader<Leiter> allLeiter) {
        return getWithCopy(Person.class, Leiter.class, loaderPerson, allLeiter, (s, t) -> s.getId().equals(t.getId()))//
                .set((person, val, index) -> {
                    if (index == 0) {
                        person.setChecked((boolean) val);
                    }
                })//
                .get((person, index) -> {
                    if (index == 0) {
                        return person.isChecked();
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
                }).save((le, cb) -> get().speichereLeiter(le.isChecked(), le.getId(),
                        le.isChecked() ? gruppeCallback.get().getId() : le.getGruppe().getId(), gespeichertCallback -> {
                            Events.get().fireLeiterSaved(le, le.isChecked() ? null : gruppeCallback.get(),
                                    le.isChecked() ? gruppeCallback.get() : null);
                            le.setGruppe(gespeichertCallback);
                            cb.get(null);
                        }))//
                .addColumn(Columns.CHECK)//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Nachname").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Vorname").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Straße").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("PLZ").build())//
                .addColumn(ColumnBuilder.get(String.class).editable(false).add("Ort").build())//
                .addColumn(ColumnBuilder.get(Date.class).editable(false).add("Geburtsdatum").build());//
    }

    public static JTableBuilder<Teilnehmer> teilnehmer(Callback<Gruppe> gruppeCallback, Loader<Person> loaderPerson,
            Loader<Teilnehmer> allTeilnehmer) {
        return getWithCopy(Person.class, Teilnehmer.class, loaderPerson, allTeilnehmer,
                (s, t) -> s.getId().equals(t.getId()))//
                        .set((person, val, index) -> {
                            if (index == 0) {
                                person.setChecked((boolean) val);
                            }
                        })//
                        .get((person, index) -> {
                            if (index == 0) {
                                return person.isChecked();
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
                        .save((te, cb) -> get().speichereTeilnehmer(te.isChecked(), te.getId(),
                                te.isChecked() ? gruppeCallback.get().getId() : te.getGruppe().getId(),
                                gespeichertCallback -> {
                                    Events.get().fireTeilnehmerSaved(te, te.isChecked() ? null : gruppeCallback.get(),
                                            te.isChecked() ? gruppeCallback.get() : null);
                                    te.setGruppe(gespeichertCallback);
                                    cb.get(null);
                                }))//
                        .addColumn(Columns.CHECK)//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Nachname").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Vorname").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Straße").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("PLZ").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Ort").build())//
                        .addColumn(ColumnBuilder.get(Date.class).editable(false).add("Geburtsdatum").build());//
    }

    public static JTableBuilder<Programm> programm(Callback<Lager> lagerCallback, Loader<Programm> loader) {
        return JTableBuilder.get(Programm.class, loader)//
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
                .save((p, cb) -> get().speichereProgramm(lagerCallback.get().getId(), p.getId(), p.getDatum(),
                        p.getMorgen(), p.getMittag(), p.getAbend(), cb))//
                .delete((programms, cb) -> get()
                        .loescheProgramm(programms.stream().map(p -> p.getId()).collect(toList()), cb))//
                .addColumn(ColumnBuilder.get(Date.class).add("Datum").width(110).build())//
                .addColumn(Columns.WOCHENTAG)//
                .addColumn(ColumnBuilder.get(String.class).add("Morgen").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Mittag").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Abend").multiline().build());//
    }

    public static JTableBuilder<Essen> essen(Callback<Lager> lagerCallback, Loader<Essen> loader) {
        return JTableBuilder.get(Essen.class, loader)//
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
                .save((e, cb) -> get().speichereEssen(lagerCallback.get().getId(), e.getId(), e.getDatum(),
                        e.getMorgen(), e.getMittag(), e.getAbend(), cb))//
                .delete((es, cb) -> get().loescheEssen(es.stream().map(p -> p.getId()).collect(toList()), cb))//
                .addColumn(ColumnBuilder.get(Date.class).add("Datum").width(110).build())//
                .addColumn(Columns.WOCHENTAG)//
                .addColumn(ColumnBuilder.get(String.class).add("Morgen").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Mittag").multiline().build())//
                .addColumn(ColumnBuilder.get(String.class).add("Abend").multiline().build());//
    }

    public static JTableBuilder<Legenda> legenda(Loader<Legenda> loader) {
        return JTableBuilder.get(Legenda.class, loader)//
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
                .save((lg, cb) -> get().speichereLegenda(lg.getId(), lg.getLagerOrt().getId(), lg.getName(),
                        lg.getVorname(), lg.getFirma(), lg.getStrasse(), lg.getPlz(), lg.getOrt(),
                        lg.getLegendaTyp() != null ? lg.getLegendaTyp().getId() : null,
                        lg.getAnrede() != null ? lg.getAnrede().getId() : null, lg.getStrasse(), lg.getFax(),
                        lg.getHandy(), lg.getEmail(), lg.getBemerkung(), cb))//
                .addColumn(ColumnBuilder.get(Legendatyp.class)
                        .add(JComboBoxBuilder.get(Legendatyp.class, get()::getAllLegendatyp).build()).add("Typ").desc()
                        .build())//
                .addColumn(ColumnBuilder.get(Anrede.class)
                        .add(JComboBoxBuilder.get(Anrede.class, get()::getAllAnrede).build()).add("Anrede").build())//
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

    public static JTableBuilder<Lagerinfo> lagerinfo(Loader<Person> loaderPerson, Loader<Lagerinfo> allLagerinfo) {
        return getWithCopy(Person.class, Lagerinfo.class, loaderPerson, allLagerinfo,
                (s, t) -> s.getId().equals(t.getId()))//
                        .set((li, val, index) -> {
                            if (index == 0) {
                                li.setChecked((boolean) val);
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
                        }).save((li, cb) -> get().speichereLagerinfo(li.isChecked(), li.getId(), cb))
                        .addColumn(Columns.CHECK)//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Nachname").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Vorname").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Straße").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("PLZ").build())//
                        .addColumn(ColumnBuilder.get(String.class).editable(false).add("Ort").build())//
                        .addColumn(ColumnBuilder.get(Date.class).editable(false).add("Geburtsdatum").build());//
    }

    public static JTableBuilder<Schaden> schaden(Loader<Schaden> loader) {
        return JTableBuilder.get(Schaden.class, loader)//
                .set((s, val, index) -> {
                    if (index == 0) {
                        s.setDatum((Date) val);
                    } else if (index == 1) {
                        s.setSchaden((String) val);
                    }
                }).get((s, index) -> {
                    if (index == 0) {
                        return s.getDatum();
                    } else if (index == 1) {
                        return s.getSchaden();
                    }
                    return null;
                })//
                .save((s, cb) -> get().speichereSchaden(s.getId(), s.getZelt().getId(), s.getDatum(), s.getSchaden(),
                        cb))//
                .delete((schaeden, cb) -> get().loescheSchaeden(schaeden.stream().map(p -> p.getId()).collect(toList()),
                        cb))
                .addColumn(ColumnBuilder.get(Date.class).add("Datum").preferredWidth(70).build())//
                .addColumn(ColumnBuilder.get(String.class).add("Schaden").multiline().preferredWidth(270).build());//
    }

    public static JTableBuilder<Zeltverleih> zeltverleih(Loader<Zeltverleih> loader) {
        return JTableBuilder.get(Zeltverleih.class, loader)//
                .set((zv, val, index) -> {
                    if (index == 0) {
                        zv.setDatum((Date) val);
                    } else if (index == 1) {
                        zv.setPerson((String) val);
                    } else if (index == 2) {
                        zv.setBemerkung((String) val);
                    }
                }).get((zv, index) -> {
                    if (index == 0) {
                        return zv.getDatum();
                    } else if (index == 1) {
                        return zv.getPerson();
                    } else if (index == 2) {
                        return zv.getBemerkung();
                    }
                    return null;
                })//
                .save((zv, cb) -> get().speichereZeltverleih(zv.getId(), zv.getZelt().getId(), zv.getDatum(),
                        zv.getPerson(), zv.getBemerkung(), cb))//
                .delete((zvs, cb) -> get().loescheZeltverleihe(zvs.stream().map(zv -> zv.getId()).collect(toList()),
                        cb))
                .addColumn(ColumnBuilder.get(Date.class).add("Datum").preferredWidth(30).build())//
                .addColumn(ColumnBuilder.get(String.class).add("Person").build())//
                .addColumn(ColumnBuilder.get(String.class).add("Bemerkung").multiline().build());//
    }

    private static String getWochentag(Date datum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.GERMAN);
    }

    private static <S, T> JTableBuilder<T> getWithCopy(Class<S> sClass, Class<T> tClass, Loader<S> sourceLoader,
            Loader<T> targetLoader, Comparator<S, T> comparator) {
        return JTableBuilder.get(tClass, asyncCallback -> {
            sourceLoader.get(allSource -> targetLoader.get(allTarget -> {
                List<T> result = allSource.stream().map(s -> {
                    for (T t : allTarget) {
                        if (comparator.isEqual(s, t)) {
                            return t;
                        }
                    }
                    try {
                        return tClass.getDeclaredConstructor(sClass).newInstance(s);
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }).collect(toList());
                asyncCallback.get(result);
            }));
        });
    }
}
