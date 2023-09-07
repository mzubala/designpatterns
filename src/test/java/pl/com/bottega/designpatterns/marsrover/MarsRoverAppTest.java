package pl.com.bottega.designpatterns.marsrover;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

class MarsRoverAppTest {

    private final FakeInput input = new FakeInput();
    private final OutputAssertions outputAssertions = new OutputAssertions();

    // TODO change this line to inject dependencies to the MarsRoverApp class, so that the tests have a chance to work
    private final MarsRoverApp sut = new MarsRoverApp();

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
        await().atMost(Duration.ofMillis(100)).untilAsserted(() -> {
            outputAssertions.assertThatNextLine().contains("Sorry, I don't understand");
        });
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

    // TODO write another test similar to the above, which checks rover's rotation ability

    private void assertPosition(int x, int y, String direction) {
        await().atMost(Duration.ofMillis(100)).untilAsserted(() -> {
            outputAssertions.assertThatNextLine().contains(String.format("(%d, %d)", x, y)).contains(direction);
        });
    }
}
