package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision.CollisionTypes;

import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision.CollidableObject;

/**
 * Created by mts01060 on 12/11/2016.
 */

public class Spike extends CollidableObject{

    int damage;
    public Spike(ImageView iv, int damage){
        super(iv.getContext(), iv.getX(), iv.getY(), "Spike", iv);
        this.damage = damage;
    }
    public int getDamage(){
        return damage;
    }
}
