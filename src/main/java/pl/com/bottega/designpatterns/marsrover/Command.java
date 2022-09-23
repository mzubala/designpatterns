package pl.com.bottega.designpatterns.marsrover;

interface Command {
    String getCode();

    void execute(MarsRover rover);

    void undo(MarsRover rover);
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

    @Override
    public void undo(MarsRover rover) {
        rover.moveBack();
    }
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

    @Override
    public void undo(MarsRover rover) {
        rover.rotateRight();
    }
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

    @Override
    public void undo(MarsRover rover) {
        rover.rotateLeft();
    }
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

    @Override
    public void undo(MarsRover rover) {
        rover.move();
    }
}