package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;

public class GameOverScreen implements Screen {

    private MyGdxGame game;

    int score;
    int highScore;

    BitmapFont scoreFont;
    BitmapFont titleFont;

    public static boolean justTouched;

    private Texture dimBackground;
    private Texture mainMenuButtonOn;
    private Texture mainMenuButtonOff;
    private Texture tryAganeButtonOn;
    private Texture tryAganeButtonOff;

    private static final int BUTTON_WIDTH = 450;
    private static final int BUTTON_HEIGHT = 100;

    private static final int MAIN_BUTTONY = 200;
    private static final int BUTTONX = MyGdxGame.getGameWidth() / 2 - BUTTON_WIDTH / 2;
    private static final int TRY_BUTTONY = 50;

    private Sprite realBack;

    public GameOverScreen(MyGdxGame game, int score){
        this.game = game;
        this.score = score;

        Preferences prefs = Gdx.app.getPreferences("MyGdxGame");
        this.highScore = prefs.getInteger("highscore", 0);
        if(score > highScore){
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        scoreFont.getData().setScale(.7f);
        titleFont.getData().setScale(2f);

        dimBackground = new Texture("dimmer.jpg");
        realBack = new Sprite(dimBackground, 0, 0);
        realBack.setSize(MyGdxGame.getGameWidth(), MyGdxGame.getGameHeight());

        mainMenuButtonOn = new Texture("buttons/mainful.png");
        mainMenuButtonOff = new Texture("buttons/main.png");
        tryAganeButtonOn = new Texture("buttons/tryful.png");
        tryAganeButtonOff = new Texture("buttons/tryempt.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: " + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highScoreLayout = new GlyphLayout(scoreFont, "Highscore: " + highScore, Color.WHITE, 0, Align.left, false);
        GlyphLayout deathBanner = new GlyphLayout(titleFont, "GAME OVER", Color.WHITE, 0, Align.center, false);

        game.batch.begin();
        realBack.draw(game.batch);
        realBack.setAlpha(.5f);
        game.batch.end();

        game.batch.begin();
        scoreFont.draw(game.batch, highScoreLayout, Gdx.graphics.getWidth() / 2 - highScoreLayout.width, Gdx.graphics.getHeight() - scoreLayout.height - highScoreLayout.height - 300 - 50 * 2);
        scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - highScoreLayout.width, Gdx.graphics.getHeight() - 300 - 15 * 2);
        titleFont.draw(game.batch, deathBanner, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 15);

        if(Gdx.input.getX() > BUTTONX
                && Gdx.input.getX() < Gdx.graphics.getWidth() - BUTTONX
                && Gdx.graphics.getHeight() - Gdx.input.getY() > MAIN_BUTTONY
                && Gdx.graphics.getHeight() - Gdx.input.getY() < MAIN_BUTTONY + BUTTON_HEIGHT){

            game.batch.draw(mainMenuButtonOn, BUTTONX, MAIN_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                justTouched = true;
                game.setScreen(new MainMenu(game));
            }
        }
        else{
            game.batch.draw(mainMenuButtonOff, BUTTONX, MAIN_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        if(Gdx.input.getX() > BUTTONX
                && Gdx.input.getX() < Gdx.graphics.getWidth() - BUTTONX
                && Gdx.graphics.getHeight() - Gdx.input.getY() > TRY_BUTTONY
                && Gdx.graphics.getHeight() - Gdx.input.getY() < TRY_BUTTONY + BUTTON_HEIGHT){

            game.batch.draw(tryAganeButtonOn, BUTTONX, TRY_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                game.setScreen(new GameScreen(game));
            }
        }
        else{
            game.batch.draw(tryAganeButtonOff, BUTTONX, TRY_BUTTONY, BUTTON_WIDTH, BUTTON_HEIGHT);
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
