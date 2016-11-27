package net.battleplugins.msutton.DungeonsAhoy;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.GameInfo.Direction;

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
        this.X = zImage.getX();
        this.Y = zImage.getY();
        this.Width = zImage.getWidth();
        this.Height = zImage.getHeight();
        fdirc = Direction.EAST;
        this.zImage = zImage;
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
                    moveTowardsPlayer();

                    Thread.sleep(1000);
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
            System.out.println();
            zImage.setX(X);
            zImage.setY(Y);
        }
    };

    private void moveTowardsPlayer(){

        // They're to the east
        if(pLocation.x > X){
            X -= speed/100000;
        }else{
            X += speed/100000;
        }
        //They're to the North
        if(pLocation.y > Y){
            Y += speed/100000;
        }else{
            Y -= speed/100000;
        }
    }
}
