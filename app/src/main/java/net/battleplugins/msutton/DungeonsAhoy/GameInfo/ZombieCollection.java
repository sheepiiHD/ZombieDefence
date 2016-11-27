package net.battleplugins.msutton.DungeonsAhoy.GameInfo;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.battleplugins.msutton.DungeonsAhoy.Zombie;
import net.battleplugins.msutton.game_project.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Matt Sutton on 11/27/2016.
 */

public class ZombieCollection implements Iterator<Zombie> {

    private final List<Zombie> zombieList = new ArrayList<>();
    @Override
    public Zombie next() {
        return zombieList.iterator().next();
    }
    @Override
    public boolean hasNext(){
        return zombieList.iterator().hasNext();
    }
    public boolean spawn(Context context, View layout, float speed, Point pLocation){
        ImageView iv = new ImageView(context);

        if(layout instanceof LinearLayout){
            try {
                LinearLayout ll = (LinearLayout) layout;

                iv.setX(ll.getX() + ll.getWidth() / 2);
                iv.setY(ll.getY() + ll.getHeight() / 2);
                iv.setImageResource(R.drawable.zombie);

                ll.addView(iv);
            }catch(NullPointerException e){
                System.out.println("This shit is definitely null.");
                return false;
            }
        }else{
            System.out.println("Yo that's not a  ");
            return false;
        }

        zombieList.add(new Zombie(iv, pLocation, speed));
        return true;


    }
}
