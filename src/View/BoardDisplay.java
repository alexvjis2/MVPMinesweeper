package View;

import Presenter.CellState;
import Presenter.Presenter;

public interface BoardDisplay {
    void addPresenter(Presenter boardPresenter);
    void updateCell(int row, int col, CellState cellState);
    void endGame(boolean win);
}
