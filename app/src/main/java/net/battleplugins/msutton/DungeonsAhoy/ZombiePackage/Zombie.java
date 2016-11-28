package net.battleplugins.msutton.DungeonsAhoy.ZombiePackage;

import android.graphics.Point;
import android.os.Handler;
import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.GameInfo.Direction;
import net.battleplugins.msutton.DungeonsAhoy.GlobalVariable;

/**
 * Created by Matt Sutton on 11/27/2016.
 */

public class Zombie{

    float X, Y;
    int Width, Height;
    Direction fdirc;
    ImageView zImage;
    Point pLocation;

    boolean dead;

    final float speed;


    public Zombie(ImageView zImage, Point pLocation, float speed){
        this.zImage = zImage;
        this.X = zImage.getX()-100;
        this.Y = zImage.getY();
        this.Width = zImage.getWidth();
        this.Height = zImage.getHeight();
        fdirc = Direction.EAST;

        this.dead = false;
        this.pLocation = pLocation;

        this.speed = speed;

        Thread thread = new Thread(this.startZombieChase);
        thread.start();
    }

    private Runnable startZombieChase = new Runnable() {
        @Override
        public void run() {
            try {
                while(!dead) {
                    Point p = new Point(GlobalVariable.playerX, GlobalVariable.playerY);
                    moveTowardsPlayer(p);

                    Thread.sleep(10);
                    updateZombie.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private Handler updateZombie = new Handler() {
        public void handleMessage(android.os.Message msg) {

            /** Because the zombie should always be on top! **/
            zImage.bringToFront();
            zImage.setX(X);
            zImage.setY(Y);

        }
    };

    private void moveTowardsPlayer(Point p){
        int compareX = p.x - (int)X;
        int compareY = p.y - (int)Y;
        l("x = " + compareX);
        l("y = " + compareY);


        // Y is closer, so we're moving horizontally.
        if(Math.abs(compareX) < Math.abs(compareY)){
            //Moving North
            if(p.y > Y){
                Y+=1;
            }
            //Moving South
            else if(p.y < Y){
                Y-=1;
            }
        }
        // X is closer, so we're moving vertically.
        else{
            //Moving East
            if(p.x > X){
                X+=1;
            }
            //Moving West
            else if(p.x < X){
                X-=1;
            }

        }
    }
    public void l(Object string){
        System.out.println("Log - " + string);
    }
}
