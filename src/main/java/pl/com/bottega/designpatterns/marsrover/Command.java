package pl.com.bottega.designpatterns.marsrover;

interface Command {
    String getCode();

    void execute(MarsRover rover);
}

// TODO create implementations of commands for moving and rotating