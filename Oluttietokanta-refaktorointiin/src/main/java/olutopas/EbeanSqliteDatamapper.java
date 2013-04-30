package olutopas;

import olutopas.Datamapper;
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

//        if (db == Main.Database.H2) {
//            DataSourceConfig hdDB = new DataSourceConfig();
//            hdDB.setDriver("org.h2.Driver");
//            hdDB.setUsername("test");
//            hdDB.setPassword("test");
//            hdDB.setUrl("jdbc:h2:mem:tests;DB_CLOSE_DELAY=-1");
//            hdDB.setHeartbeatSql("select 1 ");
//            config.setDataSourceConfig(hdDB);
//        }
//
//        if (db == Main.Database.SQLite) {
//            DataSourceConfig sqLite = new DataSourceConfig();
//            sqLite.setDriver("org.sqlite.JDBC");
//            sqLite.setUsername("mluukkai");
//            sqLite.setPassword("mluukkai");
//            sqLite.setUrl("jdbc:sqlite:beer.db");
//            config.setDataSourceConfig(sqLite);
//            config.setDatabasePlatform(new SQLitePlatform());
//            config.getDataSourceConfig().setIsolationLevel(Transaction.READ_UNCOMMITTED);
//        }

        config.setDefaultServer(false);
        config.setRegister(false);

        config.addClass(Beer.class);
        config.addClass(Brewery.class);
        config.addClass(User.class);
        config.addClass(Rating.class);

//        if (dropAndCreateDatabase) {
//            config.setDdlGenerate(true);
//            config.setDdlRun(true);
//            //config.setDebugSql(true);
//        }

        EbeanServerFactory.create(config);
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
