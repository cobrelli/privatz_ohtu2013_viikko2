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
//        commands.put(1, new EntryType(io));
//        commands.put(2, new SaveEntries(io));
//        commands.put(3, new PrintEntries(io));
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }
}
