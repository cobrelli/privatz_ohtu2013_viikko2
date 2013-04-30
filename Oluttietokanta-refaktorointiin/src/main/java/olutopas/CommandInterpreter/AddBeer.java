package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.ApplicationLogic.IO;
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
        io.printLine("to which brewery: ");
        String name = io.readString();
        Brewery brewery = server.find(Brewery.class).where().like("name", name).findUnique();

        if (brewery == null) {
            io.printLine(name + " does not exist");
            return;
        }

        io.printLine("beer to add: ");

        name = io.readString();

        Beer exists = server.find(Beer.class).where().like("name", name).findUnique();
        if (exists != null) {
            io.printLine(name + " exists already");
            return;
        }

        brewery.addBeer(new Beer(name));
        server.save(brewery);
        io.printLine(name + " added to " + brewery.getName());
    }
}
