package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class GameLevelState implements IState {
    private Background background;
    private Button GoBack_Button;
    public ArrayList<Button> Map_list= new ArrayList<Button>();
    public int tile_size = AppManager.getInstance().size.x / 100 * 80 / 10;        //화면 가로모드 기준 가로 크기 80%에 타일 배치 /10(10개)
    private int pivotX= AppManager.getInstance().size.x / 2 - tile_size * 4 - tile_size / 2;
    private int pivotY= AppManager.getInstance().size.y / 2 - tile_size * 2 - tile_size / 2;
    int display_sizeX = AppManager.getInstance().size.x/2;
    int display_sizeY = AppManager.getInstance().size.y/2;

    private Button select_title;

    @Override
    public void Init() {
        background = new Background(AppManager.getInstance().getBitmap(R.drawable.level_select_background));
        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),tile_size,tile_size);
        select_title = new Button(AppManager.getInstance().getBitmap(R.drawable.level_select_title),(int)(display_sizeX*0.6),(int)(display_sizeY*0.3));
        GoBack_Button.SetPosition(tile_size/2, tile_size/2);
        select_title.SetPosition(display_sizeX, (int)(display_sizeY*0.2));
        int i=0;
        int j=0;
      while(true){
          String s = "custom"+i+".txt";
         File file = new File(AppManager.getInstance().context.getFilesDir(),s);
         if(file.exists() == true){

              Map_list.add(new Button(AppManager.getInstance().getBitmap(R.drawable.enemysample),tile_size,tile_size));
              Map_list.get(i).SetPosition(pivotX +tile_size*i, pivotY +tile_size*j + (int)(display_sizeY*0.2));
              i++;
         }
         else
             break;
          if(i>10)
              j=1;
          }

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
        for(Button maps : Map_list)
          maps.Draw(canvas);
        select_title.Draw(canvas);
        GoBack_Button.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int _x = (int) event.getX();
        int _y = (int) event.getY();
        if(CollisionManager.CheckPointtoBox(_x,_y,GoBack_Button.m_rect))
            AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
        for(int i=0;i<Map_list.size();i++)
        if(CollisionManager.CheckPointtoBox(_x,_y,Map_list.get(i).m_rect)){
            GameState G = new GameState();
            G.stagename = "custom" + i+".txt";
            AppManager.getInstance().getGameView().ChangeGameState(G);
        }
        return false;
    }
}
