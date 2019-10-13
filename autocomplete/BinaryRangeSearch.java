package autocomplete;

import java.util.Arrays;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
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
        int low = 0;
        int high = this.terms.length - 1;
        int r = prefix.length();
        while (low <= high) {
            int mid = (low + high) / 2;
            if (this.terms[mid].queryPrefix(r).compareTo(prefix) < 0) {
                low = mid + 1;
            } else if (this.terms[mid].queryPrefix(r).compareTo(prefix) > 0) {
                high = mid - 1;
            } else {
                int first = matchesFirst(prefix, low, mid - 1);
                int last = matchesLast(prefix, mid + 1, high);
                // create array in descending order of weight
                Term[] match = Arrays.copyOfRange(this.terms, first, last);
                Arrays.sort(match, TermComparators.byReverseWeightOrder());
                return match;
            }
        }
        // not found
        return null;
    }

    private int matchesFirst(String prefix, int low, int high) {
        int r = prefix.length();
        if (low > high || this.terms[high].queryPrefix(r).compareTo(prefix) < 0) {
            return high + 1;
        }

        int mid = (low + high) / 2;
        if (this.terms[mid].queryPrefix(r).compareTo(prefix) < 0) {
            return matchesFirst(prefix, mid + 1, high);
        } else {
            return matchesFirst(prefix, low, mid - 1);
        }
    }

    private int matchesLast(String prefix, int low, int high) {
        int r = prefix.length();
        if (low > high) {
            return high + 1;
        } else if (this.terms[low].queryPrefix(r).compareTo(prefix) > 0) {
            return low;
        }

        int mid = (low + high) / 2;
        if (this.terms[mid].queryPrefix(r).compareTo(prefix) > 0) {
            return matchesLast(prefix, low, mid - 1);
        } else {
            return matchesLast(prefix, mid + 1, high);
        }
    }
}
