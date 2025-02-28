import java.util.Iterator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int minBoardMoves;
    private int solutionSize;
    private Board[] solutionPath;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode prev;
        int priority;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    // PRIVATE FUNCTIONS
    private void constructGoalPath(SearchNode node) {
        solutionSize = node.moves + 1;
        solutionPath = new Board[solutionSize];

        while (node != null) {
            solutionPath[--solutionSize] = node.board;
            node = node.prev;
        }
        minBoardMoves = solutionPath.length - 1;
    }

    // PUBLIC FUNCTIONS
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial board cannot be null");

        this.minBoardMoves = -1;
        MinPQ<SearchNode> boardPQ = new MinPQ<>();
        boardPQ.insert(new SearchNode(initial, 0, null));

        while (!boardPQ.isEmpty()) {
            SearchNode currentNode = boardPQ.delMin();

            if (currentNode.board.isGoal()) {
                constructGoalPath(currentNode);
                return;
            }

            for (Board neighbour : currentNode.board.neighbors()) {
                if (currentNode.prev == null || !neighbour.equals(currentNode.prev.board)) {
                    boardPQ.insert(new SearchNode(neighbour, currentNode.moves + 1, currentNode));
                }
            }
        }
    }

    public int moves() {
        return isSolvable() ? minBoardMoves : -1;
    }

    public boolean isSolvable() {
        return solutionPath != null;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    private int index = 0;
                    
                    public boolean hasNext() {
                        return index < solutionPath.length;
                    }

                    public Board next() {
                        if (!hasNext())
                            throw new ArrayIndexOutOfBoundsException("Array has exhausted");
                        return solutionPath[index++];
                    }
                };
            }
        };
    }

    // MAIN - UNIT TESTING
    public static void main(String[] args) {
        int[][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(tiles);
        Solver solver = new Solver(board);

        StdOut.println("Is the board solvable ?:  " + (solver.isSolvable() ? "Yes" : "No"));

        for(Board sol: solver.solution()) {
            StdOut.println(sol.toString());
        }

        StdOut.println("Minimum moves required to achieve goal position: " + solver.minBoardMoves);
    }
}
