package statistics;

import statistics.matcher.*;

public class Main {

    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstatistics.herokuapp.com/players.txt"));
        System.out.println("enemmän");
        Matcher m = new And(new HasAtLeast(10, "goals"),
                new HasAtLeast(10, "assists"),
                new PlaysIn("PHI"));

        for (Player player : stats.matches(m)) {
            System.out.println(player);
        }
        System.out.println("vähemmän");
        Matcher m1 = new And(new HasFewerThan(10, "goals"),
                new HasFewerThan(10, "assists"),
                new PlaysIn("PHI"));

        for (Player player : stats.matches(m1)) {
            System.out.println(player);
        }
        System.out.println("tai");
        Matcher m2 = new Or(new HasAtLeast(20, "goals"),
                new HasAtLeast(25, "assists"),
                new PlaysIn("PHI"));

        for (Player player : stats.matches(m2)) {
            System.out.println(player);
        }
        
        for (Player player : stats.matches(m1)) {
            System.out.println(player);
        }
        System.out.println("ei");
        Matcher m3 = new Not(new HasAtLeast(1, "goals"),
                new HasAtLeast(1, "assists"),
                new PlaysIn("PHI"));

        for (Player player : stats.matches(m3)) {
            System.out.println(player);
        }
    }
}
