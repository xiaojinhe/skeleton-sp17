package hw3.puzzle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Board implements WorldState{
    private final int n;
    private int[] tiles;
    //private int[][] tiles;
    private int hamming = -1;
    private int manhanttan = -1;

    /**
     * Constructs a board from a N-by-N array of tiles where tiles[i][j] = tile at row i, column j.
     * The N-by-N array contains the N^2 integers between 0 and N^2 - 1, where 0 represents the
     * blank square.
     */
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = copyTiles(tiles);
    }

    public Board(int[] tiles, int n) {
        this.n = n;
        this.tiles = tiles;
    }

    /**
     * Helper for copying arrays.
     */
    private int[] copyTiles(int[][] tiles) {
        int[] copy = new int[tiles.length * tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            int[] row = tiles[i];
            System.arraycopy(row, 0, copy, i * tiles.length, tiles[i].length);
        }
        return copy;
    }

    /*private int[] copyTiles(int[][] tiles) {
        int[] copy = new int[tiles.length * tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                copy[row * tiles.length + col] = tiles[row][col];
            }
        }
        return copy;
    } */

    private int[] copyTiles(int [] tiles) {
        int[] copy = new int[tiles.length];
        System.arraycopy(tiles, 0, copy, 0, tiles.length);
        return copy;
    }

    /**
     * Returns value of tile at row i, column j, or 0 if blank.
     * Throw a IndexOutOfBoundsException if i or j is not between 0 and N - 1.
     */
    public int tileAt(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("i and j must be between 0 and n - 1.");
        }
        return tiles[i * n + j];
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return n;
    }

    /**
     * Hamming estimate: The number of tiles in the wrong postion.
     */
    public int hamming() {
        if (hamming >= 0) {
            return hamming;
        }
        hamming = 0;
        for (int i = 1; i < tiles.length; i++) {
            if (tiles[i - 1] != i) {
                hamming++;
            }
        }
        return hamming;
    }

    /**
     * Manhattan estimate: the sum of the Manhattan distances (sum of the vertical and
     * horizontal distance) from the tiles to their goal positions.
     */
    public int manhattan() {
        if (manhanttan >= 0) {
            return manhanttan;
        }

        manhanttan = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tileAt(i, j) != 0) {
                    int expectedRow = (tiles[i * n + j] - 1) / n;
                    int expectedCol = (tiles[i * n + j] - 1) % n;
                    manhanttan += Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
                }
            }
        }
        return manhanttan;
    }

    /**
     * Estimated distance to goal. This method should simply return the results of manhattan()
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    private int blankLocation() {
        int zeroIndex = -1;

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                zeroIndex = i;
                break;
            }
        }

        return zeroIndex;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        LinkedList<WorldState> neighbors = new LinkedList<>();

        int zeroIndex = blankLocation();
        int zeroRow = zeroIndex / n;
        int zeroCol = zeroIndex % n;

        if (zeroRow > 0) {
            neighbors.add(new Board(swap(zeroIndex, zeroIndex - n), n));
        }

        if (zeroRow < n - 1) {
            neighbors.add(new Board(swap(zeroIndex,zeroIndex + n), n));
        }

        if (zeroCol > 0) {
            neighbors.add(new Board(swap(zeroIndex, zeroIndex - 1), n));
        }

        if (zeroCol < n - 1) {
            neighbors.add(new Board(swap(zeroIndex, zeroIndex + 1), n));
        }
        return neighbors;
    }

    /** Helper for swap two items in the array, and return the new array. */
    private int[] swap(int index1, int index2) {
        int[] copy = copyTiles(tiles);
        int tmp = copy[index1];
        copy[index1] = copy[index2];
        copy[index2] = tmp;
        return copy;
    }

    /**
     * Returns true if is this board the goal board.
     */
    @Override
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Returns true if this board's tile values are the same position as y's, otherwise false.
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.n != that.n) {
            return false;
        }

        for (int i = 0; i < tiles.length; i++) {
            if (this.tiles[i] != that.tiles[i]) {
                return false;
            }
        }

        return true;
    }

    /** Returns the string representation of the board. 
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
