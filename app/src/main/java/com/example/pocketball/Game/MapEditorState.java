package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.service.quicksettings.Tile;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class MapEditorState implements IState {
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 6;
    public static final int TILE_EMPTY = 0;
    public static final int TILE_FILL = 1;

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
    private Button Add_Button;
    private Button Remove_Button;
    private int Pressed_Button = playerbutton;

    private Point start,end;

    private long m_LastTouch = System.currentTimeMillis();
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
        Add_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.plussample),map.tile_size/2,map.tile_size/2);
        Remove_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size/2,map.tile_size/2);

        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        Save_Button.SetPosition(map.tile_size/2, AppManager.getInstance().size.y/2);
        Reset_Button.SetPosition(map.tile_size/2, AppManager.getInstance().size.y - map.tile_size/2);
        Tile_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 - map.tile_size*2 - map.tile_size/2);
        Wall_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 - map.tile_size);
        Player_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 + map.tile_size);
        Enemy_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/2 + map.tile_size*2 + map.tile_size/2);
        //Add_Button.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y - map.tile_size);
       // Remove_Button.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y - map.tile_size);

        start = new Point(-1,-1);
        end = new Point(-1,-1);
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
        if(Pressed_Button == wallbutton){
            map.Drawtouch(canvas);
        }
        GoBack_Button.Draw(canvas);
        Save_Button.Draw(canvas);
        Reset_Button.Draw(canvas);
        Tile_Button.Draw(canvas);
        Wall_Button.Draw(canvas);
        Player_Button.Draw(canvas);
        Enemy_Button.Draw(canvas);


        if(Pressed_Button == enemybutton){
            Add_Button.Draw(canvas);
            Remove_Button.Draw(canvas);
        }
        if(Pressed_Button == wallbutton){
            Remove_Button.Draw(canvas);
        }

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
            //파일 세이브
            File write = new File("file.bin");
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream("file.bin");
                fos.write(0);
                //fos.write(map.player.GetX());
                //fos.write(map.player.GetY());

            }catch (Exception e){
                e.printStackTrace();
            }

            if(fos != null){
                try{
                    fos.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Reset_Button.m_rect)){
           map.Reset();
           Pressed_Button = playerbutton;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Tile_Button.m_rect)){
            Pressed_Button = tilebutton;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Wall_Button.m_rect)){
            Pressed_Button = wallbutton;
            start.set(-1,-1); end.set(-1,-1);
            Remove_Button.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y - map.tile_size/3);
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Player_Button.m_rect)){
            Pressed_Button = playerbutton;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Enemy_Button.m_rect)){
            Pressed_Button = enemybutton;
            Add_Button.SetPosition(AppManager.getInstance().size.x/2 - map.tile_size/2,AppManager.getInstance().size.y - map.tile_size/3);
            Remove_Button.SetPosition(AppManager.getInstance().size.x/2 + map.tile_size/2,AppManager.getInstance().size.y - map.tile_size/3);
        }
        switch (Pressed_Button){
            case tilebutton:
                if(System.currentTimeMillis() - m_LastTouch>250) {
                    m_LastTouch = System.currentTimeMillis();
                    for (int i = 0; i < TILE_HEIGHT; i++)
                        for (int j = 0; j < TILE_WIDTH; j++) {
                            if (CollisionManager.CheckPointtoBox(_x, _y, map.tiles[i][j].m_rect)) {
                                if (map.tiles[i][j].prop == TILE_EMPTY) {
                                    map.tiles[i][j].prop = TILE_FILL;
                                    break;
                                }
                                if (map.tiles[i][j].prop == TILE_FILL) {
                                    map.tiles[i][j].prop = TILE_EMPTY;
                                    break;
                                }
                            }
                        }
                }
                break;
            case wallbutton:
                if(System.currentTimeMillis() - m_LastTouch>250) {
                    m_LastTouch = System.currentTimeMillis();

                    for (int i = 0; i < TILE_HEIGHT + 1; i++)
                        for (int j = 0; j < TILE_WIDTH + 1; j++) {
                            if (CollisionManager.CheckPointtoBox(_x, _y, map.touch_point[i][j])) {
                                if (start.x == -1 && start.y == -1) {
                                    start.set(j, i);
                                    break;
                                }
                                if (start.x != -1 && start.y != -1) {
                                    end.set(j, i);
                                    if(start == end)
                                        break;
                                    map.Wall_list.add(new Wall(start, end));
                                    start.set(-1, -1);
                                    end.set(-1, -1);
                                    break;
                                }
                            }
                        }
                    if (CollisionManager.CheckPointtoBox(_x, _y, Remove_Button.m_rect)) {
                        if (map.Wall_list.size() > 0)
                            map.Wall_list.remove(map.Wall_list.size() - 1);
                    }
                }
                break;
            case playerbutton:
                    if(CollisionManager.CheckPointtoBox(_x,_y,map.player.m_rect)){
                        map.player.SetPosition(_x,_y);
                    }
                break;
            case enemybutton:
                      for(Ball enem : map.enemies){
                          if(CollisionManager.CheckPointtoBox(_x,_y,enem.m_rect)){
                             enem.SetPosition(_x,_y);
                           break;
                          }
                      }
                    if(System.currentTimeMillis() - m_LastTouch>250) {
                        m_LastTouch = System.currentTimeMillis();


                        if (CollisionManager.CheckPointtoBox(_x, _y, Remove_Button.m_rect)) {
                            if (map.enemies.size() > 0)
                                map.enemies.remove(map.enemies.size() - 1);
                        }
                        if (CollisionManager.CheckPointtoBox(_x, _y, Add_Button.m_rect)) {
                            if (map.enemies.size() < 5)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemysample), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                        }
                    }
                break;
        }
        return true;
    }
}
