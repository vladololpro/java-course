package java1.task11;

/* Наследник PointStrict. equals строго проверяет класс.
 * С PointStrict никогда не совпадёт - разные классы. */
public class ColoredPointStrict extends PointStrict {

    private final Color color;

    public ColoredPointStrict(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color color() { return color; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ColoredPointStrict p = (ColoredPointStrict) o;
        return super.equals(p) && p.color == color;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + color.hashCode();
    }
}