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
    private Button Heart;
    private Background Level_Background;
    public String stagename;
    private Power power;
    public long g_PrevTime = 0;
    public float deltaX = 0.f, deltaY = 0.f;
    public boolean g_ApplyForceBool = false;
    public int life = 0;
    @Override
    public void Init() {
        map = new Map(false);
        GoBack_Button = new Button(AppManager.getInstance().getBitmap(R.drawable.gobackbuttonsample),map.tile_size,map.tile_size);
        GoBack_Button.SetPosition(map.tile_size/2, map.tile_size/2);
        Heart = new Button(AppManager.getInstance().getBitmap(R.drawable.heart),(int)(map.tile_size*0.7),(int)(map.tile_size*0.7));
        power = new Power(AppManager.getInstance().getBitmap(R.drawable.power),map.player.GetX(), map.player.GetY(), map.player.radius);
        Level_Background = new Background(AppManager.getInstance().getBitmap(R.drawable.level_background));
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
                if(i==0)
                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_1),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));
                else if(i==1)
                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_2),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));
                else if(i==2)
                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_3),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));
                else if(i==3)
                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_4),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));
                else if(i==4)
                    map.enemies.add(new Ball(AppManager.getInstance().getBitmap(R.drawable.enermy_5),Integer.parseInt(array[array_index++]),Integer.parseInt(array[array_index++]),map.tile_size/2));

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
            }
        }
        map.player.UpDate(eTime);
        for(int i = 0; i < map.enemies.size(); ++i) {
            if(map.enemies.get(i) != null) {
                map.enemies.get(i).UpDate(eTime);
            }
        }
        CheckEmptyTileToBall();

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
            Heart.SetPosition(map.pivotX+map.tile_size/2*(i-1)-map.tile_size/4,map.pivotY-(int)(map.tile_size * 0.5));
            Heart.Draw(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*if(Math.abs(map.player.m_VelX) > 0.01 || Math.abs(map.player.m_VelY) > 0.01)
            return true;
        for(int i = 0; i < map.enemies.size(); ++i)
        {
            if(Math.abs(map.enemies.get(i).m_VelX) > 0.01 || Math.abs(map.enemies.get(i).m_VelY) > 0.01)
                return true;
        }*/
        int _x = (int)event.getX();
        int _y = (int)event.getY();
        if(CollisionManager.CheckPointtoBox(_x,_y,GoBack_Button.m_rect)){
            AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (CollisionManager.CheckPointtoBox(_x, _y, map.player.m_rect)) {
                power.SetPosition(map.player.GetX(), map.player.GetY());
                power.touchevent = true;
            }
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(power.touchevent == true) {
                deltaX = (float)map.player.GetX() - (float)_x;
                deltaY = (float)map.player.GetY() + 10 - (float)_y;
                //if(dx < 0) dx = -dx;
                //if(dy < 0) dy = -dy;
                double radian = Math.atan2(deltaX ,deltaY);
                float degree = (float) (180 / Math.PI * radian);

                power.degree = (int)degree;
                power.radius = (int)Math.sqrt(Math.pow((float)Math.abs(map.player.GetX() - _x),2) + Math.pow(Math.abs((float)map.player.GetY() - _y),2));
                if(power.radius > 200) power.radius = 200;
                power.SetRadius(power.radius);
            }
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            power.touchevent = false;
            //power.radius = 10;
            deltaX = (float)map.player.GetX() - (float)_x;
            deltaY = (float)map.player.GetY() - (float)_y;
            double len = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY, 2));
            if(len > 300)
            {
                deltaX = deltaX / (float)len * 300.f;
                deltaY = deltaY / (float)len * 300.f;
                //System.out.println(deltaX + " " + deltaY);
            }
            g_ApplyForceBool = true;

            // 여기서 radius : 힘
            // degree : 각도
            // 여기서 작업하세용~
        }
        return true;
    }
    public void CheckEmptyTileToBall()
    {
        //System.out.println(map.player.GetX() + ",   " +map.player.GetY());
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
                    AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
                }
                for(int k = 0; k < map.enemies.size(); ++k)
                {
                    if(CollisionManager.CheckPointtoBox(map.enemies.get(k).GetX(),map.enemies.get(k).GetY(), map.tiles[i][j].m_rect))//적 사망
                    {
                        map.enemies.remove(map.enemies.get(k));
                    }
                }
            }
        }
        //ringoutcheck
        if(260 > map.player.GetX() || map.player.GetX() > 2150 || 140 > map.player.GetY() || map.player.GetY() > 1260)
            AppManager.getInstance().getGameView().ChangeGameState(new GameMenuState());
        for(int i = 0; i < map.enemies.size(); ++i)
        {
            if(260 > map.enemies.get(i).GetX() || map.enemies.get(i).GetX() > 2150 || 140 > map.enemies.get(i).GetY() || map.enemies.get(i).GetY() > 1260)
                map.enemies.remove(map.enemies.get(i));
        }
    }

}
