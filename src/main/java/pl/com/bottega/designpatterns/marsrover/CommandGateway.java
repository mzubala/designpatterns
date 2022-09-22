package pl.com.bottega.designpatterns.marsrover;

import java.util.HashMap;
import java.util.Map;

class CommandGateway {

    private final Map<String, Command> commandMap = new HashMap<>();

    CommandGateway() {
        // TODO call the other constructor passing all the available commands
    }

    CommandGateway(Command... commands) {
        // TODO put all the commands int commandsMap for easy access
    }

    void execute(String commandCode, MarsRover marsRover) {
        // TODO fetch a command from the map, if found execute, throw IllegalArgumentException otherwise
    }

}
