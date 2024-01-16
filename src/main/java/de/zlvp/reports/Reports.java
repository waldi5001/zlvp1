package de.zlvp.reports;

import de.zlvp.controller.AsyncCallback;
import net.sf.jasperreports.engine.JasperPrint;

public interface Reports {

    public void lageruebersicht(int lagerId, AsyncCallback<JasperPrint> cb);

    public void leiterLagerAusfuehrlich(int lagerId, AsyncCallback<JasperPrint> cb);

    public void leiterJahrAusfuehrlich(int jahrId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerStatistik(int personId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerAusfuehrlichASC(int lagerId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerAusfuehrlichGeschlecht(int lagerId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerASC(int lagerId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerGeschlecht(int lagerId, AsyncCallback<JasperPrint> cb);

    public void jahresuebersicht(int jahrId, AsyncCallback<JasperPrint> cb);

    public void jahresstatistik(int jahrId, AsyncCallback<JasperPrint> cb);

    public void alleZelte(AsyncCallback<JasperPrint> cb);

    public void zelteVonLager(int lagerId, AsyncCallback<JasperPrint> cb);

    public void zeltDetails(int zeltId, AsyncCallback<JasperPrint> cb);

    public void zeltHistorie(int zeltId, AsyncCallback<JasperPrint> cb);

    public void zeltSchaeden(int zeltId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerVonGruppe(int lagerId, AsyncCallback<JasperPrint> cb);

    public void nachtwachenliste(int lagerId, AsyncCallback<JasperPrint> cb);

    public void nachtwachenlisteGruppe(int lagerId, AsyncCallback<JasperPrint> cb);

    public void legenda(int lagerId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerHandyEmail(int lagerId, AsyncCallback<JasperPrint> cb);

    public void teilnehmerAdresseHandyEmail(int lagerId, AsyncCallback<JasperPrint> cb);

    public void etiketten(int lagerId, AsyncCallback<JasperPrint> cb);

    public void etikettenLagerinfo(AsyncCallback<JasperPrint> cb);

    public void programm(int lagerId, AsyncCallback<JasperPrint> cb);

    public void essen(int lagerId, AsyncCallback<JasperPrint> cb);

    public void gesamtstatistik(AsyncCallback<JasperPrint> cb);

    public void personenVonLagerCSV(int lagerId, AsyncCallback<JasperPrint> cb);

    public void exportLegendaCSVJahr(int jahrId, AsyncCallback<JasperPrint> cb);

}