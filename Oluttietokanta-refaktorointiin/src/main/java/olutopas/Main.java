package olutopas;

import olutopas.ApplicationLogic.EbeanSqliteDatamapper;
import olutopas.ApplicationLogic.Datamapper;
import olutopas.model.Beer;
import olutopas.model.Brewery;

import olutopas.model.Rating;
import olutopas.model.User;

public class Main {

    enum Database {

        H2, SQLite
    }

    public static void main(String[] args) {

        boolean dropAndCreateTables = false;
        Datamapper mapper = new EbeanSqliteDatamapper("jdbc:sqlite:beer.db", dropAndCreateTables, Beer.class, Brewery.class, Rating.class, User.class);
        new Application(mapper).run(dropAndCreateTables);
    }
}
