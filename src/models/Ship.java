package models;

import components.BoardCell;
import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final List<BoardCell> cells = new ArrayList<>();
    private final int size;
    private int hits = 0;

    public Ship(int size) {
        this.size = size;
    }

    public void addCell(BoardCell cell) {
        this.cells.add(cell);
    }

    public void hit() {
        this.hits++;
        if (!this.isSunk()) return;
        for (BoardCell cell : this.cells) {
            cell.applyCurrentPaint();
        }
    }

    public boolean isSunk() {
        return this.hits >= this.size;
    }

    public List<BoardCell> getCells() {
        return this.cells;
    }
}
