package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.screens.GameOverScreen.justTouched;

public class MainMenu implements Screen {

    private MyGdxGame game;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    Texture exitButtonOn;
    Texture exitButtonOff;
    Texture playButtonOn;
    Texture playButtonOff;
    private static final int PLAY_BUTTONY = 200;
    private static final int BUTTONX = MyGdxGame.getGameWidth() / 2 - BUTTON_WIDTH / 2;
    private static final int EXIT_BUTTONY = 50;

    private float touchTimer;

    BitmapFont titleFont;

    public MainMenu(MyGdxGame spaceGame){

        titleFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        titleFont.getData().setScale(1.5f, 2f);

        game = spaceGame;
        exitButtonOff = new Texture("buttons/quit_button_off.png");
        exitButtonOn = new Texture("buttons/quit_button_on.png");
        playButtonOff = new Texture("buttons/play_button_off.png");
        playButtonOn = new Texture("buttons/play_button_on.png");

        touchTimer = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.begin();
        GlyphLayout title = new GlyphLayout(titleFont, "SPACE SHOOTER", Color.WHITE, 0, Align.center, false);
        titleFont.draw(game.batch, title, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 150);
        if(Gdx.input.getX() > BUTTONX
                && Gdx.input.getX() < Gdx.graphics.getWidth() - BUTTONX
                && Gdx.graphics.getHeight() - Gdx.input.getY() > EXIT_BUTTONY
                && Gdx.graphics.getHeight() - Gdx.input.getY() < EXIT_BUTTONY + BUTTON_HEIGHT){

            game.batch.draw(exitButtonOn, BUTTONX, EXIT_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        else{
            game.batch.draw(exitButtonOff, BUTTONX, EXIT_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        if(Gdx.input.getX() > BUTTONX
                && Gdx.input.getX() < Gdx.graphics.getWidth() - BUTTONX
                && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_BUTTONY
                && Gdx.graphics.getHeight() - Gdx.input.getY() < PLAY_BUTTONY + BUTTON_HEIGHT){

            game.batch.draw(playButtonOn, BUTTONX, PLAY_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched() && !justTouched){
                game.setScreen(new GameScreen(game));
            }
            else{
                if(touchTimer >= .2f){
                    justTouched = false;
                }
                touchTimer += delta;
            }
        }
        else{
            game.batch.draw(playButtonOff, BUTTONX, PLAY_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
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
