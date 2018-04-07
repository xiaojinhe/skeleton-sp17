import java.util.Arrays;

/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 1;
        In in = new In("words.txt");
        int[] numPalindrome = new int[26];
        String longest = "";

        while (!in.isEmpty()) {
            String word = in.readString();
            for (int i = 0; i < numPalindrome.length; i++) {
                if (word.length() >= minLength && Palindrome.isPalindrome(word, new OffByN(i))) {
                   // System.out.println(word);
                    numPalindrome[i]++;
                    if (word.length() > longest.length()) {
                        longest = word;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(numPalindrome));
        System.out.println(maxN(numPalindrome));
        System.out.println(longest);
    }

    public static int maxN(int[] array) {
        int indexOfMax = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[indexOfMax]) {
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }

} 
