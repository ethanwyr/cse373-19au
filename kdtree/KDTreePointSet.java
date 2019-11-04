package kdtree;

import java.util.List;

public class KDTreePointSet implements PointSet {

    private KDNode root;
    private Point result;

    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public KDTreePointSet(List<Point> points) {
        root = new KDNode(points.get(0), 0);
        for (int i = 1; i < points.size(); i++) {
            addNode(root, points.get(i));
        }
    }

    private void addNode(KDNode node, Point pp) {
        if (node.rightTop(pp)) {
            if (node.right == null) {
                node.right = new KDNode(pp, node.k + 1);
            } else {
                addNode(node.right, pp);
            }
        } else {
            if (node.left == null) {
                node.left = new KDNode(pp, node.k + 1);
            } else {
                addNode(node.left, pp);
            }
        }
    }

    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        result = root.data;
        double minDis = root.data.distanceSquaredTo(x, y);
        findNearest(root, minDis, x, y);
        return result;
    }

    private double findNearest(KDNode node, double minDis, double x, double y) {
        if (node == null) {
            return minDis;
        }
        double dis = node.data.distanceSquaredTo(x, y);
        if (dis < minDis) {
            minDis = dis;
            result = node.data;
            if (minDis == 0.0) {
                return minDis;
            }
        }
        if (node.rightTop(x, y)) {
            minDis = findNearest(node.right, minDis, x, y);
            if (minDis != 0.0 && minDis > node.minPossibleDis(x, y)) {
                minDis = findNearest(node.left, minDis, x, y);
            }
        } else {
            minDis = findNearest(node.left, minDis, x, y);
            if (minDis != 0.0 && minDis > node.minPossibleDis(x, y)) {
                minDis = findNearest(node.right, minDis, x, y);
            }
        }
        return minDis;
    }

    // 2D Tree, k = 0 or 1
    private class KDNode {
        private Point data;
        private int k; // 0 for x, 1 for y
        private KDNode right; // right for x, top for y
        private KDNode left; // left for x, down for y

        KDNode(Point d, int k, KDNode r, KDNode l) {
            this.data = new Point(d.x(), d.y());
            if (k > 1) {
                k = 0;
            }
            this.k = k;
            this.right = r;
            this.left = l;
        }

        KDNode(Point d, int k) {
            this(d, k, null, null);
        }

        Boolean rightTop(double x, double y) {
            if (k == 1) {
                // k == 1, return true for y on the top of this point
                return y > data.y();
            } else {
                // k == 0, return true for x on the right of this point
                return x > data.x();
            }
        }

        Boolean rightTop(Point pp) {
            return rightTop(pp.x(), pp.y());
        }

        double minPossibleDis(double x, double y) {
            if (k == 1) {
                // k == 1, return (y1 - y2)^2
                return Math.pow(data.y() - y, 2);
            } else {
                // k == 0, return (x1 - x2)^2
                return Math.pow(data.x() - x, 2);
            }
        }
    }


}
