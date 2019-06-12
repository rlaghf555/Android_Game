package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.GraphicObject;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

public class GameStartState implements IState {

    private Background background;
    @Override
    public void Init() {
        background = new Background(AppManager.getInstance().getBitmap(R.drawable.title));
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
        AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
        return false;
    }
}
