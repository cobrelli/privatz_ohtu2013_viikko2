package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.IO;
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
        System.out.print("brewery to find: ");
        String n = io.readString();
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
}
