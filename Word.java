package test;


import java.util.Arrays;

public class Word {

    private final Tile[] tile;
    private final int row;
    private final int col;
    private final boolean vertical;

    Word(Tile[] tiles, int row, int col, boolean vertical)
    {
        this.tile = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;

    }

    public Tile[] getTile() {
        return tile;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false; // Remove "Word"
        Word word = (Word) o; // Declare and cast the variable
        if (getRow() != word.getRow()) return false;
        if (getCol() != word.getCol()) return false;
        if (isVertical() != word.isVertical()) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getTile(), word.getTile());
    }
}
