public class Palindrome {

    /** Buids a Deque to stores all the characters in the word with the same order. */
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /** Returns true if the given word is a palindrome, else returns false. */
    public static boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size() > 1) {
            Character first = wordDeque.removeFirst();
            Character last = wordDeque.removeLast();
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    /** Returns true if the given word is a palindrome, else returns false, using recursion. */
    public static boolean isPalindromeRecursion(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        if (wordDeque.size() <= 1) {
            return true;
        }
        if (wordDeque.removeFirst() != wordDeque.removeLast()) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1));
    }

    /** returns true if the word is palindrome according to the character comparison test provided
     * by the CharacterComparator passed in as argument cc.
     * @param word
     * @param cc
     * @return
     */
    public static boolean isPalindromeRecursion(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        if (wordDeque.size() < 2) {
            return true;
        }
        if (!cc.equalChars(wordDeque.removeFirst(), wordDeque.removeLast())) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1), cc);
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        while (wordDeque.size() > 1) {
            Character first = wordDeque.removeFirst();
            Character last = wordDeque.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Palindrome object = new Palindrome();
        Deque<Character> wordDeque = object.wordToDeque("Wovvvvrd");
        wordDeque.printDeque();
        System.out.println(object.isPalindromeRecursion("flake", new OffByOne()));
    }
}
