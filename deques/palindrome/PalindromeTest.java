package deques.palindrome;

import deques.Deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindromeSimple() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("w"));
        assertTrue(palindrome.isPalindrome("Y"));
    }

    @Test
    public void testisPalindromeEven() {
        assertTrue(palindrome.isPalindrome("ww"));
        assertTrue(palindrome.isPalindrome("wxxw"));
        assertTrue(palindrome.isPalindrome("WXYYXW"));
        assertTrue(palindrome.isPalindrome("abcdefghiihgfedcba"));
        assertFalse(palindrome.isPalindrome("WY"));
        assertFalse(palindrome.isPalindrome("Xx"));
        assertFalse(palindrome.isPalindrome("zZzZzZ"));
        assertFalse(palindrome.isPalindrome("abcbba"));
        assertFalse(palindrome.isPalindrome("abcdefgh"));
    }

    @Test
    public void testisPalindromeOdd() {
        assertTrue(palindrome.isPalindrome("www"));
        assertTrue(palindrome.isPalindrome("wxyxw"));
        assertTrue(palindrome.isPalindrome("WXYZYXW"));
        assertTrue(palindrome.isPalindrome("abcdefghihgfedcba"));
        assertFalse(palindrome.isPalindrome("WYY"));
        assertFalse(palindrome.isPalindrome("abccbba"));
        assertFalse(palindrome.isPalindrome("abcdefghi"));
    }

    @Test
    public void testisPalindromeGeneral() {
        OffByOne obo = new OffByOne();

        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("w", obo));
        assertTrue(palindrome.isPalindrome("ab", obo));
        assertTrue(palindrome.isPalindrome("bcfgheda", obo));
        assertTrue(palindrome.isPalindrome("wyx", obo));
        assertTrue(palindrome.isPalindrome("wwyzxx", obo));
        assertTrue(palindrome.isPalindrome("ADCB", obo));
        assertTrue(palindrome.isPalindrome("()", obo));
        assertTrue(palindrome.isPalindrome("Z[", obo));
        assertTrue(palindrome.isPalindrome("z{", obo));

        assertFalse(palindrome.isPalindrome("wyr", obo));
        assertFalse(palindrome.isPalindrome("wwyyxx", obo));
        assertFalse(palindrome.isPalindrome("AcDb", obo));
        assertFalse(palindrome.isPalindrome("<>", obo));
        assertFalse(palindrome.isPalindrome("Z{", obo));
        assertFalse(palindrome.isPalindrome("z[", obo));
    }
}
