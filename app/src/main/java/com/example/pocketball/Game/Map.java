package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.R;

import java.util.ArrayList;

public class Map {
    public static final int TILE_EMPTY = 0;
    public static final int TILE_FILL = 1;
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 6;
    private int Tile[][];
    private Bitmap Tile_Image;
    private Bitmap Player_Image;
    private Bitmap Enemy_Image;
    public int tile_size;              //화면 비율에 따른 화면 사이즈
    private int pivotX, pivotY;         //타일 좌측상단 PIVOT
    private Rect tmp_rect;              //그릴때 쓰는 임시Rect
    public Rect[][] touch_point;       //맵툴 벽 생성시 사용할 터치 포인트
    public Point player;
    public ArrayList<Point> enemies = new ArrayList<Point>();
    public ArrayList<Wall> Wall_list = new ArrayList<Wall>();
    private Paint wallpaint;
    private boolean Is_Tool = false;
    public Map(boolean istool){
        Is_Tool = istool;

        //이미지 세팅
        Tile_Image = AppManager.getInstance().getBitmap(R.drawable.sampletile);
        Player_Image = AppManager.getInstance().getBitmap(R.drawable.playersample);
        Enemy_Image = AppManager.getInstance().getBitmap(R.drawable.enemysample);
        tile_size = AppManager.getInstance().size.x / 100 * 80 / 10;        //화면 가로모드 기준 가로 크기 80%에 타일 배치 /10(10개)
        pivotX = AppManager.getInstance().size.x / 2 - tile_size * 4 - tile_size / 2;
        pivotY = AppManager.getInstance().size.y / 2 - tile_size * 2 - tile_size / 2;
        tmp_rect = new Rect(0,0,0,0);

        //벽 색 지정
        wallpaint = new Paint();
        wallpaint.setStrokeWidth(5f);
        wallpaint.setStyle(Paint.Style.FILL);
        wallpaint.setColor(Color.YELLOW);

        if(Is_Tool){ //맵툴
            Tile = new int[TILE_HEIGHT][TILE_WIDTH];
            for(int i=0;i<TILE_HEIGHT;i++){
                for (int j=0;j<TILE_WIDTH;j++){
                    Tile[i][j] = TILE_FILL;
                }
            }
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
            player = new Point(AppManager.getInstance().size.x/2,AppManager.getInstance().size.y/2);
        }
        else{   //맵
            //맵정보(타일,벽) 가져오기

            //공(플레이어)위치 가져오기

            //공(적) 위치 가져오기
        }

    }

    public void Draw(Canvas canvas) {
        //타일 그리기
        for (int i = 0; i < TILE_HEIGHT; i++) {
            for (int j = 0; j < TILE_WIDTH; j++) {
                if (Tile[i][j] == TILE_FILL) {
                    tmp_rect.set(pivotX + (tile_size * j) - tile_size / 2, pivotY + (tile_size * i) - tile_size / 2,
                            pivotX + (tile_size * j) + tile_size / 2, pivotY + (tile_size * i) + tile_size / 2);
                    canvas.drawBitmap(Tile_Image, null, tmp_rect, null);
                }
            }
        }
        //벽(선) 그리기
        for(Wall walls : Wall_list){
            canvas.drawLine(walls.start.x,walls.start.y,walls.end.x,walls.end.y,wallpaint);
        }
        //공(적) 그리기
        for(Point enems : enemies){
            tmp_rect.set(enems.x - tile_size / 4,enems.y - tile_size / 4 ,enems.x +tile_size/4,enems.y+tile_size/4);
            canvas.drawBitmap(Enemy_Image,null,tmp_rect,null);
        }
        //공(플레이어) 그리기
        tmp_rect.set(player.x - tile_size/4,player.y - tile_size / 4 ,player.x +tile_size/4,player.y+tile_size/4);
        canvas.drawBitmap(Player_Image,null,tmp_rect,null);
    }
}

