package pl.com.bottega.designpatterns.marsrover;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class CommandGateway {

    private final Map<String, Command> commandMap = new HashMap<>();

    private final List<Command> executedCommands = new LinkedList<>();

    private Integer lastExecutedCommandIndex;

    CommandGateway() {
        this(new RotateRightCommand(), new RotateLeftCommand(), new MoveCommand(), new MoveBackCommand());
    }

    CommandGateway(Command... commands) {
        for (Command cmd : commands) {
            // TODO decorate cmd with RegularCommand decorator
            commandMap.put(cmd.getCode(), cmd);
        }
        // TODO add special undo and redo commands to the map
    }

    void execute(String commandCode, MarsRover marsRover) {
        Command command = commandMap.get(commandCode);
        if (command == null) {
            throw new IllegalArgumentException("No such command");
        }
        command.execute(marsRover);
    }

    // TODO implement this as a decorator of any command
    private class RegularCommand implements Command {

        @Override
        public String getCode() {
            // TODO just delegate to the decorated command
            return null;
        }

        @Override
        public void execute(MarsRover rover) {
            // TODO delegate to execute method of the decorated command
            // TODO handle executedCommands and the lastExecutedCommandIndex
        }

        // TODO implement redo method
    }

    // TODO implement undo functionality - use executedCommands and lastExecutedCommandIndex
    private class UndoCommand implements Command {

        @Override
        public String getCode() {
            return null;
        }

        @Override
        public void execute(MarsRover rover) {

        }
    }

    // TODO implement redo functionality - use executedCommands and lastExecutedCommandIndex
    private class RedoCommand implements Command {

        @Override
        public String getCode() {
            return null;
        }

        @Override
        public void execute(MarsRover rover) {

        }
    }

}
