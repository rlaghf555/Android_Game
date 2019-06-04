package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.GraphicObject;

public class Power extends GraphicObject {
    public Rect m_rect;
    public int radius;
    public boolean touchevent;
    public int degree;

    public Power(Bitmap bitmap, int posx, int posy, int diameter) {   //위치 좌표, 지름
        super(bitmap);
        m_x=posx;
        m_y=posy;
        radius = diameter/2;
        m_rect = new Rect(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
        touchevent = false;
    }

    @Override
    public void SetPosition(int x, int y) {
        m_x=x;
        m_y=y;
        m_rect.set(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
    }

    public void SetRadius(int radius_)
    {
        radius = radius_;
        m_rect.set(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(rotate, m_x,m_y);
        canvas.drawBitmap(m_bitmap,null,m_rect,null);
        canvas.restore();
    }
}
