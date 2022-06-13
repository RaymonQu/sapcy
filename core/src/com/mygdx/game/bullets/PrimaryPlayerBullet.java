package com.mygdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;

public class PrimaryPlayerBullet extends Bullets {

    public PrimaryPlayerBullet(float x, float y, int velocity) {
        super(x, y, new Texture("bullet.png"), velocity);
    }

}
