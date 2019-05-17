package com.example.pocketball.MyFrameWork;

import android.graphics.Rect;

public class CollisionManager {
    public static boolean CheckBoxtoBox(Rect _rt1, Rect _rt2){
        if(_rt1.intersect(_rt2))
            return true;
        return false;
    }
    public static boolean CheckPointtoBox(float px, float py, Rect _rt1){
        if(px > _rt1.left && px < _rt1.right && py <_rt1.top && py> _rt1.bottom)
            return true;
        return false;
    }
}
