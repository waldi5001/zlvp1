package de.zlvp.controller;

import java.time.Year;
import java.util.Date;
import java.util.List;

import de.zlvp.dao.AnredeDao;
import de.zlvp.dao.EssenDao;
import de.zlvp.dao.FunktionDao;
import de.zlvp.dao.GeschlechtDao;
import de.zlvp.dao.GruppeDao;
import de.zlvp.dao.JahrDao;
import de.zlvp.dao.LagerDao;
import de.zlvp.dao.LagerinfoDao;
import de.zlvp.dao.LagerortDao;
import de.zlvp.dao.LegendaDao;
import de.zlvp.dao.LegendatypDao;
import de.zlvp.dao.LeiterDao;
import de.zlvp.dao.MaterialwartDao;
import de.zlvp.dao.PersonDao;
import de.zlvp.dao.ProgrammDao;
import de.zlvp.dao.SchadenDao;
import de.zlvp.dao.StabDao;
import de.zlvp.dao.TeilnehmerDao;
import de.zlvp.dao.UserDao;
import de.zlvp.dao.ZeltDao;
import de.zlvp.dao.ZeltdetailBezeichnungDao;
import de.zlvp.dao.ZeltdetailDao;
import de.zlvp.dao.ZeltverleihDao;
import de.zlvp.entity.Anrede;
import de.zlvp.entity.Essen;
import de.zlvp.entity.Funktion;
import de.zlvp.entity.Geschlecht;
import de.zlvp.entity.Gruppe;
import de.zlvp.entity.Jahr;
import de.zlvp.entity.Lager;
import de.zlvp.entity.Lagerinfo;
import de.zlvp.entity.Lagerort;
import de.zlvp.entity.Legenda;
import de.zlvp.entity.Legendatyp;
import de.zlvp.entity.Leiter;
import de.zlvp.entity.Materialwart;
import de.zlvp.entity.Person;
import de.zlvp.entity.Programm;
import de.zlvp.entity.Schaden;
import de.zlvp.entity.Stab;
import de.zlvp.entity.Teilnehmer;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltdetail;
import de.zlvp.entity.ZeltdetailBezeichnung;
import de.zlvp.entity.Zeltverleih;

public class ControllerImpl implements Controller {

    private JahrDao jahrDao;
    private PersonDao personDao;
    private AnredeDao anredeDao;
    private FunktionDao funktionDao;
    private GeschlechtDao geschlechtDao;
    private LagerinfoDao lagerinfoDao;
    private LagerDao lagerDao;
    private StabDao stabDao;
    private MaterialwartDao materialwartDao;
    private GruppeDao gruppeDao;
    private LeiterDao leiterDao;
    private TeilnehmerDao teilnehmerDao;
    private ZeltDao zeltDao;
    private ProgrammDao programmDao;
    private EssenDao essenDao;
    private SchadenDao schadenDao;
    private ZeltverleihDao zeltverleihDao;
    private LagerortDao lagerortDao;
    private LegendaDao legendaDao;
    private LegendatypDao legendatypDao;
    private ZeltdetailBezeichnungDao zeltdetailBezeichnungDao;
    private UserDao userDao;
    private ZeltdetailDao zeltdetailDao;

    @Override
    public List<Person> findPerson(String vorname, String nachname) {
        return personDao.findPerson(vorname, nachname);
    }

    @Override
    public List<Anrede> getAllAnrede() {
        return anredeDao.getAll();
    }

    @Override
    public List<Funktion> getAllFunktion() {
        return funktionDao.getAll();
    }

    @Override
    public List<Geschlecht> getAllGeschlecht() {
        return geschlechtDao.getAll();
    }

    @Override
    public List<Lagerinfo> getAllLagerinfo() {
        List<Lagerinfo> alleLagerinfo = lagerinfoDao.getAll();
        for (Lagerinfo lagerinfo : alleLagerinfo) {
            lagerinfo.setGeschlecht(geschlechtDao.getFromPerson(lagerinfo.getOriginalId()));
        }
        return alleLagerinfo;
    }

    @Override
    public List<Jahr> getAllJahr() {
        int heutigesJahr = Year.now().getValue();

        Jahr jahr = jahrDao.getJahrByBezeichnung(heutigesJahr);
        if (jahr == null) {
            jahrDao.insertJahr(heutigesJahr);
        }

        return jahrDao.getAll();
    }

    @Override
    public Jahr getJahr(int jahrId) {
        Jahr jahr = jahrDao.getJahr(jahrId);
        jahr.getLager().addAll(lagerDao.getAll(jahrId));
        for (Lager lager : jahr.getLager()) {
            lager.setJahr(jahr);
            lager.getGruppe().addAll(gruppeDao.getAllFromLager(lager.getId()));
            lager.setLagerort(lagerortDao.getFromLager(lager.getId()));
            for (Gruppe g : lager.getGruppe()) {
                g.getLeiter().addAll(leiterDao.getAll(g.getOriginalId()));
                g.getTeilnehmer().addAll(teilnehmerDao.getAll(g.getOriginalId()));

                for (Leiter leiter : g.getLeiter()) {
                    leiter.setGeschlecht(geschlechtDao.getFromPerson(leiter.getOriginalId()));
                }

                for (Teilnehmer teilnehmer : g.getTeilnehmer()) {
                    teilnehmer.setGeschlecht(geschlechtDao.getFromPerson(teilnehmer.getOriginalId()));
                }
            }
        }
        return jahr;
    }

    @Override
    public List<Lager> getAllLager(int jahrId) {
        return lagerDao.getAll(jahrId);
    }

    @Override
    public List<Stab> getAllStab(int lagerId) {
        List<Stab> stab = stabDao.getAll(lagerId);
        for (Stab s : stab) {
            s.setGeschlecht(geschlechtDao.getFromPerson(s.getOriginalId()));
            s.setFunktion(funktionDao.getFromStab(s.getId()));
        }
        return stab;
    }

    @Override
    public List<Materialwart> getAllMaterialwart(int lagerId) {
        List<Materialwart> all = materialwartDao.getAll(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Materialwart mw : all) {
            mw.setGeschlecht(geschlechtDao.getFromPerson(mw.getOriginalId()));
            mw.setLager(lager);
        }
        return all;
    }

    @Override
    public List<Gruppe> getAllUnassignedGruppen() {
        return gruppeDao.getAllUnasigned();
    }

    @Override
    public List<Gruppe> getAllGruppen() {
        return gruppeDao.getAll();
    }

    @Override
    public List<Gruppe> getAllGruppenFromLager(int lagerId) {
        List<Gruppe> allFromLager = gruppeDao.getAllFromLager(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Gruppe gruppe : allFromLager) {
            gruppe.setLager(lager);
        }
        return allFromLager;
    }

    @Override
    public List<Leiter> getAllLeiter(int gruppeId) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Leiter> all = leiterDao.getAll(gruppeId);
        for (Leiter leiter : all) {
            leiter.setGruppe(gruppe);
            leiter.setGeschlecht(geschlechtDao.getFromPerson(leiter.getOriginalId()));
        }
        return all;
    }

    @Override
    public List<Teilnehmer> getAllTeilnehmer(int gruppeId) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Teilnehmer> all = teilnehmerDao.getAll(gruppeId);
        for (Teilnehmer teilnehmer : all) {
            teilnehmer.setGruppe(gruppe);
            teilnehmer.setGeschlecht(geschlechtDao.getFromPerson(teilnehmer.getOriginalId()));
        }
        return all;
    }

    @Override
    public List<Person> getAllPerson() {
        return personDao.getAll();
    }

    @Override
    public List<Zelt> getAllZelt() {
        return zeltDao.getAll();
    }

    @Override
    public List<Zelt> getAllZeltFromLager(int lagerId) {
        List<Zelt> allFromLager = zeltDao.getAllFromLager(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Zelt zelt : allFromLager) {
            zelt.getLager().add(lager);
        }
        return allFromLager;
    }

    @Override
    public List<Zelt> getAllZeltFromGruppe(int gruppeId) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Zelt> allFromGruppe = zeltDao.getAllFromGruppe(gruppeId);
        for (Zelt zelt : allFromGruppe) {
            zelt.getGruppe().add(gruppe);
        }
        return allFromGruppe;
    }

    @Override
    public List<Programm> getAllProgramm(int lagerId) {
        return programmDao.getAllFromLager(lagerId);
    }

    @Override
    public List<Essen> getAllEssen(int lagerId) {
        return essenDao.getAllFromLager(lagerId);
    }

    @Override
    public List<Lagerort> getAllLagerort() {
        return lagerortDao.getAll();
    }

    @Override
    public List<Legendatyp> getAllLegendatyp() {
        return legendatypDao.getAll();
    }

    @Override
    public List<Legenda> getAllLegendaFromLagerort(int lagerortId) {
        List<Legenda> legenda = legendaDao.getAllFromLagerort(lagerortId);
        for (Legenda l : legenda) {
            l.setLegendaTyp(legendatypDao.getFromLegenda(l.getId()));
            l.setAnrede(anredeDao.getAnredeFromLegenda(l.getId()));
        }
        return legenda;
    }

    @Override
    public List<Zeltdetail> getAllZeltdetail(int zeltId) {
        List<Zeltdetail> allFromZelt = zeltdetailDao.getAllFromZelt(zeltId);
        for (Zeltdetail zeltdetail : allFromZelt) {
            zeltdetail.setZeltdetailbezeichnung(zeltdetailBezeichnungDao.getForZeltdetail(zeltdetail.getId()));
        }
        return allFromZelt;
    }

    @Override
    public List<ZeltdetailBezeichnung> getAllZeltdetailBezeichnung() {
        return zeltdetailBezeichnungDao.getAll();
    }

    @Override
    public List<Schaden> getAllSchaeden(Integer id) {
        return schadenDao.getAllFromZelt(id);
    }

    @Override
    public List<Zeltverleih> getAllZeltverleih(Integer id) {
        return zeltverleihDao.getAllFromZelt(id);
    }

    @Override
    public void speichereZeltDetailBezeichnung(String detailBezeichnung) {
        zeltdetailBezeichnungDao.speichern(detailBezeichnung);
    }

    @Override
    public void speichereLager(Integer id, String name, String thema, Date start, Date stop, int jahrId,
            int lagerortId) {
        lagerDao.speichern(id, name, thema, start, stop, lagerortId, jahrId);
    }

    @Override
    public void speichereLagerort(String lagerort) {
        lagerortDao.speichern(lagerort);
    }

    @Override
    public void speichereLegenda(Integer id, int lagerortId, String nachname, String vorname, String firma,
            String strasse, String plz, String ort, Integer legendatypId, Integer anredeId, String tel, String fax,
            String handy, String email, String bemerkung) {
        legendaDao.speichern(id, lagerortId, nachname, vorname, firma, strasse, plz, ort, legendatypId, anredeId, tel,
                fax, handy, email, bemerkung);
    }

    @Override
    public void speicherePerson(Integer id, int geschlechtId, String vorname, String nachname, String strasse,
            String plz, String ort, Date gebtag, String telnr, String email, String handy, String nottel) {
        personDao.speichern(id, vorname, nachname, gebtag, strasse, plz, ort, telnr, email, geschlechtId, handy,
                nottel);
    }

    @Override
    public void speichereStab(Integer id, int personId, int geschlechtId, String vorname, String nachname,
            String strasse, String plz, String ort, Date gebtag, String telnr, String email, String handy,
            String nottel, Integer funktionId, int lagerId) {
        personDao.speichern(personId, vorname, nachname, gebtag, strasse, plz, ort, telnr, email, geschlechtId, handy,
                nottel);

        if (id == null) {
            // Wenn ein Stab ohne Funktion ausgewählt wird.
            if (funktionId != null) {
                Stab stab = stabDao.speichern(lagerId, personId);
                stabDao.speichereStaabFunktion(stab.getId(), funktionId);
            }
        } else {
            if (funktionId == null) {
                stabDao.loeschen(id);
            } else {
                stabDao.speichereStaabFunktion(id, funktionId);
            }
        }
    }

    @Override
    public void speichereZelt(Integer zeltId, String bezeichnung, Date angeschafft, double preis) {
        zeltDao.speichern(zeltId, bezeichnung, angeschafft, preis, "EUR");
    }

    @Override
    public void speichereZeltZuLager(Integer id, int zeltId, Integer lagerId) {
        if (lagerId != null) {
            zeltDao.speichernZuLager(zeltId, lagerId);
        } else {
            zeltDao.loeschenZuLager(id);
        }

    }

    @Override
    public void speichereZeltZuGruppe(Integer id, int zeltId, Integer gruppeId) {
        if (gruppeId != null) {
            // id ist von stLaZe gefüllt
            zeltDao.speichernZuGruppe(zeltId, gruppeId);
        } else {
            zeltDao.loeschenZuGruppe(id);
        }

    }

    @Override
    public void speichereSchaden(Integer id, int zeltId, Date datum, String schaden) {
        schadenDao.speichern(id, zeltId, datum, schaden);
    }

    @Override
    public void speichereZeltverleih(Integer id, int zeltId, Date datum, String person, String bemerkung) {
        zeltverleihDao.speichern(id, zeltId, datum, person, bemerkung);
    }

    @Override
    public void speichereZeltdetail(Integer id, int zeltId, int anzahl, int bezeichnung, String schluessel) {
        zeltdetailDao.speichern(id, zeltId, anzahl, bezeichnung, schluessel);
    }

    @Override
    public void speichereMaterialwart(Integer id, int personId, int geschlechtId, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String nottel, Integer lagerId) {

        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlechtId, handy,
                nottel);

        if (lagerId != null) {
            materialwartDao.speichern(lagerId, personId);
        } else if (id != null && lagerId == null) {
            materialwartDao.loeschen(id);
        }
    }

    @Override
    public void loescheSchaden(Integer id) {
        schadenDao.loeschen(id);
    }

    @Override
    public void loescheZeltverleih(int id) {
        zeltverleihDao.loeschen(id);
    }

    @Override
    public void loescheZeltdetail(int id) {
        zeltdetailDao.loeschen(id);
    }

    @Override
    public void loescheZeltdetailBezeichnung(int id) {
        zeltdetailBezeichnungDao.loeschen(id);
    }

    @Override
    public void speichereGruppe(Integer id, Integer gruppeId, Integer lagerId, String name, String schlachtruf) {
        Gruppe gruppe = gruppeDao.speichereGruppe(gruppeId, name, schlachtruf);
        // Assign Unsassigned Fall
        if (id == null && gruppeId != null && lagerId != null) {
            gruppeDao.speicherenLager(lagerId, gruppeId);
            // Neu Anlegen Fall
        } else if (id == null && gruppeId == null && lagerId != null) {
            gruppeDao.speicherenLager(lagerId, gruppe.getId());
        } else if (id != null && lagerId == null) {
            gruppeDao.loeschen(id);
        }
    }

    @Override
    public void speichereLeiter(Integer id, int personId, Integer geschlechtId, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, Integer gruppeId) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlechtId, handy,
                telNr2);
        if (id == null && gruppeId != null) {
            leiterDao.speichere(gruppeId, personId);
        } else if (id != null && gruppeId == null) {
            leiterDao.loesche(id);
        }
    }

    @Override
    public void speichereTeilnehmer(Integer id, int personId, Integer geschlechtId, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, Integer gruppeId) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlechtId, handy,
                telNr2);
        if (id == null && gruppeId != null) {
            teilnehmerDao.speichere(gruppeId, personId);
        } else if (id != null && gruppeId == null) {
            teilnehmerDao.loesche(id);
        }
    }

    @Override
    public void aenderePasswort(String user, String neuesPasswort) {
        userDao.changePasswort(user, neuesPasswort);
    }

    @Override
    public void speichereProgramm(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend) {
        if (id == null && lagerId != null) {
            programmDao.speichereProgramm(lagerId, datum, morgen, mittag, abend);
        } else if (id != null && lagerId == null) {
            programmDao.loescheProgramm(id);
        } else {
            programmDao.aendereProgramm(id, morgen, mittag, abend, datum);
        }
    }

    @Override
    public void speichereEssen(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend) {
        if (id == null && lagerId != null) {
            essenDao.speichereEssen(lagerId, datum, morgen, mittag, abend);
        } else if (id != null && lagerId == null) {
            essenDao.loescheEssen(id);
        } else {
            essenDao.aendereEssen(id, morgen, mittag, abend, datum);
        }
    }

    @Override
    public void speichereLagerinfo(Integer id, int personId, Integer geschlechtId, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, boolean checked) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlechtId, handy,
                telNr2);
        if (checked) {
            lagerinfoDao.speichereLagerinfo(personId);
        } else if (id != null && !checked) {
            lagerinfoDao.loesche(id);
        }
    }

    @Override
    public void verschiebeGruppe(int gruppenId, int lagerId) {
        Gruppe gruppe = gruppeDao.getFromLager(gruppenId);
        gruppeDao.loeschen(gruppenId);
        gruppeDao.speicherenLager(lagerId, gruppe.getOriginalId());
    }

    @Override
    public void verschiebeLeiter(int id, int gruppeId) {
        Leiter leiter = leiterDao.get(id);
        leiterDao.loesche(id);
        leiterDao.speichere(gruppeId, leiter.getOriginalId());
    }

    @Override
    public void verschiebeTeilnehmer(int id, int gruppeId) {
        Teilnehmer teilnehmer = teilnehmerDao.get(id);
        teilnehmerDao.loesche(id);
        teilnehmerDao.speichere(gruppeId, teilnehmer.getOriginalId());
    }

    public void setJahrDao(JahrDao jahrDao) {
        this.jahrDao = jahrDao;
    }

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void setAnredeDao(AnredeDao anredeDao) {
        this.anredeDao = anredeDao;
    }

    public void setFunktionDao(FunktionDao funktionDao) {
        this.funktionDao = funktionDao;
    }

    public void setGeschlechtDao(GeschlechtDao geschlechtDao) {
        this.geschlechtDao = geschlechtDao;
    }

    public void setLagerinfoDao(LagerinfoDao lagerinfoDao) {
        this.lagerinfoDao = lagerinfoDao;
    }

    public void setLagerDao(LagerDao lagerDao) {
        this.lagerDao = lagerDao;
    }

    public void setStabDao(StabDao stabDao) {
        this.stabDao = stabDao;
    }

    public void setMaterialwartDao(MaterialwartDao materialwartDao) {
        this.materialwartDao = materialwartDao;
    }

    public void setGruppeDao(GruppeDao gruppeDao) {
        this.gruppeDao = gruppeDao;
    }

    public void setLeiterDao(LeiterDao leiterDao) {
        this.leiterDao = leiterDao;
    }

    public void setTeilnehmerDao(TeilnehmerDao teilnehmerDao) {
        this.teilnehmerDao = teilnehmerDao;
    }

    public void setZeltDao(ZeltDao zeltDao) {
        this.zeltDao = zeltDao;
    }

    public void setProgrammDao(ProgrammDao programmDao) {
        this.programmDao = programmDao;
    }

    public void setEssenDao(EssenDao essenDao) {
        this.essenDao = essenDao;
    }

    public void setSchadenDao(SchadenDao schadenDao) {
        this.schadenDao = schadenDao;
    }

    public void setZeltverleihDao(ZeltverleihDao zeltverleihDao) {
        this.zeltverleihDao = zeltverleihDao;
    }

    public void setLagerortDao(LagerortDao lagerortDao) {
        this.lagerortDao = lagerortDao;
    }

    public void setLegendaDao(LegendaDao legendaDao) {
        this.legendaDao = legendaDao;
    }

    public void setLegendatypDao(LegendatypDao legendatypDao) {
        this.legendatypDao = legendatypDao;
    }

    public void setZeltdetailBezeichnungDao(ZeltdetailBezeichnungDao zeltdetailBezeichnungDao) {
        this.zeltdetailBezeichnungDao = zeltdetailBezeichnungDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setZeltdetailDao(ZeltdetailDao zeltdetailDao) {
        this.zeltdetailDao = zeltdetailDao;
    }

}
