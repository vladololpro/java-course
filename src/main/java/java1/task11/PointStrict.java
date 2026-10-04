package java1.task11;

/* Исправление 1: строгое сравнение классов через getClass().
 * Point и ColoredPointStrict никогда не равны - даже с
 * одинаковыми координатами. Симметричность восстановлена. */
public class PointStrict {

    private final int x;
    private final int y;

    public PointStrict(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PointStrict p = (PointStrict) o;
        return p.x == x && p.y == y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}