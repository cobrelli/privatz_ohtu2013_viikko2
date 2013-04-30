package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import java.util.HashMap;
import java.util.Map;
import olutopas.IO;
import olutopas.model.User;

public class CommandInterpreter {

    private Map<String, Command> commands = new HashMap();

    public CommandInterpreter(IO io, EbeanServer server, User user) {
        commands.put("1", new FindBrewery(io, server));
        commands.put("2", new FindBeer(io, server, user));
        commands.put("3", new AddBeer(io, server));
        commands.put("4", new ListBreweries(io, server));
        commands.put("5", new ListBeers(io, server));
        commands.put("6", new AddBrewery(io, server));
        commands.put("7", new MyRatings(io, server, user));
        commands.put("8", new ListUsers(io, server));
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }
}
