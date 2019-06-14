package com.example.pocketball.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.GraphicObject;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

import java.io.File;
import java.io.FileOutputStream;

public class GameStartState implements IState {
    boolean lock = false;
    private Background background;
    String s[];
    @Override
    public void Init() {
        background = new Background(AppManager.getInstance().getBitmap(R.drawable.title));
        s = new String[10];
        s[0] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[1] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[2] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[3] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[4] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[5] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[6] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[7] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[8] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");
        s[9] = new String("3 1 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2 0 2 8 2 0 4 8 4 5 2 3 7 2 3 6 2 3 5 2 3 4 2 3 3 5");

        for(int fileindex =0; fileindex<10;fileindex++) {
            String filename = "Level" + fileindex + ".txt";
            File test = new File(filename);
            if(test.exists()==false){
                  FileOutputStream fos = null;
                  try {
                      fos = AppManager.getInstance().context.openFileOutput(filename, Context.MODE_PRIVATE);
                      fos.write(s[fileindex].getBytes());
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  if (fos != null) {
                      try {
                          fos.close();
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
            }
        }
        lock = true;
    }

    @Override
    public void Destroy() {


    }

    @Override
    public void Update() {


    }

    @Override
    public void Render(Canvas canvas) {
        background.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(lock)
         AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
        return false;
    }
}
