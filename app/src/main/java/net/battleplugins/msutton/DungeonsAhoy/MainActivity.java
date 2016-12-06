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
import net.battleplugins.msutton.DungeonsAhoy.Tools.ZombieInfo.ZombieCollection;
import net.battleplugins.msutton.game_project.R;

public class MainActivity extends AppCompatActivity {

    Context c;
    ImageView image_player;

    ZombieCollection zombieCollection = new ZombieCollection();
    Player player;

    GameStatus gs;
    CollisionChecker cc;

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
        setUpVisuals();

        /** Joysticks **/
        JoyStickHandler joyStickHandler = new JoyStickHandler(this, player);
        joyStickHandler.setup();

        /** Zombies **/
        zombieCollection.spawn(this, findViewById(R.id.linearLayout), level, player);

        /** Collision **/
        cc = new CollisionChecker(zombieCollection, player);
        cc.initiate();
    }
    /** VISUALS **/
    private void setUpVisuals(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViewById(R.id.linearLayout).setBackgroundColor(Color.GRAY);

        GlobalVariables.gameStatus = GameStatus.RUNNING;
    }
}
