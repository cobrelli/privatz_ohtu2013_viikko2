package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.IO;
import olutopas.model.Brewery;

public class AddBrewery extends Command {

    EbeanServer server;

    public AddBrewery(IO io, EbeanServer server) {
        super(io);
        this.server = server;
    }

    @Override
    public void run() {
        System.out.print("brewery to add: ");
        String name = io.readString();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery != null) {
            System.out.println(name + " already exists!");
            return;
        }

        server.save(new Brewery(name));
    }
}
