package com.example.pocketball.MyFrameWork;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.d(Tag,">>>size.x:"+AppManager.getInstance().size.x + ", size.y:"+AppManager.getInstance().size.y);
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(new GameView(this));
        AppManager.getInstance().context=this;
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        AppManager.getInstance().size.x=width;
        AppManager.getInstance().size.y=height;

    }
}
