package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.GraphicObject;

public class Ball extends GraphicObject {
    public Rect m_rect;
    public int radius;
    public float m_VelX, m_VelY;
    public float m_AccX, m_AccY;
    public float m_CoefFrict;
    //기타 등등 마찰, 어쩌구 저쩌구
    public Ball(Bitmap bitmap,int posx, int posy, int diameter) {   //위치 좌표, 지름
        super(bitmap);
        m_x=posx;
        m_y=posy;
        radius = diameter/2;
        m_rect = new Rect(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
        m_CoefFrict = 5.f;
    }

    @Override
    public void SetPosition(int x, int y) {
        m_x=x;
        m_y=y;
        m_rect.set(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
    }

    @Override
    public void Draw(Canvas canvas) {

        canvas.drawBitmap(m_bitmap,null,m_rect,null);
    }

    public void UpDate(float eTime)
    {
        float magVel = (float)Math.sqrt(m_VelX * m_VelX + m_VelY * m_VelY);
        float velX = m_VelX / magVel;
        float velY = m_VelY / magVel;
        float fricX = -velX;
        float fricY = -velY;

        float friction = m_CoefFrict * 9.8f;
        fricX *= friction;
        fricY *= friction;

        float epsilon = 1.f;

        if(magVel < epsilon)//epsilon
        {
            m_VelX = 0.f;
            m_VelY = 0.f;
        }
        else
        {
            float afterVelX = m_VelX + fricX * eTime;
            float afterVelY = m_VelY + fricY * eTime;//eTime추가

            if(afterVelX * m_VelX < 0.f)
                m_VelX = 0.f;
            else
                m_VelX = afterVelX;
            if(afterVelY * m_VelY < 0.f)
                m_VelY = 0.f;
            else
                m_VelY = afterVelY;

        }

        m_VelX = m_VelX + m_AccX * eTime;
        m_VelY = m_VelY + m_AccY * eTime;
        //cal Position

        float afterPosX = (float)m_x + m_VelX * eTime;
        float afterPosY = (float)m_y + m_VelY * eTime;

        SetPosition((int)afterPosX, (int)afterPosY);
    }

    public void ApplyForce(float x, float y, float eTime)
    {
        m_AccX = x;
        m_AccY = y;

        m_VelX = m_VelX + m_AccX * eTime;
        m_VelY = m_VelY + m_AccY * eTime;

        m_AccX = 0.f;
        m_AccY = 0.f;
    }

    public void BallCollision(Ball second, float eTime)
    {
        double len = Math.sqrt(Math.pow(this.m_x - second.m_x, 2) + Math.pow(this.m_y - second.m_y, 2));
        if(len < this.radius * 2)
        {
            float DeltaX, DeltaY;
            float power = 0.25f;

            double v1X = ((double)this.m_VelX - (double)second.m_VelX) * 10.0;
            double v1Y = ((double)this.m_VelY - (double)second.m_VelY) * 10.0;
            double v2X = (double)second.m_x - (double)this.m_x;
            double v2Y = (double)second.m_y - (double)this.m_y;

            double cos = ((v1X * v2X) + (v1Y * v2Y)) / (Math.sqrt(Math.pow(v1X, 2) + Math.pow(v1Y, 2)) * Math.sqrt(Math.pow(v2X, 2) + Math.pow(v2Y, 2)));
            double Force = Math.sqrt(Math.pow(second.m_VelX - this.m_VelX, 2) + Math.pow(second.m_VelY - this.m_VelY, 2));

            DeltaX = (this.m_x - second.m_x) * power * (float)cos * (float)Force;
            DeltaY = (this.m_y - second.m_y) * power * (float)cos * (float)Force;
            float knockback = 0.05f;

            int firstX = (int)((float)this.m_x + ((this.m_x - second.m_x) * knockback));
            int firstY = (int)((float)this.m_y + ((this.m_y - second.m_y) * knockback));
            int secondX = (int)((float)second.m_x + ((second.m_x - this.m_x) * knockback));
            int secondY = (int)((float)second.m_y + ((second.m_y - this.m_y) * knockback));

            this.SetPosition(firstX, firstY);
            second.SetPosition(secondX, secondY);

            this.ApplyForce(DeltaX, DeltaY , eTime);
            second.ApplyForce(-DeltaX, -DeltaY, eTime);
        }
    }


}
