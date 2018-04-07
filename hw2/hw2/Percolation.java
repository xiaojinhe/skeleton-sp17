package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] openSite;               // Boolean array to store the state of the sites in the system
    private final int N;                            // The size of the row or col
    private final WeightedQuickUnionUF uf;  // uf for checking percolation, which has virtual sites for both top and bottom
    private final WeightedQuickUnionUF ufBackwash;  //uf for preventing backwash in isFull(), which has only virtual top site
    private int numOfOpen;                    // Count the number of open sites in the system
    private final int virtualTop = 0;         // Index for virtual top site, set to 0
    private final int virtualBottom;                // Index for virtual bottom site, set to be N*N + 1

    /** Create N-by-N grid, with all sites initially blocked, with two virtual sites on the top and bottom.
     * Throw a IllegalAugmentException if N <= 0.
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N has to larger than 0.");
        }

        this.N = N;
        openSite = new boolean[N*N + 2];
        uf = new WeightedQuickUnionUF(N*N + 2);
        ufBackwash = new WeightedQuickUnionUF(N*N + 1);
        numOfOpen = 0;
        virtualBottom = N*N + 1;
    }

    /** Check whether the row and col is in (0 - N-1) range. */
    private void validateRowCol(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException("index has to be in the range of 0 to N - 1.");
        }
    }

    /** Translate site (row, col) into a single interger that represents it. */
    private int xyToInt(int row, int col) {
        return N*row + col + 1;
    }

    /** Open the site (row, col) if it is not open already, and union it with left, right, top, and bottom sites
     * that have been open.
     */
    public void open(int row, int col) {
        validateRowCol(row, col);
        int index = xyToInt(row, col);

        if (openSite[index]) {
            return;
        }
        openSite[index] = true;
        numOfOpen++;

        if (row == 0) {
            uf.union(index, virtualTop);
            ufBackwash.union(index, virtualTop);
        }

        if (row == N - 1) {
            if (!percolates()) {
                uf.union(index, virtualBottom);
            }
        }

        // Union the left site if it is open
        if (col > 0 && openSite[index - 1]) {
            uf.union(index, index - 1);
            ufBackwash.union(index, index - 1);
        }

        //Union the right site if it is open
        if (col < N - 1 && openSite[index + 1]) {
            uf.union(index, index + 1);
            ufBackwash.union(index, index + 1);
        }

        //Union the upper site if it is open
        if (row > 0 && openSite[index - N]) {
            uf.union(index, index - N);
            ufBackwash.union(index, index - N);
        }

        //Union the lower site if it is open
        if (row < N - 1 && openSite[index + N]) {
            uf.union(index, index + N);
            ufBackwash.union(index, index + N);
        }
    }

    /** Return true if the site (row, col) is open, otherwise false. */
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return openSite[xyToInt(row, col)];
    }

    /** Return true if the site (row, col) is full, which means that the site is connected to the virtual top
     * site, otherwise false.
     */
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        return ufBackwash.connected(xyToInt(row, col), virtualTop);
    }

    /** Return the number of open sites in the system. */
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    /** Return true if the system percolates, otherwise false. */
    public boolean percolates() {
        return uf.connected(virtualBottom, virtualTop);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(0, 1);
        p.open(0, 2);
        p.open(1, 2);
        p.open(2, 2);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.isOpen(3,3));
        System.out.println(p.isOpen(2,2));
        System.out.println(p.percolates());
        p.open(3,3);
        System.out.println(p.isFull(3,3));
        p.open(4, 0);
        System.out.println(p.isFull(4, 0));
    }
}                       
