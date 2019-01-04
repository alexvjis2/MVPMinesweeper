package Presenter;

public enum Difficulty {
    EASY(9, 9, 10),
    NORMAL(13, 15, 40),
    HARD(16, 30, 99),
    EXTREME(20, 30, 120);
    
    private final int rows;
    private final int cols;
    private final int mines;

    private Difficulty(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public int rows() { return rows; }
    public int cols() { return cols; }
    public int mines() { return mines; }
}