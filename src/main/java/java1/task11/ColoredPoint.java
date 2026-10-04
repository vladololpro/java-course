package java1.task11;

/* Цветная точка. equals требует instanceof ColoredPoint,
 * что ломает симметричность с Point. */
public class ColoredPoint extends Point {

    private final Color color;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color color() { return color; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColoredPoint)) return false;
        ColoredPoint p = (ColoredPoint) o;
        return super.equals(p) && p.color == color;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + color.hashCode();
    }

    @Override
    public String toString() {
        return "ColoredPoint(" + x() + "," + y() + "," + color + ")";
    }
}