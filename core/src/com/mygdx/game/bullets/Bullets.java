package com.mygdx.game.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullets {

    private int speed;
    private Texture texture;
    float x;
    float y;
    private boolean toBeRemoved;
    private Rectangle bulletCol;

    public Bullets(float x, float y, Texture pic, int velocity){
        speed = velocity;
        texture = pic;
        this.x = x;
        this.y = y;
        toBeRemoved = false;
        bulletCol = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float deltaTime){
        y += speed * deltaTime;
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

        bulletCol.setPosition(x, y);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public Rectangle getBulletCol(){
        return bulletCol;
    }
}
