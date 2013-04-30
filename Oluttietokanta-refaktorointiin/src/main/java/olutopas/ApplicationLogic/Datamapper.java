package olutopas.ApplicationLogic;

import olutopas.model.User;

public interface Datamapper {

    User getCurrentUser();

    void setCurrentUser(User user);
}
