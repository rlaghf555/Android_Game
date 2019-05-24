package com.example.pocketball.Game;

import android.graphics.Point;

public class Map {
    public static final int TILE_EMPTY = 0;
    public static final int TILE_FILL = 1;
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 5;
    private int Tile[][];

    public Map(){
        Tile = new int[TILE_HEIGHT][TILE_WIDTH];
        for(int i=0;i<TILE_HEIGHT;i++){
            for (int j=0;j<TILE_WIDTH;j++){
                Tile[i][j] = TILE_EMPTY;
            }
        }
    }
}
