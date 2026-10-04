package java1.task11;

/* Композиция: содержит PointValue, не наследуется от него.
 * PointValue и ColoredPointValue - разные типы без иерархии. */
public final class ColoredPointValue {

    private final PointValue point;
    private final Color color;

    public ColoredPointValue(PointValue point, Color color) {
        this.point = point;
        this.color = color;
    }

    public PointValue point() { return point; }
    public Color color() { return color; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColoredPointValue c)) return false;
        return c.point.equals(point) && c.color == color;
    }

    @Override
    public int hashCode() {
        return 31 * point.hashCode() + color.hashCode();
    }
}