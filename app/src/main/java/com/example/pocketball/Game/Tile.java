package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.GraphicObject;
import com.example.pocketball.R;

public class Tile extends GraphicObject {
    public static final int TILE_EMPTY = 0;
    public static final int TILE_FILL = 1;

    public int prop;
    public Rect m_rect;
    public int tile_size;              //화면 비율에 따른 화면 사이즈
    private int pivotX, pivotY;         //타일 좌측상단 PIVOT
    public Tile() {
        super(AppManager.getInstance().getBitmap(R.drawable.sampletile));
        prop = 1;
        tile_size = AppManager.getInstance().size.x / 100 * 80 / 10;        //화면 가로모드 기준 가로 크기 80%에 타일 배치 /10(10개)
        pivotX = AppManager.getInstance().size.x / 2 - tile_size * 4 - tile_size / 2;
        pivotY = AppManager.getInstance().size.y / 2 - tile_size * 2 - tile_size / 2;
        m_rect = new Rect(0,0,0,0);
    }

    @Override
    public void SetPosition(int i, int j) {
        m_rect.set(pivotX + (tile_size * j) - tile_size / 2, pivotY + (tile_size * i) - tile_size / 2,
                pivotX + (tile_size * j) + tile_size / 2, pivotY + (tile_size * i) + tile_size / 2);
    }

    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap,null,m_rect,null);
    }
}
