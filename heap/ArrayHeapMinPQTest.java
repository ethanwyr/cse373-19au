package heap;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */

    @Test
    public void testSimple() {
        ArrayHeapMinPQ<Character> minPQ = new ArrayHeapMinPQ<>();
        minPQ.add('W', 1);
        minPQ.add('a', 2);
        minPQ.add('n', 3);
        minPQ.add('g', 4);
        minPQ.add('Y', 5);
        minPQ.add('i', 4);
        minPQ.add('R', 5);
        minPQ.add('e', 6);
        minPQ.add('N', 6);
        assertEquals(9, minPQ.size());
        assertEquals('W', (char) minPQ.getSmallest());
        assertTrue(minPQ.contains('W'));
        assertEquals('W', (char) minPQ.removeSmallest());
        assertFalse(minPQ.contains('W'));
        assertEquals(8, minPQ.size());
        minPQ.add('W', 2);
        minPQ.changePriority('N', 1);
        assertEquals('N', (char) minPQ.getSmallest());
        minPQ.changePriority('N', 10);
    }

    @Test
    public void testRandomTime() {
        int seed = 373; // or your favorite number
        Random random = new Random(seed);
        NaiveMinPQ<Integer> pq = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100000; i += 1) {
            int n = random.nextInt();
            pq.add(i, n);
            minPQ.add(i, n);
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            pq.contains(i);
        }
        System.out.println("Slow total time elapsed: " + sw.elapsedTime() + " seconds.");
        sw = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            minPQ.contains(i);
        }
        System.out.println("Slow total time elapsed: " + sw.elapsedTime() + " seconds.");

        for (int i = 0; i < 9698; i += 1) {
            int a = pq.removeSmallest();
            int b = minPQ.removeSmallest();
            assertEquals("Failed on iteration " + i, a, b);
        }
        System.out.println("expect: " + pq.removeSmallest());
        System.out.println("actual: " + minPQ.removeSmallest());
        System.out.println("expect: " + pq.removeSmallest());
        System.out.println("actual: " + minPQ.removeSmallest());
        for (int i = 9700; i < 10000; i += 1) {
            int a = pq.removeSmallest();
            int b = minPQ.removeSmallest();
            assertEquals("Failed on iteration " + i, a, b);
        }
    }

}
