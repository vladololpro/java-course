package java1;

import java1.task11.Color;
import java1.task11.ColoredPoint;
import java1.task11.ColoredPointStrict;
import java1.task11.ColoredPointValue;
import java1.task11.Point;
import java1.task11.PointStrict;
import java1.task11.PointValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task11Test {

    @Test
    void pointAndColoredPoint_breakSymmetry() {
        Point point = new Point(1, 2);
        ColoredPoint coloredPoint = new ColoredPoint(1, 2, Color.RED);

        assertTrue(point.equals(coloredPoint));
        assertFalse(coloredPoint.equals(point));
    }

    @Test
    void strictClassComparison_restoresSymmetry() {
        PointStrict point = new PointStrict(1, 2);
        ColoredPointStrict coloredPoint = new ColoredPointStrict(1, 2, Color.RED);

        assertFalse(point.equals(coloredPoint));
        assertFalse(coloredPoint.equals(point));
    }

    @Test
    void composition_avoidsInheritanceConflict() {
        PointValue point = new PointValue(1, 2);
        ColoredPointValue coloredPoint = new ColoredPointValue(new PointValue(1, 2), Color.RED);

        assertFalse(point.equals(coloredPoint));
        assertFalse(coloredPoint.equals(point));
        assertTrue(coloredPoint.equals(new ColoredPointValue(new PointValue(1, 2), Color.RED)));
    }
}
