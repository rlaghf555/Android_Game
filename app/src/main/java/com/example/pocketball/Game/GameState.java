package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.R;

import java.io.FileInputStream;

public class GameState implements IState {
    Map map;
    private Button GoBack_Button;
    public String stagename;
    @Override
    public void Init() {
        map = new Map(false);
        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size,map.tile_size);
        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        //임시......
        FileInputStream fis = null;
        try{
            fis = AppManager.getInstance().context.openFileInput(stagename);
            String s;
            byte[] data = new byte[fis.available()];
            while (fis.read(data) != -1) {;}
            s = new String(data);
            String[] array = s.split(" ");
            int array_index =0;
            map.player.SetPosition(Integer.parseInt(array[array_index++]), Integer.parseInt(array[array_index++]));
            int index = 2;
            for(int i=0;i<6;i++) {
                for (int j = 0; j < 10; j++) {
                    map.tiles[i][j].prop = Integer.parseInt(array[array_index++]);
                }
            }
            int nextindex =0;
            int walllist_size = Integer.parseInt(array[array_index++]);
            int tmp_index = array_index;
           // for(int i=tmp_index;i<walllist_size+tmp_index;){
           //     map.Wall_list.add(new Wall(new Point(Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++])), new Point(Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]))));
           //     i+=4;
           //     nextindex =array_index+1;
           // }
            for(int i=0;i<walllist_size;i++){
                map.Wall_list.add(new Wall(new Point(Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++])), new Point(Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]))));
            }

            int enemies_size = Integer.parseInt(array[array_index++]);
          //  for(int i=nextindex;i<enemies_size+nextindex;){
          //      map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemysample),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));
          //      nextindex+=2;
          //  }
            for(int i=0;i<enemies_size;i++){
                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enemysample),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(fis != null){
            try{
                fis.close();
            }
            catch (Exception e){
                e.printStackTrace();
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
