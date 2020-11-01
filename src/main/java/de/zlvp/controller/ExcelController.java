package de.zlvp.controller;

public interface ExcelController {

    byte[] getVorlage(Integer jahrId);

    void importieren(byte[] sheet);

}