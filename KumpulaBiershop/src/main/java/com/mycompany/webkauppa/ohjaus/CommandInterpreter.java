package com.mycompany.webkauppa.ohjaus;

import com.mycompany.webkauppa.sovelluslogiikka.Ostoskori;
import java.util.HashMap;
import java.util.Map;

public class CommandInterpreter {

    public CommandInterpreter() {
    }

    public Command ostoksenLisaysKoriin(Ostoskori haeSessionOstoskori, long tuoteId) {
        return new OstoksenLisaysKoriin(haeSessionOstoskori, tuoteId);
    }

    public Command ostoksenPoistoKorista(Ostoskori haeSessionOstoskori, long tuoteId) {
        return new OstoksenPoistoKorista(haeSessionOstoskori, tuoteId);
    }

    public OstoksenSuoritus ostoksenSuoritus(String nimi, String osoite, String luottokorttinumero, Ostoskori ostoskori) {
        return new OstoksenSuoritus(nimi, osoite, luottokorttinumero, ostoskori);
    }
}
