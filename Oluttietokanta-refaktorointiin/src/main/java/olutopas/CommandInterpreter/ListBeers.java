package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import olutopas.IO;
import olutopas.model.Beer;

public class ListBeers extends Command {

    EbeanServer server;

    public ListBeers(IO io, EbeanServer server) {
        super(io);
        this.server = server;
    }

    @Override
    public void run() {

        List<Beer> beers = server.find(Beer.class).orderBy("brewery.name").findList();
        for (Beer beer : beers) {
            System.out.println(beer);
            if (beer.getRatings() != null && beer.getRatings().size() != 0) {
                System.out.println("  ratings given " + beer.getRatings().size() + " average " + beer.averageRating());
            } else {
                System.out.println("  no ratings");
            }
        }
    }
}
