package hw3.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

public class Solver {
    private ArrayList<WorldState> solSeq;
    private Node goalNode;

    /** Constructor which solves the puzzle, computing everything necessary for moves() and solution()
     * to not have to solve the problem again. Solves the puzzle using the A* algorithm. Assumes a
     * solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<Node> minPQ = new MinPQ<>();
        solSeq = new ArrayList<>();
        Node currentNode = new Node(initial, 0, null);
        minPQ.insert(currentNode);

        while (!minPQ.isEmpty() && !minPQ.min().state.isGoal()) {
            currentNode = minPQ.delMin();
            for (WorldState s : currentNode.state.neighbors()) {
                if (currentNode.prev != null && s.equals(currentNode.prev.state)) {
                    continue;
                }
                minPQ.insert(new Node(s, currentNode.moveNum + 1, currentNode));
            }
        }

        if (minPQ.isEmpty()) {
            System.out.println("There is no solution!");
        }
        else {
            goalNode = minPQ.min();
            Node res = goalNode;
            while (res != null) {
                solSeq.add(0, res.state);
                res = res.prev;
            }
        }

    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.
     */
    public int moves() {
        return goalNode.moveNum;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState to the solution.
     */
    public Iterable<WorldState> solution() {
        return solSeq;
    }

    private class Node implements Comparable<Node> {
        private WorldState state;
        private int moveNum;
        private Node prev;
        private int edtg;

        public Node(WorldState s, int n, Node p) {
            state = s;
            moveNum = n;
            prev = p;
            edtg = s.estimatedDistanceToGoal();
        }

        public WorldState getState() {
            return state;
        }

        public int getMoveNum() {
            return moveNum;
        }

        public Node getPrev() {
            return prev;
        }

        public int getEdtg() {
            return edtg;
        }

        @Override
        public int compareTo(Node o) {
            return (this.moveNum + this.edtg) - (o.moveNum + o.edtg);
        }
    }
}
