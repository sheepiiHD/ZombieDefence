package net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.game_project.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mts01060 on 12/7/2016.
 */

public class BulletCollection implements Iterator<Bullet> {

    private final List<Bullet> bulletList = new ArrayList<Bullet>();

    @Override
    public Bullet next() {
        return bulletList.iterator().next();
    }
    @Override
    public boolean hasNext(){
        return bulletList.iterator().hasNext();
    }
    public Iterator<Bullet> getBullets = bulletList.iterator();


    public void shoot(Context context, View layout, Player player){
            ImageView iv = new ImageView(context);
            iv.setImageResource(R.drawable.bullet2);

            if (layout instanceof LinearLayout) {
                try {
                    Activity ac = (Activity) context;
                    LinearLayout ll = (LinearLayout) ac.findViewById(R.id.linearLayout);

                    ll.addView(iv);
                } catch (NullPointerException e) {
                    System.out.println("This shit is definitely null.");
                }
            } else {
                System.out.println("Yo that's not a thing");
            }

            bulletList.add(new Bullet(Direction.NORTHWEST));

        }
    }
