package de.zlvp.reports;

public interface Reports {

    public void lageruebersicht(int lagerId);

    public void leiterLagerAusfuehrlich(int lagerId);

    public void leiterJahrAusfuehrlich(int jahrId);

    public void teilnehmerStatistik(int personId);

    public void teilnehmerAusfuehrlichASC(int lagerId);

    public void teilnehmerAusfuehrlichGeschlecht(int lagerId);

    public void teilnehmerASC(int lagerId);

    public void teilnehmerGeschlecht(int lagerId);

    public void jahresuebersicht(int jahrId);

    public void jahresstatistik(int jahrId);

    public void alleZelte();

    public void zelteVonLager(int lagerId);

    public void zeltDetails(int zeltId);

    public void zeltHistorie(int zeltId);

    public void zeltSchaeden(int zeltId);

    public void teilnehmerVonGruppe(int lagerId);

    public void nachtwachenliste(int lagerId);

    public void nachtwachenlisteGruppe(int lagerId);

    public void legenda(int lagerId);

    public void teilnehmerHandyEmail(int lagerId);

    public void teilnehmerAdresseHandyEmail(int lagerId);

    public void etiketten(int lagerId);

    public void etikettenLagerinfo();

    public void programm(int lagerId);

    public void essen(int lagerId);

    public void gesamtstatistik();

    public void exportOutlook(int lagerId);

    public void exportLegendaCSVJahr(int jahrId);

    public void exportEmailCSVJahr(int jahrId);

    public void exportEmailCSVLager(int lagerId);
}