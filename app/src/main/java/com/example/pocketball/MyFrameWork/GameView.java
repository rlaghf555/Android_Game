package com.example.pocketball.MyFrameWork;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pocketball.Game.GameStartState;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameViewThread m_thread;
    private IState m_state;
    public GameView(Context context){
        super(context);
        //키 입력 처리를 받기 위해서
        setFocusable(true);
        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());
         getHolder().addCallback(this);
        ChangeGameState(new GameStartState());
        m_thread = new GameViewThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //스레드를 실행 상태로 만듬
        m_thread.setRunning(true);
        //스레드를 실행
        m_thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        m_thread.setRunning(false);
        while(retry){
            try{
                m_thread.join();
                retry=false;
            }catch (InterruptedException e){
                //스레드가 종료되도록 계속 시도
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void Update(){
        m_state.Update();
      //  long GameTime = System.currentTimeMillis();
    }

    protected void myonDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        m_state.Render(canvas);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return m_state.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return m_state.onTouchEvent(event);
    }

    public void ChangeGameState(IState _state){
        if(m_state!=null)
            m_state.Destroy();
        _state.Init();
        m_state=_state;
    }
}
