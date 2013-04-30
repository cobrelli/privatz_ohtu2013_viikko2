package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.IO;
import olutopas.model.Beer;
import olutopas.model.Brewery;

public class AddBeer extends Command {

    EbeanServer server;

    public AddBeer(IO io, EbeanServer server) {
        super(io);
        this.server = server;
    }

    @Override
    public void run() {
        System.out.print("to which brewery: ");
        String name = io.readString();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            System.out.println(name + " does not exist");
            return;
        }

        System.out.print("beer to add: ");

        name = io.readString();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            System.out.println(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        System.out.println(name + " added to " + brewery.getName());
    }
}
