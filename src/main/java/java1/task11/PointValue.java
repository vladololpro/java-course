package java1.task11;

/* Композиция вместо наследования: ColoredPointValue не является
 * PointValue, а содержит его. Нет иерархии - нет конфликта. */
public final class PointValue {

    private final int x;
    private final int y;

    public PointValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointValue p)) return false;
        return p.x == x && p.y == y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}