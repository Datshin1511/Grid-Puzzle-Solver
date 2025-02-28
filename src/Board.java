import java.util.Arrays;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private final int size;
    private final int zeroRow;
    private final int zeroCol;
    private final int hammingScore;
    private final int manhattanScore;

    // PRIVATE FUNCTIONS
    private int[][] copyTiles() {
        int[][] copyArray = new int[size][size];
        for(int i=0; i<size; i++)
            copyArray[i] = Arrays.copyOf(tiles[i], size);
         return copyArray;
    }

    private void exchangeBlankTile(Board board, int nR, int nC) {
        int temp = board.tiles[zeroRow][zeroCol];
        board.tiles[zeroRow][zeroCol] = board.tiles[nR][nC];
        board.tiles[nR][nC] = temp;
    }
    
    // PUBLIC FUNCTIONS
    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = new int[size][size];
        
        int manhattan = 0, hamming = 0, zeroR = -1, zeroC = -1;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                this.tiles[i][j] = tiles[i][j];

                if(tiles[i][j] == 0) { zeroR = i; zeroC = j; }
                else {
                    int correctRow = (tiles[i][j] - 1) / size;
                    int correctCol = (tiles[i][j] - 1) % size;

                    if(tiles[i][j] != (i * size + j + 1)) hamming++;
                    manhattan = Math.abs(i - correctRow) + Math.abs(j - correctCol);
                }
            }
        }

        this.manhattanScore = manhattan;
        this.hammingScore = hamming;
        this.zeroRow = zeroR;
        this.zeroCol = zeroC;

    }

    public String toString() {
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

    public int dimension() {
        return size;
    }

    public int hamming() {
        return hammingScore;
    }

    public int manhattan() {
         return manhattanScore;
    }

    public boolean isGoal() {
        return hammingScore == 0 || manhattanScore == 0;   
    }

    public boolean equals(Object y) {
        return tiles.equals(y);
    }

    public Board twin() {
        return new Board(copyTiles());
    }

    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    private final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                    private final int max = directions.length;
                    private int index = 0;

                    public boolean hasNext() {
                        while (index < max) {
                            int nRow = zeroRow + directions[index][0];
                            int nCol = zeroCol + directions[index][1];

                            if(nRow >= 0 && nRow < size && nCol >= 0 && nCol < size) return true;
                            index++;
                        }
                        return false;
                    }

                    public Board next() {
                        if(!hasNext()){
                            throw new ArrayIndexOutOfBoundsException("Array has exhausted");
                        }

                        int nRow = zeroRow + directions[index][0];
                        int nCol = zeroRow + directions[index][1];

                        Board tempBoard = twin();
                        exchangeBlankTile(tempBoard, nRow, nCol);
                        index++;

                        return tempBoard;

                    }
    
                };
            }
            
        };
    }

    // MAIN - UNIT TESTING
    public static void main(String[] args) {        
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

        Board board = new Board(tiles);

        StdOut.println("Hamming distance: " + board.hamming());
        StdOut.println("Manhattan distance: " + board.manhattan());

        StdOut.println("--------------------------");
        StdOut.println("Current Board: ");
        StdOut.println("--------------------------");
        StdOut.println(board.toString());

        Iterator<Board> it = board.neighbors().iterator();

        StdOut.println("--------------------------");
        StdOut.println("Neighbours");
        StdOut.println("--------------------------");

        while(it.hasNext()){
            StdOut.println(it.next().toString());
            StdOut.println();
        }
    }

}
