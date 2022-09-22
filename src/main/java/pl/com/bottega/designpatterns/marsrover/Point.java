package pl.com.bottega.designpatterns.marsrover;

record Point(
    int x, int y
) {
    Point moveBy(Point vector) {
        return new Point(x + vector.x, y + vector.y);
    }

    Point reflect() {
        // TODO implement reflecting this point against the point (0, 0)
        return null;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
