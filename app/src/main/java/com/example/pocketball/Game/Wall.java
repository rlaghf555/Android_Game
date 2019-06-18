package com.example.pocketball.Game;

import android.graphics.Point;
import android.support.v4.math.MathUtils;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.SoundManager;

public class Wall {
    Point start;
    Point end;
    Point start_index;
    Point end_index;
    double normalX;
    double normalY;
    public Wall(Point m_start, Point m_end){
        start = new Point();
        end = new Point();
        start_index= new Point();
        end_index= new Point();
        start_index.set(m_start.x,m_start.y);
        end_index.set(m_end.x,m_end.y);
        int tile_size = AppManager.getInstance().size.x / 100 * 80 / 10;
        int pivotX = AppManager.getInstance().size.x / 2 - tile_size * 4 - tile_size / 2;
        int pivotY = AppManager.getInstance().size.y / 2 - tile_size * 2 - tile_size / 2;
        start.x = pivotX - tile_size/2 + tile_size*m_start.x;
        start.y = pivotY - tile_size/2 + tile_size*m_start.y;
        end.x = pivotX - tile_size/2 + tile_size*m_end.x;
        end.y = pivotY - tile_size/2 + tile_size*m_end.y;
        double deltaX, deltaY, length;
        deltaX = end.x - start.x;
        deltaY = end.y - start.y;
        length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        deltaX = deltaX / length;
        deltaY = deltaY / length;
        normalX = -deltaY;
        normalY = deltaX;

    }
    public boolean BallToWallCollision(Ball ball)
    {
        double a, b, c ,A, B, R;
        double d;
        R = ball.radius;
        A = ball.GetX();
        B = ball.GetY();
        //(x - a)^2 + (y - b)^2 = r^2
        if(end.x == start.x) {//x = a꼴
           double T;
           T = end.x;
           a = 1.0;
           b = -2 * B;
           c = (T * T) - (2 * T * A) + (A * A) + (B * B) - (R * R);
           d = b * b - (4 * a * c);
           if(d < 0)
               return false;
           else {
               double yposp = (-b + Math.sqrt(d)) / (2 * a);
               double yposm = (-b - Math.sqrt(d)) / (2 * a);
               if(start.y > end.y) {
                    if((end.y < yposp && yposp < start.y) || (end.y < yposm && yposm < start.y)) {
                        ApplyReflectVel(ball, 1.0, 0.0, -start.x);
                        return true;
                    }
               }
               else {
                   if((start.y < yposp && yposp < end.y) || (start.y < yposm && yposm < end.y)) {
                       ApplyReflectVel(ball, 1.0, 0.0, -start.x);
                       return true;
                   }
               }
           }
        }
        else {
            double M, D;
            M = (end.y - start.y) / (end.x - start.x);
            D = start.y - M * start.x;
            a = M * M + 1;
            b = 2 * M * (D - B) - 2 * A;
            c = (A * A) + Math.pow(D - B, 2) - (R * R);
            d = b * b - (4 * a * c);
            if(d < 0)
                return false;
            else {
                double xpos = (-b + Math.sqrt(d)) / (2.0 * a);
                if(start.x > end.x) {
                    if(end.x < xpos && xpos < start.x) {
                        ApplyReflectVel(ball, M, -1.0, D);
                        return true;
                    }
                }
                else {
                    if(start.x < xpos && xpos < end.x) {
                        ApplyReflectVel(ball, M, -1.0, D);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void ApplyReflectVel(Ball ball, double a, double b, double c)
    {
        double ReflectX = 0.0, ReflectY = 0.0;
        //내적하기.
        double cos = (Math.sqrt(Math.pow(ball.m_VelX, 2) + Math.pow(ball.m_VelY, 2)) * Math.sqrt(Math.pow(normalX, 2) + Math.pow(normalY, 2))) / (normalX * ball.m_VelX + normalY * ball.m_VelY);
        double length = (ball.radius) - Math.abs((a * ball.GetX() + b * ball.GetY() + c) / (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))));
       // System.out.println(ball.radius + "   " + length);
        if(cos > 0)//노말 그대로 사용
        {
            ReflectX = ball.m_VelX - 2 * (ball.m_VelX * normalX + ball.m_VelY * normalY) * normalX;
            ReflectY = ball.m_VelY - 2 * (ball.m_VelX * normalX + ball.m_VelY * normalY) * normalY;
            ball.SetPosition(ball.GetX() - (int)(normalX * length * 2.0), ball.GetY() -(int)(normalY * length* 2.0));
        }
        else if (cos <= 0)//노말 반대로 사용
        {
            ReflectX = ball.m_VelX - 2 * (ball.m_VelX * -normalX + ball.m_VelY * -normalY) * -normalX;
            ReflectY = ball.m_VelY - 2 * (ball.m_VelX * -normalX + ball.m_VelY * -normalY) * -normalY;
            ball.SetPosition(ball.GetX() - (int)(-normalX * length* 2.0), ball.GetY() - (int)(-normalY * length* 2.0));
        }
        ball.SetVel((float)ReflectX, (float)ReflectY);

        double pow = Math.sqrt(Math.pow(ReflectX,2)+Math.pow(ReflectY,2));
        pow/=300;
        pow =  MathUtils.clamp(pow,0,1);
        float tmp= ball.GetX()/AppManager.getInstance().size.x;
        float s_left=0,s_right=0;
        if(tmp<0.5) {
            s_left = tmp;
            s_right = -tmp;
        }
        else if(tmp>=0.5){
            s_left = -tmp;
            s_right = tmp;
        }
        SoundManager.getInstance().play(1,(float)pow+s_left,(float)pow+s_right);
    }
}
