package pl.com.bottega.designpatterns.marsrover;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MrsRoverTest {

    private final MarsRover sut = new MarsRover();

    @Test
    void initiatesPositionAndDirection() {
        assertState(new Point(0, 0), Direction.NORTH);
    }

    @Test
    void movesNorth() {
        // when
        sut.move();

        // then
        assertState(new Point(0, 1), Direction.NORTH);

        // when
        sut.move();
        sut.move();
        sut.move();

        // then
        assertState(new Point(0, 4), Direction.NORTH);
    }

    @Test
    void movesSouth() {
        // given
        sut.rotateLeft();
        sut.rotateLeft();

        // when
        sut.move();
        sut.move();

        // then
        assertState(new Point(0, -2), Direction.SOUTH);
    }

    @Test
    void movesWest() {
        // given
        sut.rotateLeft();

        // when
        sut.move();
        sut.move();

        // then
        assertState(new Point(-2, 0), Direction.WEST);
    }

    @Test
    void movesEast() {
        // given
        sut.rotateRight();

        // when
        sut.move();
        sut.move();

        // then
        assertState(new Point(2, 0), Direction.EAST);
    }

    @Test
    void movesAndRotatesInDifferentDirections() {
        // when
        sut.move();
        sut.move(); // 0, 2, N
        sut.rotateRight(); // 0, 2, E
        sut.move();
        sut.move();
        sut.move(); // 3, 2, E
        sut.rotateLeft(); // 3, 2, N
        sut.move(); // 3, 3, N
        sut.rotateLeft(); // 3, 3, W
        sut.move();
        sut.move();
        sut.move(); // 0, 3, W
        sut.rotateLeft(); // 0, 3, S
        sut.move();
        sut.move();
        sut.move();

        // then
        assertState(new Point(0, 0), Direction.SOUTH);
    }

    @Test
    void rotatesLeft() {
        // when
        sut.rotateLeft();
        sut.rotateLeft();
        sut.rotateLeft();
        sut.rotateLeft();

        //
        assertState(new Point(0, 0), Direction.NORTH);
    }


    @Test
    void rotatesRight() {
        // when
        sut.rotateRight();
        sut.rotateRight();
        sut.rotateRight();
        sut.rotateRight();

        //
        assertState(new Point(0, 0), Direction.NORTH);
    }

    @Test
    void movesBackNorth() {
        // when
        sut.move();

        // then
        assertState(new Point(0, 1), Direction.NORTH);

        // when
        sut.moveBack();
        sut.moveBack();
        sut.moveBack();

        // then
        assertState(new Point(0, -2), Direction.NORTH);
    }

    @Test
    void movesBackSouth() {
        // given
        sut.rotateLeft();
        sut.rotateLeft();

        // when
        sut.moveBack();
        sut.moveBack();

        // then
        assertState(new Point(0, 2), Direction.SOUTH);
    }

    @Test
    void movesBackWest() {
        // given
        sut.rotateLeft();

        // when
        sut.moveBack();
        sut.moveBack();

        // then
        assertState(new Point(2, 0), Direction.WEST);
    }

    @Test
    void movesBackEast() {
        // given
        sut.rotateRight();

        // when
        sut.moveBack();
        sut.moveBack();

        // then
        assertState(new Point(-2, 0), Direction.EAST);
    }

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
