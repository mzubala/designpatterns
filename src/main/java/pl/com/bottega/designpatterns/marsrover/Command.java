package pl.com.bottega.designpatterns.marsrover;

interface Command {
    String getCode();

    void execute(MarsRover rover);
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
}