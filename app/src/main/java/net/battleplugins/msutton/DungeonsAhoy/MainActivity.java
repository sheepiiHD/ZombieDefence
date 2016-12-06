package net.battleplugins.msutton.DungeonsAhoy;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Runnables.CollisionChecker;
import net.battleplugins.msutton.DungeonsAhoy.Tools.JoyStick.JoyStickHandler;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GameStatus;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.Zombie;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;
import net.battleplugins.msutton.game_project.R;

public class MainActivity extends AppCompatActivity {

    Context c;
    ImageView image_player;
    TextView textView1, textView2, textView3, textView4, textView5, textView6;

    ZombieCollection zombieCollection = new ZombieCollection();

    Player player;

    GameStatus gs;
    CollisionChecker cc;

    int[] pLoc = new int[2];
    int[] zLoc = new int[2];

    int level = 1;

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
        Bitmap p = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.player, null)).getBitmap();
        player = new Player(image_player, (int)image_player.getX(), (int)image_player.getY(), p);


        /** Visuals **/
        populateTextViews();
        setUpVisuals();
        checkDevMode();

        /** Joysticks **/
        JoyStickHandler joyStickHandler = new JoyStickHandler(this, player);
        joyStickHandler.setup();

        /** Threading **/
        c = this;

        zombieCollection.spawn(c, findViewById(R.id.linearLayout), level, player);

        Thread catchPos = new Thread(updateLocations);
        catchPos.start();

        //Thread spawn = new Thread(spawnZombies);
        //spawn.start();

        /** Collision **/
        cc = new CollisionChecker(zombieCollection, player);
        cc.initiate();
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



    private Runnable updateLocations = new Runnable(){
        @Override
        public void run(){
            try {
                while(true) {
                    image_player.getLocationInWindow(pLoc);
                    Zombie zoms = zombieCollection.next();
                    if(zoms != null){
                        zoms.getZombieImage().getLocationInWindow(zLoc);
                    }
                    //System.out.println("Zombie: x = " + zLoc[0] + "; y = " + zLoc[1]);
                    //System.out.println("Player: x = " + pLoc[0] + "; y = " + pLoc[1]);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
     *  VISUALS
     */
    private void setUpVisuals(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViewById(R.id.linearLayout).setBackgroundColor(Color.GRAY);

        GlobalVariables.gameStatus = GameStatus.RUNNING;
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
