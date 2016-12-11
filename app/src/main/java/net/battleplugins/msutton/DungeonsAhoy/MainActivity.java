package net.battleplugins.msutton.DungeonsAhoy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.battleplugins.msutton.game_project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Pre setup **/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /** Setup content **/
        setContentView(R.layout.activity_main);

        /** Visuals **/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViewById(R.id.start_layout).setBackgroundColor(Color.BLACK);
    }

    public void startGame(View view){

        Intent game = new Intent(this, GameActivity.class);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(game);
    }
    public void quitGame(View view){

    }
}
