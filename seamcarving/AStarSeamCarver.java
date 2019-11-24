package seamcarving;

import astar.AStarGraph;
import astar.AStarSolver;
import astar.ShortestPathsSolver;
import astar.WeightedEdge;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        if (x < 0 || x > picture.width() - 1) {
            throw new IndexOutOfBoundsException("Index x is outside its prescribed range.");
        }
        if (y < 0 || y > picture.height() - 1) {
            throw new IndexOutOfBoundsException("Index y is outside its prescribed range.");
        }

        int l = x - 1;
        int r = x + 1;
        int t = y - 1;
        int b = y + 1;
        if (l < 0) {
            l += picture.width();
        }
        if (r >= picture.width()) {
            r -= picture.width();
        }
        if (t < 0) {
            t += picture.height();
        }
        if (b >= picture.height()) {
            b -= picture.height();
        }
        Color left = picture.get(l, y);
        Color right = picture.get(r, y);
        Color top = picture.get(x, t);
        Color bottom = picture.get(x, b);

        return Math.sqrt(Math.pow(left.getBlue() - right.getBlue(), 2) +
                Math.pow(left.getGreen() - right.getGreen(), 2) +
                Math.pow(left.getRed() - right.getRed(), 2) +
                Math.pow(top.getBlue() - bottom.getBlue(), 2) +
                Math.pow(top.getGreen() - bottom.getGreen(), 2) +
                Math.pow(top.getRed() - bottom.getRed(), 2));
    }

    public int[] findHorizontalSeam() {
        Point start = new Point(-1, -1, 0);
        Point goal = new Point(picture.width(), picture.height(), 0);
        SeamGraph seam = new SeamGraph();
        ShortestPathsSolver<Point> solver = new AStarSolver<>(seam, start, goal, 30);

        if (solver.outcome() == ShortestPathsSolver.SolverOutcome.SOLVED) {
            int[] horizontal = new int[picture.width()];
            List<Point> p = solver.solution();
            for (int i = 0; i < picture.width(); i++) {
                horizontal[i] = p.get(i + 1).y();
            }
            return horizontal;
        } else {
            return null;
        }
    }

    public int[] findVerticalSeam() {
        Point start = new Point(-1, -1, 1);
        Point goal = new Point(picture.width(), picture.height(), 1);
        SeamGraph seam = new SeamGraph();
        ShortestPathsSolver<Point> solver = new AStarSolver<>(seam, start, goal, 30);

        if (solver.outcome() == ShortestPathsSolver.SolverOutcome.SOLVED) {
            int[] vertical = new int[picture.height()];
            List<Point> p = solver.solution();
            for (int i = 0; i < picture.height(); i++) {
                vertical[i] = p.get(i + 1).x();
            }
            return vertical;
        } else {
            return null;
        }
    }

    public class SeamGraph implements AStarGraph<Point> {
        @Override
        public List<WeightedEdge<Point>> neighbors(Point b) {
            List<WeightedEdge<Point>> neighborEdges = new ArrayList<>();
            int d = b.d();
            int start;
            int end;
            if ((b.x() == picture.width() - 1 && d == 0) || b.y() == picture.height() - 1 && d == 1) {
                // for end point
                neighborEdges.add(new WeightedEdge<>(b, new Point(picture.width(), picture.height(), d), 0));
            } else if (d == 0) {
                if (b.x() == -1 && b.y() == -1) {
                    // for start point
                    start = 0;
                    end = picture.height() - 1;
                } else {
                    // normal case
                    start = b.y() - 1;
                    end = b.y() + 1;
                    if (start < 0) {
                        // top edge case
                        start = 0;
                    }
                    if (end > picture.height() - 1) {
                        // bottom edge case
                        end = picture.height() - 1;
                    }
                }
                for (int i = start; i <= end; i++) {
                    neighborEdges.add(new WeightedEdge<>(b, new Point(b.x() + 1, i, d), energy(b.x() + 1, i)));
                }
            } else {
                if (b.x() == -1 && b.y() == -1) {
                    // for start point
                    start = 0;
                    end = picture.width() - 1;
                } else {
                    // normal case
                    start = b.x() - 1;
                    end = b.x() + 1;
                    if (start < 0) {
                        // right edge case
                        start = 0;
                    }
                    if (end > picture.width() - 1) {
                        // left edge case
                        end = picture.width() - 1;
                    }
                }
                for (int i = start; i <= end; i++) {
                    neighborEdges.add(new WeightedEdge<>(b, new Point(i, b.y() + 1, d), energy(i, b.y() + 1)));
                }
            }
            return neighborEdges;
        }

        @Override
        public double estimatedDistanceToGoal(Point s, Point goal) {
            return 0.0;
        }
    }

    public class Point {

        private int x;
        private int y;
        private int d;

        public Point(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public int d() {
            return d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return Double.compare(point.x, x) == 0 &&
                    Double.compare(point.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
