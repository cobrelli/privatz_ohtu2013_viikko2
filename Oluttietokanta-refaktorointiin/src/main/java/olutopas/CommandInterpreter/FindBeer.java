package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.IO;
import olutopas.model.Beer;
import olutopas.model.Rating;
import olutopas.model.User;

public class FindBeer extends Command {

    EbeanServer server;
    User user;

    public FindBeer(IO io, EbeanServer server, User user) {
        super(io);
        this.server = server;
        this.user = user;
    }

    @Override
    public void run() {
        System.out.print("beer to find: ");
        String n = io.readString();
        Beer foundBeer = server.find(Beer.class).where().like("name", n).findUnique();

        if (foundBeer == null) {
            System.out.println(n + " not found");
            return;
        }

        System.out.println(foundBeer);

        if (foundBeer.getRatings() != null && foundBeer.getRatings().size() != 0) {
            System.out.println("  number of ratings: " + foundBeer.getRatings().size() + " average " + foundBeer.averageRating());
        } else {
            System.out.println("no ratings");
        }

        System.out.print("give rating (leave empty if not): ");
        try {
            int rating = Integer.parseInt(io.readString());
            addRating(foundBeer, rating);
        } catch (Exception e) {
        }
    }
//        
//        System.out.print("brewery to find: ");
//        String n = io.readString();
////        System.out.println(n);
////        System.out.println(server.toString());
//        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();
////        Brewery foundBrewery = null;
//        if (foundBrewery == null) {
//            System.out.println(n + " not found");
//            return;
//        }
//
//        System.out.println(foundBrewery);
//        for (Beer bier : foundBrewery.getBeers()) {
//            System.out.println("   " + bier.getName());
//        }
//    }

    private void addRating(Beer foundBeer, int value) {
        Rating rating = new Rating(foundBeer, user, value);
        server.save(rating);
    }
}
