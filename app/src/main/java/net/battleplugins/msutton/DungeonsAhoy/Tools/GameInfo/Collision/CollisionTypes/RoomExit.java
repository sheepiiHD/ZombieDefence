package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision.CollisionTypes;

import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision.CollidableObject;

/**
 * Created by mts01060 on 12/11/2016.
 */

public class RoomExit extends CollidableObject {


    public RoomExit(ImageView iv){
        super(iv.getContext(), iv.getX(), iv.getY(), "Exit", iv);
    }
}
