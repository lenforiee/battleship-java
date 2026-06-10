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
    private final HBox boardsContainer;
    private final VBox playerSection;
    private final VBox enemySection;

    public GameView(Stage parentStage) {
        super("Statki - Rozgrywka", 1500, 850, 10);

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

        Button exitBtn = this.createStyledButton(
                "OPUŚĆ GRĘ",
                Color.rgb(230, 57, 70),
                "#1E1E24",
                "#2D2D35",
                DS_RED,
                DS_BLACK,
                FONT_MAIN_25
        );

        exitBtn.setOnAction(e -> {
            boolean answer = AlertView.showConfirm(
                    stage,
                    "Opuść gre?",
                    "Opuszczenie gry",
                    "Czy napewno chcesz opuścić grę?"
            );
            if (!answer) return;

            GameManager.stopTimer();
            this.closeStage();
            parentStage.show();
        });

        sidePanel.getChildren().addAll(gameTitle, infoBox, exitBtn);

        this.boardsContainer = new HBox(50);
        this.boardsContainer.setAlignment(Pos.CENTER);

        this.playerSection = new VBox(10);
        this.playerSection.setAlignment(Pos.CENTER);
        Text playerTitle = new Text("TWOJA PLANSZA");
        playerTitle.setFont(FONT_MAIN_25);
        playerTitle.setFill(Color.web("#ACC7C8"));
        this.playerSection.getChildren().add(playerTitle);

        this.enemySection = new VBox(10);
        this.enemySection.setAlignment(Pos.CENTER);
        Text enemyTitle = new Text("PLANSZA PRZECIWNIKA");
        enemyTitle.setFont(FONT_MAIN_25);
        enemyTitle.setFill(Color.web("#ACC7C8"));
        this.enemySection.getChildren().add(enemyTitle);

        this.boardsContainer.getChildren().addAll(playerSection, enemySection);
        mainLayout.getChildren().addAll(sidePanel, this.boardsContainer);
        this.root.getChildren().add(mainLayout);

        this.refreshView();

        this.stage.setScene(this.scene);

        String username1 = GameManager.getCurrentUsername();
        AlertView.showInfo(
                stage,
                "Start bitwy",
                "Obie floty gotowe!",
                "Czas rozpocząć bitwę\n" + username1 + ", przygotuj się i kliknij OK."
        );
        this.stage.show();
    }

    public void refreshView() {
        this.turnLabel.setText(GameManager.getCurrentUsername());

        this.playerSection.getChildren().remove(1, this.playerSection.getChildren().size());
        this.enemySection.getChildren().remove(1, this.enemySection.getChildren().size());

        components.BattleBoard myBoard = (GameConfig.currentPlayerId == 1) ? GameConfig.userBoard1 : GameConfig.userBoard2;
        components.BattleBoard enemyBoard = (GameConfig.currentPlayerId == 1) ? GameConfig.userBoard2 : GameConfig.userBoard1;

        myBoard.setInteractionAllowed(false);
        enemyBoard.setInteractionAllowed(true);

        this.playerSection.getChildren().add(myBoard);
        this.enemySection.getChildren().add(enemyBoard);
    }

    public void updateTimerText() {
        timerLabel.setText("CZAS: " + GameConfig.secondsLeft + "s");
    }

    public Stage getStage() {
        return this.stage;
    }
}