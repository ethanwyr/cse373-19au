package kdtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Naive nearest neighbor implementation using a linear scan.
 */
public class NaivePointSet implements PointSet {

    private ArrayList<Point> nps;

    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public NaivePointSet(List<Point> points) {
        nps = new ArrayList<>();
        nps.addAll(points);
    }

    /**
     * Returns the point in this set closest to (x, y) in O(N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        Point result = nps.get(0);
        double minDis = nps.get(0).distanceSquaredTo(x, y);
        double dis;
        for (int i = 1; i < nps.size(); i++) {
            dis = nps.get(i).distanceSquaredTo(x, y);
            if (dis < minDis) {
                minDis = dis;
                result = nps.get(i);
                if (minDis == 0.0) {
                    break;
                }
            }
        }
        return result;
    }
}
