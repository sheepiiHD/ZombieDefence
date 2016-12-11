package net.battleplugins.msutton.DungeonsAhoy;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Runnables.CollisionChecker;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.Direction;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GameStatus;
import net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables.GlobalVariables;
import net.battleplugins.msutton.DungeonsAhoy.Tools.JoyStick.JoyStickHandler;
import net.battleplugins.msutton.DungeonsAhoy.Tools.PlayerInfo.Player;
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;
import net.battleplugins.msutton.game_project.R;

public class GameActivity extends AppCompatActivity {


    Context c;
    ImageView image_player;

    ZombieCollection zombieCollection = new ZombieCollection();
    Player player;

    GameStatus gs;
    CollisionChecker cc;


    Player p;

    int level = 1;

    public Runnable r = new Runnable(){
        @Override
        public void run(){
            while(true) {
                p = player;
                p.setDirection(Direction.EAST);
                updateBullets.sendEmptyMessage(0);
            }
        }

    };
    public Handler updateBullets = new Handler() {
        public void handleMessage(Message msg) {
            player.setDirection(p.getDirection());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Pre setup **/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /** Setup content **/
        setContentView(R.layout.activity_game);

        /** Instantiate player **/
        image_player = (ImageView)findViewById(R.id.player);
        Bitmap p = ((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.player, null)).getBitmap();
        player = new Player(image_player, (int)image_player.getX(), (int)image_player.getY(), p);

        /** Visuals **/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViewById(R.id.linearLayout).setBackgroundColor(Color.GRAY);
        GlobalVariables.gameStatus = GameStatus.RUNNING;

        /** Joysticks **/
        JoyStickHandler joyStickHandler = new JoyStickHandler(this, player, zombieCollection);
        joyStickHandler.setup();

        /** Zombies **/
        zombieCollection.spawn(this, findViewById(R.id.linearLayout), level, player);

        /** Collision **/
        cc = new CollisionChecker(zombieCollection, player);
        cc.initiate();
    }
}
