package com.example.pocketball.MyFrameWork;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.pocketball.Game.GameState;


public class AppManager {
    private static AppManager s_Instance;
    private GameView m_gameview; //main gameview
    private Resources m_resources;  //main gameview의 resources
    public GameState m_gamestate;
    public Point size = new Point();
    void setGameView(GameView _gameview){
        m_gameview =_gameview;
    }
    void setResources(Resources _resources){
        m_resources = _resources;
    }

    public GameView getGameView(){
        return m_gameview;
    }
    public Resources getResources(){
        return m_resources;
    }
    public Bitmap getBitmap(int r){
        return BitmapFactory.decodeResource(m_resources,r);
    }


    public static AppManager getInstance(){
        if(s_Instance==null){
            s_Instance = new AppManager();
        }
        return s_Instance;
    }
}
