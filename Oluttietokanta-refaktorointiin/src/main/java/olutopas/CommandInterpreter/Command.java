package olutopas.CommandInterpreter;

import olutopas.IO;

public abstract class Command {

    protected IO io;

    public Command(IO io) {
        this.io = io;
    }

    public abstract void run();
}