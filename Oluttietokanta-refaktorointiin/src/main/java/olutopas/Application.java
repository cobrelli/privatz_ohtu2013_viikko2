package olutopas;

import olutopas.ApplicationLogic.userIO;
import olutopas.ApplicationLogic.EbeanSqliteDatamapper;
import olutopas.ApplicationLogic.Datamapper;
import com.avaje.ebean.EbeanServer;
import olutopas.CommandInterpreter.Command;
import olutopas.CommandInterpreter.CommandInterpreter;
import olutopas.model.User;

public class Application {

    private EbeanServer server;
    private User user;
    userIO io = new userIO();
    CommandInterpreter komennot;

//    public Application(EbeanServer server) {
//        this.server = server;
//        komennot = new CommandInterpreter(io, server, this.user);
//    }
    public Application(Datamapper mapper) {
        this.server = ((EbeanSqliteDatamapper) mapper).getServer();
        this.komennot = new CommandInterpreter(io, server, user);
    }

    public void run(boolean newDatabase) {
        login();

        io.printLine("\nWelcome to Ratebeer " + user.getName());

        while (true) {
            menu();
            io.printLine("> ");
            String command = io.readString();

            Command komento = komennot.getCommand(command);

            if (command.equals("q")) {
                break;
            }

            if (komento != null) {
                komento.run();
            }

            io.printLine("\npress enter to continue");
            io.readString();
        }

        io.printLine("bye");
    }

    private void menu() {
        io.printLine("");
        io.printLine("1   find brewery");
        io.printLine("2   find/rate beer");
        io.printLine("3   add beer");
        io.printLine("4   list breweries");
        io.printLine("5   list beers");
        io.printLine("6   add brewery");
        io.printLine("7   show my ratings");
        io.printLine("8   list users");
        io.printLine("q   quit");
        io.printLine("");
    }

    private void login() {
        while (true) {
            io.printLine("\nLogin (give ? to register a new user)\n");

            io.printLine("username: ");
            String name = io.readString();

            if (name.equals("?")) {
                registerUser();
                continue;
            }

            user = server.find(User.class).where().like("name", name).findUnique();

            if (user != null) {
                break;
            }
            io.printLine("unknown user");
        }
    }

    private void registerUser() {
        io.printLine("Register a new user");
        io.printLine("give username: ");
        String name = io.readString();
        User u = server.find(User.class).where().like("name", name).findUnique();
        if (u != null) {
            io.printLine("user already exists!");
            return;
        }
        server.save(new User(name));
        io.printLine("user created!\n");
    }
}
