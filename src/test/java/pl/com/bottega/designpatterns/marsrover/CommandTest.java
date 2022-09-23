package pl.com.bottega.designpatterns.marsrover;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static pl.com.bottega.designpatterns.marsrover.Direction.WEST;

class CommandTest {

    private final MarsRover rover = new MarsRover();

    static Stream<Command> commandsUnderTest() {
        return Stream.of(new MoveBackCommand(), new MoveCommand(), new RotateLeftCommand(), new RotateRightCommand());
    }

    @ParameterizedTest
    @MethodSource("commandsUnderTest")
    void executesAndUndoesCommand(Command command) {
        // given
        rover.move();
        rover.move();
        rover.rotateLeft();

        // when
        command.execute(rover);
        command.undo(rover);

        // then
        assertThat(rover.getPosition()).isEqualTo(new Point(0, 2));
        assertThat(rover.getDirection()).isEqualTo(WEST);
    }

}
