package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision.CollisionTypes;

import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision.CollidableObject;

/**
 * Created by mts01060 on 12/11/2016.
 */

public class Wall extends CollidableObject {

    public Wall(ImageView iv){
        super(iv.getContext(), iv.getX(), iv.getY(), "Wall", iv);
    }
}
