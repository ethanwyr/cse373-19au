package astar;

import edu.princeton.cs.algs4.Stopwatch;
import heap.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private int numState;
    private double timeSpent;

    private HashMap<Vertex, VInfo> visited;
    private ArrayHeapMinPQ<Vertex> fringe;

    private class VInfo {
        private Double distTo;
        private Vertex edgeTo;

        VInfo(Double d, Vertex e) {
            this.distTo = d;
            this.edgeTo = e;
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
        visited = new HashMap<>();
        fringe = new ArrayHeapMinPQ<>();
        solution = new ArrayList<>();

        // add the start Vertex into internal data structure as a beginning
        numState = 0;
        relax(start, 0, null, input.estimatedDistanceToGoal(start, end));

        // visit all the vertex in the Priority Queue until time out.
        Vertex currV;
        VInfo currInfo;
        double currDis;
        while (fringe.size() != 0 && sw.elapsedTime() < timeout) {
            // get next vertex in the PQ
            currV = fringe.removeSmallest();
            currInfo = visited.get(currV);

            // if the current vertex is the target return
            if (currV.equals(end)) {
                solutionWeight = currInfo.distTo;
                solution.add(end);
                while (currInfo.edgeTo != null) {
                    currV = currInfo.edgeTo;
                    currInfo = visited.get(currV);
                    solution.add(0, currV);
                }
                outcome = SolverOutcome.SOLVED;
                timeSpent = sw.elapsedTime();
                return;
            }

            // if not the target, increment the numState
            numState++;

            // find and release all the edges
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(currV);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e.to(), currInfo.distTo + e.weight(), currV, input.estimatedDistanceToGoal(e.to(), end));
            }
        }

        // if not return in the loop, outcome is unsolvable or timeouts
        if (fringe.isEmpty()) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else {
            outcome = SolverOutcome.TIMEOUT;
        }
        timeSpent = sw.elapsedTime();
    }

    private void relax(Vertex v, double weight, Vertex from, double h) {
        VInfo info = visited.get(v);
        if (info != null) {
            if (weight < info.distTo) {
                info.distTo = weight;
                info.edgeTo = from;
                if (fringe.contains(v)) {
                    fringe.changePriority(v, weight + h);
                }
            }
        } else {
            info = new VInfo(weight, from);
            visited.put(v, info);
            fringe.add(v, weight + h);
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
