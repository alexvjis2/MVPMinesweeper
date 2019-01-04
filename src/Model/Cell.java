package Model;

public interface Cell {
    boolean isMined();
    boolean isOpened();
    boolean isFlagged();
    void open();
    void toggleFlag();
    void addMine();
}