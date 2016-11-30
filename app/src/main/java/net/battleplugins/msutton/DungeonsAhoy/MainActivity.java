package net.battleplugins.msutton.DungeonsAhoy;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GameStatus;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.DungeonsAhoy.Tools.JoyStickClass;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;
import net.battleplugins.msutton.game_project.R;

public class MainActivity extends AppCompatActivity {

    /** Joystick Variables **/
    JoyStickClass js_move, js_shoot;
    RelativeLayout layout_joystick_move, layout_joystick_shoot;


    ImageView image_player;
    TextView textView1, textView2, textView3, textView4, textView5, textView6;



    ZombieCollection zombieCollection = new ZombieCollection();

    Player player;

    GameStatus gs;

    int[] locations = new int[2];

    int wave = 0;
    int level = 1;

    Context c;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Pre setup **/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        /** Setup content **/
        setContentView(R.layout.activity_main);

        /** Instantiate player **/
        image_player = (ImageView)findViewById(R.id.player);

        int x = (int)image_player.getX();
        int y = (int)image_player.getY();
        Point p = new Point(x, y);


        /** Visuals **/
        populateTextViews();
        setUpVisuals();
        checkDevMode();

        /** Joysticks **/
        setupJoysticks();
        initiateMovementJoystickListener();
        initiateShootingJoystickListener();

        /** Threading **/
        c = this;

        Thread move = new Thread(calculateMovement);
        move.start();

        Thread catchPos = new Thread(updateLocations);
        catchPos.start();

        gs = GameStatus.RUNNING;

        Thread spawn = new Thread(spawnZombies);
        spawn.start();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus){
            //TODO Things
        }

    }

    private void fillDirectionPlayer(){
        textView6.setText("Direction Player: " + player.getDirection().getDirectionAsString());
    }

    /**
     *  ░███░░█░░░░█░█░░░█░█░░░█░░░███░░░███░░█░░░████░░███░
     *  ░█░░█░█░░░░█░██░░█░██░░█░░█░░░█░░█░░█░█░░░█░░░░█░░░░
     *  ░███░░█░░░░█░█░█░█░█░█░█░░█████░░███░░█░░░████░░██░░
     *  ░█░█░░█░░░░█░█░░██░█░░██░░█░░░█░░█░░█░█░░░█░░░░░░░█░
     *  ░█░░█░░████░░█░░░█░█░░░█░░█░░░█░░████░███░████░███░░
     */

    private Runnable spawnZombies = new Runnable() {
        @Override
        public void run() {
            try {
                while(gs.equals(GameStatus.RUNNING)) {
                    zombieCollection.spawn(c, findViewById(R.id.linearLayout), level, player);
                    Thread.sleep(GlobalVariables.waveTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable calculateMovement = new Runnable() {
        @Override
        public void run() {
            try {
                while(true) {
                    if(GlobalVariables.moving) {
                        player.mPlayer();
                    }

                    Thread.sleep(5);
                    updatePlayer.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable updateLocations = new Runnable(){
        @Override
        public void run(){
            try {
                while(true) {
                    image_player.getLocationInWindow(locations);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    public Handler updatePlayer = new Handler() {
        public void handleMessage(Message msg) {
            image_player.setX(player.x);
            image_player.setY(player.y);

        }
    };



    private void populateTextViews(){
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        textView6 = (TextView)findViewById(R.id.textView6);
    }

    /**
     * Set up joysticks, so they're operational and able to be used.
     */
    private void setupJoysticks(){

        /**
         * Movement Joystick
         */
        layout_joystick_move = (RelativeLayout)findViewById(R.id.layout_joystick_movement);

        js_move = new JoyStickClass(getApplicationContext()
                , layout_joystick_move, R.drawable.image_button);
        js_move.setStickSize(50, 50);
        js_move.setLayoutSize(150, 150);
        js_move.setLayoutAlpha(35);
        js_move.setStickAlpha(100);
        js_move.setOffset(20);
        js_move.setMinimumDistance(20);

        initiateMovementJoystickListener();
        /**
         * Shooting Joystick
         */

        layout_joystick_shoot = (RelativeLayout)findViewById(R.id.layout_joystick_shooting);

        js_shoot = new JoyStickClass(getApplicationContext(), layout_joystick_shoot, R.drawable.image_button);
        js_shoot.setStickSize(50, 50);
        js_shoot.setLayoutSize(150, 150);
        js_shoot.setLayoutAlpha(35);
        js_shoot.setStickAlpha(100);
        js_shoot.setOffset(20);
        js_shoot.setMinimumDistance(20);

        initiateShootingJoystickListener();
    }

    /**
     * Initiate the listener so we can move the character around.
     */
    private void initiateMovementJoystickListener(){

        Bitmap p = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.player, null)).getBitmap();
        player = new Player(this, p, image_player.getX(), image_player.getY());


        layout_joystick_move.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js_move.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView1.setText("X : " + String.valueOf(js_move.getX()));
                    textView2.setText("Y : " + String.valueOf(js_move.getY()));
                    textView3.setText("Angle : " + String.valueOf(js_move.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js_move.getDistance()));

                    GlobalVariables.moving = true;

                    Bitmap p = ((BitmapDrawable)ResourcesCompat.getDrawable(getResources(), R.drawable.player, null)).getBitmap();

                    int direction = js_move.get8Direction();
                    switch(direction){
                        case JoyStickClass.STICK_UP:
                            textView5.setText("Direction : Up");
                            if(player.getDirection() != Direction.NORTH) {
                                player.setDirection(Direction.NORTH);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.NORTH);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_UPRIGHT:
                            textView5.setText("Direction : Up Right");
                            if(player.getDirection() != Direction.NORTHEAST) {
                                player.setDirection(Direction.NORTHEAST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.NORTHEAST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_RIGHT:
                            textView5.setText("Direction : Right");
                            if(player.getDirection() != Direction.EAST) {
                                player.setDirection(Direction.EAST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.EAST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_DOWNRIGHT:
                            textView5.setText("Direction : Down Right");
                            if(player.getDirection() != Direction.SOUTHEAST) {
                                player.setDirection(Direction.SOUTHEAST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTHEAST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_DOWN:
                            textView5.setText("Direction : Down");
                            if(player.getDirection() != Direction.SOUTH) {
                                player.setDirection(Direction.SOUTH);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTH);
                                }
                            }
                            fillDirectionPlayer();

                            break;
                        case JoyStickClass.STICK_DOWNLEFT:
                            textView5.setText("Direction : Down Left");
                            if(player.getDirection() != Direction.SOUTHWEST) {
                                player.setDirection(Direction.SOUTHWEST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTHWEST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_LEFT:
                            textView5.setText("Direction : Left");
                            if(player.getDirection() != Direction.WEST) {
                                player.setDirection(Direction.WEST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.WEST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_UPLEFT:
                            textView5.setText("Direction : Up Left");
                            if(player.getDirection() != Direction.NORTHWEST) {
                                player.setDirection(Direction.NORTHWEST);
                                if(GlobalVariables.shooting == false) {
                                    player.setFacingDirection(Direction.NORTHWEST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_NONE:
                            textView5.setText("Direction : Center");
                            break;
                    }
                    player.rPlayer();

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    textView1.setText("X :");
                    textView2.setText("Y :");
                    textView3.setText("Angle :");
                    textView4.setText("Distance :");
                    textView5.setText("Direction :");
                    textView6.setText("Direction :");

                    GlobalVariables.moving = false;
                }
                return true;
            }
        });
    }
    private void initiateShootingJoystickListener(){

        layout_joystick_shoot.setOnTouchListener(new View.OnTouchListener() {
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

    /**
     *  VISUALS
     */
    private void setUpVisuals(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViewById(R.id.linearLayout).setBackgroundColor(Color.GRAY);
    }
    private void checkDevMode(){
        if(GlobalVariables.dev_mode) {
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
            textView4.setVisibility(View.VISIBLE);
            textView5.setVisibility(View.VISIBLE);
            textView6.setVisibility(View.VISIBLE);
        }else{
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            textView4.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            textView6.setVisibility(View.GONE);
        }
    }

}
