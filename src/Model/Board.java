package Model;

public interface Board {
    Cell cellAt(int row, int col);
    int getRows();
    int getCols();
    int getMinesAround(int row, int col);
    void addEndGamListener(EndGameListener endGameListener);
    void addStateChangeListener(StateChangeListener stateChangeListener);
}
