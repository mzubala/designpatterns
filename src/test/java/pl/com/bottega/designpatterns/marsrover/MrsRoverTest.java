package pl.com.bottega.designpatterns.marsrover;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// TODO write unit tests checking the MarsRover class logic
class MrsRoverTest {

    private final MarsRover sut = new MarsRover();

    private void assertState(Point expectedPosition, Direction expectedDirection) {
        assertPosition(expectedPosition);
        assertDirection(expectedDirection);
    }

    private void assertPosition(Point expected) {
        assertThat(sut.getPosition()).isEqualTo(expected);
    }

    private void assertDirection(Direction expected) {
        assertThat(sut.getDirection()).isEqualTo(expected);
    }
}
