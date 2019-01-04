package Presenter;

import Model.Board;
import Model.Cell;
import View.BoardDisplay;
import View.StartMenuDisplay;
import java.util.HashSet;
import java.util.Set;

/**
 * Presenter class
 * TODO consider divide by 2 (BoardPresenter and StartMenuPresenter)
 */
public class Presenter {
    private Set<BoardDisplay> boardDisplays;
    private Set<StartMenuDisplay> startMenuDisplays;
    
    private Board board;
    
    public Presenter() {
        boardDisplays = new HashSet();
        startMenuDisplays = new HashSet();
    }

    public void addBoardDisplay(BoardDisplay bd) {
        boardDisplays.add(bd);
    }
    
    public void addStartMenuDisplay(StartMenuDisplay smd) {
        startMenuDisplays.add(smd);
    }
    
    public void openCell(int row, int col) {
        board.cellAt(row, col).open();
    }
    public void toggleFlag(int row, int col) {
        board.cellAt(row, col).toggleFlag();
    }
    
    public int getMinesAround(int row, int col) {
        return board.getMinesAround(row, col);
    }
    
    public int getRows() { return board.getRows(); }
    public int getCols() { return board.getCols(); }
    
    public void backToStartMenu(StartMenuDisplay smd) { 
        boardDisplays.clear();
        smd.addPresenter(this);
    }

    
    public void newGame (Difficulty diff, BoardDisplay sbd) {
        board = new Board(diff.rows(), diff.cols(), diff.mines());
        
        board.addStateChangeListener((row, col) -> {
            for (BoardDisplay boardDisplay : boardDisplays) {
                Cell cell = board.cellAt(row, col);
                boardDisplay.updateCell(row, col, getCellState(cell));
            }
        });
        
        board.addEndGameListener(win -> {
            for (BoardDisplay boardDisplay : boardDisplays) {
                boardDisplay.endGame(win);
            }
        });
        
        startMenuDisplays.clear();
        sbd.addPresenter(this);
    }
    
    static CellState getCellState (Cell cell) {
        if ( cell.isOpened() ) {
            return cell.isMined() ? CellState.MINE : CellState.NOMINE; 
        } else {
            return cell.isFlagged() ? CellState.FLAGGED : CellState.CLOSED;
        }
    }
}
