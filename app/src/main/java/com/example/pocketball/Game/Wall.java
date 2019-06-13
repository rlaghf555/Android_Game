package com.example.pocketball.Game;

import android.graphics.Point;

import com.example.pocketball.MyFrameWork.AppManager;

public class Wall {
    Point start;
    Point end;
    Point start_index;
    Point end_index;
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
    }
    public void BallToWallCollision(Ball ball)
    {
        //y = Mx + D , (x - A)^2 + (y - B)^2 = T^2
        double M, D, A, B;
        if(end.x == start.x) {
            M = 0.0;
        }
        else
            M = (end.y - start.y) / (end.x - start.x);
        D = start.y - M * start.x;
        A = ball.GetX();
        B = ball.GetY();
        double a = M + 1;
        double b = 2 * M * (D - B) - 2 * A;
        double c = A * A + Math.pow(D - B, 2) - ball.radius * ball.radius;

        double d = b * b - (4 * a * c);
        if(d >= 0)
            System.out.println(" Y = " + M + "x + "+ D );

    }
}
