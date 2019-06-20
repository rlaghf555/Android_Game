package com.example.pocketball.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.service.quicksettings.Tile;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.GraphicObject;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.MyFrameWork.SoundManager;
import com.example.pocketball.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

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
    public static final int lifebutton =5;
    public static final int SAVE_T = 1;
    public static final int SAVE_TP = 2;

    private Map map;
    private Button GoBack_Button;
    private Button Tile_Button;
    private Button Wall_Button;
    private Button Player_Button;
    private Button Enemy_Button;
    private Button Save_Button;
    private Button Reset_Button;
    private Button Life_Button;
    private Button Add_Button;
    private Button Remove_Button;
    private Button Heart;
    private int life=5;
    private int Pressed_Button = playerbutton;
    private boolean player_move = false;
    private boolean enemy_move = false;
    private int enemy_num = -1;
    private Point start,end;

    private long m_LastTouch = System.currentTimeMillis();
    @Override
    public void Init() {
        if(!SoundManager.getInstance().m_Background.isPlaying())
            SoundManager.getInstance().m_Background.start();
        map = new Map(true);

        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size,map.tile_size);
        Save_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.savebuttonsample),map.tile_size,map.tile_size);
        Reset_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.resetbuttonsample),map.tile_size,map.tile_size);
        Tile_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.tile),map.tile_size,map.tile_size);
        Wall_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.wall2),map.tile_size,map.tile_size);
        Player_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.playersample),map.tile_size,map.tile_size);
        Enemy_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.enemysample),map.tile_size,map.tile_size);
        Life_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.lifebutton),map.tile_size,map.tile_size);
        Add_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.plussample),map.tile_size/2,map.tile_size/2);
        Remove_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size/2,map.tile_size/2);
        Heart = new Button(AppManager.getInstance().getBitmap(R.drawable.heart),map.tile_size/2,map.tile_size/2);

        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        Save_Button.SetPosition(map.tile_size/2, AppManager.getInstance().size.y/2);
        Reset_Button.SetPosition(map.tile_size/2, AppManager.getInstance().size.y - map.tile_size/2);
        Tile_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/6 *1);
        Wall_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/6 *2);
        Player_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/6 *3);
        Enemy_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/6 *4);
        Life_Button.SetPosition(AppManager.getInstance().size.x - map.tile_size/2, AppManager.getInstance().size.y/6 *5);

        //Add_Button.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y - map.tile_size);
       // Remove_Button.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y - map.tile_size);
        map.player.SetPosition(map.touch_point[3][5].centerX(),map.touch_point[3][5].centerY());
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
        Life_Button.Draw(canvas);
        for(int i = 1;i<=life;i++){
            Heart.SetPosition(map.pivotX+map.tile_size/2*(i-1)-map.tile_size/4,map.pivotY-map.tile_size);
            Heart.Draw(canvas);
        }
        if(Pressed_Button == enemybutton){
            Add_Button.Draw(canvas);
            Remove_Button.Draw(canvas);
        }
        if(Pressed_Button == wallbutton){
            Remove_Button.Draw(canvas);
        }
        if(Pressed_Button == lifebutton){
            Add_Button.Draw(canvas);
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
        if(event.getAction()==event.ACTION_UP){
            enemy_move=false;
            enemy_num= -1;
            player_move=false;
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,GoBack_Button.m_rect)){
            AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Save_Button.m_rect)){
            if(System.currentTimeMillis() - m_LastTouch>1000) {
                m_LastTouch = System.currentTimeMillis();
                //파일 세이브
                int fileindex = 0;
                String filename = "custom" + fileindex+".txt";
                while(true) {
                    File file = new File(AppManager.getInstance().context.getFilesDir(),filename);
                    if (file.exists() == true){
                        fileindex++;
                        filename = "custom" + fileindex+".txt";
                    }
                    else
                        break;
                }
                FileOutputStream fos = null;
                try {
                    fos = AppManager.getInstance().context.openFileOutput(filename, Context.MODE_PRIVATE);
                    String s = new String("");

                    s = s + map.player.tile_i;
                    s = s + " ";
                    s = s + map.player.tile_j;
                    s = s + " ";
                    s = s + map.player.save_flag;
                    s = s + " ";

                    for (int i = 0; i < TILE_HEIGHT; i++) {
                        for (int j = 0; j < TILE_WIDTH; j++) {
                            s = s + map.tiles[i][j].prop + " ";
                        }
                    }

                    s = s + map.Wall_list.size();
                    for (int i = 0; i < map.Wall_list.size(); i++) {
                        s = s + " " + map.Wall_list.get(i).start_index.x + " " + map.Wall_list.get(i).start_index.y;
                        s = s + " " + map.Wall_list.get(i).end_index.x + " " + map.Wall_list.get(i).end_index.y;
                    }

                    s = s + " "+ map.enemies.size();
                    for (int i = 0; i < map.enemies.size(); i++) {
                        s = s + " " + map.enemies.get(i).save_flag;
                        s = s + " " + map.enemies.get(i).tile_i + " " + map.enemies.get(i).tile_j;
                    }
                    s = s + " " + life;
                    fos.write(s.getBytes());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (fos != null) {
                    try {
                        fos.close();
                        AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            for(int i=0;i<100;i++){
                long ttmp = System.currentTimeMillis();
            }
            //map.Reset();

            //AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Reset_Button.m_rect)){
           map.Reset();
           life =5;
           Pressed_Button = playerbutton;
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Tile_Button.m_rect)){
            Pressed_Button = tilebutton;
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Wall_Button.m_rect)){
            Pressed_Button = wallbutton;
            start.set(-1,-1); end.set(-1,-1);
            Remove_Button.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y - map.tile_size/3);
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Player_Button.m_rect)){
            Pressed_Button = playerbutton;
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Enemy_Button.m_rect)){
            Pressed_Button = enemybutton;
            Add_Button.SetPosition(AppManager.getInstance().size.x/2 - map.tile_size/2,AppManager.getInstance().size.y - map.tile_size/3);
            Remove_Button.SetPosition(AppManager.getInstance().size.x/2 + map.tile_size/2,AppManager.getInstance().size.y - map.tile_size/3);
            return true;
        }
        if(CollisionManager.CheckPointtoBox(_x,_y,Life_Button.m_rect)){
            Pressed_Button = lifebutton;
            Add_Button.SetPosition(AppManager.getInstance().size.x/2 - map.tile_size/2,AppManager.getInstance().size.y - map.tile_size/3);
            Remove_Button.SetPosition(AppManager.getInstance().size.x/2 + map.tile_size/2,AppManager.getInstance().size.y - map.tile_size/3);
            return true;
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
                    return true;
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
                    return true;
                }
                break;
            case playerbutton:
                    if(player_move){
                        for (int i = 0; i < TILE_HEIGHT; i++)
                            for (int j = 0; j < TILE_WIDTH; j++) {
                                if(CollisionManager.CheckPointtoBox(_x,_y,map.tiles[i][j].m_rect)){
                                    map.player.SetPosition(map.tiles[i][j].m_rect.centerX(),map.tiles[i][j].m_rect.centerY());
                                    map.player.tile_pos(i,j,SAVE_T);
                                    break;
                                }
                            }
                        for (int i = 0; i < TILE_HEIGHT+1; i++)
                            for (int j = 0; j < TILE_WIDTH+1; j++) {
                                if(CollisionManager.CheckPointtoBox(_x,_y,map.touch_point[i][j])){
                                    map.player.SetPosition(map.touch_point[i][j].centerX(),map.touch_point[i][j].centerY());
                                    map.player.tile_pos(i,j,SAVE_TP);
                                    break;
                                }
                            }
                        return true;
                    }
                    if(CollisionManager.CheckPointtoBox(_x,_y,map.player.m_rect)){
                        player_move = true;

                        return true;
                    }
                break;
            case enemybutton:
                if(!enemy_move){
                    if(event.getAction()==event.ACTION_DOWN) {
                        for (int i = 0; i < map.enemies.size(); i++) {
                            if (CollisionManager.CheckPointtoBox(_x, _y, map.enemies.get(i).m_rect)) {
                                enemy_move = true;
                                enemy_num = i;
                                break;
                            } else {
                                enemy_move = false;
                                enemy_num = -1;

                            }
                        }
                    }
                }
                else {
                    if(event.getAction()==event.ACTION_MOVE){
                        for (int i = 0; i < TILE_HEIGHT; i++)
                            for (int j = 0; j < TILE_WIDTH; j++) {
                                if(CollisionManager.CheckPointtoBox(_x,_y,map.tiles[i][j].m_rect)){
                                    map.enemies.get(enemy_num).SetPosition(map.tiles[i][j].m_rect.centerX(),map.tiles[i][j].m_rect.centerY());
                                    map.enemies.get(enemy_num).tile_pos(i,j,SAVE_T);
                                    break;
                                }
                            }

                        for (int i = 0; i < TILE_HEIGHT+1; i++)
                            for (int j = 0; j < TILE_WIDTH+1; j++) {
                                if(CollisionManager.CheckPointtoBox(_x,_y,map.touch_point[i][j])){
                                    map.enemies.get(enemy_num).SetPosition(map.touch_point[i][j].centerX(),map.touch_point[i][j].centerY());
                                    map.enemies.get(enemy_num).tile_pos(i,j,SAVE_TP);
                                    break;
                                }
                            }
                    }
                    return true;
                }
                    if(System.currentTimeMillis() - m_LastTouch>250) {
                        m_LastTouch = System.currentTimeMillis();


                        if (CollisionManager.CheckPointtoBox(_x, _y, Remove_Button.m_rect)) {
                            if (map.enemies.size() > 0)
                                map.enemies.remove(map.enemies.size() - 1);
                        }
                        if (CollisionManager.CheckPointtoBox(_x, _y, Add_Button.m_rect)) {
                            if (map.enemies.size() < 6){
                                if(map.enemies.size()==0)
                                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemy_1), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                                else if(map.enemies.size()==1)
                                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemy_2), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                                else if(map.enemies.size()==2)
                                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemy_3), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                                else if(map.enemies.size()==3)
                                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemy_4), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                                else if(map.enemies.size()==4)
                                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemy_5), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                                else if(map.enemies.size()==5)
                                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemy_6), AppManager.getInstance().size.x / 2, AppManager.getInstance().size.y / 2, map.tile_size / 2));
                                map.enemies.get(map.enemies.size()-1).tile_pos(3,5,SAVE_TP);

                            }
                        }
                        return true;
                    }
                break;
            case lifebutton:
                if(System.currentTimeMillis() - m_LastTouch>250) {
                    m_LastTouch = System.currentTimeMillis();
                    if (CollisionManager.CheckPointtoBox(_x, _y, Remove_Button.m_rect)) {
                        if (life > 1)
                            life--;
                    }
                    if (CollisionManager.CheckPointtoBox(_x, _y, Add_Button.m_rect)) {
                        if (life < 20)
                            life++;
                    }
                    return true;
                }
                break;
        }
        return true;
    }
}
