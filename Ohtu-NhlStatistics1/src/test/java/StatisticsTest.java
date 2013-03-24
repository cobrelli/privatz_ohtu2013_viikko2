/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import ohtuesimerkki.Player;
import ohtuesimerkki.Reader;
import ohtuesimerkki.Statistics;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vito
 */
public class StatisticsTest {

    Statistics stats;
    Reader readerStub = new Reader() {
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<Player>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri", "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }

        @Override
        public int extractInt(String str) {
            return Integer.parseInt(str.trim());
        }
    };
    
    public StatisticsTest() {
    }

    @Before
    public void setUp() {
        stats = new Statistics(readerStub);
    }
    
    @Test
    public void testaaEttaSearchPalauttaaNullJosPelaajaaEiLoydy(){
        assertEquals(null, stats.search("Semenkoasd"));
    }
    
    @Test
    public void testaaEttaLoydetaanPelaaja(){
        assertEquals("Semenko", stats.search("Semenko").getName());
    }
    
    @Test
    public void testaaEttaPelaajaLoytyyOikein(){
        Player semenko = readerStub.getPlayers().get(0);
        assertEquals(semenko.toString(), stats.search("Semenko").toString());
    }
    
    @Test
    public void testaaEttaTiiminPelaajatLoydetaan(){
        assertEquals(1, stats.team("PIT").size());
    }
    
    @Test
    public void testaaEttaTiiminPelaajatLoydetaan2(){
        assertEquals(3, stats.team("EDM").size());
    }
    
    @Test
    public void testaaEttaVaaraNimiAntaaPelaajienMaaranOikein(){
        assertEquals(0, stats.team("ASDSA").size());
    }
    
    @Test
    public void testaaEttaTiimiLoytyyJaPelaajanNimiOikein(){
        assertEquals("Lemieux", stats.team("PIT").get(0).getName());
    }
    
    @Test
    public void testaaEttaVoidaanLisataTeamiin(){
        assertEquals(true, stats.team("PIT").add(new Player("a", "b", 0, 0)));
    }
    
    @Test
    public void testaaEttaHuippuPelaajaPalautetaan(){
        assertEquals("Gretzky", stats.topScorers(1).get(0).getName());
    }
    
    @Test
    public void testaaEttaHuippuPelaajiaPalautetaanOikeaMaara(){
        assertEquals(3, stats.topScorers(3).size());
    }
    
    @Test
    public void testaaEttaHuippuPelaajiaPalautetaanOikein(){
        Player pelaaja = readerStub.getPlayers().get(4);
        assertEquals(pelaaja.getName(), stats.topScorers(1).get(0).getName());
    }
    
    public void testaaEttaHuippuPelaajiaPalautetaanOikeinLisatylla(){
        Player pelaaja = new Player("a", "b", 100, 100);
        readerStub.getPlayers().add(pelaaja);
        assertEquals(pelaaja.getName(), stats.topScorers(4).get(0).getName());
    }
    
    @Test
    public void testaaEttaVoidaanLisataTopScorersiin(){
        assertEquals(true, stats.topScorers(1).add(new Player("a", "b", 0, 0)));
    }
    
    @Test
    public void testaaEtteiNegatiivisiaListata(){
        assertEquals(0, stats.topScorers(-1).size());
    }
}
