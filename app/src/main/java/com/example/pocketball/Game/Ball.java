package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.math.MathUtils;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.GraphicObject;
import com.example.pocketball.MyFrameWork.SoundManager;

public class Ball extends GraphicObject {
        public Rect m_rect;
        public int radius;
        public float m_VelX, m_VelY;
        public float m_AccX, m_AccY;
        public float m_CoefFrict;
        public boolean m_WallCollision;
        public int tile_i, tile_j;
        public int save_flag =0;
        public boolean draw= true;
        public boolean moving;
        //기타 등등 마찰, 어쩌구 저쩌구
        public Ball(Bitmap bitmap,int posx, int posy, int diameter) {   //위치 좌표, 지름
            super(bitmap);
            m_x = posx;
            m_y = posy;
            radius = diameter/2;
            m_rect = new Rect(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
            m_CoefFrict = 15.f;
            m_WallCollision = false;
            draw= true;
            moving = false;
        }

    @Override
    public void SetPosition(int x, int y) {
        m_x = x;
        m_y = y;
        m_rect.set(m_x - radius, m_y - radius, m_x + radius, m_y + radius);
    }
    public void tile_pos(int i, int j, int flag){
            tile_i = i;
            tile_j = j;
            save_flag = flag;
    }
    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap,null,m_rect,null);
    }

    public void SetVel(float x, float y)
    {
        m_VelX = x;
        m_VelY = y;
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
            moving = false;
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
       double pow = Math.sqrt(Math.pow((double)x,2)+Math.pow((double)y,2));
       pow/=300;
       pow =  MathUtils.clamp(pow,0,1);
       MathUtils.clamp(m_x,0,AppManager.getInstance().size.x);
       float tmp= m_x/AppManager.getInstance().size.x;
       float s_left=0,s_right=0;
       if(tmp<0.5) {
           s_left = tmp;
           s_right = -tmp;
       }
       else if(tmp>=0.5){
           s_left = -tmp;
           s_right = tmp;
     }
       moving=true;
       SoundManager.getInstance().play(1,(float)pow+s_left,(float)pow+s_right);
    }

    public void BallToBallCollision(Ball second, float eTime)
    {
        double len = Math.sqrt(Math.pow(this.m_x - second.m_x, 2) + Math.pow(this.m_y - second.m_y, 2));
        if(len < this.radius * 2)
        {
            float ForceX, ForceY;

            double v1X = ((double)this.m_VelX - (double)second.m_VelX) * 10.0;
            double v1Y = ((double)this.m_VelY - (double)second.m_VelY) * 10.0;
            double v2X = (double)second.m_x - (double)this.m_x;
            double v2Y = (double)second.m_y - (double)this.m_y;

            double DeltaNormalX = v2X / len;
            double DeltaNormalY = v2Y / len;
            float knockback = ((this.radius * 2) - (float)len) / 2.0f;

            int firstX = (int)((float)this.m_x + (-DeltaNormalX * knockback));
            int firstY = (int)((float)this.m_y + (-DeltaNormalY * knockback));
            int secondX = (int)((float)second.m_x + (DeltaNormalX * knockback));
            int secondY = (int)((float)second.m_y + (DeltaNormalY * knockback));

            this.SetPosition(firstX, firstY);
            second.SetPosition(secondX, secondY);

            double cos = ((v1X * v2X) + (v1Y * v2Y)) / (Math.sqrt(Math.pow(v1X, 2) + Math.pow(v1Y, 2)) * Math.sqrt(Math.pow(v2X, 2) + Math.pow(v2Y, 2)));
            double Force = Math.sqrt(Math.pow(second.m_VelX - this.m_VelX, 2) + Math.pow(second.m_VelY - this.m_VelY, 2));

            ForceX = (float)-DeltaNormalX * this.radius * 2 * (float)cos * (float)Force;
            ForceY = (float)-DeltaNormalY * this.radius * 2 * (float)cos * (float)Force;

            this.ApplyForce(ForceX, ForceY , 0.01f);
            second.ApplyForce(-ForceX, -ForceY, 0.01f);
        }
    }
    public double Power(){
        double pow = Math.sqrt(Math.pow((double)m_VelX,2)+Math.pow((double)m_VelX,2));
        return pow;
    }
}
