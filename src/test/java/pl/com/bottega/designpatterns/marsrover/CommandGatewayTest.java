package pl.com.bottega.designpatterns.marsrover;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static pl.com.bottega.designpatterns.marsrover.Direction.NORTH;
import static pl.com.bottega.designpatterns.marsrover.Direction.WEST;

class CommandGatewayTest {

    private CommandGateway sut = new CommandGateway(new RotateLeftCommand(), new MoveCommand());

    private final MarsRover rover = new MarsRover();

    @Test
    void executesCommands() {
        // when
        sut.execute("m", rover);
        sut.execute("m", rover);

        // then
        assertRoverState(0, 2, NORTH);
    }

    @Test
    void throwsErrorWhenCommandIsUnknown() {
        // expect
        assertThatThrownBy(() -> sut.execute("wrong command", rover)).isInstanceOf(IllegalArgumentException.class);
        assertRoverState(0, 0, NORTH);
    }

    @Test
    void undoesLastExecutedCommand() {
        // when
        sut.execute("m", rover);
        sut.execute("rl", rover);
        sut.execute("undo", rover);

        // then
        assertRoverState(0, 1, NORTH);
    }

    @Test
    void redoesLastUndoneCommand() {
        // when
        sut.execute("m", rover);
        sut.execute("rl", rover);
        sut.execute("undo", rover);
        sut.execute("redo", rover);

        // then
        assertRoverState(0, 1, WEST);
    }

    @Test
    void undoesAndRedoesManyCommands() {
        // when
        sut.execute("m", rover);
        sut.execute("m", rover);
        sut.execute("rl", rover);
        sut.execute("m", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);
        sut.execute("redo", rover);
        sut.execute("undo", rover);

        // then
        assertRoverState(0, 1, NORTH);
    }

    @Test
    void doesNothingWhenThereIsNothingToUndoOrRedo() {
        // when
        sut.execute("m", rover);
        sut.execute("m", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);

        // then
        assertRoverState(0, 0, NORTH);

        // when
        sut.execute("redo", rover);

        // then
        assertRoverState(0, 1, NORTH);

        // when
        sut.execute("redo", rover);
        sut.execute("redo", rover);
        sut.execute("redo", rover);

        // then
        assertRoverState(0, 2, NORTH);
    }

    @Test
    void loosesUndoneCommandsWhenNewCommandIsExecuted() {
        // when
        sut.execute("m", rover);
        sut.execute("m", rover);
        sut.execute("m", rover);
        sut.execute("rl", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);
        sut.execute("m", rover);
        sut.execute("redo", rover);
        sut.execute("redo", rover);

        // expect
        assertRoverState(0, 3, NORTH);

        // when
        sut.execute("undo", rover);
        sut.execute("undo", rover);
        sut.execute("undo", rover);

        // then
        assertRoverState(0, 0, NORTH);
    }

    private void assertRoverState(int x, int y, Direction direction) {
        assertThat(rover.getPosition()).isEqualTo(new Point(x, y));
        assertThat(direction).isEqualTo(direction);
    }
}
