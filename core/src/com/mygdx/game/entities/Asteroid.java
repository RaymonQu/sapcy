package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.mygdx.game.MyGdxGame;

public class Asteroid extends BasicEnemy{

    private Circle asteroidHitBox;

    public Asteroid(int x, int velocity) {
        super(x, MyGdxGame.getGameHeight(), new Texture("asteroid.png"), velocity, 25, 25);
        asteroidHitBox = new Circle( x, y, 13f);
    }

    @Override
    public void update(float delta){
        super.y -= super.getSpeed() * delta;
        super.update(delta);
        if(super.y <  -super.getTexture().getHeight()){
            super.setToBeRemoved(true);
        }
        asteroidHitBox.setPosition(x, y);
    }


    public Circle getAsteroidHitBox(){
        return asteroidHitBox;
    }
}
