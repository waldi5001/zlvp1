package de.zlvp.controller;

public interface ExcelController {

    byte[] getVorlage();

    void importieren(byte[] sheet);

}