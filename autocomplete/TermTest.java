package autocomplete;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TermTest {
    @Test
    public void testSimpleCompareTo() {
        Term a = new Term("autocomplete", 0);
        Term b = new Term("me", 0);
        assertTrue(a.compareTo(b) < 0); // "autocomplete" < "me"
    }

    @Test
    public void testSimpleCompareToByReverseWeightOrder() {
        Term a = new Term("autocomplete", 37);
        Term b = new Term("autob", 14);
        assertTrue(a.compareToByReverseWeightOrder(b) < 0); // a b, a < b
    }

    @Test
    public void testSimpleCompareToByPrefixOrder() {
        Term a = new Term("autocomplete", 0);
        Term b = new Term("autob", 0);
        assertEquals(0, a.compareToByPrefixOrder(b, 0));
        assertEquals(0, a.compareToByPrefixOrder(b, 1));
        assertEquals(0, a.compareToByPrefixOrder(b, 4));
        assertTrue(a.compareToByPrefixOrder(b, 8) > 0);
    }
}
