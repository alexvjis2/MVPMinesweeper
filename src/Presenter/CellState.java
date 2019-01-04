package Presenter;

/**
 * Simple representation of cell states.
 */
public enum CellState { 
    /** Cell closed without flag. */
    CLOSED,
    
    /** Cell closed with flag. */
    FLAGGED,
    
    /** Cell opened and mined. */
    MINE,
    
    /** Cell opened (no mine). */
    NOMINE
}
