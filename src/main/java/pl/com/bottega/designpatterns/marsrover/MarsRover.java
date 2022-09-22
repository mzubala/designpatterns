package pl.com.bottega.designpatterns.marsrover;

import static pl.com.bottega.designpatterns.marsrover.Direction.NORTH;

class MarsRover {

    private Point position = new Point(0, 0);

    private Direction direction = NORTH;

    void move() {
        position = position.moveBy(direction.vector);
    }

    void rotateLeft() {
        direction = direction.prev();
    }

    void rotateRight() {
        direction = direction.next();
    }

    void moveBack() {
        // TODO change position by moving by the reverseVector
    }

    Point getPosition() {
        return position;
    }

    Direction getDirection() {
        return direction;
    }
}