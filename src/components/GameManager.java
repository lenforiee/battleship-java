package components;

import config.GameConfig;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.CellState;
import models.Ship;
import views.AlertView;
import views.BoardSetupView;
import views.GameView;

public class GameManager {

    public static Stage parentStage;
    private static GameView activeGameView = null;

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
            GameConfig.userBoard1.resetAllCellActions();
            GameConfig.userBoard2.resetAllCellActions();

            GameConfig.currentPlayerId = 1;
            activeGameView = new GameView(GameManager.parentStage);
            GameManager.startTimer();
        }
    }

    public static void handleShot(int x, int y) {
        BattleBoard enemyBoard = (GameConfig.currentPlayerId == 1) ? GameConfig.userBoard2 : GameConfig.userBoard1;
        BoardCell cell = enemyBoard.getCell(x, y);

        if (cell.isAlreadyShot()) return;

        if (cell.hasShip()) {
            Ship ship = cell.getShip();
            ship.hit();

            cell.setState(ship.isSunk() ? CellState.HIT_SHIP_SUNK : CellState.HIT_SHIP_ALIVE);
            cell.updateStyle();

            if (ship.isSunk()) {
                AlertView.showInfo(activeGameView.getStage(), "Sukces", "ZATOPIONY!", "Okręt przeciwnika idzie na dno!");

                if (enemyBoard.areAllShipsSunk()) {
                    GameManager.endGame(GameConfig.currentPlayerId == 1 ? GameConfig.username1 : GameConfig.username2);
                    return;
                }
            }
            if (GameConfig.bonusMoveOnHit) {
                GameManager.resetTimer();
                return;
            }
        } else {
            cell.markAsMissed();
        }

        GameManager.switchTurn();
    }

    private static void switchTurn() {
        GameManager.stopTimer();
        activeGameView.getStage().hide();

        int nextPlayerId = (GameConfig.currentPlayerId == 1) ? 2 : 1;
        String nextUsername = (nextPlayerId == 1) ? GameConfig.username1 : GameConfig.username2;
        if (nextUsername.isEmpty()) nextUsername = "GRACZ " + nextPlayerId;

        AlertView.showInfo(
                activeGameView.getStage(),
                "Zmiana tury",
                "Koniec ruchu!",
                "Przekaż myszkę graczowi: " + nextUsername + ".\nKliknij OK, aby kontynuować."
        );

        GameConfig.currentPlayerId = nextPlayerId;
        activeGameView.refreshView();
        activeGameView.getStage().show();
        GameManager.startTimer();
    }

    public static void startTimer() {
        GameConfig.secondsLeft = GameConfig.TURN_TIME_LIMIT;
        if (activeGameView != null) activeGameView.updateTimerText();

        GameConfig.turnTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            GameConfig.secondsLeft--;
            if (activeGameView != null) activeGameView.updateTimerText();

            if (GameConfig.secondsLeft <= 0) {
                GameManager.stopTimer();

                Platform.runLater(() -> {
                    AlertView.showInfo(activeGameView.getStage(), "Koniec czasu", "CZAS MINĄŁ!", "Spóźniłeś się! Tura przechodzi na przeciwnika.");
                    GameManager.switchTurn();
                });
            }
        }));
        GameConfig.turnTimer.setCycleCount(Timeline.INDEFINITE);
        GameConfig.turnTimer.play();
    }

    public static void stopTimer() {
        if (GameConfig.turnTimer != null) {
            GameConfig.turnTimer.stop();
        }
    }

    private static void resetTimer() {
        GameManager.stopTimer();
        GameManager.startTimer();
    }

    private static void endGame(String winnerName) {
        stopTimer();
        if (winnerName.isEmpty()) winnerName = "GRACZ " + GameConfig.currentPlayerId;
        AlertView.showInfo(activeGameView.getStage(), "Koniec gry", "KONIEC BITWY!", "Zwycięzcą zostaje: " + winnerName + "!");
        activeGameView.getStage().close();
    }

    public static String getCurrentUsername() {
        String name = GameConfig.currentPlayerId == 1 ? GameConfig.username1 : GameConfig.username2;
        return name.isEmpty() ? "GRACZ " + GameConfig.currentPlayerId : name;
    }

}
