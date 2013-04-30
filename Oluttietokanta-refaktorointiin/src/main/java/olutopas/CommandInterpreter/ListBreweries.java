package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import olutopas.ApplicationLogic.IO;
import olutopas.model.Brewery;

public class ListBreweries extends Command {

    EbeanServer server;

    public ListBreweries(IO io, EbeanServer server) {
        super(io);
        this.server = server;
    }

    @Override
    public void run() {
        List<Brewery> breweries = server.find(Brewery.class).findList();
        for (Brewery brewery : breweries) {
            io.printLine(brewery.toString());
        }
    }
}
