package com.example.pocketball.MyFrameWork;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class GraphicObject {
    protected Bitmap m_bitmap;
    protected float rotate;
    protected int m_x;
    protected int m_y;
    public GraphicObject(Bitmap bitmap){
        m_bitmap = bitmap;
        m_x=0;
        m_y=0;
    }
    public void Draw(Canvas canvas){
        canvas.save();
        canvas.rotate(rotate, m_x,m_y);
        canvas.drawBitmap(m_bitmap,m_x,m_y,null);
        canvas.restore();
    }
    public void SetPosition(int x,int y){
        m_x=x;
        m_y=y;
    }
    public void Rotate(float m_rotate){
        rotate = m_rotate;
    }
    public int GetX(){
        return m_x;
    }
    public int GetY(){
        return m_y;
    }
    public void delete(){
        m_bitmap.recycle();
    }
}
