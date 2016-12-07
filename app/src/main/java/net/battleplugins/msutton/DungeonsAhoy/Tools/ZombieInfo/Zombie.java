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
    float zombie_speed = 1;

    boolean dead;
    int[] zLoc;


    public Zombie(ImageView zImage, Player player){
        zLoc = new int[2];
        zImage.getLocationOnScreen(zLoc);

        this.zImage = zImage;
        this.X = zLoc[0];
        this.Y = zLoc[1];
        this.Width = zImage.getWidth();
        this.Height = zImage.getHeight();
        this.fdirc = Direction.EAST;
        this.player = player;
        this.dead = false;

        Thread thread = new Thread(this.startZombieChase);
        thread.start();
    }

    public ImageView getZombieImage(){
        return zImage;
    }
    private Runnable startZombieChase = new Runnable() {
        @Override
        public void run() {
            try {
                while(!dead) {
                    //moveTowardsPlayer();

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
            zImage.getLocationOnScreen(zLoc);
            zImage.bringToFront();
            zImage.setX(zLoc[0]);
            zImage.setY(zLoc[1]);

        }
    };

    private double [] moveTowardsPlayer(double [] player_pos, double [] zombie_pos) {
        double [] player_pos_old = player_pos.clone();
        double [] zombie_pos_old = zombie_pos.clone();

        double zombie_pos_new_x = player_pos_old[0] - zombie_pos_old[0];
        if (zombie_pos_new_x > 0) {
            zombie_pos_new_x = zombie_pos_new_x - zombie_speed;
        } else {
            zombie_pos_new_x = zombie_pos_new_x + zombie_speed;
        }

        double zombie_pos_new_y = player_pos_old[1] - zombie_pos_old[1];
        if(zombie_pos_new_y > 0){
            zombie_pos_new_y = zombie_pos_new_y - zombie_speed;
        } else {
            zombie_pos_new_y = zombie_pos_new_y + zombie_speed;
        }

        // TODO: do the same for the Y pos.

        // Bring it together
        double [] zombie_pos_new = {zombie_pos_new_x, zombie_pos_new_y};

        // One step closer to the Brainz!
        return zombie_pos_new;
    }
    public void l(Object string){
        System.out.println("Log - " + string);
    }

    private double[] copyFromIntArray(int[] source) {
        double[] dest = new double[source.length];
        for(int i=0; i<source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }
}
