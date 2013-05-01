package com.mycompany.webkauppa.ohjaus;

import com.mycompany.webkauppa.sovelluslogiikka.Ostoskori;
import com.mycompany.webkauppa.sovelluslogiikka.Tuote;
import com.mycompany.webkauppa.sovelluslogiikka.Varasto;

public class OstoksenLisaysKoriin implements Command{

    private Ostoskori ostoskori;
    private long tuoteId;
    private Varasto varasto;

    OstoksenLisaysKoriin(Ostoskori ostoskori, long tuoteId) {
        this.ostoskori = ostoskori;
        this.tuoteId = tuoteId;
        this.varasto = Varasto.getInstance();
    }

    public void suorita() {
        boolean saatiinTuote = varasto.otaVarastosta(tuoteId);
        
        if (!saatiinTuote) {
            return;
        }
       
        Tuote tuote = varasto.etsiTuote(tuoteId);                      
        ostoskori.lisaaTuote(tuote);                

    }
}
