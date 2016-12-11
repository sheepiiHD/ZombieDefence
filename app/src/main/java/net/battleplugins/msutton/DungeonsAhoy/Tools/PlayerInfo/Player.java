package net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Runnables.CollisionChecker;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;

/**
 * Created by mts01060 on 11/30/2016.
 */

public class Player {

    protected Direction dirc;
    protected Direction fdirc;
    protected float a = 0;
    protected float x, y;
    protected Bitmap playerimage;
    protected ImageView pImage;
    protected float velocity;
    protected CollisionChecker cc;
    protected BulletCollection bc;
    protected Context c;
    protected Activity ac;


    public Player(ImageView pImage, float x, float y, Bitmap p) {
        this.dirc = Direction.EAST;
        this.fdirc = Direction.EAST;
        this.x = x;
        this.y = y;
        this.playerimage = p;
        this.pImage = pImage;
        this.cc = new CollisionChecker(this);
        this.bc = new BulletCollection();
        //this.c = pImage.getContext();
        //this.ac = (Activity)c;
        //bc.initiate(this, c, ac.findViewById(R.id.linearLayout));

        velocity = 1;

        /** Threading **/
        Thread movementThread = new Thread(calculateMovement);
        movementThread.start();
    }

    /**
     *  ░███░░█░░░░█░█░░░█░█░░░█░░░███░░░███░░█░░░████░░███░
     *  ░█░░█░█░░░░█░██░░█░██░░█░░█░░░█░░█░░█░█░░░█░░░░█░░░░
     *  ░███░░█░░░░█░█░█░█░█░█░█░░█████░░███░░█░░░████░░██░░
     *  ░█░█░░█░░░░█░█░░██░█░░██░░█░░░█░░█░░█░█░░░█░░░░░░░█░
     *  ░█░░█░░████░░█░░░█░█░░░█░░█░░░█░░████░███░████░███░░
     */
    private Runnable calculateMovement = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {

                    DisplayMetrics displayMetrics = pImage.getContext().getResources().getDisplayMetrics();
                    int[] s_wh = {displayMetrics.widthPixels, displayMetrics.heightPixels};
                    int[] p_wh = {pImage.getWidth(), pImage.getHeight()};
                    if (GlobalVariables.moving && !cc.checkWallCollision(getDirection(), getPosition(), p_wh, s_wh)) {
                        mPlayer();
                    }

                    Thread.sleep(5);
                    updatePlayer.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public Handler updatePlayer = new Handler() {
        public void handleMessage(Message msg) {
                pImage.setX(x);
                pImage.setY(y);
        }
    };


    /**
     * MOVE PLAYER
     **/
    public void mPlayer() {
        switch (dirc.getDirectionActual()) {
            case 0:
                y = y - velocity;
                break;
            case 1:
                x = x + velocity;
                break;
            case 2:
                y = y + velocity;
                break;
            case 3:
                x = x - velocity;
                break;
            case 4:
                y = y - velocity / (float) 1.5;
                x = x + velocity / (float) 1.5;
                break;
            case 5:
                y = y - velocity / (float) 1.5;
                x = x - velocity / (float) 1.5;
                break;
            case 6:
                y = y + velocity / (float) 1.5;
                x = x + velocity / (float) 1.5;
                break;
            case 7:
                y = y + velocity / (float) 1.5;
                x = x - velocity / (float) 1.5;
                break;
        }
    }

    /**
     * SHOOT PLAYER
     **/
    public void sPlayer() {
        if(GlobalVariables.shooting){
        }
    }

    /**
     * ROTATE PLAYER
     **/
    public void rPlayer() {
        switch (fdirc.getDirectionActual()) {
            /**
             * 000 = East
             * 045 = North East
             * 090 = North
             * 135 = North West
             * 180 = West
             * 225 = South West
             * 270 = South
             * 315 = South East
             */
            case 0:
                pImage.setImageBitmap(rotatePlayer(playerimage, 270));
                break;
            case 1:
                pImage.setImageBitmap(rotatePlayer(playerimage, 0));
                break;
            case 2:
                pImage.setImageBitmap(rotatePlayer(playerimage, 90));
                break;
            case 3:
                pImage.setImageBitmap(rotatePlayer(playerimage, 180));
                break;
            case 4:
                pImage.setImageBitmap(rotatePlayer(playerimage, 315));
                break;
            case 5:
                pImage.setImageBitmap(rotatePlayer(playerimage, 225));
                break;
            case 6:
                pImage.setImageBitmap(rotatePlayer(playerimage, 45));
                break;
            case 7:
                pImage.setImageBitmap(rotatePlayer(playerimage, 135));
                break;


        }
    }

    public Direction getDirection(){
        return dirc;
    }
    public void setDirection(Direction dirc){
        this.dirc = dirc;
    }
    public Direction getFacingDirection(){
        return fdirc;
    }
    public void setFacingDirection(Direction fdirc){
        this.fdirc = fdirc;
    }
    public ImageView getPlayerImage(){return this.pImage;}

    public int[] getPosition(){
        int[] pCords = new int[2];
        pImage.getLocationOnScreen(pCords);

        return pCords;
    }
    private Bitmap rotatePlayer(Bitmap src, float angle) {
        try {
            Matrix m = new Matrix();
            m.postRotate(angle);

            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
        } catch (NullPointerException e) {
            return null;
        }
    }

}
