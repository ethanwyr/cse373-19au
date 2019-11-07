package astar;

import edu.princeton.cs.algs4.Stopwatch;
import heap.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private int numState;
    private double timeSpent;

    private ArrayList<IndexVertex> visit;
    private ArrayList<Double> distTo;
    private ArrayList<Integer> edgeTo;
    private ArrayHeapMinPQ<IndexVertex> fringe;

    // internal data type, represent a vertex and its index
    private class IndexVertex {
        private Vertex v;
        private int loc;

        IndexVertex(Vertex v, int loc) {
            this.v = v;
            this.loc = loc;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            IndexVertex that = (IndexVertex) o;
            return Objects.equals(v, that.v);
        }

        @Override
        public int hashCode() {
            return Objects.hash(v);
        }
    }

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        // initialization of the internal data structure and timer
        Stopwatch sw = new Stopwatch();
        visit = new ArrayList<>();
        distTo = new ArrayList<>();
        edgeTo = new ArrayList<>();
        fringe = new ArrayHeapMinPQ<>();
        solution = new ArrayList<>();

        // add the start Vertex into internal data structure as a beginning
        numState = 0;
        IndexVertex next = new IndexVertex(start, 0);
        relax(start, 0, -1, input.estimatedDistanceToGoal(start, end));

        // visit all the vertex in the Priority Queue until time out.
        IndexVertex currV;
        int currI;
        double currDis;
        while (fringe.size() != 0 && sw.elapsedTime() < timeout) {
            // get next vertex in the PQ
            currV = fringe.removeSmallest();
            currI = currV.loc;
            currDis = distTo.get(currI);
            numState++;

            // if the current vertex is the target return
            if (currV.v.equals(end)) {
                solutionWeight = currDis;
                solution.add(end);
                while (currI != 0) {
                    currI = edgeTo.get(currI);
                    solution.add(0, visit.get(currI).v);
                }
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                return;
            }

            // find and release all the edges
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(currV.v);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e.to(), currDis + e.weight(), currI, input.estimatedDistanceToGoal(e.to(), end));
            }
        }

        // if not return in the loop, outcome is unsolvable or timeout
        if (fringe.isEmpty()) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else {
            outcome = SolverOutcome.TIMEOUT;
        }
        timeSpent = sw.elapsedTime();
    }

    private void relax(Vertex v, double weight, int from, double h) {
        IndexVertex next = new IndexVertex(v, -1);
        if (fringe.contains(next)) {
            int index = fringe.getItem(next).loc;
            if (weight < distTo.get(index)) {
                distTo.set(index, weight);
                edgeTo.set(index, from);
                fringe.changePriority(next, weight + h);
            }
        } else {
            next.loc = visit.size();
            visit.add(next);
            distTo.add(weight);
            edgeTo.add(from);
            fringe.add(next, weight + h);
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return numState;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
