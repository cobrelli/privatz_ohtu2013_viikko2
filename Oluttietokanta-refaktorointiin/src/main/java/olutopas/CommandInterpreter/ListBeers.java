package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import olutopas.ApplicationLogic.IO;
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
            io.printLine(beer.toString());
            if (beer.getRatings() != null && beer.getRatings().size() != 0) {
                io.printLine("  ratings given " + beer.getRatings().size() + " average " + beer.averageRating());
            } else {
                io.printLine("  no ratings");
            }
        }
    }
}
