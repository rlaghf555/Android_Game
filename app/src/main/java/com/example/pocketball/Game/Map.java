package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.R;

import java.util.ArrayList;

public class Map {
    public static final int TILE_EMPTY = 0;
    public static final int TILE_FILL = 1;
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 6;
    public Tile tiles[][];
    private Bitmap Tile_Image;
    private Bitmap Player_Image;
    private Bitmap Enemy_Image;
    public int tile_size;              //화면 비율에 따른 화면 사이즈
    public int pivotX, pivotY;         //타일 좌측상단 PIVOT
    private Rect tmp_rect;              //그릴때 쓰는 임시Rect
    public Rect[][] touch_point;       //맵툴 벽 생성시 사용할 터치 포인트
    public Ball player;
    public ArrayList<Ball> enemies = new ArrayList<Ball>();
    public ArrayList<Wall> Wall_list = new ArrayList<Wall>();
    private Paint wallpaint;
    private boolean Is_Tool = false;
    public Rect map_rect;
    public Map(boolean istool){
        Is_Tool = istool;

        //이미지 세팅
        Tile_Image = AppManager.getInstance().getBitmap(R.drawable.tile);
        Player_Image = AppManager.getInstance().getBitmap(R.drawable.player);
        Enemy_Image = AppManager.getInstance().getBitmap(R.drawable.enemysample);
        tile_size = AppManager.getInstance().size.x / 100 * 80 / 10;        //화면 가로모드 기준 가로 크기 80%에 타일 배치 /10(10개)
        pivotX = AppManager.getInstance().size.x / 2 - tile_size * 4 - tile_size / 2;
        pivotY = AppManager.getInstance().size.y / 2 - tile_size * 2 - tile_size / 2;
        tmp_rect = new Rect(0,0,0,0);

        //벽 색 지정
        wallpaint = new Paint();
        wallpaint.setStrokeWidth(20f);
        wallpaint.setStyle(Paint.Style.FILL);
        wallpaint.setColor(Color.YELLOW);

        if(Is_Tool){ //맵툴
            tiles = new Tile[TILE_HEIGHT][TILE_WIDTH];
            for(int i=0;i<TILE_HEIGHT;i++){
                for (int j=0;j<TILE_WIDTH;j++){
                    tiles[i][j] = new Tile();
                    tiles[i][j].prop = TILE_FILL;
                    tiles[i][j].SetPosition(i,j);
                }
            }
            map_rect = new Rect(tiles[0][0].m_rect.left,tiles[0][0].m_rect.top,tiles[0][9].m_rect.right,tiles[5][0].m_rect.bottom);
            //터치 영역, 벽 좌표 지정 전용
            touch_point = new Rect[TILE_HEIGHT+1][TILE_WIDTH+1];
            int touch_size = tile_size/3;
            for(int i=0;i<TILE_HEIGHT+1;i++){
                for (int j=0;j<TILE_WIDTH+1;j++){
                    touch_point[i][j] = new Rect(0,0,0,0);
                    touch_point[i][j].left = pivotX - tile_size / 2 - touch_size + tile_size*j;
                    touch_point[i][j].top = pivotY - tile_size / 2 - touch_size + tile_size*i;
                    touch_point[i][j].right = pivotX - tile_size / 2 + touch_size + tile_size*j;
                    touch_point[i][j].bottom = pivotY - tile_size / 2 + touch_size + tile_size*i;
                }
            }
            player = new Ball(Player_Image,AppManager.getInstance().size.x/2,AppManager.getInstance().size.y/2,tile_size/2);
            player.tile_pos(3,5,2);
        }
        else{   //맵
            //맵정보(타일,벽) 가져오기
            tiles = new Tile[TILE_HEIGHT][TILE_WIDTH];

            for(int i=0;i<TILE_HEIGHT;i++){
                for (int j=0;j<TILE_WIDTH;j++){
                    tiles[i][j] = new Tile();
                    tiles[i][j].prop = TILE_FILL;
                    tiles[i][j].SetPosition(i,j);
                }
            }
            map_rect = new Rect(tiles[0][0].m_rect.left,tiles[0][0].m_rect.top,tiles[0][9].m_rect.right,tiles[5][0].m_rect.bottom);

            touch_point = new Rect[TILE_HEIGHT+1][TILE_WIDTH+1];
            int touch_size = tile_size/3;
            for(int i=0;i<TILE_HEIGHT+1;i++){
                for (int j=0;j<TILE_WIDTH+1;j++){
                    touch_point[i][j] = new Rect(0,0,0,0);
                    touch_point[i][j].left = pivotX - tile_size / 2 - touch_size + tile_size*j;
                    touch_point[i][j].top = pivotY - tile_size / 2 - touch_size + tile_size*i;
                    touch_point[i][j].right = pivotX - tile_size / 2 + touch_size + tile_size*j;
                    touch_point[i][j].bottom = pivotY - tile_size / 2 + touch_size + tile_size*i;
                }
            }
            //공(플레이어)위치 가져오기
            player = new Ball(Player_Image,AppManager.getInstance().size.x/2,AppManager.getInstance().size.y/2,tile_size/2);
            player.tile_pos(3,5,2);
            //공(적) 위치 가져오기
        }

    }
    public void Drawtouch(Canvas canvas){
        Paint touch = new Paint();
        touch.setStrokeWidth(30f);
        touch.setColor(Color.RED);
        for(int i=0;i<TILE_HEIGHT+1;i++){
            for (int j=0;j<TILE_WIDTH+1;j++){
            //canvas.drawRect(touch_point[i][j],touch);
            canvas.drawPoint(touch_point[i][j].centerX(),touch_point[i][j].centerY(),touch);
            }
        }
    }
    public void Draw(Canvas canvas) {
        //타일 그리기
        for (int i = 0; i < TILE_HEIGHT; i++) {
            for (int j = 0; j < TILE_WIDTH; j++) {
                if (tiles[i][j].prop == TILE_FILL) {
                    tiles[i][j].Draw(canvas);
                }
            }
        }
        //벽(선) 그리기
        for(Wall walls : Wall_list){
            if(walls!=null)
            canvas.drawLine(walls.start.x,walls.start.y,walls.end.x,walls.end.y,wallpaint);
        }
        for(int i=0;i<Wall_list.size();i++){
            if(Wall_list.get(i)!=null){
                canvas.drawLine(Wall_list.get(i).start.x,Wall_list.get(i).start.y,Wall_list.get(i).end.x,Wall_list.get(i).end.y,wallpaint);
            }
        }
        //공(적) 그리기
        for(int i=0;i<enemies.size();i++) {
            if (enemies.get(i) != null)
                enemies.get(i).Draw(canvas);
        }
        //공(플레이어) 그리기
        if(player.draw)
        player.Draw(canvas);
    }
    public void Reset(){
        for (int i = 0; i < TILE_HEIGHT; i++) {
            for (int j = 0; j < TILE_WIDTH; j++) {
                tiles[i][j].prop = TILE_FILL;
            }
        }
        player.SetPosition(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y/2);
        Wall_list.clear();
        enemies.clear();
    }

}

