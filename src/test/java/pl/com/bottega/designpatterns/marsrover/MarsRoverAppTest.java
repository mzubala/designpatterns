package pl.com.bottega.designpatterns.marsrover;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarsRoverAppTest {

    private final FakeInput input = new FakeInput();
    private final OutputAssertions outputAssertions = new OutputAssertions();

    private final MarsRoverApp sut = new MarsRoverApp(
        input.toInputStream(),
        outputAssertions.toOutputStream(),
        new CommandGateway()
    );

    @BeforeEach
    void setup() {
        new Thread(sut::run).start();
    }

    @AfterEach
    void tearDown() {
        input.close();
        outputAssertions.close();
    }

    @Test
    void printsRoverPositionAtTheBeginning() {
        // expect
        assertPosition(0, 0, "NORTH");
    }

    @Test
    void printsInfoAboutUnknownCommands() {
        // given
        outputAssertions.skipLine();

        // when
        input.input("unknown command");

        // then
        outputAssertions.assertThatNextLine().contains("Sorry, I don't understand");
    }

    @Test
    void movesRover() {
        // given
        outputAssertions.skipLine();

        // when
        input.input("m");
        input.input("m");

        // then
        assertPosition(0, 1, "NORTH");
        assertPosition(0, 2, "NORTH");
    }

    @Test
    void movesRoverBack() {
        // given
        outputAssertions.skipLine();

        // when
        input.input("b");
        input.input("b");

        // then
        assertPosition(0, -1, "NORTH");
        assertPosition(0, -2, "NORTH");
    }


    @Test
    void rotatesRover() {
        // given
        outputAssertions.skipLine();

        // when
        input.input("rl");
        input.input("rl");
        input.input("rl");
        input.input("rl");
        input.input("rr");
        input.input("rr");
        input.input("rr");
        input.input("rr");

        // then
        assertPosition(0, 0, "WEST");
        assertPosition(0, 0, "SOUTH");
        assertPosition(0, 0, "EAST");
        assertPosition(0, 0, "NORTH");
        assertPosition(0, 0, "EAST");
        assertPosition(0, 0, "SOUTH");
        assertPosition(0, 0, "WEST");
        assertPosition(0, 0, "NORTH");
    }

    @Test
    void userCanUndoAndRedo() {
        // given
        outputAssertions.skipLine();

        // when
        input.input("m");
        input.input("m");
        input.input("undo");
        input.input("redo");
        
        // then
        assertPosition(0, 1, "NORTH");
        assertPosition(0, 2, "NORTH");
        assertPosition(0, 1, "NORTH");
        assertPosition(0, 2, "NORTH");
    }

    private void assertPosition(int x, int y, String direction) {
        outputAssertions.assertThatNextLine().contains(String.format("(%d, %d)", x, y)).contains(direction);
    }
}
