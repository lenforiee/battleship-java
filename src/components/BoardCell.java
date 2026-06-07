package components;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.Orientation;
import models.Ship;

public class BoardCell extends StackPane {
    private final int x;
    private final int y;
    private boolean wasShot = false;
    private Ship parentShip = null;
    private String radiusStyle = "";
    private boolean isHovered = false;

    public BoardCell(int x, int y) {
        this.x = x;
        this.y = y;

        this.setPrefSize(50, 50);
        this.applyCurrentPaint();

        this.setOnMouseEntered(e -> {
            if (wasShot) return;
            this.isHovered = true;
            this.applyCurrentPaint();
        });

        this.setOnMouseExited(e -> {
            if (wasShot) return;
            this.isHovered = false;
            applyCurrentPaint();
        });

        this.setOnMouseClicked(e -> handleOnClick());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setShip(Ship ship) {
        this.parentShip = ship;
        this.applyCurrentPaint();
    }

    public boolean hasShip() {
        return this.parentShip != null;
    }

    public void applyCurrentPaint() {
        if (this.wasShot && this.parentShip != null) {
            String background = this.parentShip.isSunk() ? "#2E1212" : "#4A1A1A";
            String border = this.parentShip.isSunk() ? "#732626" : "#A33838";
            this.changeStyle(background, border);
            return;
        }

        String bg = this.isHovered ? "#22353E" : (parentShip != null ? "#1A2D2B" : "#18252B");
        String border = this.isHovered ? "#436475" : (parentShip != null ? "#387A7A" : "#263841");
        this.changeStyle(bg, border);
    }

    public void setCorners(Orientation orientation, boolean isFirst) {
        boolean isVertical = orientation == Orientation.VERTICAL;

        int tl = (isVertical && isFirst) || (!isVertical && isFirst) ? 10 : 0;
        int tr = (isVertical && isFirst) || (!isVertical && !isFirst) ? 10 : 0;
        int br = (isVertical && !isFirst) || (!isVertical && !isFirst) ? 10 : 0;
        int bl = (isVertical && !isFirst) || (!isVertical && isFirst) ? 10 : 0;

        this.radiusStyle = String.format("-fx-background-radius: %dpx %dpx %dpx %dpx; -fx-border-radius: %dpx %dpx %dpx %dpx;",
                tl, tr, br, bl, tl, tr, br, bl);

        this.applyCurrentPaint();
    }

    private void changeStyle(String background, String border) {
        this.setStyle("-fx-background-color: " + background + "; -fx-border-color: " + border + "; -fx-border-width: 0.5px;" + this.radiusStyle);
    }

    private void handleOnClick() {
        if (this.wasShot) return;
        this.wasShot = true;

        if (this.parentShip != null) {
            this.parentShip.hit();
        } else {
            Rectangle dot = new Rectangle(30, 30);
            dot.setFill(Color.CRIMSON);
            dot.setOpacity(0.6);
            this.getChildren().add(dot);
        }

        this.applyCurrentPaint();
    }
}