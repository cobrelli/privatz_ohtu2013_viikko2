package com.mycompany.ohmawebkauppa.sovelluslogiikka.ohjaus;

import com.mycompany.webkauppa.ohjaus.CommandInterpreter;
import com.mycompany.webkauppa.ohjaus.OstoksenPoistoKorista;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.mycompany.webkauppa.sovelluslogiikka.*;

public class OstoksenPoistoKoristaTest {

    Varasto varasto = Varasto.getInstance();
    Ostoskori kori;
    long tuoteid = 1;
    OstoksenPoistoKorista ostoksenPoisto;
    CommandInterpreter command;

    @Before
    public void setUp() {
        Tuote tuote = varasto.etsiTuote(tuoteid);
        kori = new Ostoskori();
        kori.lisaaTuote(tuote);
        command = new CommandInterpreter();
    }

    @Test
    public void poistettuTuoteEiEnaaKorissa() {
//        ostoksenPoisto = new OstoksenPoistoKorista(kori, tuoteid);
//        ostoksenPoisto.suorita();
        command.ostoksenPoistoKorista(kori, tuoteid).suorita();

        assertEquals(0, kori.tuotteitaKorissa());
        assertEquals(0, kori.hinta());
        assertEquals(0, kori.ostokset().size());
    }

    @Test
    public void tuotteenMaaraKasvaa() {
        int varastossaAluksi = varasto.etsiTuote(tuoteid).getSaldo();

//        ostoksenPoisto = new OstoksenPoistoKorista(kori, tuoteid);
//        ostoksenPoisto.suorita();
        command.ostoksenPoistoKorista(kori, tuoteid).suorita();

        assertEquals(varastossaAluksi + 1, varasto.etsiTuote(tuoteid).getSaldo());
    }
}
