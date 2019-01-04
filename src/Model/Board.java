package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int mines;
    private int openedCells;
    
    private final Cell[][] cells;
    private final int[][] minesAround;
    private EndGameListener endGameListener;
    private StateChangeListener stateChangeListener;
    
    public Board(int rows, int cols, int mines) {
        this.mines = mines;
        openedCells = 0;
        cells = new Cell[rows][cols];
        minesAround = new int[rows][cols];
        
        initialize();
    }
    
    public Cell cellAt(int row, int col) {
        return cells[row][col];
    }
    
    public int getMinesAround(int row, int col) {
        return minesAround[row][col];
    }

    public void addEndGameListener(EndGameListener egl) {
        this.endGameListener = egl;
    }

    public void addStateChangeListener(StateChangeListener scl) {
        this.stateChangeListener = scl;
    }

    public int getRows() { return cells.length; }
    public int getCols() { return cells[0].length; }
    
    private void initialize() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                cells[i][j] = this.getCellImplementation(i, j);
            }
        }
        armMines();
    }
    
    private void armMines() {
        Random r = new Random();
        int minesToArm = mines;
        
        while(minesToArm > 0) {
            int row = r.nextInt(getRows());
            int col = r.nextInt(getCols());
            
            if ( !cells[row][col].isMined() ) {
                cells[row][col].addMine();
                minesToArm--;
                incrementNeighboursMineCount(row, col);
            }
        }
    }
    
    private void incrementNeighboursMineCount(int row, int col) {
        if (inBounds(row-1, col-1)) { minesAround[row-1][col-1]++; }
        if (inBounds(row-1, col)) { minesAround[row-1][col]++; }
        if (inBounds(row-1, col+1)) { minesAround[row-1][col+1]++; }
        if (inBounds(row, col-1)) { minesAround[row][col-1]++; }
        if (inBounds(row, col+1)) { minesAround[row][col+1]++; }
        if (inBounds(row+1, col-1)) { minesAround[row+1][col-1]++; }
        if (inBounds(row+1, col)) { minesAround[row+1][col]++; }
        if (inBounds(row+1, col+1)) { minesAround[row+1][col+1]++; }
    }
    
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < getRows() && col >= 0 && col < getCols();
    }
    
    protected Cell getCellImplementation(int row, int col) {
        return new Cell() {
            private boolean mined, flagged, opened;
            private List<Cell> neighbours;

            @Override
            public boolean isMined() { return mined; }

            @Override
            public boolean isOpened() { return opened; }

            @Override
            public boolean isFlagged() { return flagged; }

            @Override
            public void open() {
                if (opened || flagged) { return; }
                opened = true;
                openedCells++;
                stateChangeListener.execute(row, col);
                if (isMined()) {
                    endGameListener.execute(false);
                } else if (openedCells == (getRows() * getCols() - mines)) {
                    endGameListener.execute(true);
                } else if (getMinesAround(row, col) == 0) {
                    openNeighbours();
                }
            }

            @Override
            public void toggleFlag() {
                flagged = !flagged;
                stateChangeListener.execute(row, col);
            }

            @Override
            public void addMine() {
                mined = true;
            }
            
            private void openNeighbours() {
                for (Cell cell : getNeighbours()) { cell.open(); }
            }

            private List<Cell> getNeighbours() {
                if (neighbours == null) {
                    ArrayList<Cell> n = new ArrayList();
                    
                    if (inBounds(row-1, col-1)) { n.add(cells[row-1][col-1]); }
                    if (inBounds(row-1, col  )) { n.add(cells[row-1][col]); }
                    if (inBounds(row-1, col+1)) { n.add(cells[row-1][col+1]); }
                    if (inBounds(row  , col-1)) { n.add(cells[row][col-1]); }
                    if (inBounds(row  , col+1)) { n.add(cells[row][col+1]); }
                    if (inBounds(row+1, col-1)) { n.add(cells[row+1][col-1]); }
                    if (inBounds(row+1, col  )) { n.add(cells[row+1][col]); }
                    if (inBounds(row+1, col+1)) { n.add(cells[row+1][col+1]); }

                    neighbours = n;
                }
                return neighbours;
            }
        };
    }
}
