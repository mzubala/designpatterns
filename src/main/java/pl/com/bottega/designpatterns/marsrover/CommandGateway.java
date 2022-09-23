package pl.com.bottega.designpatterns.marsrover;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class CommandGateway {

    private final Map<String, Command> commandMap = new HashMap<>();

    private final List<RegularCommand> executedCommands = new LinkedList<>();

    private Integer lastExecutedCommandIndex;

    CommandGateway() {
        this(new RotateRightCommand(), new RotateLeftCommand(), new MoveCommand(), new MoveBackCommand());
    }

    CommandGateway(Command... commands) {
        for (Command cmd : commands) {
            commandMap.put(cmd.getCode(), new RegularCommand(cmd));
        }
        addSpecialCommands();
    }

    void execute(String commandCode, MarsRover marsRover) {
        Command command = commandMap.get(commandCode);
        if (command == null) {
            throw new IllegalArgumentException("No such command");
        }
        command.execute(marsRover);
    }

    private class RegularCommand implements Command {

        private Command decorated;

        RegularCommand(Command decorated) {
            this.decorated = decorated;
        }

        @Override
        public String getCode() {
            return decorated.getCode();
        }

        @Override
        public void execute(MarsRover rover) {
            decorated.execute(rover);
            if (lastExecutedCommandIndex != null) {
                for (int i = executedCommands.size() - 1; i > lastExecutedCommandIndex; i--) {
                    executedCommands.remove(i);
                }
            } else {
                executedCommands.clear();
            }
            executedCommands.add(this);
            lastExecutedCommandIndex = executedCommands.size() - 1;
        }

        void redo(MarsRover rover) {
            decorated.execute(rover);
        }

        @Override
        public void undo(MarsRover rover) {
            decorated.undo(rover);
        }
    }

    private void addSpecialCommands() {
        var redoCmd = new RedoCommand();
        var undoCmd = new UndoCommand();
        commandMap.put(redoCmd.getCode(), redoCmd);
        commandMap.put(undoCmd.getCode(), undoCmd);
    }

    private class UndoCommand implements Command {

        @Override
        public String getCode() {
            return "undo";
        }

        @Override
        public void execute(MarsRover rover) {
            if (lastExecutedCommandIndex != null) {
                var command = executedCommands.get(lastExecutedCommandIndex);
                command.undo(rover);
                if(lastExecutedCommandIndex == 0) {
                    lastExecutedCommandIndex = null;
                } else {
                    lastExecutedCommandIndex--;
                }
            }
        }

        @Override
        public void undo(MarsRover rover) {

        }
    }

    private class RedoCommand implements Command {

        @Override
        public String getCode() {
            return "redo";
        }

        @Override
        public void execute(MarsRover rover) {
            if (lastExecutedCommandIndex != null && lastExecutedCommandIndex + 1 < executedCommands.size()) {
                var command = executedCommands.get(lastExecutedCommandIndex + 1);
                command.redo(rover);
                lastExecutedCommandIndex++;
            } else if (lastExecutedCommandIndex == null && executedCommands.size() > 0) {
                var command = executedCommands.get(0);
                command.redo(rover);
                lastExecutedCommandIndex = 0;
            }
        }

        @Override
        public void undo(MarsRover rover) {

        }
    }

}
