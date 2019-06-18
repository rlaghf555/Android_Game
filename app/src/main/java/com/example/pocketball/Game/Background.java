package com.example.pocketball.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pocketball.MyFrameWork.AppManager;
import com.example.pocketball.MyFrameWork.GraphicObject;

public class Background extends GraphicObject {
    public Background(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public void Draw(Canvas canvas) {
        canvas.drawBitmap(m_bitmap,null,new Rect(0,0, AppManager.getInstance().size.x, AppManager.getInstance().size.y),null);
    }

}
