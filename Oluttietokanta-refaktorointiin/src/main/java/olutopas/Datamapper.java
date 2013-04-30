package olutopas;

import olutopas.model.User;

public interface Datamapper {

    User getCurrentUser();

    void setCurrentUser(User user);
}
