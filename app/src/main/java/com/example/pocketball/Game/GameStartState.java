package com.example.pocketball.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.GraphicObject;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.MyFrameWork.SoundManager;
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
        s[0] = new String("2 5 2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 3 4 4 4 1 4 1 6 1 6 1 6 4 1 2 4 5 2");
        s[1] = new String("2 8 2 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 4 9 4 9 1 9 1 3 1 5 3 7 3 5 3 4 3 2 2 2 3 2 4 8 4");
        s[2] = new String("1 5 2 0 0 1 1 1 1 1 1 0 0 0 0 1 1 1 1 1 1 0 0 0 0 1 1 0 0 1 1 0 0 0 0 1 1 0 0 1 1 0 0 0 0 1 1 1 1 1 1 0 0 0 0 1 1 1 1 1 1 0 0 10 4 6 2 4 2 4 2 2 2 2 4 0 4 0 6 0 6 0 8 2 8 2 8 4 8 4 6 6 6 6 4 6 4 4 6 4 6 2 4 2 2 2 3 3 2 3 7 5");
        s[3] = new String("2 5 2 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 1 1 1 1 0 0 0 13 2 2 4 2 6 2 8 2 4 4 6 4 4 6 2 4 2 4 0 4 0 1 1 1 1 1 2 0 4 0 5 1 5 1 6 0 8 0 9 1 9 1 10 1 6 6 8 4 8 4 10 4 3 2 3 2 2 5 5 2 3 8 7");
        s[4] = new String("1 5 2 1 1 0 0 1 1 0 0 1 1 1 1 0 0 1 1 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 0 0 1 1 0 0 0 0 1 1 0 0 1 1 0 0 9 2 2 4 2 2 4 0 4 0 4 0 2 4 4 6 4 6 2 8 2 8 4 10 4 10 4 10 2 4 2 4 1 6 2 6 1 4 2 5 3 2 1 1 2 5 7 2 1 9 7");
        s[5] = new String("3 5 2 1 0 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 0 1 12 0 6 1 3 1 3 0 0 10 0 9 3 9 3 10 6 2 6 5 5 5 5 8 6 2 0 5 1 5 1 8 0 5 2 4 3 5 4 6 3 2 3 3 3 7 3 8 3 4 2 2 3 2 4 3 2 2 7 2 4 7 9");
        s[6] = new String("3 5 2 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 0 1 1 1 0 0 0 1 1 0 0 0 1 1 0 0 0 1 1 0 0 0 1 1 1 0 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 10 1 0 0 1 0 5 0 1 0 5 1 6 9 0 10 1 10 1 10 5 10 5 9 6 4 0 5 1 5 1 6 0 4 6 5 5 5 5 6 6 4 1 1 1 1 4 3 1 4 6 1 1 8 9");
        s[7] = new String("4 5 2 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1 1 0 0 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 12 5 0 3 2 5 0 7 2 7 2 10 2 3 2 0 2 0 2 2 4 2 4 0 6 0 6 5 5 5 5 10 6 10 6 8 4 8 4 10 2 3 3 4 3 6 3 7 3 5 2 3 2 1 4 2 1 4 7 2 3 8 2 2 5 6");
        s[8] = new String("3 5 2 1 1 1 0 0 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 30 0 3 1 0 1 0 3 2 3 2 5 1 5 1 7 2 7 2 9 0 9 0 10 3 2 5 3 4 3 4 4 5 4 5 3 6 3 6 2 5 7 4 6 5 7 4 8 5 8 5 7 6 7 6 6 5 6 5 8 5 4 5 2 5 0 5 1 6 0 4 1 4 9 4 10 4 10 5 9 6 9 6 10 6 1 6 0 6 3 5 3 6 7 5 7 6 8 2 9 2 9 2 9 1 9 1 8 2 2 2 1 2 1 2 1 1 1 1 2 2 5 2 5 1 2 5 9 2 3 1 2 3 9 2 2 5 8");
        s[9] = new String("3 7 2 1 1 0 0 1 1 0 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 0 0 1 1 6 8 1 6 1 4 1 2 1 1 2 1 4 2 5 4 5 6 5 8 5 9 4 9 2 6 2 3 4 1 2 3 1 3 3 2 2 3 2 3 3 2 4 3 10");

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
