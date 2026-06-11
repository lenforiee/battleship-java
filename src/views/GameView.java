package views;

import components.GameManager;
import config.GameConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameView extends AbstractView {

    private final Text turnLabel;
    private final Text timerLabel;
    private final VBox boardSection;
    private final Text boardTitle;

    public GameView(Stage parentStage) {
        super("Statki - Rozgrywka", 1000, 750, 10);

        this.root.setPadding(new Insets(25));
        this.root.setAlignment(Pos.CENTER);

        HBox mainLayout = new HBox(40);
        mainLayout.setAlignment(Pos.CENTER);

        VBox sidePanel = new VBox(20);
        sidePanel.setAlignment(Pos.TOP_CENTER);
        sidePanel.setPrefWidth(250);

        Text gameTitle = new Text("GRA W STATKI");
        gameTitle.setFont(FONT_MAIN_36);
        gameTitle.setFill(Color.web("#DFE9EB"));

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setStyle("-fx-background-color: #121214; -fx-padding: 15px; -fx-background-radius: 10px; -fx-border-color: #27272A; -fx-border-width: 1.5px;");

        this.turnLabel = new Text();
        this.turnLabel.setFont(FONT_MAIN_25);
        this.turnLabel.setFill(Color.web("#52B788"));

        this.timerLabel = new Text("CZAS: " + GameConfig.TURN_TIME_LIMIT + "s");
        this.timerLabel.setFont(FONT_MAIN_25);
        this.timerLabel.setFill(Color.web("#E63946"));

        infoBox.getChildren().addAll(this.turnLabel, this.timerLabel);

        Button peekBtn = this.createStyledButton(
                "PODGLĄD FLOTY",
                Color.rgb(172, 199, 200),
                "#1D2E36",
                "#63888E",
                DS_BLUE,
                DS_BLACK,
                FONT_MAIN_25
        );

        Button exitBtn = this.createStyledButton(
                "OPUŚĆ GRĘ",
                Color.rgb(230, 57, 70),
                "#1E1E24",
                "#2D2D35",
                DS_RED,
                DS_BLACK,
                FONT_MAIN_25
        );

        peekBtn.setOnMousePressed(e -> showMyBoard());
        peekBtn.setOnMouseReleased(e -> showEnemyBoard());

        exitBtn.setOnAction(e -> {
            boolean answer = AlertView.showConfirm(
                    this.stage,
                    "Opuść gre?",
                    "Opuszczenie gry",
                    "Czy napewno chcesz opuścić grę?"
            );
            if (!answer) return;

            GameManager.stopTimer();
            this.closeStage();
            parentStage.show();
        });

        sidePanel.getChildren().addAll(gameTitle, infoBox, peekBtn, exitBtn);

        this.boardSection = new VBox(15);
        this.boardSection.setAlignment(Pos.CENTER);
        this.boardTitle = new Text();
        this.boardTitle.setFont(FONT_MAIN_25);
        this.boardTitle.setFill(Color.web("#ACC7C8"));
        this.boardSection.getChildren().add(this.boardTitle);

        mainLayout.getChildren().addAll(sidePanel, this.boardSection);
        this.root.getChildren().add(mainLayout);

        this.refreshView();
        this.stage.setScene(this.scene);

        String username1 = GameManager.getCurrentUsername();
        AlertView.showInfo(
                this.stage,
                "Start bitwy",
                "Obie floty gotowe!",
                "Czas rozpocząć bitwę\n" + username1 + ", przygotuj się i kliknij OK."
        );
        this.stage.show();
    }

    public void refreshView() {
        this.turnLabel.setText(GameManager.getCurrentUsername());
        this.showEnemyBoard();
    }

    private void showEnemyBoard() {
        this.boardTitle.setText("PLANSZA PRZECIWNIKA (STRZELAJ)");
        this.boardSection.getChildren().clear();
        this.boardSection.getChildren().add(this.boardTitle);

        components.BattleBoard enemyBoard = (GameConfig.currentPlayerId == 1) ? GameConfig.userBoard2 : GameConfig.userBoard1;
        enemyBoard.setInteractionAllowed(true);
        enemyBoard.setShipsVisible(false);

        boardSection.getChildren().add(enemyBoard);
    }

    private void showMyBoard() {
        this.boardTitle.setText("TWOJA FLOTA (PODGLĄD)");
        this.boardSection.getChildren().clear();
        this.boardSection.getChildren().add(this.boardTitle);

        components.BattleBoard myBoard = (GameConfig.currentPlayerId == 1) ? GameConfig.userBoard1 : GameConfig.userBoard2;
        myBoard.setInteractionAllowed(false);
        myBoard.setShipsVisible(true);

        boardSection.getChildren().add(myBoard);
    }

    public void updateTimerText() {
        this.timerLabel.setText("CZAS: " + GameConfig.secondsLeft + "s");
    }

    public Stage getStage() {
        return this.stage;
    }
}