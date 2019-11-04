package kdtree;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class KDTreePointSetTest {

    @Test
    public void testSimple() {
        Point p1 = new Point(1.1, 2.2); // Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        PointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        double x = 3.0;
        double y = 4.0;           // Mouse-click at (3, 4)
        Point ret = nn.nearest(x, y);   // ret == p2
        assertEquals(ret, p2);
        p2 = new Point(3.9, 4.9);
        assertEquals(ret, new Point(3.3, 4.4));
        assertEquals(3.3, ret.x(), 0.0);        // Evaluates to 3.3
        assertEquals(4.4, ret.y(), 0.0);        // Evaluates to 4.4

        p2 = new Point(3.3, 4.4);
        PointSet pp = new KDTreePointSet(List.of(p1, p2, p3));
        Point result = pp.nearest(x, y);   // ret == p2
        assertEquals(result, p2);
        p2 = new Point(3.9, 4.9);
        assertEquals(result, new Point(3.3, 4.4));
        assertEquals(3.3, result.x(), 0.0);        // Evaluates to 3.3
        assertEquals(4.4, result.y(), 0.0);        // Evaluates to 4.4
    }

    @Test
    public void testRandomTime() {
        int seed = 373; // or your favorite number
        Random random = new Random(seed);
        List<Point> pps = new ArrayList<>();
        double x;
        double y;
        for (int i = 0; i < 100000; i += 1) {
            x = random.nextDouble();
            y = random.nextDouble();
            pps.add(new Point(x, y));
        }

        PointSet nn = new NaivePointSet(pps);
        PointSet pp = new KDTreePointSet(pps);
        Point p1;
        Point p2;
        for (int i = 0; i < 100000; i += 1) {
            x = random.nextDouble();
            y = random.nextDouble();
            p1 = nn.nearest(x, y);
            p2 = pp.nearest(x, y);
            assertEquals(p1, p2);
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            x = random.nextDouble();
            y = random.nextDouble();
            p1 = nn.nearest(x, y);
        }
        System.out.println("Slow total time elapsed: " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            x = random.nextDouble();
            y = random.nextDouble();
            p2 = pp.nearest(x, y);
        }
        System.out.println("Slow total time elapsed: " + sw.elapsedTime() + " seconds.");
    }


}
