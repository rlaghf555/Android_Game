package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.GraphicObject;

public class Button extends GraphicObject {
    private int width, height;
    public Rect m_rect;
    public Button(Bitmap bitmap,int m_width, int m_height) {
        super(bitmap);
        width = m_width/2;
        height = m_height/2;
        m_rect = new Rect(0,0,0,0);
        m_rect.set(m_x - width, m_y - height, m_x + width, m_y + height);

    }
    public Button(Bitmap bitmap) {
        super(bitmap);
        width = m_bitmap.getWidth()/2;
        height = m_bitmap.getHeight()/2;
        m_rect = new Rect(0,0,0,0);
    }

    @Override
    public void SetPosition(int x, int y) {
        m_x=x;
        m_y=y;
        m_rect.set(m_x - width, m_y - height, m_x + width, m_y + height);

    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap,null,m_rect,null);
    }
}
