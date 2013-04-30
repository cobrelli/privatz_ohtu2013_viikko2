package ohtu.intjoukkosovellus;

public class IntJoukko {

    public int kapasiteetti = 5;
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        ljono = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = 5;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        ljono = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = 5;
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            System.out.println("Virhe annetuissa arvoissa");
            return;
        }
        ljono = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (alkioidenLkm == 0) {
            return alustaTyhja(luku);
        }
        if (!sisaltaa(luku)) {
            return lisaaUusiLuku(luku);
        }
        return false;
    }

    public boolean sisaltaa(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int indeksi = etsiIndeksi(luku);
        int apu;

        if (indeksi != -1) {
            poistaAlkio(indeksi);
            return true;
        }
        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);
    }

    public int taulukonKoko() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + ljono[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i] + ", ";
            }
            tuotos += ljono[alkioidenLkm - 1] + "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(ljono, 0, taulu, 0, taulu.length);
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        for (int i : b.toIntArray()) {
            x.lisaa(i);
        }
        for (int i : a.toIntArray()) {
            x.lisaa(i);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        for (int i : a.toIntArray()) {
            for (int j : b.toIntArray()) {
                if (i == j) {
                    y.lisaa(j);
                }
            }
        }
        return y;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        for (int i : a.toIntArray()) {
            if(!b.sisaltaa(i)){
                z.lisaa(i);
            }
        }
        return z;
    }

    public boolean alustaTyhja(int luku) {
        ljono[0] = luku;
        alkioidenLkm++;
        return true;
    }

    public boolean lisaaUusiLuku(int luku) {
        ljono[alkioidenLkm] = luku;
        alkioidenLkm++;
        if (alkioidenLkm % ljono.length == 0) {
            int[] taulukkoOld = ljono;
            kopioiTaulukko(ljono, taulukkoOld);
            ljono = new int[alkioidenLkm + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, ljono);
        }
        return true;
    }

    private int etsiIndeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                int indeksi = i;
                ljono[indeksi] = 0;
                return indeksi;
            }
        }
        return -1;
    }

    public void poistaAlkio(int indeksi) {
        int apu;
        for (int j = indeksi; j < alkioidenLkm - 1; j++) {
            apu = ljono[j];
            ljono[j] = ljono[j + 1];
            ljono[j + 1] = apu;
        }
        alkioidenLkm--;
    }
}
