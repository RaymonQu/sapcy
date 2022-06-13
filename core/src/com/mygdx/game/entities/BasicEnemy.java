package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BasicEnemy {

    private int speed;
    private Texture texture;
    int x;
    int y;
    private boolean toBeRemoved;
    public static int width;
    public static int height;

    public BasicEnemy(int x, int y, Texture pic, int velocity, int width, int height){
        speed = velocity;
        texture = pic;
        this.x = x;
        this.y = y;
        toBeRemoved = false;
        this.width = width;
        this.height = height;
    }

    public void update(float deltaTime){
        if(y > Gdx.graphics.getHeight()){
            toBeRemoved = true;
        }
        if(y < 0){
            toBeRemoved = true;
        }
        if(x > Gdx.graphics.getWidth()){
            toBeRemoved = true;
        }
        if(x < 0){
            toBeRemoved = true;
        }
    }

    public Texture getTexture(){
        return texture;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y, 25, 25);
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public void setToBeRemoved(boolean val){
        toBeRemoved = val;
    }

    public float getSpeed() {
        return speed;
    }

    public int getY(){
        return y;
    }

    public int getX(){
        return x;
    }
}
