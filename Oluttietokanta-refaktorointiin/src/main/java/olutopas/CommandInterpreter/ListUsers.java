package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import olutopas.IO;
import olutopas.model.User;

public class ListUsers extends Command {

    EbeanServer server;

    public ListUsers(IO io, EbeanServer server) {
        super(io);
        this.server = server;
    }

    @Override
    public void run() {
        List<User> users = server.find(User.class).findList();
        for (User user : users) {
            System.out.println(user.getName() + " " + user.getRatings().size() + " ratings");
        }
    }
}
