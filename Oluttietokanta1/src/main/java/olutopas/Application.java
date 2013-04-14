package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import java.util.Scanner;
import javax.persistence.OptimisticLockException;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Pub;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public Application(EbeanServer server) {
        this.server = server;
    }

    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }

        System.out.println("Login (give ? to register new user)");
        System.out.print("username: ");
        String nimi = scanner.nextLine();

        if (nimi.equals("?")) {
            addUser();
            System.out.println("Login (give ? to register new user)");
            System.out.print("username: ");
            nimi = scanner.nextLine();
        }

        user = findUser(nimi);

        if (user != null) {
            ohjelma();
        }
    }

    private void ohjelma() {
        System.out.println("Welcome " + user.getName() + " !");

        while (true) {
            menu();
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                break;
            } else if (command.equals("1")) {
                findBrewery();
            } else if (command.equals("2")) {
                findBeer();
            } else if (command.equals("3")) {
                addBeer();
            } else if (command.equals("4")) {
                listBreweries();
            } else if (command.equals("5")) {
                deleteBeer();
            } else if (command.equals("6")) {
                listBeers();
            } else if (command.equals("7")) {
                addBrewery();
            } else if (command.equals("8")) {
                deleteBrewery();
            } else if (command.equals("9")) {
                addPub();
            } else if (command.equals("10")) {
                addBeerToPub();
            } else if (command.equals("11")) {
                listPubs();
            } else if (command.equals("12")) {
                deletePub();
            } else if (command.equals("y")) {
                listUsers();
            } else if (command.equals("t")) {
                listMyRatings();
            } else {
                System.out.println("unknown command");
            }

            System.out.print("\npress enter to continue");
            scanner.nextLine();
        }

        System.out.println("bye");
    }

    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find/rate beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   delete beer");
        System.out.println("6   list beers");
        System.out.println("7   add brewery");
        System.out.println("8   delete brewery");
        System.out.println("9   add pub");
        System.out.println("10  add beer to pub");
        System.out.println("11  list pubs");
        System.out.println("12 delete pub");
        System.out.println("y   list users");
        System.out.println("t   list my ratings");
        System.out.println("0   quit");
        System.out.println("");
    }

    // jos kanta on luotu uudelleen, suoritetaan tämä ja laitetaan kantaan hiukan dataa
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);

        server.save(new Brewery("Paulaner"));
        server.save(new Pub("Pikkulintu"));
    }

    private void findBeer() {
        System.out.print("beer to find: ");
        String n = scanner.nextLine();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println("found: " + foundBeer);
        if (foundBeer.getPubs() == null || foundBeer.getPubs().isEmpty()) {
            System.out.println("  not available currently!");

        } else {
            System.out.println("  available now in:");
            for (Pub pub : foundBeer.getPubs()) {
                System.out.println("   " + pub);
            }
        }
        System.out.println(printRating(foundBeer));
        addRating(foundBeer);
    }

    private User findUser(String name) {
        User foundUser = server.find(User.class).where().like("name", name).findUnique();

        if (foundUser == null) {
            System.out.println(name + " not found");
            return null;
        }

        return foundUser;
    }

    private void findBrewery() {
        System.out.print("brewery to find: ");
        String n = scanner.nextLine();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();

        if (foundBrewery == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBrewery);
        for (Beer bier : foundBrewery.getBeers()) {
            System.out.println("   " + bier.getName());
        }
    }

    private void listBreweries() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            System.out.println(brewery);
        }
    }

    private void listBeers() {
        List<Beer> beers = server.find(Beer.class).findList();
        for (Beer beer : beers) {
            System.out.println(beer);
            System.out.println(printRating(beer));
        }
    }

    private void listUsers() {
        List<User> users = server.find(User.class).findList();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void listMyRatings() {
        List<Rating> ratings = server.find(Rating.class).findList();
        for (Rating rating : ratings) {
            if (rating.getUser().getName().equals(user.getName())) {
                System.out.println(rating);
            }
        }
    }

    private String printRating(Beer beer) {
        List<Rating> ratings = server.find(Rating.class).findList();

        int amount = 0;
        double value = 0;
        for (Rating rating : ratings) {
            if (rating.getBeer().getName() == beer.getName()) {
                amount++;
                value += rating.getValue();
            }
        }

        if (amount == 0) {
            return "no ratings";
        }

        return "  number of ratings: " + amount + " average " + (value / amount);
    }

    private void addRating(Beer beer) {
        System.out.print("give rating (leave empty if not) ");
        Integer value = Integer.parseInt(scanner.nextLine());

        Rating rating = new Rating(beer, user, value);
        server.save(rating);
    }

    private void addBeer() {
        System.out.print("to which brewery: ");
        String name = scanner.nextLine();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = scanner.nextLine();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }

    private void addBrewery() {
        System.out.print("give name of the brewery: ");
        String name = scanner.nextLine();

        Brewery uusi = new Brewery(name);
        if (name == null) {
            System.out.println("nothing added");
            return;
        }

        Brewery exists = server.find(Brewery.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(uusi);
        System.out.println(uusi.getName() + " lisatty");
    }

    private void addUser() {
        System.out.println("Register new user: ");
        System.out.print("give username: ");
        String name = scanner.nextLine();

        User uusi = new User(name);
        if (name == null) {
            System.out.println("Empty not accepted");
            return;
        }

        User exists = server.find(User.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(uusi);
        System.out.println(uusi.getName() + " lisatty");
    }

    private void deleteBeer() {
        System.out.print("beer to delete: ");
        String n = scanner.nextLine();
        Beer beerToDelete = server.find(Beer.class).where().like("name", n).findUnique();

        if (beerToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(beerToDelete);
        System.out.println("deleted: " + beerToDelete);

    }

    private void deleteBrewery() {
        System.out.print("brewery to delete: ");
        String n = scanner.nextLine();
        Brewery breweryToDelete = server.find(Brewery.class).where().like("name", n).findUnique();

        if (breweryToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(breweryToDelete);
        System.out.println("deleted: " + breweryToDelete);
    }

    private void addBeerToPub() {
        System.out.print("beer: ");
        String name = scanner.nextLine();
        Beer beer = server.find(Beer.class).where().like("name", name).findUnique();

        if (beer == null) {
            System.out.println("does not exist");
            return;
        }

        System.out.print("pub: ");
        name = scanner.nextLine();
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();

        if (pub == null) {
            System.out.println("does not exist");
            return;
        }

        pub.addBeer(beer);
        server.save(pub);
    }

    private void addPub() {
        System.out.print("pub to add: ");

        String name = scanner.nextLine();

        Pub exists = server.find(Pub.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        server.save(new Pub(name));
    }
    
    private void showBeerInPub(String name){
        Pub pub = server.find(Pub.class).where().like("name", name).findUnique();
        for (Beer beer : pub.getBeers()) {
            System.out.println(beer);
        }
    }
    
    public void listPubs(){
        List<Pub> pubs = server.find(Pub.class).findList();
        for (Pub pub : pubs) {
            System.out.println(pub.getName());
            showBeerInPub(pub.getName());
        }
    }
    
    private void deletePub() {
        System.out.print("pub to delete: ");
        String n = scanner.nextLine();
        Pub pubToDelete = server.find(Pub.class).where().like("name", n).findUnique();

        if (pubToDelete == null) {
            System.out.println(n + " not found");
            return;
        }

        server.delete(pubToDelete);
        System.out.println("deleted: " + pubToDelete);

    }
}
