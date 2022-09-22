package pl.com.bottega.designpatterns.marsrover;

record Point(
    int x, int y
) {
    Point moveBy(Point vector) {
        return new Point(x + vector.x, y + vector.y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
