package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.service.quicksettings.Tile;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

public class MapEditorState implements IState {
    public static final int nopress = 0;
    public static final int tilebutton = 1;
    public static final int wallbutton = 2;
    public static final int playerbutton = 3;
    public static final int enemybutton = 4;

    private Map map;
    private Button GoBack_Button;
    private Button Tile_Button;
    private Button Wall_Button;
    private Button Player_Button;
    private Button Enemy_Button;
    private Button Save_Button;
    private Button Reset_Button;
    private int Pressed_Button = nopress;

    @Override
    public void Init() {
        map = new Map(true);

        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size,map.tile_size);
        Save_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.savebuttonsample),map.tile_size,map.tile_size);
        Reset_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.resetbuttonsample),map.tile_size,map.tile_size);
        Tile_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.sampletile),map.tile_size,map.tile_size);
        Wall_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.wallsample),map.tile_size,map.tile_size);
        Player_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.playersample),map.tile_size,map.tile_size);
        Enemy_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.enemysample),map.tile_size,map.tile_size);


        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        Save_Button.SetPosition(map.tile_size/2, AppManager.getInstance().size.y/2);
        Reset_Button.SetPosition(map.tile_size/2, AppManager.getInstance().size.y - map.tile_size/2);
        Tile_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 - map.tile_size*2 - map.tile_size/2);
        Wall_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 - map.tile_size);
        Player_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 + map.tile_size);
        Enemy_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 + map.tile_size*2 + map.tile_size/2);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas) {
        map.Draw(canvas);

        GoBack_Button.Draw(canvas);
        Save_Button.Draw(canvas);
        Reset_Button.Draw(canvas);
        Tile_Button.Draw(canvas);
        Wall_Button.Draw(canvas);
        Player_Button.Draw(canvas);
        Enemy_Button.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int _x = (int)event.getX();
        int _y = (int)event.getY();
        if(CollisionManager.CheckPointtoBox(_x,_y,GoBack_Button.m_rect)){
            AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Save_Button.m_rect)){

        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Reset_Button.m_rect)){

        }
        switch (Pressed_Button){
            case tilebutton:
                break;
            case wallbutton:
                break;
            case playerbutton:
                break;
            case enemybutton:
                break;
        }
        return false;
    }
}
