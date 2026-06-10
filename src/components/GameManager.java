package components;

import config.GameConfig;
import javafx.stage.Stage;
import views.AlertView;
import views.BoardSetupView;
import views.GameView;

public class GameManager {

    public static Stage parentStage;

    public static void startGameBoardSetup(Stage parentStage) {
        GameManager.parentStage = parentStage;
        GameConfig.currentPlayerId = 1;
        new BoardSetupView(parentStage);
    }

    public static void handleBoardConfirmed(Stage stage) {
        if (GameConfig.currentPlayerId == 1) {

            String username1 = GameManager.getCurrentUsername();
            GameConfig.currentPlayerId = 2;
            String username2 = GameManager.getCurrentUsername();

            AlertView.showInfo(
                    stage,
                    "Zmiana gracza",
                    username1 + " ZAPISAŁ PLANSZE!",
                    username1 + ", odejdź od komputera.\n" + username2 + ", kliknij OK, aby rozstawić statki."
            );

            new BoardSetupView(GameManager.parentStage);
        } else {

            GameConfig.userBoard1.getChildren().forEach(node -> {
                if (node instanceof BoardCell cell) {
                    cell.setDefaultActions();
                }
            });

            GameConfig.userBoard2.getChildren().forEach(node -> {
                if (node instanceof BoardCell cell) {
                    cell.setDefaultActions();
                }
            });

            new GameView(GameManager.parentStage);
        }
    }

    public static String getCurrentUsername() {
        String name = GameConfig.currentPlayerId == 1 ? GameConfig.username1 : GameConfig.username2;
        return name.isEmpty() ? "GRACZ " + GameConfig.currentPlayerId : name;
    }

}
