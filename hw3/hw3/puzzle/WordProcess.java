package hw3.puzzle;

import edu.princeton.cs.introcs.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WordProcess {
    private static final String WORDFILE = "input/words10000.txt";
    private HashMap<String, Set<String>> allNeighbors;
    private Set<String> words;

    /**
     * Reads the wordfile specified by the wordfile variable.
     */
    private void readWords() {
        words = new HashSet<String>();

        In in = new In(WORDFILE);
        while (!in.isEmpty()) {
            words.add(in.readString());
        }
        wordNeighbors();
    }

    public WordProcess() {
        readWords();
    }

    private void wordNeighbors() {
        allNeighbors = new HashMap<>();
        for (String s : words) {
            for (String n : words) {
                Set<String> set = new HashSet<>();
                if (editDistance(s, n) == 1) {
                    set.add(n);
                }
                allNeighbors.put(s, set);
            }
        }
    }

    public Set<String> getWords() {
        return words;
    }

    public HashMap<String, Set<String>> getAllNeighbors() {
        return allNeighbors;
    }

    public static void main(String[] args) {
        WordProcess w = new WordProcess();
    }

    /**
     * Computes the edit distance between a and b. From
     * https://rosettacode.org/wiki/Levenshtein_distance.
     */
    private static int editDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
