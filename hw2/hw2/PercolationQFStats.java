package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationQFStats {

    private final double mean;
    private final double stddev;
    private final double cLow;
    private final double cHigh;

    /** Constructor to perform T independent experiments for percolation on an N-by-N grid. */
    public PercolationQFStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be larger than 0.");
        }

        double[] thresholds = new double[T];

        for (int i = 0; i < T; i++) {
            PercolationQF p = new PercolationQF(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            thresholds[i] = p.numberOfOpenSites() * 1.0 / (N * N);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        cLow = mean - 1.96 * stddev / Math.sqrt(T);
        cHigh = mean + 1.96 * stddev / Math.sqrt(T);
    }

    /** Return the sample mean of percolation threshold. */
    public double mean() {
        /*double sum = 0;
        for (double x : thresholds) {
            sum += x;
        }
        return sum/this.T; */
        return mean;
    }

    /** Return the sample standard deviation of percolation threshold. */
    public double stddev() {
        /*double sumOfDev = 0;
        for (double x : thresholds) {
            sumOfDev += (x - mean()) * (x - mean());
        }
        return sumOfDev / (this.T - 1); */
        return stddev;
    }

    /** Return the low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return cLow;
    }

    /** Return the high endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return cHigh;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pers = new PercolationStats(N, T);
        StdOut.println("mean                    = " + pers.mean());
        StdOut.println("stddev                  = " + pers.stddev());
        StdOut.println("95% confidence interval = " + pers.confidenceLow() + ", " + pers.confidenceHigh());
    }
}

