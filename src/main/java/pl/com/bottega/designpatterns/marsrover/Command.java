package pl.com.bottega.designpatterns.marsrover;

interface Command {
    String getCode();

    void execute(MarsRover rover);

    // TODO add undo method
}

class MoveCommand implements Command {

    @Override
    public String getCode() {
        return "m";
    }

    @Override
    public void execute(MarsRover rover) {
        rover.move();
    }

    // TODO implement the undo method
}

class RotateLeftCommand implements Command {

    @Override
    public String getCode() {
        return "rl";
    }

    @Override
    public void execute(MarsRover rover) {
        rover.rotateLeft();
    }

    // TODO implement the undo method
}

class RotateRightCommand implements Command {

    @Override
    public String getCode() {
        return "rr";
    }

    @Override
    public void execute(MarsRover rover) {
        rover.rotateRight();
    }

    // TODO implement the undo method
}

class MoveBackCommand implements Command {

    @Override
    public String getCode() {
        return "b";
    }

    @Override
    public void execute(MarsRover rover) {
        rover.moveBack();
    }

    // TODO implement the undo method
}