package olutopas.CommandInterpreter;

import com.avaje.ebean.EbeanServer;
import olutopas.ApplicationLogic.IO;
import olutopas.model.Rating;
import olutopas.model.User;

public class MyRatings extends Command {

    EbeanServer server;
    User user;

    public MyRatings(IO io, EbeanServer server, User user) {
        super(io);
        this.server = server;
        this.user = user;
    }

    @Override
    public void run() {

        io.printLine("Ratings by " + user.getName());
        for (Rating rating : user.getRatings()) {
            io.printLine(rating.toString());
        }
    }
}
