package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Collision;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by mts01060 on 12/11/2016.
 */

public abstract class CollidableObject extends ImageView{

    float x, y;
    String type;
    ImageView iv;


    public void setX(float x){ this.x = x; }
    public void setY(float y){ this.y = y; }

    public CollidableObject(Context c, float x, float y, String type, ImageView iv){
        super(c);
        this.x = x;
        this.y = y;
        this.type = type;
        this.iv = iv;
    }
}
