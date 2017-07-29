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
import de.zlvp.entity.Zelt;
import de.zlvp.entity.Zeltdetail;
import de.zlvp.entity.ZeltdetailBezeichnung;
import de.zlvp.entity.Zeltverleih;

public interface Controller {

    List<Person> findPerson(String vorname, String nachname);

    List<Anrede> getAllAnrede();

    List<Essen> getAllEssen(int lagerId);

    List<Funktion> getAllFunktion();

    List<Gruppe> getAllGruppen();

    List<Gruppe> getAllGruppenFromLager(int lagerId);

    List<Jahr> getAllJahr();

    List<Lager> getAllLager(int jahrId);

    List<Lagerinfo> getAllLagerinfo();

    List<Lagerort> getAllLagerort();

    List<Legenda> getAllLegendaFromLagerort(int lagerortId);

    List<Legendatyp> getAllLegendatyp();

    List<Leiter> getAllLeiter(int gruppeId);

    List<Materialwart> getAllMaterialwart(int lagerId);

    List<Person> getAllPerson();

    List<Programm> getAllProgramm(int lagerId);

    List<Schaden> getAllSchaeden(Integer id);

    List<Stab> getAllStab(int lagerId);

    List<Teilnehmer> getAllTeilnehmer(int gruppeId);

    List<Gruppe> getAllUnassignedGruppen();

    List<Zelt> getAllZelt();

    List<Zeltdetail> getAllZeltdetail(int zeltId);

    List<ZeltdetailBezeichnung> getAllZeltdetailBezeichnung();

    List<Zelt> getAllZeltFromGruppe(int gruppeId);

    List<Zelt> getAllZeltFromLager(int lagerId);

    List<Zeltverleih> getAllZeltverleih(Integer id);

    Jahr getJahr(int jahrId);

    void loescheSchaden(Integer id);

    void loescheZeltdetail(int id);

    void loescheZeltdetailBezeichnung(int id);

    void loescheZeltverleih(int id);

    void speichereGruppe(Integer id, Integer gruppeId, Integer lagerId, String name, String schlachtruf);

    void speichereLager(Integer id, String name, String thema, Date start, Date stop, int jahrId, int lagerortId);

    void speichereLagerort(String lagerort);

    void speichereLegenda(Integer id, int lagerortId, String nachname, String vorname, String firma, String strasse,
            String plz, String ort, Integer legendatypId, Integer anredeId, String tel, String fax, String handy,
            String email, String bemerkung);

    void speichereLeiter(Integer id, int personId, Geschlecht geschlecht, String vorname, String name, String strasse,
            String plz, String ort, Date gebDat, String telNr, String email, String handy, String telNr2,
            Integer gruppeId);

    void speichereMaterialwart(Integer id, int personId, Geschlecht geschlecht, String vorname, String name, String strasse,
            String plz, String ort, Date gebDat, String telNr, String email, String handy, String nottel,
            Integer lagerId);

    void speicherePerson(Integer id, Geschlecht geschlecht, String vorname, String nachname, String strasse, String plz,
            String ort, Date gebtag, String telnr, String email, String handy, String nottel);

    void speichereSchaden(Integer id, int zeltId, Date datum, String schaden);

    void speichereStab(Integer id, int personId, Geschlecht geschlecht, String vorname, String nachname, String strasse,
            String plz, String ort, Date gebtag, String telnr, String email, String handy, String nottel,
            Integer funktionId, int lagerId);

    void speichereTeilnehmer(Integer id, int personId, Geschlecht geschlecht, String vorname, String name,
            String strasse, String plz, String ort, Date gebDat, String telNr, String email, String handy,
            String telNr2, Integer gruppeId);

    void speichereZelt(Integer zeltId, String bezeichnung, Date angeschafft, double preis);

    void speichereZeltdetail(Integer id, int zeltId, int anzahl, int bezeichnung, String schluessel);

    void speichereZeltDetailBezeichnung(String detailBezeichnung);

    void speichereZeltverleih(Integer id, int zeltId, Date datum, String person, String bemerkung);

    void speichereZeltZuGruppe(Integer id, int zeltId, Integer gruppeId);

    void speichereZeltZuLager(Integer id, int zeltId, Integer lagerId);

    void aenderePasswort(String user, char[] neuesPasswort);

    void speichereProgramm(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend);

    void speichereEssen(Integer lagerId, Integer id, Date datum, String morgen, String mittag, String abend);

    void speichereLagerinfo(Integer id, int personId, Geschlecht geschlecht, String vorname, String name, String strasse,
            String plz, String ort, Date gebDat, String telNr, String email, String handy, String telNr2,
            boolean delete);

    void verschiebeGruppe(int gruppenId, int lagerId);

    void verschiebeLeiter(int id, int gruppeId);

    void verschiebeTeilnehmer(int id, int gruppeId);

    List<User> getAllUsers();

    List<String> getAllGroups();

    void createUser(String username, char[] cs);

    void grantUser(String user, String group);

    void revokeUser(String user, String group);

    void dropUser(String username);

}