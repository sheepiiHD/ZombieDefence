package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo;

import android.graphics.Rect;

import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.Zombie;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;

import java.util.Iterator;

/**
 * Created by Matt Sutton on 11/27/2016.
 */

public class CollisionChecker{

    ZombieCollection zc;
    Player p;
    public CollisionChecker(ZombieCollection zc, Player p){
        this.zc = zc;
        this.p = p;


        Thread zombieCollision = new Thread(checkZombieCollision);
        zombieCollision.start();
    }

    public Runnable checkZombieCollision = new Runnable(){
        @Override
        public void run(){
            while(true) {
                for(Iterator<Zombie> i = zc.getZombies; i.hasNext(); ){
                    Zombie z = i.next();

                    z.getZombieImage();
                    Rect player  = new Rect();
                }
            }
        }
    };


}
