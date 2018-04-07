import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.LinkedList;
import java.util.Queue;

public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked(int v) {
        return -1;
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {

        IndexMinPQ<Integer> fringe = new IndexMinPQ<>(maze.V());
        fringe.insert(s, distTo[s] + h(s));

        while (!fringe.isEmpty() && !targetFound) {
            int v = fringe.delMin();
            marked[v] = true;
            announce();
            if (v == t) {
                targetFound = true;
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    //announce();
                    int estimate = distTo[w] + h(w);
                    if (fringe.contains(w)) {
                        if (estimate < fringe.keyOf(w)) {
                            fringe.changeKey(w, estimate);
                        }
                    } else {
                        fringe.insert(w, estimate);
                    }
                }
            }
        }
    }

    private void astar2(int s) {

        marked[s] = true;
        if (s == t) {
            targetFound = true;
            return;
        }

        IndexMinPQ<Integer> fringe = new IndexMinPQ<>(maze.V());
        fringe.insert(s, distTo[s] + h(s));

        while (!fringe.isEmpty() && !targetFound) {
            int v = fringe.delMin();

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    marked[w] = true;
                    announce();
                    if (w == t) {
                        targetFound = true;
                        return;
                    }
                    int estimate = distTo[w] + h(w);
                    fringe.insert(w, estimate);
                }
            }
        }
    }

    @Override
    public void solve() {
        Stopwatch sw = new Stopwatch();
        astar(s);
        double t = sw.elapsedTime();
        System.out.println(t);
    }

}
