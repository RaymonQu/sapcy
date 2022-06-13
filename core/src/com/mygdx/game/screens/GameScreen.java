package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.bullets.PrimaryPlayerBullet;
import com.mygdx.game.entities.Asteroid;
import com.mygdx.game.tools.Explosion;

import java.util.ArrayList;

import static com.mygdx.game.MyGdxGame.SPEED;

public class GameScreen implements Screen {

    ShapeRenderer shapeRenderer;
    private float deathTimer;
    private Texture img;
    private float x;
    private float y;
    private static final int shipWidth = 50;
    private static final int shipLength = 75;
    private Texture Background;
    private static final float shootWaitTimer = .35f;
    private float shootTimer;
    private int score;
    private float health;
    private Texture hpBar;
    private boolean dead;

    private static final float MIN_ASTEROID_SPAWN_TIMER = .05f;
    private static final float MAX_ASTEROID_SPAWN_TIMER = .1f;

    private Circle playerCircle;

    private float asteroidSpawnTimer;

    private MyGdxGame game;

    private ArrayList<PrimaryPlayerBullet> bullets;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Explosion> explosions;

    private BitmapFont scoreFont;
    private Explosion playerExplosion;

    public GameScreen(MyGdxGame game){
        this.game = game;
        x = MyGdxGame.getGameWidth() / 2 - shipWidth / 2;
        y = 15;
        playerCircle = new Circle(x, y, 25);
        bullets = new ArrayList<PrimaryPlayerBullet>();
        asteroids = new ArrayList<Asteroid>();
        explosions = new ArrayList<Explosion>();
        shootTimer = 0;
        asteroidSpawnTimer = (float) (Math.random() * (MAX_ASTEROID_SPAWN_TIMER - MIN_ASTEROID_SPAWN_TIMER) + MIN_ASTEROID_SPAWN_TIMER);
        score = 0;
        health = 1;

        hpBar = new Texture("blank.png");

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        deathTimer = 0;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        img = new Texture("ship.png");
        Background = new Texture("space.png");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.begin();
        game.batch.draw(Background, 0, 0);

        shootTimer += delta;

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= shootWaitTimer){
            shootTimer = 0;
            bullets.add(new PrimaryPlayerBullet(x + 5, y + 8, 700));
            bullets.add(new PrimaryPlayerBullet(x + shipWidth - 1, y + 8, 700));
        }

        asteroidSpawnTimer -= delta;
        if(asteroidSpawnTimer <= 0){
            asteroidSpawnTimer = (float) (Math.random() * (MAX_ASTEROID_SPAWN_TIMER - MIN_ASTEROID_SPAWN_TIMER) + MIN_ASTEROID_SPAWN_TIMER);
            asteroids.add(new Asteroid(((int) (Math.random() * Gdx.graphics.getWidth()) - Asteroid.width), 350));
        }


        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for(Asteroid asteroid: asteroids){
            asteroid.update(delta);
            if(asteroid.isToBeRemoved()){
                asteroidsToRemove.add(asteroid);
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        ArrayList<PrimaryPlayerBullet> bulletsToRemove = new ArrayList<PrimaryPlayerBullet>();
        for (PrimaryPlayerBullet bullet: bullets){
            bullet.update(delta);
            if(bullet.isToBeRemoved()){
                bulletsToRemove.add(bullet);
            }
        }

        ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        for(Explosion explosion: explosions){
            explosion.update(delta);
            if(explosion.isToRemove()){
                explosionsToRemove.add(explosion);
            }
        }

        explosions.removeAll(explosionsToRemove);

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y += SPEED * Gdx.graphics.getDeltaTime();
            if(y + shipLength > MyGdxGame.getGameHeight()){
                 y = Gdx.graphics.getHeight() - shipLength;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= SPEED * Gdx.graphics.getDeltaTime();
            if(y < 0){
                y = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += SPEED * Gdx.graphics.getDeltaTime();
            if(x + shipWidth > MyGdxGame.getGameWidth()){
                x = Gdx.graphics.getWidth() - shipWidth;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= SPEED * Gdx.graphics.getDeltaTime();
            if(x < 0){
                x = 0;
            }
        }
        playerCircle.setPosition(x, y);

        for(Asteroid asteroid: asteroids) {
            if (Intersector.overlaps(playerCircle, asteroid.getAsteroidHitBox())) {
                asteroidsToRemove.add(asteroid);
                health -= .1;
                score -= 500;
                if (score < 0) {
                    score = 0;
                }
                if(health <= 0 && !dead){
                    dead = true;
                    playerExplosion = new Explosion(x, y);
                    explosions.add(playerExplosion);
                }
            }
        }
        if(dead){
            deathTimer += delta;
        }


        //(bullet.getRectangle().collidesWith(asteroid.getRectangleForAsteroid()))
        for(PrimaryPlayerBullet bullet: bullets) {
            for (Asteroid asteroid : asteroids) {
                if (Intersector.overlaps(asteroid.getAsteroidHitBox(), bullet.getBulletCol())) {
                    asteroidsToRemove.add(asteroid);
                    bulletsToRemove.add(bullet);
                    explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
                    score += 100;
                }
            }
        }

        asteroids.removeAll(asteroidsToRemove);
        bullets.removeAll(bulletsToRemove);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height - 10);

        for(Asteroid asteroid: asteroids){
            asteroid.render(game.batch);
        }

        for(PrimaryPlayerBullet bullet: bullets){
            bullet.render(game.batch);
        }

        for(Explosion explosion: explosions){
            explosion.render(game.batch);
        }

        if(health > .7) {
            game.batch.setColor(Color.GREEN);
        }
        else if(health > .4) {
            game.batch.setColor(Color.YELLOW);
        }
        else if(health >= 0) {
            game.batch.setColor(Color.RED);
        }
        if (health <= 0 && deathTimer >= 1f){
            this.dispose();
            game.batch.end();
            game.setScreen(new GameOverScreen(game, score));
            return;
        }
        game.batch.draw(hpBar, 0, 0, Gdx.graphics.getWidth() * health, 10);
        game.batch.setColor(Color.WHITE);
        if(!dead){
            game.batch.draw(img, x, y, shipWidth, shipLength);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
