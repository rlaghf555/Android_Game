package com.example.pocketball.Game;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.CollisionManager;
import com.example.pocketball.MyFrameWork.IState;
import com.example.pocketball.MyFrameWork.SoundManager;
import com.example.pocketball.R;

import java.io.FileInputStream;

public class GameState implements IState {
    public static final int SAVE_T = 1;
    public static final int SAVE_TP = 2;
    Map map;
    private Button GoBack_Button;
    private Button Heart;
    private Button Stage_clear;
    private Button Stage_fail;
    private Button Stage_clear_button;
    private Button Stage_fail_button;
    private Background Level_Background;
    public String stagename;
    private Power power;
    public long g_PrevTime = 0;
    public float deltaX = 0.f, deltaY = 0.f;
    public boolean g_ApplyForceBool = false;
    public int life = 0;

    private boolean Stage_clear_flag = false;
    private boolean Stage_fail_flag = false;

    @Override
    public void Init() {
        if(!SoundManager.getInstance().m_Background.isPlaying())
            SoundManager.getInstance().m_Background.start();
        int display_sizeX = AppManager.getInstance().size.x/2;
        int display_sizeY = AppManager.getInstance().size.y/2;
        map = new Map(false);
        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size,map.tile_size);
        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        Heart = new Button(AppManager.getInstance().getBitmap(R.drawable.heart),(int)(map.tile_size*0.7),(int)(map.tile_size*0.7));
        power = new Power(AppManager.getInstance().getBitmap(R.drawable.power),map.player.GetX(), map.player.GetY(), map.player.radius);
        Level_Background = new Background(AppManager.getInstance().getBitmap(R.drawable.level_background));
        Stage_clear = new Button(AppManager.getInstance().getBitmap(R.drawable.stage_clear),(int)(display_sizeX*1.2),(int)(display_sizeY*1.2));
        Stage_clear.SetPosition(display_sizeX,display_sizeY);
        Stage_fail = new Button(AppManager.getInstance().getBitmap(R.drawable.stage_fail),(int)(display_sizeX*1.2),(int)(display_sizeY*1.2));
        Stage_fail.SetPosition(display_sizeX,display_sizeY);
        Stage_clear_button = new Button(AppManager.getInstance().getBitmap(R.drawable.stage_clear_button),(int)(display_sizeX*0.25),(int)(display_sizeY*0.17));
        Stage_clear_button.SetPosition(display_sizeX,display_sizeY + (int)(display_sizeY * 0.4));
        Stage_fail_button = new Button(AppManager.getInstance().getBitmap(R.drawable.stage_fail_button),(int)(display_sizeX*0.25),(int)(display_sizeY*0.17));
        Stage_fail_button.SetPosition(display_sizeX,display_sizeY + (int)(display_sizeY * 0.4));

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
            int save_i = Integer.parseInt(array[array_index++]);
            int save_j = Integer.parseInt(array[array_index++]);
            int tmpsf = Integer.parseInt(array[array_index++]);
            if(tmpsf == SAVE_T)
                 map.player.SetPosition(map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY());
            else if(tmpsf == SAVE_TP)
                map.player.SetPosition(map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY());

            map.player.save_flag = tmpsf;
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
                int tmp_sf = Integer.parseInt(array[array_index++]);
                save_i = Integer.parseInt(array[array_index++]);
                save_j = Integer.parseInt(array[array_index++]);
                if(tmp_sf == SAVE_T) {
                    if (i == 0)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_1), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                    else if (i == 1)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_2), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                    else if (i == 2)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_3), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                    else if (i == 3)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_4), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                    else if (i == 4)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_5), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                }
                else if(tmp_sf == SAVE_TP){
                    if (i == 0)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_1), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                    else if (i == 1)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_2), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                    else if (i == 2)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_3), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                    else if (i == 3)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_4), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                    else if (i == 4)
                        map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_5), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));

                }
            }
            life = Integer.parseInt(array[array_index++]);
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
    public void Update() {//다


        if(power.touchevent == true)
        {
            power.Rotate(-power.degree + 180);
        }
        if(g_PrevTime == 0 )
        {
            g_PrevTime = System.currentTimeMillis();
            return;
        }
        long CurrentTime = System.currentTimeMillis();
        long ElapsedTime = CurrentTime - g_PrevTime;
        g_PrevTime = CurrentTime;
        float eTime = (float)ElapsedTime / 1000.0f;

        if(g_ApplyForceBool)//ShootPlayer
        {
            map.player.ApplyForce(deltaX, deltaY, 3.f);
            g_ApplyForceBool = false;
        }

        //BallToBallCollision
        for (int i = 0; i < map.enemies.size() ; i++)
        {
            map.player.BallToBallCollision(map.enemies.get(i), eTime);
        }
        for(int i = 0; i < map.enemies.size() - 1;i++)
        {
            for(int j = i + 1; j < map.enemies.size();j++) {
                if(map.enemies.get(i) == null)
                    continue;
                if(map.enemies.get(j) == null)
                    continue;
                if(map.enemies.get(i) == map.enemies.get(j))
                    continue;
                map.enemies.get(i).BallToBallCollision(map.enemies.get(j) , eTime);
            }
        }

        //BallToWallCollision
        if(map.Wall_list.size() > 0) {
            for(int i = 0; i < map.Wall_list.size(); ++i) {
                map.Wall_list.get(i).BallToWallCollision(map.player);
                for(int j = 0; j < map.enemies.size(); ++j)
                    map.Wall_list.get(i).BallToWallCollision(map.enemies.get(j));
            }
        }
        map.player.UpDate(eTime);
        for(int i = 0; i < map.enemies.size(); ++i) {
            if(map.enemies.get(i) != null) {
                map.enemies.get(i).UpDate(eTime);
            }
        }
        CheckEmptyTileToBall();


        if(map.player.moving==false){
            if(map.enemies.size()==0)
                Stage_clear_flag=true;
            else if(life ==0)
                Stage_fail_flag = true;
        }
    }

    @Override
    public void Render(Canvas canvas) {
        Level_Background.Draw(canvas);
        map.Draw(canvas);
        GoBack_Button.Draw(canvas);
        if(power.touchevent == true) {
            power.Draw(canvas);
        }
        for(int i = 1;i<=life;i++){
            Heart.SetPosition(map.pivotX+map.tile_size/2*(i-1)-map.tile_size/4,map.tile_size/3);
            Heart.Draw(canvas);
        }
        if(Stage_clear_flag == true){
            Stage_clear.Draw(canvas);
            Stage_clear_button.Draw(canvas);
        }
        if(Stage_fail_flag == true){
            Stage_fail.Draw(canvas);
            Stage_fail_button.Draw(canvas);
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
            AppManager.getInstance().getGameView().ChangeGameState(new GameLevelState());
        }

        if(Stage_clear_flag == true)
            if(CollisionManager.CheckPointtoBox(_x,_y,Stage_clear_button.m_rect)){
                Stage_clear_flag = false;
                Stage_fail_flag = false;
                // 다음스테이지로
            }
        if(Stage_fail_flag == true)
            if(CollisionManager.CheckPointtoBox(_x,_y,Stage_fail_button.m_rect)){
                Stage_clear_flag = false;
                Stage_fail_flag = false;
                map.enemies.clear();
                map.Wall_list.clear();
                map.player.m_VelX=0; map.player.m_VelY=0;
                map.player.draw=true;
                map.player.moving = false;
                FileInputStream fis = null;
                try{
                    fis = AppManager.getInstance().context.openFileInput(stagename);
                    String s;
                    byte[] data = new byte[fis.available()];
                    while (fis.read(data) != -1) {;}
                    s = new String(data);
                    String[] array = s.split(" ");
                    int array_index =0;
                    int save_i = Integer.parseInt(array[array_index++]);
                    int save_j = Integer.parseInt(array[array_index++]);
                    int tmpsf = Integer.parseInt(array[array_index++]);
                    if(tmpsf == SAVE_T)
                        map.player.SetPosition(map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY());
                    else if(tmpsf == SAVE_TP)
                        map.player.SetPosition(map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY());

                    map.player.save_flag = tmpsf;
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
                        int tmp_sf = Integer.parseInt(array[array_index++]);
                        save_i = Integer.parseInt(array[array_index++]);
                        save_j = Integer.parseInt(array[array_index++]);
                        if(tmp_sf == SAVE_T) {
                            if (i == 0)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_1), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                            else if (i == 1)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_2), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                            else if (i == 2)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_3), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                            else if (i == 3)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_4), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                            else if (i == 4)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_5), map.tiles[save_i][save_j].m_rect.centerX(), map.tiles[save_i][save_j].m_rect.centerY(), map.tile_size / 2));
                        }
                        else if(tmp_sf == SAVE_TP){
                            if (i == 0)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_1), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                            else if (i == 1)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_2), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                            else if (i == 2)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_3), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                            else if (i == 3)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_4), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));
                            else if (i == 4)
                                map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_5), map.touch_point[save_i][save_j].centerX(), map.touch_point[save_i][save_j].centerY(), map.tile_size / 2));

                        }
                    }
                    life = Integer.parseInt(array[array_index++]);
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
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (CollisionManager.CheckPointtoBox(_x, _y, map.player.m_rect)) {
                    power.SetPosition(map.player.GetX(), map.player.GetY());
                    power.touchevent = true;
                }
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (power.touchevent == true) {
                    deltaX = (float) map.player.GetX() - (float) _x;
                    deltaY = (float) map.player.GetY() + 10 - (float) _y;
                    double radian = Math.atan2(deltaX, deltaY);
                    float degree = (float) (180 / Math.PI * radian);

                    power.degree = (int) degree;
                    power.radius = (int) Math.sqrt(Math.pow((float) Math.abs(map.player.GetX() - _x), 2) + Math.pow(Math.abs((float) map.player.GetY() - _y), 2));
                    if (power.radius > 200) power.radius = 200;
                    power.SetRadius(power.radius);
                }
                return true;

            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if(power.touchevent ==  true) {
                    power.touchevent = false;
                    if (life > 0)
                        life -= 1;

                    deltaX = (float) map.player.GetX() - (float) _x;
                    deltaY = (float) map.player.GetY() - (float) _y;
                    double len = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
                    if (len > 300) {
                        deltaX = deltaX / (float) len * 300.f;
                        deltaY = deltaY / (float) len * 300.f;
                    }
                    g_ApplyForceBool = true;
                    return true;
                }
            }

        return true;
    }
    public void CheckEmptyTileToBall()
    {
        for(int i = 0; i < Map.TILE_HEIGHT; ++i)
        {
            for(int j = 0; j < Map.TILE_WIDTH; ++j)
            {
                if (map.tiles[i][j].prop == Map.TILE_FILL)//안비어있으면 넘어감
                {
                    continue;
                }
                if(CollisionManager.CheckPointtoBox(map.player.GetX(),map.player.GetY(), map.tiles[i][j].m_rect ))//플레이어 사망
                {
                    map.player.draw=false;
                    Stage_fail_flag=true;
                   // AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
                    break;
                }
                for(int k = 0; k < map.enemies.size(); ++k)
                {
                    if(CollisionManager.CheckPointtoBox(map.enemies.get(k).GetX(),map.enemies.get(k).GetY(), map.tiles[i][j].m_rect))//적 사망
                    {
                        map.enemies.remove(map.enemies.get(k));
                        if(k!=0)
                            Stage_fail_flag=true;
                    }
                }
            }
        }
        //ringoutcheck
       if(map.map_rect.left > map.player.GetX() || map.player.GetX() > map.map_rect.right || map.map_rect.top > map.player.GetY() || map.player.GetY() > map.map_rect.bottom){
          // AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
           map.player.draw=false;
           Stage_fail_flag=true;
       }
       for(int i = 0; i < map.enemies.size(); ++i)
       {
           if(map.map_rect.left > map.enemies.get(i).GetX() || map.enemies.get(i).GetX() > map.map_rect.right || map.map_rect.top > map.enemies.get(i).GetY() || map.enemies.get(i).GetY() > map.map_rect.bottom) {
               map.enemies.remove(map.enemies.get(i));
               if(i!=0)
                   Stage_fail_flag=true;
           }
       }
    }

}
