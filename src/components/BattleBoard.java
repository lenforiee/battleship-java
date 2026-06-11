package components;

import javafx.scene.layout.GridPane;
import config.GameConfig;
import models.Orientation;
import models.Ship;

import java.util.ArrayList;
import java.util.List;

public class BattleBoard extends GridPane {

    private final int size = GameConfig.matrixSize;
    private final BoardCell[][] cells = new BoardCell[size][size];

    private boolean shipsVisible = true;

    public BattleBoard() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                BoardCell cell = new BoardCell(col, row);
                this.cells[col][row] = cell;
                this.add(cell, col, row);
            }
        }

        this.setStyle("-fx-border-color: #324B56; -fx-border-width: 2px; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-background-color: #18252B; -fx-padding: 0; -fx-hgap: 0; -fx-vgap: 0;");
    }

    public void setInteractionAllowed(boolean allowed) {
        this.setMouseTransparent(!allowed);
    }

    public void resetAllCellActions() {
        this.getChildren().forEach(node -> {
            if (node instanceof BoardCell cell) {
                cell.setDefaultActions();
            }
        });
    }

    public void setShipsVisible(boolean visible) {
        this.shipsVisible = visible;
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.cells[col][row].setRenderShipVisible(visible);
            }
        }
    }

    public boolean isShipsVisible() {
        return this.shipsVisible;
    }

    public BoardCell getCell(int x, int y) {
        return this.cells[x][y];
    }

    public boolean areAllShipsSunk() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                BoardCell cell = this.cells[col][row];
                if (cell.hasShip() && !cell.getShip().isSunk()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean placeShip(int startX, int startY, int shipSize, Orientation orientation) {
        if (orientation == Orientation.VERTICAL && startY + shipSize > this.size) return false;
        if (orientation == Orientation.HORIZONTAL && startX + shipSize > this.size) return false;

        List<BoardCell> targetCells = new ArrayList<>();
        Ship newShip = new Ship();
        for (int i = 0; i < shipSize; i++) {
            int currentX = orientation == Orientation.VERTICAL ? startX : startX + i;
            int currentY = orientation == Orientation.HORIZONTAL ? startY : startY + i;

            BoardCell cell = this.cells[currentX][currentY];
            if (this.isPlacementInvalid(currentX, currentY, newShip)) return false;

            targetCells.add(cell);
            cell.setShip(newShip);
            newShip.addCell(cell);
        }

        return true;
    }

    public void clearBoard() {
        this.getChildren().forEach(node -> {
            if (node instanceof BoardCell cell) {
                cell.resetCell();
            }
        });
    }

    private boolean isPlacementInvalid(int currentX, int currentY, Ship ship) {
        List<BoardCell> shipCells = ship.getCells();
        for (int x = currentX - 1; x <= currentX + 1; x++) {
            for (int y = currentY - 1; y <= currentY + 1; y++) {
                if (x >= 0 && x < this.size && y >= 0 && y < this.size && !shipCells.contains(this.cells[x][y])) {
                    if (this.cells[x][y].hasShip()) return true;
                }
            }
        }
        return false;
    }
}