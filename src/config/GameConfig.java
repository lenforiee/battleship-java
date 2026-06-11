package config;

import components.BattleBoard;
import javafx.animation.Timeline;

public class GameConfig {
    public static int matrixSize = 10;
    public static boolean bonusMoveOnHit = true;
    public static String username1 = "";
    public static String username2 = "";
    public static BattleBoard userBoard1;
    public static BattleBoard userBoard2;

    public static int currentPlayerId = 1;

    public static final int TURN_TIME_LIMIT = 20;
    public static int secondsLeft = TURN_TIME_LIMIT;
    public static Timeline turnTimer = null;
}
