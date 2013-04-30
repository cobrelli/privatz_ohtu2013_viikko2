package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.ApplicationLogic.IO;
import olutopas.model.Beer;
import olutopas.model.Brewery;

public class FindBrewery extends Command {

    EbeanServer server;

    public FindBrewery(IO io, EbeanServer server) {
        super(io);
        this.server = server;
    }

    @Override
    public void run() {
        io.printLine("brewery to find: ");
        String n = io.readString();
        Brewery foundBrewery = server.find(Brewery.class).where().like("name", n).findUnique();
        if (foundBrewery == null) {
            io.printLine(n + " not found");
            return;
        }

        io.printLine(foundBrewery.toString());
        for (Beer bier : foundBrewery.getBeers()) {
            io.printLine("   " + bier.getName());
        }
    }
}
