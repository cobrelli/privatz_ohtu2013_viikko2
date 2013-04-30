package ohtu.intjoukkosovellus;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntJoukkoTest {

    IntJoukko joukko;

    @Before
    public void setUp() {
        joukko = new IntJoukko();
        joukko.lisaa(10);
        joukko.lisaa(3);
    }

    @Test
    public void lukujaLisattyMaara() {
        joukko.lisaa(4);
        assertEquals(3, joukko.taulukonKoko());
    }

    @Test
    public void samaLukuMeneeJoukkoonVaanKerran() {
        joukko.lisaa(10);
        joukko.lisaa(3);
        assertEquals(2, joukko.taulukonKoko());
    }

    @Test
    public void vainLisatytLuvutLoytyvat() {
        assertTrue(joukko.sisaltaa(10));
        assertFalse(joukko.sisaltaa(5));
        assertTrue(joukko.sisaltaa(3));
    }

    @Test
    public void poistettuEiOleEnaaJoukossa() {
        joukko.poista(3);
        assertFalse(joukko.sisaltaa(3));
        assertEquals(1, joukko.taulukonKoko());
    }

    @Test
    public void palautetaanOikeaTaulukko() {
        int[] odotettu = {3, 55, 99};

        joukko.lisaa(55);
        joukko.poista(10);
        joukko.lisaa(99);

        int[] vastaus = joukko.toIntArray();
        Arrays.sort(vastaus);
        assertArrayEquals(odotettu, vastaus);
    }

    @Test
    public void toimiiKasvatuksenJalkeen() {
        int[] lisattavat = {1, 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14};
        for (int luku : lisattavat) {
            joukko.lisaa(luku);
        }
        assertEquals(14, joukko.taulukonKoko());
        assertTrue(joukko.sisaltaa(11));
        joukko.poista(11);
        assertFalse(joukko.sisaltaa(11));
        assertEquals(13, joukko.taulukonKoko());
    }

    @Test
    public void toStringToimii() {
        assertEquals("{10, 3}", joukko.toString());
    }
}
