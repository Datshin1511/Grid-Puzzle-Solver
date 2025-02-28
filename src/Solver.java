import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    public int minBoardMoves;
    private MinPQ<Board> boardPossibilites;

    public Solver(Board initial){
        // TODO: Complete the function
        boardPossibilites.insert(initial);
        Iterator<Board> it = initial.neighbors();

        while(it.hasNext()){
            it.next();
        }
        minBoardMoves = 0;
    }

    public boolean isSolvable(){
        // TODO: Complete the function
        return false;
    }

    public int moves(){
        return minBoardMoves;
    }

    public Iterable<Board> solution(){
        // TODO: Complete the function
        return null;
    }

    public static void main(String[] args) {
        // TODO: Unit testing
    }

}
