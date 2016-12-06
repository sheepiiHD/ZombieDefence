package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Runnables;

import android.graphics.Rect;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GameStatus;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.Zombie;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;

/**
 * Created by Matt Sutton on 11/27/2016.
 */

public class CollisionChecker{

    ZombieCollection zc;
    Player p;

    public CollisionChecker(ZombieCollection zc, Player p){
        this.zc = zc;
        this.p = p;
    }
    public void initiate(){
        Thread zombieCollision = new Thread(checkZombieCollision);
        zombieCollision.start();
    }
    private Runnable checkZombieCollision = new Runnable(){
        @Override
        public void run(){
            while(GlobalVariables.running) {
                try {
                    while (zc.hasNext()) {
                        /** Zombie **/
                        Zombie z = zc.next();
                        Rect zRec = new Rect();
                        z.getZombieImage().getDrawingRect(zRec);

                        /** Player **/
                        Rect pRec = new Rect();
                        p.getPlayerImage().getDrawingRect(pRec);

                        if (Rect.intersects(zRec, pRec)) {
                            handleCollision();
                        }
                    }
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    GlobalVariables.gameStatus = GameStatus.ERROR;
                }
            }
        }
    };


    private void handleCollision(){
        GlobalVariables.gameStatus = GameStatus.GAME_OVER;
    }


}
