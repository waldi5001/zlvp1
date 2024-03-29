package de.zlvp.controller;

import de.zlvp.dao.*;
import de.zlvp.entity.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static de.zlvp.entity.Funktion.REMOVE;
import static java.lang.String.valueOf;

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
    public void getPerson(int personId, AsyncCallback<Person> callback) {
        callback.get(personDao.getPerson(personId));
    }

    @Override
    public void getAllAnrede(AsyncCallback<List<Anrede>> callback) {
        callback.get(anredeDao.getAll());
    }

    @Override
    public void getAllLagerFromPerson(int personId, AsyncCallback<List<LagerDao.LagerDTO>> callback) {
        callback.get(lagerDao.getAllOfPerson(personId));
    }

    @Override
    public void getLagerFromGruppe(int gruppeId, AsyncCallback<Lager> callback) {
        callback.get(lagerDao.getFromGruppe(gruppeId));
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
                g.getLeiter().addAll(leiterDao.getAll(g.getId()));
                g.getTeilnehmer().addAll(teilnehmerDao.getAll(g.getId()));
                g.setLager(lager);
                g.setChecked(true);
            }
        }
        callback.get(jahr);
    }

    @Override
    public void getAllStab(int lagerId, AsyncCallback<List<Stab>> callback) {
        List<Stab> all = stabDao.getAll(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Stab stab : all) {
            stab.setLager(lager);
        }
        callback.get(all);
    }

    @Override
    public void getAllMaterialwart(int lagerId, AsyncCallback<List<Materialwart>> callback) {
        List<Materialwart> all = materialwartDao.getAll(lagerId);
        Lager lager = lagerDao.get(lagerId);
        for (Materialwart mw : all) {
            mw.setLager(lager);
            mw.setChecked(true);
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
    public void getAllLeiter(int gruppeId, AsyncCallback<List<Leiter>> callback) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Leiter> all = leiterDao.getAll(gruppeId);
        for (Leiter leiter : all) {
            leiter.setGruppe(gruppe);
            // Ob das hier so gut ist?
            leiter.setChecked(true);
        }
        callback.get(all);
    }

    @Override
    public void getAllTeilnehmer(int gruppeId, AsyncCallback<List<Teilnehmer>> callback) {
        Gruppe gruppe = gruppeDao.get(gruppeId);
        List<Teilnehmer> all = teilnehmerDao.getAll(gruppeId);
        for (Teilnehmer teilnehmer : all) {
            teilnehmer.setGruppe(gruppe);
            // Ob das hier so gut ist?
            teilnehmer.setChecked(true);
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
        for (Zelt zelt : allFromLager) {
            zelt.setLager(lagerDao.get(lagerId));
            zelt.setChecked(true);
        }
        callback.get(allFromLager);
    }

    @Override
    public void getAllZeltFromGruppe(int gruppeId, AsyncCallback<List<Zelt>> callback) {
        List<Zelt> allFromGruppe = zeltDao.getAllFromGruppe(gruppeId);
        for (Zelt zelt : allFromGruppe) {
            zelt.setGruppe(gruppeDao.get(gruppeId));
            zelt.setChecked(true);
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
        Lagerort lagerort = lagerortDao.get(lagerortId);
        for (Legenda l : legenda) {
            l.setLegendaTyp(legendatypDao.getFromLegenda(l.getId()));
            l.setAnrede(anredeDao.getAnredeFromLegenda(l.getId()));
            l.setLagerOrt(lagerort);
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
        List<Schaden> allFromZelt = schadenDao.getAllFromZelt(id);
        Zelt zelt = zeltDao.get(id);
        for (Schaden schaden : allFromZelt) {
            schaden.setZelt(zelt);
        }
        callback.get(allFromZelt);
    }

    @Override
    public void getAllZeltverleih(Integer id, AsyncCallback<List<Zeltverleih>> callback) {
        List<Zeltverleih> allFromZelt = zeltverleihDao.getAllFromZelt(id);
        Zelt zelt = zeltDao.get(id);
        for (Zeltverleih zeltverleih : allFromZelt) {
            zeltverleih.setZelt(zelt);
        }
        callback.get(allFromZelt);
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
    public void aendereLager(Integer id, String name, String thema, Date start, Date stop,
            AsyncCallback<Void> callback) {
        lagerDao.aendern(id, name, thema, start, stop);
        callback.get(null);
    }

    @Override
    public void speichereLagerort(String lagerort, AsyncCallback<Void> callback) {
        lagerortDao.speichern(lagerort);
        callback.get(null);
    }

    @Override
    public void speichereLegendatyp(String legendatyp, AsyncCallback<Void> callback) {
        legendatypDao.speichern(legendatyp);
        callback.get(null);
    }

    @Override
    public void speichereAnrede(String anrede, AsyncCallback<Void> callback) {
        anredeDao.speichereAnrede(anrede);
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
    public void speichereStab(Integer id, Funktion funktion, int lagerId, AsyncCallback<Lager> callback) {
        if (funktion == REMOVE) {
            stabDao.loeschen(lagerId, id);
        } else {
            stabDao.speichern(lagerId, id, funktion);
        }
        callback.get(lagerDao.get(lagerId));
    }

    @Override
    public void speichereZelt(Integer zeltId, String bezeichnung, Date angeschafft, double preis, Waehrung waehrung,
            AsyncCallback<Void> callback) {
        zeltDao.speichern(zeltId, bezeichnung, angeschafft, preis, waehrung);
        callback.get(null);
    }

    @Override
    public void speichereZeltZuLager(boolean add, Integer id, Integer lagerId, AsyncCallback<Lager> callback) {
        if (add) {
            zeltDao.speichernZuLager(id, lagerId);
            callback.get(lagerDao.get(lagerId));
        } else {
            zeltDao.loeschenZuLager(id, lagerId);
            callback.get(null);
        }
    }

    @Override
    public void speichereZeltZuGruppe(boolean add, Integer id, Integer gruppeId, AsyncCallback<Gruppe> callback) {
        if (add) {
            zeltDao.speichernZuGruppe(id, gruppeId);
            callback.get(gruppeDao.get(gruppeId));
        } else {
            zeltDao.loeschenZuGruppe(id, gruppeId);
            callback.get(null);
        }
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
    public void speichereMaterialwart(boolean add, Integer id, Integer lagerId, AsyncCallback<Lager> callback) {
        if (add) {
            materialwartDao.speichern(lagerId, id);
            callback.get(lagerDao.get(lagerId));
        } else {
            materialwartDao.loeschen(lagerId, id);
            callback.get(null);
        }
    }

    @Override
    public void loescheSchaeden(List<Integer> ids, AsyncCallback<Void> callback) {
        for (Integer id : ids) {
            schadenDao.loeschen(id);
        }
        callback.get(null);
    }

    @Override
    public void loescheZeltverleihe(List<Integer> ids, AsyncCallback<Void> callback) {
        for (Integer id : ids) {
            zeltverleihDao.loeschen(id);
        }
        callback.get(null);
    }

    @Override
    public void loescheZeltdetails(List<Integer> ids, AsyncCallback<Void> callback) {
        for (Integer id : ids) {
            zeltdetailDao.loeschen(id);
        }
        callback.get(null);
    }

    @Override
    public void loescheZeltdetailBezeichnung(int id, AsyncCallback<Void> callback) {
        zeltdetailBezeichnungDao.loeschen(id);
        callback.get(null);
    }

    @Override
    public void speichereGruppe(boolean add, Integer id, int lagerId, String name, String schlachtruf,
            AsyncCallback<Gruppe> callback) {
        Gruppe gruppe = gruppeDao.speichereGruppe(id, name, schlachtruf);
        if (add) {
            gruppeDao.speicherenZuLager(lagerId, gruppe.getId());
        } else {
            gruppeDao.loeschenVonLager(lagerId, id);
        }
        callback.get(gruppe);
    }

    @Override
    public void aendereGruppe(int id, String name, String schlachtruf, AsyncCallback<Void> callback) {
        gruppeDao.speichereGruppe(id, name, schlachtruf);
        callback.get(null);
    }

    @Override
    public void speichereLeiter(boolean add, int personId, int gruppeId, AsyncCallback<Gruppe> callback) {
        if (add) {
            leiterDao.speichere(gruppeId, personId);
            callback.get(gruppeDao.get(gruppeId));
        } else {
            leiterDao.loeschen(gruppeId, personId);
            callback.get(null);
        }
    }

    @Override
    public void speichereTeilnehmer(boolean add, int personId, int gruppeId, AsyncCallback<Gruppe> callback) {
        if (add) {
            teilnehmerDao.speichere(personId, gruppeId);
            callback.get(gruppeDao.get(gruppeId));
        } else {
            teilnehmerDao.loeschen(personId, gruppeId);
            callback.get(null);
        }
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
    public void speichereLagerinfo(boolean add, Integer id, AsyncCallback<Void> callback) {
        if (add) {
            lagerinfoDao.speichereLagerinfo(id);
        } else {
            lagerinfoDao.loeschen(id);
        }
        callback.get(null);
    }

    @Override
    public void verschiebeGruppe(int id, int srcLager, int destLager, AsyncCallback<Void> callback) {
        gruppeDao.loeschenVonLager(srcLager, id);
        gruppeDao.speicherenZuLager(destLager, id);
        callback.get(null);
    }

    @Override
    public void verschiebeLeiter(int personId, int srcGruppeId, int destGruppeId, AsyncCallback<Void> callback) {
        leiterDao.loeschen(srcGruppeId, personId);
        leiterDao.speichere(destGruppeId, personId);
        callback.get(null);
    }

    @Override
    public void verschiebeTeilnehmer(int personId, int srcGruppeId, int destGruppeId, AsyncCallback<Void> callback) {
        teilnehmerDao.loeschen(personId, srcGruppeId);
        teilnehmerDao.speichere(personId, destGruppeId);
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
