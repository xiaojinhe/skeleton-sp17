import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycle = false;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        int[] parent = new int[maze.V()];
        for (int v = 0; v < maze.V(); v++) {
            if (!marked[v]) {
                parent[v] = v;
                distTo[v] = 0;
                dfsCycles(v, parent);
                if (cycle) {
                    return;
                }
            }
        }
    }

    private void dfsCycles(int v, int[] parent) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (cycle) {
                return;
            } else if (!marked[w]) {
                parent[w] = v;
                distTo[w] = distTo[v] + 1;
                dfsCycles(w, parent);
            } else if (parent[v] != w) {
                cycle = true;
                for (int x = v; x != w; x = parent[x]) {
                    edgeTo[x] = parent[x];
                }
                edgeTo[w] = v;
                announce();
            }
        }
    }
}
