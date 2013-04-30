package olutopas;

import com.avaje.ebean.EbeanServer;
import java.util.List;
import javax.persistence.OptimisticLockException;
import olutopas.CommandInterpreter.Command;
import olutopas.CommandInterpreter.CommandInterpreter;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Rating;
import olutopas.model.User;

public class Application {
    
    private EbeanServer server;
//    private Scanner scanner = new Scanner(System.in);
    private User user;
    userIO io = new userIO();
    CommandInterpreter komennot;
    
    public Application(EbeanServer server) {
        this.server = server;
        komennot = new CommandInterpreter(io, server, this.user);
    }
    
    public void run(boolean newDatabase) {
        if (newDatabase) {
            seedDatabase();
        }
        
        login();
        
        System.out.println("\nWelcome to Ratebeer " + user.getName());
        
        while (true) {
            menu();
            System.out.print("> ");
            String command = io.readString();
            
            Command komento = komennot.getCommand(command);
            if (komento != null) {
                komento.run();
            }
            
            if (command.equals("q")) {
                break;
            } //            else if (command.equals("0")) {
            //                listUsers();
            //            } 
            else {
                System.out.println("unknown command");
            }
            
            System.out.print("\npress enter to continue");
            io.readString();
        }
        
        System.out.println("bye");
    }
    
    private void menu() {
        System.out.println("");
        System.out.println("1   find brewery");
        System.out.println("2   find/rate beer");
        System.out.println("3   add beer");
        System.out.println("4   list breweries");
        System.out.println("5   list beers");
        System.out.println("6   add brewery");
        System.out.println("7   show my ratings");
        System.out.println("8   list users");
        System.out.println("q   quit");
        System.out.println("");
    }
    
    private void seedDatabase() throws OptimisticLockException {
        Brewery brewery = new Brewery("Schlenkerla");
        brewery.addBeer(new Beer("Urbock"));
        brewery.addBeer(new Beer("Lager"));
        // tallettaa myös luodut oluet, sillä Brewery:n OneToMany-mappingiin on määritelty
        // CascadeType.all
        server.save(brewery);

        // luodaan olut ilman panimon asettamista
        Beer b = new Beer("Märzen");
        server.save(b);

        // jotta saamme panimon asetettua, tulee olot lukea uudelleen kannasta
        b = server.find(Beer.class, b.getId());
        brewery = server.find(Brewery.class, brewery.getId());
        brewery.addBeer(b);
        server.save(brewery);
        
        server.save(new Brewery("Paulaner"));
        
        server.save(new User("mluukkai"));
    }
    
    private void login() {
        while (true) {
            System.out.println("\nLogin (give ? to register a new user)\n");
            
            System.out.print("username: ");
            String name = io.readString();
            
            if (name.equals("?")) {
                registerUser();
                continue;
            }
            
            user = server.find(User.class).where().like("name", name).findUnique();
            
            if (user != null) {
                break;
            }
            System.out.println("unknown user");
        }
    }
    
    private void registerUser() {
        System.out.println("Register a new user");
        System.out.print("give username: ");
        String name = io.readString();
        User u = server.find(User.class).where().like("name", name).findUnique();
        if (u != null) {
            System.out.println("user already exists!");
            return;
        }
        server.save(new User(name));
        System.out.println("user created!\n");
    }
}
