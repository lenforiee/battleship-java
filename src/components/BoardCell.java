package components;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.CellState;
import models.Ship;

public class BoardCell extends StackPane {
    private final int x;
    private final int y;

    private Ship parentShip = null;
    private boolean isHovered = false;
    private CellState state = CellState.EMPTY;

    private boolean renderShipVisible = true;

    public BoardCell(int x, int y) {
        this.x = x;
        this.y = y;

        this.setPrefSize(50, 50);
        this.updateStyle();
        this.setDefaultActions();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public CellState getState() {
        return this.state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean isAlreadyShot() {
        return this.state == CellState.MISSED || this.state == CellState.HIT_SHIP_ALIVE || this.state == CellState.HIT_SHIP_SUNK;
    }

    public models.Ship getShip() {
        return this.parentShip;
    }

    public boolean hasShip() {
        return this.parentShip != null;
    }

    private void changeStyle(String background, String border) {
        this.setStyle("-fx-background-color: " + background + "; -fx-border-color: " + border + "; -fx-border-width: 0.5px;");
    }

    public void setRenderShipVisible(boolean visible) {
        this.renderShipVisible = visible;
        this.updateStyle();
    }

    public void setDefaultActions() {
        this.setOnMouseEntered(e -> {
            if (this.isAlreadyShot()) return;
            this.isHovered = true;
            this.updateStyle();
        });

        this.setOnMouseExited(e -> {
            if (this.isAlreadyShot()) return;
            this.isHovered = false;
            this.updateStyle();
        });

        this.setOnMouseClicked(e -> {
            if (config.GameConfig.turnTimer == null) return;
            GameManager.handleShot(this.x, this.y); // wywołanie logiki menedźera gry.
        });
    }

    public void resetCell() {
        this.parentShip = null;
        this.state = CellState.EMPTY;
        this.isHovered = false;
        this.getChildren().clear();
        this.updateStyle();
    }

    public void setShip(Ship ship) {
        this.parentShip = ship;
        this.state = (ship != null) ? CellState.SHIP_HIDDEN : CellState.EMPTY;
        this.updateStyle();
    }

    public void updateStyle() {
        String background;
        String border;

        switch (this.state) {
            case HIT_SHIP_SUNK -> {
                background = "#2E1212";
                border = "#732626";
            }
            case HIT_SHIP_ALIVE -> {
                background = "#4A1A1A";
                border = "#A33838";
            }
            case MISSED -> {
                background = "#18252B";
                border = "#263841";
            }
            case SHIP_HIDDEN -> {
                if (this.renderShipVisible) {
                    background = this.isHovered ? "#22353E" : "#1A2D2B";
                    border = this.isHovered ? "#436475" : "#387A7A";
                } else {
                    background = this.isHovered ? "#22353E" : "#18252B";
                    border = this.isHovered ? "#436475" : "#263841";
                }
            }
            default -> {
                background = this.isHovered ? "#22353E" : "#18252B";
                border = this.isHovered ? "#436475" : "#263841";
            }
        }
        this.changeStyle(background, border);
    }

    public void markAsMissed() {
        this.state = CellState.MISSED;
        Rectangle dot = new Rectangle(30, 30);
        dot.setFill(Color.CRIMSON);
        dot.setOpacity(0.6);
        this.getChildren().add(dot);
        this.updateStyle();
    }
}