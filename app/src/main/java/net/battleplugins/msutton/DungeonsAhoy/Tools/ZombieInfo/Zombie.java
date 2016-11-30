package net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;

/**
 * Created by Matt Sutton on 11/27/2016.
 */

public class Zombie{

    float X, Y;
    int Width, Height;
    Direction fdirc;
    ImageView zImage;
    Player player;

    boolean dead;



    public Zombie(ImageView zImage, Player player){
        this.zImage = zImage;
        this.X = zImage.getX()-100;
        this.Y = zImage.getY();
        this.Width = zImage.getWidth();
        this.Height = zImage.getHeight();
        this.fdirc = Direction.EAST;
        this.player = player;
        this.dead = false;

        Thread thread = new Thread(this.startZombieChase);
        thread.start();
    }

    private Runnable startZombieChase = new Runnable() {
        @Override
        public void run() {
            try {
                while(!dead) {
                    moveTowardsPlayer((int)player.x, (int)player.y);

                    Thread.sleep(10);
                    updateZombie.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private Handler updateZombie = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {

            /** Because the zombie should always be on top! **/
            zImage.bringToFront();
            zImage.setX(X);
            zImage.setY(Y);

        }
    };

    private void moveTowardsPlayer(int player_x, int player_y){
        int compareX = player_x - (int)X;
        int compareY = player_y - (int)Y;


        // Y is closer, so we're moving horizontally.
        if(Math.abs(compareX) < Math.abs(compareY)){
            //Moving North
            if(player_y > Y){
                Y+=1;
            }
            //Moving South
            else if(player_y < Y){
                Y-=1;
            }
        }
        // X is closer, so we're moving vertically.
        else{
            //Moving East
            if(player_x > X){
                X+=1;
            }
            //Moving West
            else if(player_x < X){
                X-=1;
            }

        }
    }
    public void l(Object string){
        System.out.println("Log - " + string);
    }
}