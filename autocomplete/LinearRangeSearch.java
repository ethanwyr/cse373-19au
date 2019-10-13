package autocomplete;

import java.util.Arrays;

public class LinearRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("Term is null.");
        }
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("Term contains null.");
            }
        }
        this.terms = Arrays.copyOfRange(terms, 0, terms.length);
        Arrays.sort(this.terms);
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix is null.");
        }
        // find the terms index
        int r = prefix.length();
        int first = 0;
        int end = this.terms.length;
        while (this.terms[first].queryPrefix(r).compareTo(prefix) != 0) {
            first++;
            if (first == end) {
                return null; // not found;
            }
        }
        int last = first + 1;
        while (last < end && this.terms[last].queryPrefix(r).compareTo(prefix) == 0) {
            last++;
        }
        // create array in descending order of weight
        Term[] match = Arrays.copyOfRange(this.terms, first, last);
        Arrays.sort(match, TermComparators.byReverseWeightOrder());
        return match;
    }
}

