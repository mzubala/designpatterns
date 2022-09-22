package pl.com.bottega.designpatterns.marsrover;

enum Direction {

    NORTH(new Point(0, 1)),
    EAST(new Point(1, 0)),
    SOUTH(new Point(0, -1)),
    WEST(new Point(-1, 0));

    Point vector;

    Direction(Point vector) {
        this.vector = vector;
    }

    Direction next() {
        return Direction.values()[(ordinal() + 1) % values().length];
    }

    Direction prev() {
        if (ordinal() == 0) {
            return values()[values().length - 1];
        } else {
            return Direction.values()[ordinal() - 1];
        }
    }
}
