package olutopas.ApplicationLogic;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import olutopas.model.Beer;
import olutopas.model.Brewery;
import olutopas.model.Rating;
import olutopas.model.User;

public class EbeanSqliteDatamapper implements Datamapper {

    private Class[] luokat;
    private EbeanServer server;
    private String tietokantaUrl;
    boolean dropAndCreate;
    User user;

    public EbeanSqliteDatamapper(String tietokantaUrl, boolean dropAndCreate, Class... luokat) {
        this.luokat = luokat;
        this.dropAndCreate = dropAndCreate;
        this.tietokantaUrl = tietokantaUrl;
        init();
    }

    public void init() {
        ServerConfig config = new ServerConfig();
        config.setName("beerDb");
        DataSourceConfig sqLite = new DataSourceConfig();

        sqLite.setDriver("org.sqlite.JDBC");
        sqLite.setUsername("mluukkai");
        sqLite.setPassword("mluukkai");
        sqLite.setUrl("jdbc:sqlite:beer.db");
        config.setDataSourceConfig(sqLite);
        sqLite.setUrl("jdbc:sqlite:beer.db");
        config.setDataSourceConfig(sqLite);
        config.setDatabasePlatform(new SQLitePlatform());
        config.getDataSourceConfig().setIsolationLevel(Transaction.READ_UNCOMMITTED);

        config.setDefaultServer(false);
        config.setRegister(false);

        config.addClass(Beer.class);
        config.addClass(Brewery.class);
        config.addClass(User.class);
        config.addClass(Rating.class);

        if (dropAndCreate) {
            config.setDdlGenerate(true);
            config.setDdlRun(true);
        }

        for (Class luokka : luokat) {
            config.addClass(luokka);
        }

        server = EbeanServerFactory.create(config);
    }

    public Brewery brewerywithName(String n) {
        return server.find(Brewery.class).where().like("name", n).findUnique();
    }

    // muut metodit
    // apumetodi, jonka avulla Application-olio pääsee aluksi käsiksi EbeanServer-olioon
    public EbeanServer getServer() {
        return server;
    }

    @Override
    public User getCurrentUser() {
        return this.user;
    }

    @Override
    public void setCurrentUser(User user) {
        this.user = user;
    }
}
