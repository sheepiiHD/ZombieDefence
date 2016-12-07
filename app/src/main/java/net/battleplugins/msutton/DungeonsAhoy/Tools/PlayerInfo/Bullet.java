package net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;

/**
 * Created by mts01060 on 12/7/2016.
 */

public class Bullet {

    Direction d;
    final float velocity = 1;

    float x, y;
    public Bullet(Direction d, float x, float y){
        this.d = d;
        this.x = x;
        this.y = y;
    }

    public void fire(){
        switch(d.getDirectionActual()){
            case 0:
                y = y - velocity;
                break;
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
                y = y - velocity / (float) 1.5;
                x = x + velocity / (float) 1.5;
                break;
            case 5:
                y = y - velocity / (float) 1.5;
                x = x - velocity / (float) 1.5;
                break;
            case 6:
                y = y + velocity / (float) 1.5;
                x = x + velocity / (float) 1.5;
                break;
            case 7:
                y = y + velocity / (float) 1.5;
                x = x - velocity / (float) 1.5;
                break;
            default:
                System.out.println("ERROR!!!!");
        }
    }
}
