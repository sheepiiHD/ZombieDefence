package net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.game_project.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mts01060 on 12/7/2016.
 */

public class BulletCollection implements Iterator<Bullet> {

    /**
     * Saved Variables
     **/
    Player player;
    Context c;
    View layout;

    /**
     * Iterator Variables
     **/
    private final List<Bullet> bulletList = new ArrayList<Bullet>();

    public Bullet next() {
        return bulletList.iterator().next();
    }

    public boolean hasNext() {
        return bulletList.iterator().hasNext();
    }

    public Iterator<Bullet> getBullets = bulletList.iterator();

    public void initiate(Player player, Context c, View layout) {
        this.player = player;
        this.c = c;
        this.layout = layout;
    }

    /**
     * Runnables
     **/
    private Runnable spawnNewBullet = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (GlobalVariables.shooting) {
                        bulletList.add(new Bullet(player.getFacingDirection(), player.getPosition()[0], player.getPosition()[1]));
                        Thread.sleep(GlobalVariables.shootDelay);
                    }
                } catch (InterruptedException e) {
                    Log.d("BulletCollection", "Error in spawnNewBullet function.");
                }
            }
        }
    };

    private Runnable bulletsFly = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    while (hasNext()) {
                        Bullet b = next();
                        b.fire();
                    }
                    Thread.sleep(GlobalVariables.shootSpeed);
                } catch (InterruptedException e) {
                    Log.d("BulletCollection", "Error in bulletsFly function.");
                }

            }
        }
    };

    public Handler updateBullets = new Handler() {
        public void handleMessage(Message msg) {

            //TODO: Add loop to go through all of the bullets (to grab their x and y) and set the location to the ImageView
            ImageView iv = new ImageView(c);
            iv.setImageResource(R.drawable.bullet2);

            if (layout instanceof LinearLayout) {
                try {
                    Activity ac = (Activity) c;
                    LinearLayout ll = (LinearLayout) ac.findViewById(R.id.linearLayout);

                    ll.addView(iv);
                } catch (NullPointerException e) {
                    System.out.println("This shit is definitely null.");
                }
            }

        }
    };
}
