package de.zlvp.controller;

import static de.zlvp.entity.Funktion.REMOVE;
import static java.lang.String.valueOf;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.zlvp.dao.AnredeDao;
import de.zlvp.dao.EssenDao;
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
import de.zlvp.entity.User;
import de.zlvp.entity.Waehrung;
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltdetail;
import de.zlvp.entity.ZeltdetailBezeichnung;
import de.zlvp.entity.Zeltverleih;

public class ControllerImpl implements Controller {

    private JahrDao jahrDao;
    private PersonDao personDao;
    private AnredeDao anredeDao;
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
    public void findPerson(String vorname, String nachname, AsyncCallback<List<Person>> callback) {
        callback.get(personDao.findPerson(vorname, nachname));
    }

    @Override
    public void getAllAnrede(AsyncCallback<List<Anrede>> callback) {
        callback.get(anredeDao.getAll());
    }

    @Override
    public void getAllLagerinfo(AsyncCallback<List<Lagerinfo>> callback) {
        List<Lagerinfo> all = lagerinfoDao.getAll();
        for (Lagerinfo lagerinfo : all) {
            // Ob das hier so gut ist? Brauch ich weil ich kein anderes
            // Kriterium hab um anzuzeigen dass ich eine Lagerinfo bin.
            lagerinfo.setChecked(true);
        }
        callback.get(all);
    }

    @Override
    public void getAllJahr(AsyncCallback<List<Jahr>> callback) {
        int heutigesJahr = Year.now().getValue();

        Jahr jahr = jahrDao.getJahrByBezeichnung(heutigesJahr);
        if (jahr == null) {
            jahrDao.insertJahr(heutigesJahr);
        }

        callback.get(jahrDao.getAll());
    }

    @Override
    public void getJahr(int jahrId, AsyncCallback<Jahr> callback) {
        Jahr jahr = jahrDao.getJahr(jahrId);
        jahr.getLager().addAll(lagerDao.getAll(jahrId));
        for (Lager lager : jahr.getLager()) {
            lager.setJahr(jahr);
            lager.getGruppe().addAll(gruppeDao.getAllFromLager(lager.getId()));
            lager.setLagerort(lagerortDao.getFromLager(lager.getId()));
            for (Gruppe g : lager.getGruppe()) {
                g.getLeiter().addAll(leiterDao.getAll(g.getOriginalId()));
                g.getTeilnehmer().addAll(teilnehmerDao.getAll(g.getOriginalId()));
            }
        }
        callback.get(jahr);
    }

    @Override
    public void getAllLager(int jahrId, AsyncCallback<List<Lager>> callback) {
        callback.get(lagerDao.getAll(jahrId));
    }

    @Override
    public void getAllStab(int lagerId, AsyncCallback<List<Stab>> callback) {
        callback.get(stabDao.getAll(lagerId));
    }

    @Override
    public void getAllMaterialwart(int lagerId, AsyncCallback<List<Materialwart>> callback) {
        List<Materialwart> all = materialwartDao.getAll(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Materialwart mw : all) {
            mw.setLager(lager);
        }
        callback.get(all);
    }

    @Override
    public void getAllUnassignedGruppen(AsyncCallback<List<Gruppe>> callback) {
        callback.get(gruppeDao.getAllUnasigned());
    }

    @Override
    public void getAllGruppen(AsyncCallback<List<Gruppe>> callback) {
        callback.get(gruppeDao.getAll());
    }

    @Override
    public void getAllGruppenFromLager(int lagerId, AsyncCallback<List<Gruppe>> callback) {
        List<Gruppe> allFromLager = gruppeDao.getAllFromLager(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Gruppe gruppe : allFromLager) {
            gruppe.setLager(lager);
        }
        callback.get(allFromLager);
    }

    @Override
    public void getAllLeiter(int gruppeId, AsyncCallback<List<Leiter>> callback) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Leiter> all = leiterDao.getAll(gruppeId);
        for (Leiter leiter : all) {
            leiter.setGruppe(gruppe);
        }
        callback.get(all);
    }

    @Override
    public void getAllTeilnehmer(int gruppeId, AsyncCallback<List<Teilnehmer>> callback) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Teilnehmer> all = teilnehmerDao.getAll(gruppeId);
        for (Teilnehmer teilnehmer : all) {
            teilnehmer.setGruppe(gruppe);
        }
        callback.get(all);
    }

    @Override
    public void getAllPersons(AsyncCallback<List<Person>> callback) {
        callback.get(personDao.getAll());
    }

    @Override
    public void getAllZelt(AsyncCallback<List<Zelt>> callback) {
        callback.get(zeltDao.getAll());
    }

    @Override
    public void getAllZeltFromLager(int lagerId, AsyncCallback<List<Zelt>> callback) {
        List<Zelt> allFromLager = zeltDao.getAllFromLager(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Zelt zelt : allFromLager) {
            zelt.getLager().add(lager);
        }
        callback.get(allFromLager);
    }

    @Override
    public void getAllZeltFromGruppe(int gruppeId, AsyncCallback<List<Zelt>> callback) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Zelt> allFromGruppe = zeltDao.getAllFromGruppe(gruppeId);
        for (Zelt zelt : allFromGruppe) {
            zelt.getGruppe().add(gruppe);
        }
        callback.get(allFromGruppe);
    }

    @Override
    public void getAllProgramm(int lagerId, AsyncCallback<List<Programm>> callback) {
        callback.get(programmDao.getAllFromLager(lagerId));
    }

    @Override
    public void getAllEssen(int lagerId, AsyncCallback<List<Essen>> callback) {
        callback.get(essenDao.getAllFromLager(lagerId));
    }

    @Override
    public void getAllLagerort(AsyncCallback<List<Lagerort>> callback) {
        callback.get(lagerortDao.getAll());
    }

    @Override
    public void getAllLegendatyp(AsyncCallback<List<Legendatyp>> callback) {
        callback.get(legendatypDao.getAll());
    }

    @Override
    public void getAllLegendaFromLagerort(int lagerortId, AsyncCallback<List<Legenda>> callback) {
        List<Legenda> legenda = legendaDao.getAllFromLagerort(lagerortId);
        for (Legenda l : legenda) {
            l.setLegendaTyp(legendatypDao.getFromLegenda(l.getId()));
            l.setAnrede(anredeDao.getAnredeFromLegenda(l.getId()));
        }
        callback.get(legenda);
    }

    @Override
    public void getAllZeltdetail(int zeltId, AsyncCallback<List<Zeltdetail>> callback) {
        List<Zeltdetail> allFromZelt = zeltdetailDao.getAllFromZelt(zeltId);
        for (Zeltdetail zeltdetail : allFromZelt) {
            zeltdetail.setZeltdetailbezeichnung(zeltdetailBezeichnungDao.getForZeltdetail(zeltdetail.getId()));
        }
        callback.get(allFromZelt);
    }

    @Override
    public void getAllZeltdetailBezeichnung(AsyncCallback<List<ZeltdetailBezeichnung>> callback) {
        callback.get(zeltdetailBezeichnungDao.getAll());
    }

    @Override
    public void getAllSchaeden(Integer id, AsyncCallback<List<Schaden>> callback) {
        callback.get(schadenDao.getAllFromZelt(id));
    }

    @Override
    public void getAllZeltverleih(Integer id, AsyncCallback<List<Zeltverleih>> callback) {
        callback.get(zeltverleihDao.getAllFromZelt(id));
    }

    @Override
    public void speichereZeltDetailBezeichnung(String detailBezeichnung, AsyncCallback<Void> callback) {
        zeltdetailBezeichnungDao.speichern(detailBezeichnung);
        callback.get(null);
    }

    @Override
    public void speichereLager(Integer id, String name, String thema, Date start, Date stop, int jahrId, int lagerortId,
            AsyncCallback<Void> callback) {
        lagerDao.speichern(id, name, thema, start, stop, lagerortId, jahrId);
        callback.get(null);
    }

    @Override
    public void speichereLagerort(String lagerort, AsyncCallback<Void> callback) {
        lagerortDao.speichern(lagerort);
        callback.get(null);
    }

    @Override
    public void speichereLegenda(Integer id, int lagerortId, String nachname, String vorname, String firma,
            String strasse, String plz, String ort, Integer legendatypId, Integer anredeId, String tel, String fax,
            String handy, String email, String bemerkung, AsyncCallback<Void> callback) {
        legendaDao.speichern(id, lagerortId, nachname, vorname, firma, strasse, plz, ort, legendatypId, anredeId, tel,
                fax, handy, email, bemerkung);
        callback.get(null);
    }

    @Override
    public void speicherePerson(Integer id, Geschlecht geschlecht, String vorname, String nachname, String strasse,
            String plz, String ort, Date gebtag, String telnr, String email, String handy, String nottel,
            AsyncCallback<Void> callback) {
        personDao.speichern(id, vorname, nachname, gebtag, strasse, plz, ort, telnr, email, geschlecht, handy, nottel);
        callback.get(null);
    }

    @Override
    public void speichereStab(Integer id, int personId, Geschlecht geschlecht, String vorname, String nachname,
            String strasse, String plz, String ort, Date gebtag, String telnr, String email, String handy,
            String nottel, Funktion funktion, int lagerId, AsyncCallback<Void> callback) {
        personDao.speichern(personId, vorname, nachname, gebtag, strasse, plz, ort, telnr, email, geschlecht, handy,
                nottel);
        // Wenn ein Stab ohne Funktion ausgewählt wird ignorieren.
        if (id == null && funktion != REMOVE) {
            // ansonsten anlegen
            stabDao.speichern(id, lagerId, personId, funktion);
        } else {
            if (funktion == REMOVE) {
                stabDao.loeschen(id);
            } else {
                stabDao.speichern(id, lagerId, personId, funktion);
            }
        }
        callback.get(null);
    }

    @Override
    public void speichereZelt(Integer zeltId, String bezeichnung, Date angeschafft, double preis, Waehrung waehrung,
            AsyncCallback<Void> callback) {
        zeltDao.speichern(zeltId, bezeichnung, angeschafft, preis, waehrung);
        callback.get(null);
    }

    @Override
    public void speichereZeltZuLager(Integer id, int zeltId, Integer lagerId, AsyncCallback<Void> callback) {
        if (lagerId != null) {
            zeltDao.speichernZuLager(zeltId, lagerId);
        } else {
            zeltDao.loeschenZuLager(id);
        }
        callback.get(null);
    }

    @Override
    public void speichereZeltZuGruppe(Integer id, int zeltId, Integer gruppeId, AsyncCallback<Void> callback) {
        if (gruppeId != null) {
            // id ist von stLaZe gefüllt
            zeltDao.speichernZuGruppe(zeltId, gruppeId);
        } else {
            zeltDao.loeschenZuGruppe(id);
        }
        callback.get(null);
    }

    @Override
    public void speichereSchaden(Integer id, int zeltId, Date datum, String schaden, AsyncCallback<Void> callback) {
        schadenDao.speichern(id, zeltId, datum, schaden);
        callback.get(null);
    }

    @Override
    public void speichereZeltverleih(Integer id, int zeltId, Date datum, String person, String bemerkung,
            AsyncCallback<Void> callback) {
        zeltverleihDao.speichern(id, zeltId, datum, person, bemerkung);
        callback.get(null);
    }

    @Override
    public void speichereZeltdetail(Integer id, int zeltId, int anzahl, int bezeichnung, String schluessel,
            AsyncCallback<Void> callback) {
        zeltdetailDao.speichern(id, zeltId, anzahl, bezeichnung, schluessel);
        callback.get(null);
    }

    @Override
    public void speichereMaterialwart(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String nottel, Integer lagerId, AsyncCallback<Void> callback) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlecht, handy,
                nottel);
        if (lagerId != null) {
            materialwartDao.speichern(lagerId, personId);
        } else if (id != null && lagerId == null) {
            materialwartDao.loeschen(id);
        }
        callback.get(null);
    }

    @Override
    public void loescheSchaden(Integer id, AsyncCallback<Void> callback) {
        schadenDao.loeschen(id);
        callback.get(null);
    }

    @Override
    public void loescheZeltverleih(int id, AsyncCallback<Void> callback) {
        zeltverleihDao.loeschen(id);
        callback.get(null);
    }

    @Override
    public void loescheZeltdetail(int id, AsyncCallback<Void> callback) {
        zeltdetailDao.loeschen(id);
        callback.get(null);
    }

    @Override
    public void loescheZeltdetailBezeichnung(int id, AsyncCallback<Void> callback) {
        zeltdetailBezeichnungDao.loeschen(id);
        callback.get(null);
    }

    @Override
    public void speichereGruppe(Integer id, Integer gruppeId, Integer lagerId, String name, String schlachtruf,
            AsyncCallback<Void> callback) {
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
        callback.get(null);
    }

    @Override
    public void speichereLeiter(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, Integer gruppeId, AsyncCallback<Void> callback) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlecht, handy,
                telNr2);
        if (id == null && gruppeId != null) {
            leiterDao.speichere(gruppeId, personId);
        } else if (id != null && gruppeId == null) {
            leiterDao.loesche(id);
        }
        callback.get(null);
    }

    @Override
    public void speichereTeilnehmer(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, Integer gruppeId, AsyncCallback<Void> callback) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlecht, handy,
                telNr2);
        if (id == null && gruppeId != null) {
            teilnehmerDao.speichere(gruppeId, personId);
        } else if (id != null && gruppeId == null) {
            teilnehmerDao.loesche(id);
        }
        callback.get(null);
    }

    @Override
    public void aenderePasswort(String user, char[] neuesPasswort, AsyncCallback<Void> callback) {
        userDao.changePasswort(user, valueOf(neuesPasswort));
        callback.get(null);
    }

    @Override
    public void speichereProgramm(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend,
            AsyncCallback<Void> callback) {
        if (id == null && lagerId != null) {
            programmDao.speichereProgramm(lagerId, datum, morgen, mittag, abend);
        } else {
            programmDao.aendereProgramm(id, morgen, mittag, abend, datum);
        }
        callback.get(null);
    }

    @Override
    public void loescheProgramm(List<Integer> ids, AsyncCallback<Void> callback) {
        for (Integer id : ids) {
            programmDao.loescheProgramm(id);
        }
        callback.get(null);
    }

    @Override
    public void speichereEssen(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend,
            AsyncCallback<Void> callback) {
        if (id == null && lagerId != null) {
            essenDao.speichereEssen(lagerId, datum, morgen, mittag, abend);
        } else {
            essenDao.aendereEssen(id, morgen, mittag, abend, datum);
        }
        callback.get(null);
    }

    @Override
    public void loescheEssen(List<Integer> ids, AsyncCallback<Void> callback) {
        for (Integer id : ids) {
            essenDao.loescheEssen(id);
        }
        callback.get(null);
    }

    @Override
    public void speichereLagerinfo(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, boolean checked, AsyncCallback<Void> callback) {
        personDao.speichern(personId, vorname, name, gebDat, strasse, plz, ort, telNr, email, geschlecht, handy,
                telNr2);
        if (checked) {
            lagerinfoDao.speichereLagerinfo(personId);
        } else if (id != null && !checked) {
            lagerinfoDao.loesche(id);
        }
        callback.get(null);
    }

    @Override
    public void verschiebeGruppe(int gruppenId, int lagerId, AsyncCallback<Void> callback) {
        Gruppe gruppe = gruppeDao.getFromLager(gruppenId);
        gruppeDao.loeschen(gruppenId);
        gruppeDao.speicherenLager(lagerId, gruppe.getOriginalId());
        callback.get(null);
    }

    @Override
    public void verschiebeLeiter(int id, int gruppeId, AsyncCallback<Void> callback) {
        Leiter leiter = leiterDao.get(id);
        leiterDao.loesche(id);
        leiterDao.speichere(gruppeId, leiter.getOriginalId());
        callback.get(null);
    }

    @Override
    public void verschiebeTeilnehmer(int id, int gruppeId, AsyncCallback<Void> callback) {
        Teilnehmer teilnehmer = teilnehmerDao.get(id);
        teilnehmerDao.loesche(id);
        teilnehmerDao.speichere(gruppeId, teilnehmer.getOriginalId());
        callback.get(null);
    }

    @Override
    public void createUser(String username, char[] cs, AsyncCallback<Void> callback) {
        userDao.createUser(username, valueOf(cs));
        callback.get(null);
    }

    @Override
    public void getAllGroups(AsyncCallback<List<String>> callback) {
        callback.get(userDao.getAllGroups());
    }

    @Override
    public void getAllUsers(AsyncCallback<List<User>> callback) {
        List<User> result = new ArrayList<>();
        for (String userId : userDao.getAllUsers()) {
            User user = new User(userId);
            user.getGroups().addAll(userDao.getGroupsForUser(userId));
            result.add(user);
        }
        callback.get(result);
    }

    @Override
    public void grantUser(String user, String groups, AsyncCallback<Void> callback) {
        userDao.grantUser(user, groups);
        callback.get(null);
    }

    @Override
    public void revokeUser(String user, String groups, AsyncCallback<Void> callback) {
        userDao.revokeUser(user, groups);
        callback.get(null);
    }

    @Override
    public void dropUser(String username, AsyncCallback<Void> callback) {
        userDao.dropUser(username);
        callback.get(null);
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
