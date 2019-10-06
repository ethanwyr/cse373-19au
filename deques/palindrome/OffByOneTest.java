package deques.palindrome;

import org.junit.Test;

import static org.junit.Assert.*;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('y', 'x'));
        assertTrue(offByOne.equalChars('C', 'D'));
        assertTrue(offByOne.equalChars('N', 'M'));
        assertTrue(offByOne.equalChars(':', ';'));
        assertTrue(offByOne.equalChars(')', '('));

        assertFalse(offByOne.equalChars('w', 'w'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('g', 'c'));
        assertFalse(offByOne.equalChars('G', 'q'));
        assertFalse(offByOne.equalChars('s', 'Z'));
        assertFalse(offByOne.equalChars('X', 'x'));
        assertFalse(offByOne.equalChars('Y', 'Y'));
        assertFalse(offByOne.equalChars('G', 'Q'));
        assertFalse(offByOne.equalChars('S', 'Z'));
        assertFalse(offByOne.equalChars('+', '='));
    }
}
