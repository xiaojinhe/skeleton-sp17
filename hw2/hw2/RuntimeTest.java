package hw2;

import edu.princeton.cs.algs4.Stopwatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** Performs a timing test on two different percolation implementations.
 * @author xjhe
 */
public class RuntimeTest {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        for (int n = 32; n <= 2048 * 4; n = n * 2) {
            Stopwatch sw1 = new Stopwatch();
            PercolationStats percStats = new PercolationStats(n, 10);
            double time1 = sw1.elapsedTime();
            builder.append(n + " " + 10 + " " + time1 + "\n");
        }
        try (FileWriter output = new FileWriter(new File("Percolation with WQU.txt" ))) {
            output.write(builder.toString());
        } catch (IOException e) {
            System.out.println(e);
        }

        /*builder.setLength(0);

        for (int n = 32; n <= 1024; n = n * 2) {
            Stopwatch sw2 = new Stopwatch();
            PercolationQFStats percQFStats = new PercolationQFStats(n, 2);
            double time2 = sw2.elapsedTime();
            builder.append(n + " " + 2 + " " + time2 + "\n");
        }
        try (FileWriter output = new FileWriter(new File("Percolation with QF.txt" ))) {
            output.write(builder.toString());
        } catch (IOException e) {
            System.out.println(e);
        } */
    }
}
