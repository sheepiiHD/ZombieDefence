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

    double[] tLocation;


    public Zombie(ImageView zImage, float X, float Y, Player player){
        zLoc = new int[2];
        zImage.getLocationOnScreen(zLoc);

        this.zImage = zImage;
        this.X = X;
        this.Y = Y;
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
                    double[] d1 = {player.getPosition()[0]-850, player.getPosition()[1]-300};
                    double[] d2 = {X, Y};
                    tLocation = moveTowardsPlayer(d1, d2);

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
            zImage.setX((float)tLocation[0]);
            zImage.setY((float)tLocation[1]);

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

        // Bring it together
        double [] zombie_pos_new = {zombie_pos_new_x, zombie_pos_new_y};

        // One step closer to the Brainz!
        return zombie_pos_new;
    }
}
