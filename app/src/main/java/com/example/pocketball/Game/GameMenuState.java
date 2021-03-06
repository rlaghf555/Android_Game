package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.MyFrameWork.SoundManager;
import com.example.pocketball.R;

public class GameMenuState implements IState {

    private Background background;
    private Button gamestart;
    private Button mapeditor;
    float rotate =0.0f;
    @Override
    public void Init() {
        if(!SoundManager.getInstance().m_Background.isPlaying())
            SoundManager.getInstance().m_Background.start();
        int display_sizeX = AppManager.getInstance().size.x/2;
        int display_sizeY = AppManager.getInstance().size.y/2;
        background = new Background(AppManager.getInstance().getBitmap(R.drawable.title));
        gamestart = new Button(AppManager.getInstance().getBitmap(R.drawable.title_select_1),(int)(display_sizeX*0.6),(int)(display_sizeY*0.3));
        mapeditor = new Button(AppManager.getInstance().getBitmap(R.drawable.title_select_2),(int)(display_sizeX*0.6),(int)(display_sizeY*0.3));

        gamestart.SetPosition(display_sizeX,display_sizeY + display_sizeY/4);
        mapeditor.SetPosition(display_sizeX,display_sizeY + (int)(display_sizeY*0.6));
        SoundManager.getInstance().m_Background.start();

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Render(Canvas canvas){
        background.Draw(canvas);
        gamestart.Draw(canvas);
        mapeditor.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int _x = (int) event.getX();
        int _y = (int) event.getY();

        if(CollisionManager.CheckPointtoBox(_x,_y,gamestart.m_rect)){
            AppManager.getInstance().getGameView().ChangeGameState(new GameLevelState());  //레벨들 여러개 보이는 state로 넘어감

        }
        if(CollisionManager.CheckPointtoBox(_x,_y,mapeditor.m_rect)){
            AppManager.getInstance().getGameView().ChangeGameState(new MapEditorState()); //맵 툴 state로 넘어감

        }

        return false;
    }
}
