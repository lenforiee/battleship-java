package models;

import components.BoardCell;
import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final List<BoardCell> cells = new ArrayList<>();
    private int hits = 0;

    public Ship() {
    }

    public void addCell(BoardCell cell) {
        this.cells.add(cell);
    }

    public void hit() {
        this.hits++;
        if (!this.isSunk()) return;
        for (BoardCell cell : this.cells) {
            cell.setState(CellState.HIT_SHIP_SUNK);
            cell.updateStyle();
        }
    }

    public boolean isSunk() {
        if (this.cells.isEmpty()) return false;
        return this.hits >= this.cells.size();
    }

    public List<BoardCell> getCells() {
        return this.cells;
    }
}
