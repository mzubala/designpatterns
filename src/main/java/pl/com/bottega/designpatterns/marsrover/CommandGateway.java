package pl.com.bottega.designpatterns.marsrover;

import java.util.HashMap;
import java.util.Map;

class CommandGateway {

    private final Map<String, Command> commandMap = new HashMap<>();

    CommandGateway() {
        this(new RotateRightCommand(), new RotateLeftCommand(), new MoveCommand(), new MoveBackCommand());
    }

    CommandGateway(Command... commands) {
        for (Command cmd : commands) {
            commandMap.put(cmd.getCode(), cmd);
        }
    }

    void execute(String commandCode, MarsRover marsRover) {
        Command command = commandMap.get(commandCode);
        if (command == null) {
            throw new IllegalArgumentException("No such command");
        }
        command.execute(marsRover);
    }

}
