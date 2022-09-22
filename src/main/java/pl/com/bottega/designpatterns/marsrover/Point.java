package pl.com.bottega.designpatterns.marsrover;

// TODO implement methods
record Point(
    int x, int y
) {
    // TODO this should simply add coordinates
    Point moveBy(Point vector) {
        return null;
    }

    // TODO Let this return "(x, y)"
    @Override
    public String toString() {
        return "";
    }
}
