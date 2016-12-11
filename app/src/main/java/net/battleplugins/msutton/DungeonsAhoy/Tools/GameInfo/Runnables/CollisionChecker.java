package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Runnables;

import android.graphics.Rect;
import android.util.Log;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GameStatus;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.Zombie;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;

/**
 * Created by Matt Sutton on 11/27/2016.
 */

public class CollisionChecker{

    ZombieCollection zc;
    Player p;

    public CollisionChecker(ZombieCollection zc, Player p){
        this.zc = zc;
        this.p = p;
    }
    public CollisionChecker(Player p){
        this.p = p;
    }
    public void initiate(){
        if(zc != null) {
            Thread zombieCollision = new Thread(checkZombieCollision);
            zombieCollision.start();
        }else{
            Log.d("CollisionChecker", "You're trying to initiate zc without initializing a ZombieCollection.");
        }
    }
    private Runnable checkZombieCollision = new Runnable(){
        @Override
        public void run(){
            while(GlobalVariables.running) {
                try {
                    while (zc.hasNext()) {
                        /** Zombie **/
                        Zombie z = zc.next();
                        Rect zRec = new Rect();
                        z.getZombieImage().getDrawingRect(zRec);

                        /** Player **/
                        Rect pRec = new Rect();
                        p.getPlayerImage().getDrawingRect(pRec);

                        if (Rect.intersects(zRec, pRec)) {
                            handleZombieCollision();
                        }
                    }
                    Thread.sleep(100);
                }catch(InterruptedException e){
                    GlobalVariables.gameStatus = GameStatus.ERROR;
                }
            }
        }
    };
    /**
    public int checkObjectCollision(Direction dirc, int[] playerLoc, int[] WH_Player, ArrayList<CollidableObject> collidables){
        int player_y = playerLoc[1];
        int player_x = playerLoc[0];

        for(CollidableObject co : collidables){
            switch(dirc.getDirectionAsString()){
                case "North":
                    if(co instanceof Wall){

                    }else if(co instanceof RoomExit){

                    }
                case "East":
                    if(player_x + 1 + WH_Player[0] > WH_Screen[0]) {
                        return true;
                    }else{
                        return false;
                    }
                case "South":
                    if(player_y + 1 + WH_Player[1] > WH_Screen[1]) {
                        return true;
                    }else{
                        return false;
                    }
                case "West":
                    if(player_x - 1 < 0){
                        return true;
                    }else{
                        return false;
                    }
                case "North East":
                    if(player_y - 1 < 0 || player_x + 1 + WH_Player[0] > WH_Screen[0]){
                        return true;
                    }else {
                        return false;
                    }
                case "North West":
                    if(player_y - 1 < 0 || player_x - 1 < 0){
                        return true;
                    }else {
                        return false;
                    }
                case "South East":
                    if(player_y + 1 + WH_Player[1] > WH_Screen[1] || player_x + 1 + WH_Player[0] > WH_Screen[0]){
                        return true;
                    }else {
                        return false;
                    }
                case "South West":
                    if(player_y + 1 + WH_Player[1] > WH_Screen[1] || player_x - 1 < 0){
                        return true;
                    }else {
                        return false;
                    }
            }
        }
    }
    **/
    public boolean checkWallCollision(Direction dirc, int[] locOnScreen, int[] WH_Player, int[] WH_Screen){

        int player_x = locOnScreen[0];
        int player_y = locOnScreen[1];

        switch(dirc.getDirectionAsString()){
            case "North":
                if(player_y - 1 < 0) {
                    return true;
                }else{
                    return false;
                }
            case "East":
                if(player_x + 1 + WH_Player[0] > WH_Screen[0]) {
                    return true;
                }else{
                    return false;
                }
            case "South":
                if(player_y + 1 + WH_Player[1] > WH_Screen[1]) {
                    return true;
                }else{
                    return false;
                }
            case "West":
                if(player_x - 1 < 0){
                    return true;
                }else{
                    return false;
                }
            case "North East":
                if(player_y - 1 < 0 || player_x + 1 + WH_Player[0] > WH_Screen[0]){
                    return true;
                }else {
                    return false;
                }
            case "North West":
                if(player_y - 1 < 0 || player_x - 1 < 0){
                    return true;
                }else {
                    return false;
                }
            case "South East":
                if(player_y + 1 + WH_Player[1] > WH_Screen[1] || player_x + 1 + WH_Player[0] > WH_Screen[0]){
                    return true;
                }else {
                    return false;
                }
            case "South West":
                if(player_y + 1 + WH_Player[1] > WH_Screen[1] || player_x - 1 < 0){
                    return true;
                }else {
                    return false;
                }
            default:
                return false;
        }


    }


    private void handleZombieCollision(){
        GlobalVariables.gameStatus = GameStatus.GAME_OVER;
    }


}
