package com.example.pocketball.MyFrameWork;

import android.content.pm.ActivityInfo;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import com.example.pocketball.R;

public class MainActivity extends AppCompatActivity {


    //Display display = getWindowManager().getDefaultDisplay();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // display.getSize(AppManager.getInstance().size);
        //Log.d(Tag,">>>size.x:"+AppManager.getInstance().size.x + ", size.y:"+AppManager.getInstance().size.y);
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(new GameView(this));
    }
}
