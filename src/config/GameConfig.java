package config;

import components.BattleBoard;

public class GameConfig {
    public static int matrixSize = 10;
    public static boolean bonusMoveOnHit = true;
    public static String username1 = "";
    public static String username2 = "";
    public static BattleBoard userBoard1;
    public static BattleBoard userBoard2;

    public static int currentPlayerId = 1;
}
