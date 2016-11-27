package net.battleplugins.msutton.DungeonsAhoy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.GameInfo.Difficulty;
import net.battleplugins.msutton.DungeonsAhoy.GameInfo.Direction;
import net.battleplugins.msutton.DungeonsAhoy.GameInfo.WeaponType;
import net.battleplugins.msutton.game_project.R;

/**
 * Created by mts01060 on 11/14/2016.
 */


public class Player {

    Direction dirc;
    Direction fdirc;
    WeaponType wt;
    Difficulty difficulty;
    private Context context;
    float a = 0;
    Bitmap playerimage;
    float x, y;
    float velocity;


    public Player(Context context, Bitmap p, float x, float y) {
        dirc = Direction.EAST;
        fdirc = Direction.EAST;
        difficulty = Difficulty.Medium;
        this.context = context;
        this.x = x;
        this.y = y;
        playerimage = p;
        velocity = 1;
    }

    public Player(Difficulty dif) {
        this.difficulty = dif;
    }

    public Direction setDirection(Direction direction) {
        return this.dirc = direction;
    }

    public Direction getDirection() {
        return this.dirc;
    }

    public Direction setFacingDirection(Direction direction){ return this.fdirc = direction; }

    public Direction getFacingDirection(){ return this.fdirc; }

    public Difficulty setDifficulty(Difficulty difficulty) {
        return this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public WeaponType setWeaponType(WeaponType wt) {
        return this.wt = wt;
    }

    public WeaponType getWeaponType() {
        return this.wt;
    }

    public void mPlayer(){
        Direction direction = getDirection();
        boolean wegood;
        ImageView player = null;
        Bitmap playerpic = playerimage;
        try {
            player = (ImageView)((Activity)context).findViewById(R.id.player);
            if(player != null && playerpic != null) {
                wegood = true;
            }else{
                System.out.println(player + " " + playerpic);
                wegood = false;
            }
        }catch(NullPointerException e) {
            System.out.println("WE NOT GOOD HOMIE");
            wegood = false;
        }
        if(wegood) {
            Bitmap temp;
            switch (direction.getDirectionActual()) {
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
                //North
                case 0:
                    y = y - velocity;

                    break;
                //East
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
                    y = y - velocity/(float)1.5;
                    x = x + velocity/(float)1.5;
                    break;
                case 5:
                    y = y - velocity/(float)1.5;
                    x = x - velocity/(float)1.5;
                    break;
                case 6:
                    y = y + velocity/(float)1.5;
                    x = x + velocity/(float)1.5;
                    break;
                case 7:
                    y = y + velocity/(float)1.5;
                    x = x - velocity/(float)1.5;
                    break;
            }
        }else{
        }
    }

    public void shoot(){
        ImageView iv = new ImageView(context);
    }
    public void rPlayer() {
        Direction direction = getFacingDirection();
        boolean wegood;
        ImageView player = null;
        Bitmap playerpic = playerimage;
        try {
            player = (ImageView)((Activity)context).findViewById(R.id.player);
            if(player != null && playerpic != null) {
                wegood = true;
            }else{
                System.out.println(player + " " + playerpic);
                wegood = false;
            }
        }catch(NullPointerException e) {
            System.out.println("WE NOT GOOD HOMIE");
            wegood = false;
        }
        if(wegood) {
            switch (direction.getDirectionActual()) {
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
                //North
                case 0:
                    player.setImageBitmap(rotatePlayer(playerpic, 270));
                    break;
                //East
                case 1:
                    player.setImageBitmap(rotatePlayer(playerpic, 0));
                    break;
                case 2:
                    player.setImageBitmap(rotatePlayer(playerpic, 90));
                    break;
                case 3:
                    player.setImageBitmap(rotatePlayer(playerpic, 180));
                    break;
                case 4:
                    player.setImageBitmap(rotatePlayer(playerpic, 315));
                    break;
                case 5:
                    player.setImageBitmap(rotatePlayer(playerpic, 225));
                    break;
                case 6:
                    player.setImageBitmap(rotatePlayer(playerpic, 45));
                    break;
                case 7:
                    player.setImageBitmap(rotatePlayer(playerpic, 135));
                    break;

            }
        }else{
        }
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
