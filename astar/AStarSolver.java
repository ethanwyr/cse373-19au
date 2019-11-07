package astar;

import java.util.List;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    // TODO: add fields as necessary

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet; replace this with your code.");
    }

    @Override
    public SolverOutcome outcome() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet; replace this with your code.");
    }

    @Override
    public List<Vertex> solution() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet; replace this with your code.");
    }

    @Override
    public double solutionWeight() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet; replace this with your code.");
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet; replace this with your code.");
    }

    @Override
    public double explorationTime() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet; replace this with your code.");
    }
}
