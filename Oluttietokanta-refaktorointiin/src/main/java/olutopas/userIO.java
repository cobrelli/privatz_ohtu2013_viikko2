package olutopas;

import java.util.Scanner;

public class userIO implements IO {

    private Scanner scanner;

    public userIO() {
        scanner = new Scanner(System.in);
    }

    public int readInt() {
        int palautettava = 0;
        try {
            palautettava = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            printLine("Anna komento numerona");
        }

        if (palautettava == 0) {
            return readInt();
        }

        return palautettava;
    }

    public void printLine(String text) {
        System.out.println(text);
    }

    public void printLineChange() {
        System.out.println();
    }

    public String readString() {
        String luettava = scanner.nextLine();
//        if (luettava.isEmpty()) {
//            System.out.println("Syöte ei saa olla tyhjä: ");
//            return readString();
//        }
        return luettava;
    }
}
