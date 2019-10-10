package autocomplete;

public class Term implements Comparable<Term> {
    // TODO: add fields as necessary

    /**
     * Initializes a term with the given query string and weight.
     * @throws IllegalArgumentException if query is null or weight is negative
     */
    public Term(String query, long weight) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /**
     * Compares the two terms in lexicographic order by query.
     * @throws NullPointerException if the specified object is null
     */
    public int compareTo(Term that) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /** Compares to another term, in descending order by weight. */
    public int compareToByReverseWeightOrder(Term that) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /**
     * Compares to another term in lexicographic order, but using only the first r characters
     * of each query. If r is greater than the length of any term's query, compares using the
     * term's full query.
     * @throws IllegalArgumentException if r < 0
     */
    public int compareToByPrefixOrder(Term that, int r) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /** Returns this term's query. */
    public String query() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /**
     * Returns the first r characters of this query.
     * If r is greater than the length of the query, returns the entire query.
     * @throws IllegalArgumentException if r < 0
     */
    public String queryPrefix(int r) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /** Returns this term's weight. */
    public long weight() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }
}
