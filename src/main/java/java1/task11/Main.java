package java1.task11;

/* Демонстрация нарушения симметричности и двух исправлений. */
public class Main {

    public static void main(String[] args) {

        Point p = new Point(1, 2);
        ColoredPoint cp = new ColoredPoint(1, 2, Color.RED);

        System.out.println("--- Нарушение симметричности ---");
        System.out.println("p.equals(cp) = " + p.equals(cp));
        System.out.println("cp.equals(p) = " + cp.equals(p));

        PointStrict ps = new PointStrict(1, 2);
        ColoredPointStrict cps = new ColoredPointStrict(1, 2, Color.RED);

        System.out.println();
        System.out.println("--- Строгое сравнение классов ---");
        System.out.println("ps.equals(cps) = " + ps.equals(cps));
        System.out.println("cps.equals(ps) = " + cps.equals(ps));

        PointValue pv = new PointValue(1, 2);
        ColoredPointValue cpv =
            new ColoredPointValue(new PointValue(1, 2), Color.RED);

        System.out.println();
        System.out.println("--- Композиция ---");
        System.out.println("pv.equals(cpv) = " + pv.equals(cpv));
        System.out.println("cpv.equals(pv) = " + cpv.equals(pv));
    }
}