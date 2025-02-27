import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Board{
    private int[][] tiles;
    private final int[][] goalTile;
    private final int size;

    private class BoardIterator implements Iterator<Board> {
        Board[] neighbouringBoards;
        int current = 0;

        public boolean hasNext() {
            return current < neighbouringBoards.length;
        }

        public Board next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException("Array has exhausted");
            }

            Board board = neighbouringBoards[current];
            current = (current + 1) % neighbouringBoards.length;
            return board;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    public Board(int[][] tiles){
        this.tiles = tiles;
        size = tiles.length;

        goalTile = new int[size][size];

        int rowCount = 0;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                goalTile[i][j] = rowCount+j+1;
            }
            rowCount += 3;
        }

        goalTile[size-1][size-1] = 0;
    }

    public String toString(){
        String bluePrint = "";
        
        bluePrint += Integer.toString(size);
        bluePrint += "\n";

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                bluePrint += Integer.toString(tiles[i][j]);
                if(j != size-1) bluePrint += " ";
            }
            bluePrint += "\n";
        }

        return bluePrint;
    }

    public String goalTileToString(){
        String bluePrint = "";
        
        bluePrint += Integer.toString(size);
        bluePrint += "\n";

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                bluePrint += Integer.toString(goalTile[i][j]);
                if(j != size-1) bluePrint += " ";
            }
            bluePrint += "\n";
        }

        return bluePrint;
    }

    public int dimension(){
        return size;
    }

    public int hamming(){
        int hammingScore = 0;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(tiles[i][j] == 0) continue;
                if(tiles[i][j] != goalTile[i][j]) hammingScore++;
            }
        }

        return hammingScore;
    }

    public int manhattan(){
        int manhattanScore = 0;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                int element = tiles[i][j];

                if(element == 0) continue;

                int actualRow = (element % size == 0) ? (element/size - 1) : (element/size);
                int actualCol = (element % size == 0) ? (size - 1) : (element%size - 1);

                if(i != actualRow) 
                    manhattanScore += (actualRow > i) ? (actualRow - i) : (i - actualRow);
                if(j != actualCol)
                    manhattanScore += (actualCol > j) ? (actualCol - j) : (j - actualCol);
            }
        }

        return manhattanScore;
    }

    public boolean isGoal(){
        return tiles.equals(goalTile);   
    }

    public boolean equals(Object y){
        return tiles.equals(y);
    }

    public Board twin(){
        return new Board(tiles);
    }

    public Iterator<Board> neighbours(){
        return new BoardIterator();
    }

    public static void main(String[] args) {
        // Unit-testing code
        
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

        Board board = new Board(tiles);

        StdOut.println("Hamming distance: " + board.hamming());
        StdOut.println("Manhattan distance: " + board.manhattan());
    }

}
