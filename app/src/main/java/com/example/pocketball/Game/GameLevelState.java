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
    private Button delete_Button;
    public ArrayList<Button> MainMap_list= new ArrayList<Button>();
    public ArrayList<Button> CustomMap_list= new ArrayList<Button>();

    public int tile_size = AppManager.getInstance().size.x / 100 * 80 / 10;        //화면 가로모드 기준 가로 크기 80%에 타일 배치 /10(10개)
    private int pivotX= AppManager.getInstance().size.x / 2 - tile_size * 4 - tile_size / 2;
    private int pivotY= AppManager.getInstance().size.y / 2 - tile_size * 2 - tile_size / 2;
    int display_sizeX = AppManager.getInstance().size.x/2;
    int display_sizeY = AppManager.getInstance().size.y/2;
    int state =0;
    private boolean delete = false;
    private Button select_title;
    private long m_LastTouch = System.currentTimeMillis();
    @Override
    public void Init() {
        background = new Background(AppManager.getInstance().getBitmap(R.drawable.level_select_background));
        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),tile_size,tile_size);
        select_title = new Button(AppManager.getInstance().getBitmap(R.drawable.level_select_title),(int)(display_sizeX*0.6),(int)(display_sizeY*0.3));
        delete_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.delete),tile_size,tile_size);

        GoBack_Button.SetPosition(tile_size/2, tile_size/2);
        select_title.SetPosition(display_sizeX, (int)(display_sizeY*0.2));
        delete_Button.SetPosition(AppManager.getInstance().size.x-tile_size/2, tile_size/2);
        int i=0;
      while(true){
         String s = "Level"+i+".txt";
         File file = new File(AppManager.getInstance().context.getFilesDir(),s);
         if(file.exists() == true){

             MainMap_list.add(new Button(AppManager.getInstance().getBitmap(R.drawable.enemysample),tile_size,tile_size));
             if(i<5)
                MainMap_list.get(i).SetPosition(pivotX +tile_size*i, pivotY + (int)(display_sizeY*0.2));
             else
                 MainMap_list.get(i).SetPosition(pivotX +tile_size*(i-5), pivotY +tile_size + (int)(display_sizeY*0.2));
             i++;

         }
         else{
             break;
         }

      }
      i=0;
        while(true){
            String s = "custom"+i+".txt";
            File file = new File(AppManager.getInstance().context.getFilesDir(),s);
            if(file.exists() == true){
                CustomMap_list.add(new Button(AppManager.getInstance().getBitmap(R.drawable.enemysample),tile_size,tile_size));
                if(i<5)
                  CustomMap_list.get(i).SetPosition(pivotX +tile_size*i, pivotY + (int)(display_sizeY*0.2));
                else
                  CustomMap_list.get(i).SetPosition(pivotX +tile_size*(i-5), pivotY +tile_size + (int)(display_sizeY*0.2));
                i++;
            }
            else {
                break;
            }
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
        if(state == 0){
            for(Button maps : MainMap_list)
                maps.Draw(canvas);
        }
        if(state == 1){
            for(Button maps : CustomMap_list)
                maps.Draw(canvas);
            delete_Button.Draw(canvas);

        }
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

        if(CollisionManager.CheckPointtoBox(_x,_y,GoBack_Button.m_rect)) {
            AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());

        }
        if(CollisionManager.CheckPointtoBox(_x,_y,select_title.m_rect)){
            if(state ==0)
                state=1;
            else state=0;
            return false;
        }
        if(state ==0) {
            for (int i = 0; i < MainMap_list.size(); i++)
                if (CollisionManager.CheckPointtoBox(_x, _y, MainMap_list.get(i).m_rect)) {
                    GameState G = new GameState();
                    G.stagename = "Level" + i + ".txt";
                    AppManager.getInstance().getGameView().ChangeGameState(G);
                    break;
                }
        }
        else if(state ==1) {
            if(System.currentTimeMillis() - m_LastTouch>700) {
                m_LastTouch = System.currentTimeMillis();
            if(CollisionManager.CheckPointtoBox(_x,_y,delete_Button.m_rect)){
                if(delete==false){
                delete = true;
                delete_Button.ChangeImage(AppManager.getInstance().getBitmap(R.drawable.delete_activate));
                }
                else{
                    delete = false;
                    delete_Button.ChangeImage(AppManager.getInstance().getBitmap(R.drawable.delete));
                }
            }
            for (int i = 0; i < CustomMap_list.size(); i++)
                if (CollisionManager.CheckPointtoBox(_x, _y, CustomMap_list.get(i).m_rect)) {
                    if (delete == false) {
                        GameState G = new GameState();
                        G.stagename = "custom" + i + ".txt";
                        AppManager.getInstance().getGameView().ChangeGameState(G);
                        break;
                    } else {
                        File delete_file = new File(AppManager.getInstance().context.getFilesDir() + "/custom" + i + ".txt");
                        if (delete_file.exists())
                            delete_file.delete();
                        for (int j = i; j < CustomMap_list.size(); j++) {
                            File rename_file = new File(AppManager.getInstance().context.getFilesDir() + "/custom" + j + ".txt");
                            int tmp = j + 1;
                            File file_ = new File(AppManager.getInstance().context.getFilesDir() + "/custom" + tmp + ".txt");
                            file_.renameTo(rename_file);
                        }
                        CustomMap_list.remove(CustomMap_list.size() - 1);
                    }
                }
            }
        }
        return false;
    }
}
