package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

public class GameMenuState implements IState {

    private Background background;
    private Button gamestart;
    private Button mapeditor;
    float rotate =0.0f;
    @Override
    public void Init() {
        int display_sizeX = AppManager.getInstance().size.x/2;
        int display_sizeY = AppManager.getInstance().size.y/2;
        background = new Background(AppManager.getInstance().getBitmap(R.drawable.sampleimage));
        gamestart = new Button(AppManager.getInstance().getBitmap(R.drawable.gamestartsample),display_sizeX/2,display_sizeY/2);
        mapeditor = new Button(AppManager.getInstance().getBitmap(R.drawable.mapeditorsample),display_sizeX/2,display_sizeY/2);


        gamestart.SetPosition(display_sizeX,display_sizeY);
        mapeditor.SetPosition(display_sizeX,display_sizeY+display_sizeY/2);
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {
        // gamestart.Rotate(rotate);
        // rotate +=1;
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
           // GameState G = new GameState();
           // G.stagename = "save.txt";
           // AppManager.getInstance().getGameView().ChangeGameState(G);
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,mapeditor.m_rect)){
            AppManager.getInstance().getGameView().ChangeGameState(new MapEditorState()); //맵 툴 state로 넘어감



        }

        return false;
    }
}
