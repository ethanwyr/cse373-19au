package deques.palindrome;

import deques.ArrayDeque;
import deques.Deque;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> letter = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            letter.addLast(word.charAt(i));
        }
        return letter;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> letter = wordToDeque(word);
        return isPalindromeHelper(letter);
    }

    private boolean isPalindromeHelper(Deque<Character> letter) {
        if (letter.size() <= 1) {
            return true;
        }
        char f = letter.removeFirst();
        char l = letter.removeLast();
        boolean nextL = isPalindromeHelper(letter);
        letter.addFirst(f);
        letter.addLast(l);
        return (f == l) && nextL;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> letter = wordToDeque(word);
        return isPalindromeHelper(letter, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> letter, CharacterComparator cc) {
        if (letter.size() <= 1) {
            return true;
        }
        char f = letter.removeFirst();
        char l = letter.removeLast();
        boolean nextL = isPalindromeHelper(letter, cc);
        letter.addFirst(f);
        letter.addLast(l);
        return cc.equalChars(f, l) && nextL;
    }
}
