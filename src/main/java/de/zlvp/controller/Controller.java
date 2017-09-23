package de.zlvp.controller;

import java.util.Date;
import java.util.List;

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

public interface Controller {

    void findPerson(String vorname, String nachname, AsyncCallback<List<Person>> callback);

    void getAllAnrede(AsyncCallback<List<Anrede>> callback);

    void getAllEssen(int lagerId, AsyncCallback<List<Essen>> callback);

    void getAllGruppen(AsyncCallback<List<Gruppe>> callback);

    void getAllJahr(AsyncCallback<List<Jahr>> callback);

    void getAllLager(int jahrId, AsyncCallback<List<Lager>> callback);

    void getAllLagerinfo(AsyncCallback<List<Lagerinfo>> callback);

    void getAllLagerort(AsyncCallback<List<Lagerort>> callback);

    void getAllLegendaFromLagerort(int lagerortId, AsyncCallback<List<Legenda>> callback);

    void getAllLegendatyp(AsyncCallback<List<Legendatyp>> callback);

    void getAllLeiter(int gruppeId, AsyncCallback<List<Leiter>> callback);

    void getAllMaterialwart(int lagerId, AsyncCallback<List<Materialwart>> callback);

    void getAllPersons(AsyncCallback<List<Person>> callback);

    void getAllProgramm(int lagerId, AsyncCallback<List<Programm>> callback);

    void getAllSchaeden(Integer id, AsyncCallback<List<Schaden>> callback);

    void getAllStab(int lagerId, AsyncCallback<List<Stab>> callback);

    void getAllTeilnehmer(int gruppeId, AsyncCallback<List<Teilnehmer>> callback);

    void getAllUnassignedGruppen(AsyncCallback<List<Gruppe>> callback);

    void getAllZelt(AsyncCallback<List<Zelt>> callback);

    void getAllZeltdetail(int zeltId, AsyncCallback<List<Zeltdetail>> callback);

    void getAllZeltdetailBezeichnung(AsyncCallback<List<ZeltdetailBezeichnung>> callback);

    void getAllZeltFromGruppe(int gruppeId, AsyncCallback<List<Zelt>> callback);

    void getAllZeltFromLager(int lagerId, AsyncCallback<List<Zelt>> callback);

    void getAllZeltverleih(Integer id, AsyncCallback<List<Zeltverleih>> callback);

    void getJahr(int jahrId, AsyncCallback<Jahr> callback);

    void loescheSchaden(Integer id, AsyncCallback<Void> callback);

    void loescheZeltdetail(int id, AsyncCallback<Void> callback);

    void loescheZeltdetailBezeichnung(int id, AsyncCallback<Void> callback);

    void loescheZeltverleih(int id, AsyncCallback<Void> callback);

    void speichereGruppe(boolean add, Integer id, int lagerId, String name, String schlachtruf,
            AsyncCallback<Gruppe> callback);

    void aendereGruppe(int id, String name, String schlachtruf, AsyncCallback<Void> callback);

    void speichereLager(Integer id, String name, String thema, Date start, Date stop, int jahrId, int lagerortId,
            AsyncCallback<Void> callback);

    void aendereLager(Integer id, String name, String thema, Date start, Date stop, AsyncCallback<Void> callback);

    void speichereLagerort(String lagerort, AsyncCallback<Void> callback);

    void speichereLegenda(Integer id, int lagerortId, String nachname, String vorname, String firma, String strasse,
            String plz, String ort, Integer legendatypId, Integer anredeId, String tel, String fax, String handy,
            String email, String bemerkung, AsyncCallback<Void> callback);

    void speichereLeiter(boolean add, int personId, int gruppeId, AsyncCallback<Gruppe> callback);

    void speichereMaterialwart(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String nottel, Integer lagerId, AsyncCallback<Void> callback);

    void speicherePerson(Integer id, Geschlecht geschlecht, String vorname, String nachname, String strasse, String plz,
            String ort, Date gebtag, String telnr, String email, String handy, String nottel,
            AsyncCallback<Void> callback);

    void speichereSchaden(Integer id, int zeltId, Date datum, String schaden, AsyncCallback<Void> callback);

    void speichereStab(Integer id, int personId, Geschlecht geschlecht, String vorname, String nachname, String strasse,
            String plz, String ort, Date gebtag, String telnr, String email, String handy, String nottel,
            Funktion funktion, int lagerId, AsyncCallback<Void> callback);

    void speichereTeilnehmer(boolean add, int personId, int gruppeId, AsyncCallback<Gruppe> callback);

    void speichereZelt(Integer zeltId, String bezeichnung, Date angeschafft, double preis, Waehrung waehrung,
            AsyncCallback<Void> callback);

    void speichereZeltdetail(Integer id, int zeltId, int anzahl, int bezeichnung, String schluessel,
            AsyncCallback<Void> callback);

    void speichereZeltDetailBezeichnung(String detailBezeichnung, AsyncCallback<Void> callback);

    void speichereZeltverleih(Integer id, int zeltId, Date datum, String person, String bemerkung,
            AsyncCallback<Void> callback);

    void speichereZeltZuGruppe(Integer id, int zeltId, Integer gruppeId, AsyncCallback<Void> callback);

    void speichereZeltZuLager(Integer id, int zeltId, Integer lagerId, AsyncCallback<Void> callback);

    void aenderePasswort(String user, char[] neuesPasswort, AsyncCallback<Void> callback);

    void speichereProgramm(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend,
            AsyncCallback<Void> callback);

    void loescheProgramm(List<Integer> ids, AsyncCallback<Void> callback);

    void speichereEssen(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend,
            AsyncCallback<Void> callback);

    void loescheEssen(List<Integer> ids, AsyncCallback<Void> callback);

    void speichereLagerinfo(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, boolean delete, AsyncCallback<Void> callback);

    void verschiebeGruppe(int id, int srcLager, int destLager, AsyncCallback<Void> callback);

    void verschiebeLeiter(int personId, int srcGruppeId, int destGruppeId, AsyncCallback<Void> callback);

    void verschiebeTeilnehmer(int personId, int srcGruppeId, int destGruppeId, AsyncCallback<Void> callback);

    void getAllUsers(AsyncCallback<List<User>> callback);

    void getAllGroups(AsyncCallback<List<String>> callback);

    void createUser(String username, char[] cs, AsyncCallback<Void> callback);

    void grantUser(String user, String group, AsyncCallback<Void> callback);

    void revokeUser(String user, String group, AsyncCallback<Void> callback);

    void dropUser(String username, AsyncCallback<Void> callback);

}