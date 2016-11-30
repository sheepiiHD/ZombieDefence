package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables;

/**
 * Created by mts01060 on 11/30/2016.
 */

public class GameStatus {

    public static final GameStatus GAME_OVER = new GameStatus(0);
    public static final GameStatus RUNNING = new GameStatus(0);
    public static final GameStatus PAUSED = new GameStatus(2);
    public static final GameStatus ERROR = new GameStatus(3);

    public GameStatus(int i ){

    }
}
