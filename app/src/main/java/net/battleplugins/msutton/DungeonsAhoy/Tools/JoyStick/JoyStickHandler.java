package net.battleplugins.msutton.DungeonsAhoy.Tools.JoyStick;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.game_project.R;

/**
 * Created by Matt Sutton on 12/6/2016.
 */

public class JoyStickHandler {

    Context c;
    RelativeLayout layout_joystick_movement, layout_joystick_shooting;
    JoyStickClass js_move, js_shoot;

    int[] pos;
    Player player;

    public JoyStickHandler(Context c, Player p){
        this.c = c;
        this.player = p;

        /** Instantiate the joysticks. **/
        View v1 = ((Activity)c).findViewById(R.id.layout_joystick_movement);
        layout_joystick_movement = (RelativeLayout) v1;

        View v2 = ((Activity)c).findViewById(R.id.layout_joystick_shooting);
        layout_joystick_shooting = (RelativeLayout) v2;
    }

    public void setup(){
        draw();
        setupMovementListener();
        setupShootingListener();
    }

    private void draw(){
        /** Movement Joystick **/
        js_move = new JoyStickClass(c.getApplicationContext(), layout_joystick_movement, R.drawable.image_button);
        js_move.setStickSize(50, 50);
        js_move.setLayoutSize(150, 150);
        js_move.setLayoutAlpha(35);
        js_move.setStickAlpha(100);
        js_move.setOffset(20);
        js_move.setMinimumDistance(20);

        js_shoot = new JoyStickClass(c.getApplicationContext(), layout_joystick_shooting, R.drawable.image_button);
        js_shoot.setStickSize(50, 50);
        js_shoot.setLayoutSize(150, 150);
        js_shoot.setLayoutAlpha(35);
        js_shoot.setStickAlpha(100);
        js_shoot.setOffset(20);
        js_shoot.setMinimumDistance(20);
    }
    private void setupMovementListener(){
        layout_joystick_movement.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js_move.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    ((Activity)c).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    int[] screen = {displaymetrics.widthPixels, displaymetrics.heightPixels};
                    int[] p_WH = {player.getPlayerImage().getWidth(), player.getPlayerImage().getHeight()};


                    int direction = js_move.get8Direction();
                    switch(direction){
                        case JoyStickClass.STICK_UP:
                            if(player.getDirection() != Direction.NORTH) {
                                player.setDirection(Direction.NORTH);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.NORTH);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_UPRIGHT:
                            if(player.getDirection() != Direction.NORTHEAST) {
                                player.setDirection(Direction.NORTHEAST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.NORTHEAST);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_RIGHT:
                            if(player.getDirection() != Direction.EAST) {
                                player.setDirection(Direction.EAST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.EAST);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_DOWNRIGHT:
                            if(player.getDirection() != Direction.SOUTHEAST) {
                                player.setDirection(Direction.SOUTHEAST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTHEAST);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_DOWN:
                            if(player.getDirection() != Direction.SOUTH) {
                                player.setDirection(Direction.SOUTH);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTH);
                                }
                            }

                            break;
                        case JoyStickClass.STICK_DOWNLEFT:
                            if(player.getDirection() != Direction.SOUTHWEST) {
                                player.setDirection(Direction.SOUTHWEST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTHWEST);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_LEFT:
                            if(player.getDirection() != Direction.WEST) {
                                player.setDirection(Direction.WEST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.WEST);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_UPLEFT:
                            if(player.getDirection() != Direction.NORTHWEST) {
                                player.setDirection(Direction.NORTHWEST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.NORTHWEST);
                                }
                            }
                            break;
                        case JoyStickClass.STICK_NONE:
                            break;
                    }
                    GlobalVariables.moving = true;
                    player.rPlayer();

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    GlobalVariables.moving = false;
                }
                return true;
            }
        });
    }
    private void setupShootingListener(){
        layout_joystick_shooting.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js_shoot.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    GlobalVariables.shooting = true;


                    int direction = js_shoot.get8Direction();

                    switch(direction){
                        case JoyStickClass.STICK_UP:
                            if(player.getDirection() != Direction.NORTH) {
                                player.setFacingDirection(Direction.NORTH);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_UPRIGHT:
                            if(player.getDirection() != Direction.NORTHEAST) {
                                player.setFacingDirection(Direction.NORTHEAST);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_RIGHT:
                            if(player.getDirection() != Direction.EAST) {
                                player.setFacingDirection(Direction.EAST);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_DOWNRIGHT:
                            if(player.getDirection() != Direction.SOUTHEAST) {
                                player.setFacingDirection(Direction.SOUTHEAST);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_DOWN:
                            if(player.getDirection() != Direction.SOUTH) {
                                player.setFacingDirection(Direction.SOUTH);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_DOWNLEFT:
                            if(player.getDirection() != Direction.SOUTHWEST) {
                                player.setFacingDirection(Direction.SOUTHWEST);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_LEFT:
                            if(player.getDirection() != Direction.WEST) {
                                player.setFacingDirection(Direction.WEST);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_UPLEFT:
                            if(player.getDirection() != Direction.NORTHWEST) {
                                player.setFacingDirection(Direction.NORTHWEST);
                            }
                            GlobalVariables.shooting = true;
                            break;
                        case JoyStickClass.STICK_NONE:
                            GlobalVariables.shooting = false;
                            break;
                    }
                    player.rPlayer();

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    GlobalVariables.shooting = false;
                }
                return true;
            }
        });
    }
}
