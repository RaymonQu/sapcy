package com.mygdx.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {
    
    public static final float FRAME_LENGTH = .2f;
    public static final int OFFSET = 8;
    public static final int SIZE = 32;

    private static Animation<TextureRegion> anim;
    private float x;
    private float y;
    private float stateTime;
    private boolean toRemove;

    public Explosion(float x, float y){
        toRemove = false;
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        stateTime = 0;
        anim = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture("explosion.png"), SIZE, SIZE)[0]);
    }


    public void update(float delta){
        stateTime += delta;
        if(anim.isAnimationFinished(stateTime)){
            toRemove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(anim.getKeyFrame(stateTime), x, y);
    }


    public boolean isToRemove() {
        return toRemove;
    }
}
