package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

public class GameState implements IState {
    Map map;
    private Button GoBack_Button;
    @Override
    public void Init() {
        map = new Map(false);
        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size,map.tile_size);
        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        //임시......
        map.player.SetPosition(AppManager.getInstance().size.x/2-map.tile_size*2,AppManager.getInstance().size.y/2);
        map.Wall_list.add(new Wall(new Point(0,0),new Point(10,0)));
        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemysample),map.player.GetX()+map.tile_size*2,map.player.GetY(),map.tile_size/2));
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
        return false;
    }
}
