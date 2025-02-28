import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

public class Board{
    private final int[][] tiles;
    private final int[] openTile;
    private final int[][] goalTile;
    private final int size;
    private Board[] neighbours;

    private class BoardIterator implements Iterator<Board> {
        int current = 0;

        public boolean hasNext() {
            return current < neighbours.length;
        }

        public Board next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException("Array has exhausted");
            }

            Board board = neighbours[current++];
            // current = (current + 1) % neighbours.length;
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
        openTile = new int[2];
        neighbours = new Board[4];

        finalBoard();        
    }

    private void exchange(Board board, int oRow, int oCol, int nRow, int nCol){
        int temp = board.tiles[oRow][oCol];
        board.tiles[oRow][oCol] = board.tiles[nRow][nCol];
        board.tiles[nRow][nCol] = temp;

        board.openTile[0] = nRow;
        board.openTile[1] = nCol;
    }

    public void finalBoard(){
            int rowCount = 0;
    
            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    if(tiles[i][j] == 0){
                        openTile[0] = i;
                        openTile[1] = j;
                    }
                    goalTile[i][j] = rowCount+j+1;
                }
                rowCount += 3;
            }
    
            goalTile[size-1][size-1] = 0;
        }

    private void createBoardNeighbours(){
        int r = openTile[0], c = openTile[1];
        Board temp = null;
        int index = 0;

        // Board comparisons
        if (c == 0){
            if (r == 0){
                temp = twin();
                exchange(temp, r, c, r+1, c);
                neighbours[index++] = temp;
                temp = twin();
                exchange(temp, r, c, r, c+1);
                neighbours[index++] = temp;
            }
            else if (r == size - 1){
                temp = twin();
                exchange(temp, r, c, r-1, c);
                neighbours[index++] = temp;
                temp = twin();
                exchange(temp, r, c, r, c+1);
                neighbours[index++] = temp;
            }
            else {
                temp = twin();
                exchange(temp, r, c, r-1, c);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r+1, c);  
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r, c+1);
                neighbours[index++] = temp;
            }
        }
        else if (c == size-1) {
            if(r == 0){
                temp = twin();
                exchange(temp, r, c, r+1, c);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r, c-1);
                neighbours[index++] = temp;
            }
            else if (r == size-1){
                temp = twin();
                exchange(temp, r, c, r-1, c);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r, c-1);
                neighbours[index++] = temp;
            }
            else {
                temp = twin();
                exchange(temp, r, c, r-1, c);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r+1, c);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r, c-1);
                neighbours[index++] = temp;
            }
        }
        else {
            if (r == 0){
                temp = twin();
                exchange(temp, r, c, r, c-1);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r, c+1);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r+1, c);
                neighbours[index++] = temp;
            }
            else if (r == size-1){
                temp = twin();
                exchange(temp, r, c, r, c-1);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r, c+1);
                neighbours[index++] = temp;

                temp = twin();
                exchange(temp, r, c, r-1, c);
                neighbours[index++] = temp;
            }
            else {
                temp = this.twin();
                exchange(temp, r, c, r-1, c);
                neighbours[index++] = temp;
                temp = null;

                temp = this.twin();
                exchange(temp, r, c, r+1, c);
                neighbours[index++] = temp;
                temp = null;

                temp = this.twin();
                exchange(temp, r, c, r, c-1);
                neighbours[index++] = temp;
                temp = null;

                temp = this.twin();
                exchange(temp, r, c, r, c+1);
                neighbours[index++] = temp;
                temp = null;
            }
        }

        temp = null;
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
        int[][] copyBoard = new int[size][size];

        for(int i=0; i<size; i++){
            copyBoard[i] = tiles[i].clone();
        }

        return new Board(copyBoard);
    }

    public Iterator<Board> neighbors(){
        createBoardNeighbours();
        return new BoardIterator();
    }

    public static void main(String[] args) {
        // Unit-testing code
        
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

        Board board = new Board(tiles);

        StdOut.println("Hamming distance: " + board.hamming());
        StdOut.println("Manhattan distance: " + board.manhattan());

        Iterator<Board> it = board.neighbors();

        StdOut.println("Current board neighbours");

        while(it.hasNext()){
            StdOut.println(it.next().toString());
            StdOut.println("--------------------------");
        }
    }

}
