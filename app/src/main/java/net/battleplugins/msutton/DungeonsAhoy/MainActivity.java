package net.battleplugins.msutton.DungeonsAhoy;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.battleplugins.msutton.DungeonsAhoy.GameInfo.Difficulty;
import net.battleplugins.msutton.DungeonsAhoy.GameInfo.Direction;
import net.battleplugins.msutton.DungeonsAhoy.GameInfo.ZombieCollection;
import net.battleplugins.msutton.game_project.R;

public class MainActivity extends AppCompatActivity {

    Boolean dev_mode = GlobalVariable.dev_mode;

    RelativeLayout layout_joystick_move, layout_joystick_shoot;
    ImageView image_player;
    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    Bitmap p;
    JoyStickClass js_move, js_shoot;

    ZombieCollection zombieCollection = new ZombieCollection();

    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        View v = findViewById(R.id.linearLayout);



        v.setBackgroundColor(Color.GRAY);

        image_player = (ImageView)findViewById(R.id.player);

        int x = (int)image_player.getX();
        int y = (int)image_player.getY();



        Point p = new Point(x, y);
        zombieCollection.spawn(this, v, 1, p);

        if(dev_mode){
            enableDeveloperMode();
        }else{
            player = new Player(Difficulty.Medium);
        }
        populateTextViews();
        setupJoysticks();
        initiateMovementJoystickListener();
        initiateShootingJoystickListener();
        setUpVisuals();

        Thread move = new Thread(calculateMovement);
        move.start();

    }
    private void enableDeveloperMode(){
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        textView3.setVisibility(View.VISIBLE);
        textView4.setVisibility(View.VISIBLE);
        textView5.setVisibility(View.VISIBLE);
        textView6.setVisibility(View.VISIBLE);
    }
    private void fillDirectionPlayer(){
        textView6.setText("Direction Player: " + player.getDirection().getDirectionAsString());
    }

    private Runnable calculateMovement = new Runnable() {
        @Override
        public void run() {
            try {
                while(true) {
                    if(GlobalVariable.moving) {
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
    public Handler updatePlayer = new Handler() {
        public void handleMessage(android.os.Message msg) {
            image_player.setX(player.x);
            image_player.setY(player.y);

            GlobalVariable.playerX = (int)player.x;
            GlobalVariable.playerY = (int)player.y;
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

                    GlobalVariable.moving = true;

                    Bitmap p = ((BitmapDrawable)ResourcesCompat.getDrawable(getResources(), R.drawable.player, null)).getBitmap();

                    int direction = js_move.get8Direction();
                    switch(direction){
                        case JoyStickClass.STICK_UP:
                            textView5.setText("Direction : Up");
                            if(player.getDirection() != Direction.NORTH) {
                                player.setDirection(Direction.NORTH);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.NORTH);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_UPRIGHT:
                            textView5.setText("Direction : Up Right");
                            if(player.getDirection() != Direction.NORTHEAST) {
                                player.setDirection(Direction.NORTHEAST);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.NORTHEAST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_RIGHT:
                            textView5.setText("Direction : Right");
                            if(player.getDirection() != Direction.EAST) {
                                player.setDirection(Direction.EAST);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.EAST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_DOWNRIGHT:
                            textView5.setText("Direction : Down Right");
                            if(player.getDirection() != Direction.SOUTHEAST) {
                                player.setDirection(Direction.SOUTHEAST);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTHEAST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_DOWN:
                            textView5.setText("Direction : Down");
                            if(player.getDirection() != Direction.SOUTH) {
                                player.setDirection(Direction.SOUTH);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTH);
                                }
                            }
                            fillDirectionPlayer();

                            break;
                        case JoyStickClass.STICK_DOWNLEFT:
                            textView5.setText("Direction : Down Left");
                            if(player.getDirection() != Direction.SOUTHWEST) {
                                player.setDirection(Direction.SOUTHWEST);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.SOUTHWEST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_LEFT:
                            textView5.setText("Direction : Left");
                            if(player.getDirection() != Direction.WEST) {
                                player.setDirection(Direction.WEST);
                                if(GlobalVariable.shooting == false) {
                                    player.setFacingDirection(Direction.WEST);
                                }
                            }
                            fillDirectionPlayer();
                            break;
                        case JoyStickClass.STICK_UPLEFT:
                            textView5.setText("Direction : Up Left");
                            if(player.getDirection() != Direction.NORTHWEST) {
                                player.setDirection(Direction.NORTHWEST);
                                if(GlobalVariable.shooting == false) {
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

                    GlobalVariable.moving = false;
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


                    GlobalVariable.shooting = true;


                    int direction = js_shoot.get8Direction();

                    switch(direction){
                        case JoyStickClass.STICK_UP:
                            if(player.getDirection() != Direction.NORTH) {
                                player.setFacingDirection(Direction.NORTH);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_UPRIGHT:
                            if(player.getDirection() != Direction.NORTHEAST) {
                                player.setFacingDirection(Direction.NORTHEAST);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_RIGHT:
                            if(player.getDirection() != Direction.EAST) {
                                player.setFacingDirection(Direction.EAST);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_DOWNRIGHT:
                            if(player.getDirection() != Direction.SOUTHEAST) {
                                player.setFacingDirection(Direction.SOUTHEAST);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_DOWN:
                            if(player.getDirection() != Direction.SOUTH) {
                                player.setFacingDirection(Direction.SOUTH);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_DOWNLEFT:
                            if(player.getDirection() != Direction.SOUTHWEST) {
                                player.setFacingDirection(Direction.SOUTHWEST);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_LEFT:
                            if(player.getDirection() != Direction.WEST) {
                                player.setFacingDirection(Direction.WEST);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_UPLEFT:
                            if(player.getDirection() != Direction.NORTHWEST) {
                                player.setFacingDirection(Direction.NORTHWEST);
                            }
                            GlobalVariable.shooting = true;
                            break;
                        case JoyStickClass.STICK_NONE:
                            GlobalVariable.shooting = false;
                            break;
                    }
                    player.rPlayer();

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    GlobalVariable.shooting = false;
                }
                return true;
            }
        });
    }

    private void setUpVisuals(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

}
