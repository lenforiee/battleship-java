package views;

import components.BattleBoard;
import components.BoardCell;
import components.GameManager;
import config.GameConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Orientation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BoardSetupView extends AbstractView {

    private int selectedShipSize = -1;
    private Orientation currentOrientation = Orientation.HORIZONTAL;
    private Button selectedShipButton = null;

    private final Map<Integer, Integer> shipCounts = new HashMap<>();
    private final Map<Integer, Label> shipLabels = new HashMap<>();
    private final Map<Integer, Button> shipButtons = new HashMap<>();

    private final BattleBoard battleBoard;

    public BoardSetupView(Stage parentStage) {
        super("Statki - Ustawienie statków", 1000, 800, 25);
        if (GameConfig.currentPlayerId == 1) {
            GameConfig.userBoard1 = new BattleBoard();
            this.battleBoard = GameConfig.userBoard1;
        } else {
            GameConfig.userBoard2 = new BattleBoard();
            this.battleBoard = GameConfig.userBoard2;
        }

        this.root.setAlignment(Pos.TOP_CENTER);
        this.root.setSpacing(20);

        this.shipCounts.put(5, 1);
        this.shipCounts.put(4, 2);
        this.shipCounts.put(3, 4);
        this.shipCounts.put(2, 2);
        this.shipCounts.put(1, 1);

        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("USTAWIENIE STATKÓW - " + GameManager.getCurrentUsername());
        titleLabel.setFont(FONT_MAIN_36);
        titleLabel.setStyle("-fx-text-fill: #E2E8F0; -fx-letter-spacing: 2px;");
        titleLabel.setEffect(DS_BLACK);

        Label subtitleLabel = new Label("Zatwierdź flotę, aby rozpocząć grę");
        subtitleLabel.setFont(FONT_MAIN_16);
        subtitleLabel.setStyle("-fx-text-fill: #64748B;");

        headerBox.getChildren().addAll(titleLabel, subtitleLabel);

        HBox mainContent = new HBox(40);
        mainContent.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        VBox leftPanel = new VBox(15);
        leftPanel.setPrefWidth(420);

        VBox shipsSection = this.createSectionBox("NARZĘDZIA PLANSZY");
        HBox boardSectionButtons = new HBox(15);
        boardSectionButtons.setAlignment(Pos.CENTER);

        Button randomBtn = this.createStyledButton(
                "LOSOWY UKŁAD",
                Color.rgb(172, 199, 200),
                "#314644",
                "#8CB59D",
                DS_GREEN,
                DS_BLACK,
                FONT_MAIN_16
        );
        randomBtn.setOnAction(e -> this.handleRandomPlacement());

        Button clearBtn = this.createStyledButton(
                "WYCZYŚĆ PLANSZE",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#9F7A79",
                DS_RED,
                DS_BLACK,
                FONT_MAIN_16
        );
        clearBtn.setOnAction(e -> this.handleClearBoard());

        boardSectionButtons.getChildren().addAll(randomBtn, clearBtn);
        ((VBox) shipsSection.getChildren().get(1)).getChildren().add(boardSectionButtons);

        VBox availableShipsSection = this.createSectionBox("DOSTĘPNE STATKI");
        VBox shipsList = new VBox(10);
        shipsList.setPadding(new Insets(5, 5, 5, 0));

        shipsList.getChildren().addAll(
                this.createShipRow(5, "Lotniskowiec"),
                this.createShipRow(4, "Pancernik"),
                this.createShipRow(3, "Krążownik"),
                this.createShipRow(2, "Niszczyciel"),
                this.createShipRow(1, "Kuter podwodny")
        );

        ScrollPane shipsScrollPane = new ScrollPane(shipsList);
        shipsScrollPane.setFitToWidth(true);
        shipsScrollPane.setPrefHeight(190);
        shipsScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-viewport-background: transparent; -fx-hbar-policy: never; -fx-vbar-policy: as-needed;");
        ((VBox) availableShipsSection.getChildren().get(1)).getChildren().add(shipsScrollPane);

        VBox instructionsSection = this.createSectionBox("INSTRUKCJE");
        VBox instructionsContent = new VBox(12);

        Label instructionsText = new Label("1. Wybierz statek.\n2. Kliknij na planszy, aby go postawić.\n3. Użyj przycisków orientacji do zmiany kierunku.");
        instructionsText.setFont(FONT_MAIN_16);
        instructionsText.setStyle("-fx-text-fill: #94A3B8; -fx-line-spacing: 4px;");

        HBox orientationBox = new HBox(10);
        orientationBox.setAlignment(Pos.CENTER_LEFT);
        Label orientationLabel = new Label("ORIENTACJA:");
        orientationLabel.setFont(FONT_MAIN_16);
        orientationLabel.setStyle("-fx-text-fill: #64748B; -fx-font-weight: bold;");

        HBox toggleBox = new HBox(0);
        toggleBox.setStyle("-fx-background-color: #1E293B; -fx-background-radius: 5px; -fx-padding: 2px;");

        Button btnPionowa = new Button("PIONOWA");
        Button btnPozioma = new Button("POZIOMA");

        btnPionowa.setFont(FONT_MAIN_16);
        btnPionowa.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-padding: 4px 10px;");
        btnPionowa.setOnAction(e -> {
            this.currentOrientation = Orientation.VERTICAL;
            btnPionowa.setStyle("-fx-background-color: #334155; -fx-text-fill: #94A3B8; -fx-padding: 4px 10px; -fx-background-radius: 4px;");
            btnPozioma.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-padding: 4px 10px;");
        });

        btnPozioma.setFont(FONT_MAIN_16);
        btnPozioma.setStyle("-fx-background-color: #334155; -fx-text-fill: #94A3B8; -fx-padding: 4px 10px; -fx-background-radius: 4px;");
        btnPozioma.setOnAction(e -> {
            this.currentOrientation = Orientation.HORIZONTAL;
            btnPozioma.setStyle("-fx-background-color: #334155; -fx-text-fill: #94A3B8; -fx-padding: 4px 10px; -fx-background-radius: 4px;");
            btnPionowa.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-padding: 4px 10px;");
        });

        toggleBox.getChildren().addAll(btnPionowa, new Label(" / "), btnPozioma);
        orientationBox.getChildren().addAll(orientationLabel, toggleBox);

        instructionsContent.getChildren().addAll(instructionsText, orientationBox);
        ((VBox) instructionsSection.getChildren().get(1)).getChildren().add(instructionsContent);

        leftPanel.getChildren().addAll(shipsSection, availableShipsSection, instructionsSection);

        VBox rightPanel = new VBox(15);
        rightPanel.setAlignment(Pos.TOP_CENTER);

        Label boardTitle = new Label("TWOJA PLANSZA (UKŁAD FLOTY)");
        boardTitle.setFont(FONT_MAIN_16);
        boardTitle.setStyle("-fx-text-fill: #64748B; -fx-font-weight: bold; -fx-letter-spacing: 1px;");

        this.battleBoard.getChildren().forEach(node -> {
            if (node instanceof BoardCell cell) {
                cell.setOnMouseClicked(e -> this.handleBoardClick(cell));
            }
        });

        rightPanel.getChildren().addAll(boardTitle, this.battleBoard);
        mainContent.getChildren().addAll(leftPanel, rightPanel);

        HBox footerBox = new HBox(40);
        footerBox.setAlignment(Pos.CENTER);

        Button confirmBtn = this.createStyledButton(
                "ZATWIERDŹ FLOTĘ",
                Color.rgb(172, 199, 200),
                "#314644",
                "#8CB59D",
                DS_GREEN,
                DS_BLACK,
                FONT_MAIN_16
        );
        confirmBtn.setOnAction(e -> this.handleConfirmBoard());

        Button cancelBtn = this.createStyledButton(
                "ANULUJ I WRÓĆ",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#9F7A79",
                DS_RED,
                DS_BLACK,
                FONT_MAIN_16
        );
        cancelBtn.setOnAction(e -> {
            this.closeStage();
            parentStage.show();
        });

        footerBox.getChildren().addAll(confirmBtn, cancelBtn);
        this.root.getChildren().addAll(headerBox, mainContent, footerBox);
        this.stage.show();
    }

    private void handleClearBoard() {

        this.battleBoard.clearBoard();
        this.shipCounts.put(5, 1);
        this.shipCounts.put(4, 2);
        this.shipCounts.put(3, 4);
        this.shipCounts.put(2, 2);
        this.shipCounts.put(1, 1);

        this.shipCounts.forEach((size, count) -> {
            Button btn = this.shipButtons.get(size);
            btn.setDisable(false);
            btn.setText("[UMIEŚĆ]");
            Label lbl = this.shipLabels.get(size);
            lbl.setText(lbl.getText().replaceAll("\\(x\\d+\\)", "(x" + count + ")"));
        });

        this.selectedShipSize = -1;
    }

    private void handleBoardClick(BoardCell cell) {
        if (this.selectedShipSize == -1) return;

        int remaining = this.shipCounts.get(this.selectedShipSize);
        if (remaining <= 0) return;

        boolean success = this.battleBoard.placeShip(cell.getX(), cell.getY(), this.selectedShipSize, this.currentOrientation);

        if (success) {
            remaining--;
            this.shipCounts.put(this.selectedShipSize, remaining);

            Label label = this.shipLabels.get(this.selectedShipSize);
            label.setText(label.getText().replaceAll("\\(x\\d+\\)", "(x" + remaining + ")"));

            if (remaining == 0) {
                Button btn = this.shipButtons.get(this.selectedShipSize);
                btn.setDisable(true);
                btn.setText("[ZAKOŃCZONO]");
                this.selectedShipSize = -1;
                if (this.selectedShipButton != null) {
                    this.selectedShipButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-weight: bold; -fx-padding: 2px 5px;");
                }
            }
        }
    }

    private void handleRandomPlacement() {
        this.handleClearBoard();
        Random random = new Random();
        int[] sizesToPlace = {5, 4, 4, 3, 3, 3, 3, 2, 2, 1};

        for (int size : sizesToPlace) {
            boolean placed = false;
            int attempts = 0;

            while (!placed && attempts < 200) {
                int x = random.nextInt(GameConfig.matrixSize);
                int y = random.nextInt(GameConfig.matrixSize);
                Orientation orientation = random.nextBoolean() ? Orientation.VERTICAL : Orientation.HORIZONTAL;

                placed = this.battleBoard.placeShip(x, y, size, orientation);
                attempts++;
            }
        }

        this.shipCounts.keySet().forEach(size -> {
            this.shipCounts.put(size, 0);
            Button btn = this.shipButtons.get(size);
            btn.setDisable(true);
            btn.setText("[ZAKOŃCZONO]");
            Label lbl = this.shipLabels.get(size);
            lbl.setText(lbl.getText().replaceAll("\\(x\\d+\\)", "(x0)"));
        });
        this.selectedShipSize = -1;
    }

    private void handleConfirmBoard() {
        boolean boardComplete = this.shipCounts.values().stream().allMatch(count -> count == 0);
        if (!boardComplete) {
            AlertView.showInfo(this.stage, "Statki - Błąd konfiguracji", "Flota niekompletna!", "Musisz rozstawić wszystkie okręty na planszy przed zatwierdzeniem floty!");
            return;
        }

        this.closeStage();
        GameManager.handleBoardConfirmed(this.stage);
    }

    private VBox createSectionBox(String titleText) {
        VBox sectionBox = new VBox(6);
        sectionBox.setAlignment(Pos.TOP_LEFT);

        Label sectionLabel = new Label(titleText);
        sectionLabel.setFont(FONT_MAIN_16);
        sectionLabel.setStyle("-fx-text-fill: #64748B; -fx-font-weight: bold; -fx-letter-spacing: 1px;");

        VBox contentBox = new VBox();
        contentBox.setStyle("-fx-border-color: #27272A; -fx-border-width: 1.5px; -fx-border-radius: 8px; -fx-background-color: #121214; -fx-background-radius: 8px; -fx-padding: 12px;");

        sectionBox.getChildren().addAll(sectionLabel, contentBox);
        return sectionBox;
    }

    private HBox createShipRow(int size, String description) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);

        HBox shipPreview = new HBox(2);
        shipPreview.setAlignment(Pos.CENTER_LEFT);
        shipPreview.setPrefWidth(90);
        for (int i = 0; i < size; i++) {
            Rectangle segment = new Rectangle(14, 14);
            segment.setFill(Color.web("#1A2D2B"));
            segment.setStroke(Color.web("#387A7A"));
            segment.setStrokeWidth(1);
            segment.setArcWidth(4);
            segment.setArcHeight(4);
            shipPreview.getChildren().add(segment);
        }

        int count = this.shipCounts.get(size);
        Label descLabel = new Label(size + " - " + description + " (x" + count + ")");
        descLabel.setFont(FONT_MAIN_16);
        descLabel.setStyle("-fx-text-fill: #94A3B8;");
        this.shipLabels.put(size, descLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnPlace = new Button("[UMIEŚĆ]");
        btnPlace.setFont(FONT_MAIN_16);
        btnPlace.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-weight: bold; -fx-padding: 2px 5px;");

        btnPlace.setOnMouseEntered(e -> {
            btnPlace.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-weight: bold; -fx-padding: 2px 5px;");
        });

        btnPlace.setOnAction(e -> {
            if (this.shipCounts.get(size) <= 0) return;

            if (this.selectedShipButton != null) {
                this.selectedShipButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-weight: bold; -fx-padding: 2px 5px;");
            }

            this.selectedShipSize = size;
            this.selectedShipButton = btnPlace;
            btnPlace.setStyle("-fx-background-color: transparent; -fx-text-fill: #52B788; -fx-font-weight: bold; -fx-padding: 2px 5px;");
        });

        this.shipButtons.put(size, btnPlace);
        row.getChildren().addAll(shipPreview, descLabel, spacer, btnPlace);
        return row;
    }
}