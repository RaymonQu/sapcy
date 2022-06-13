package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MainMenu;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static float SPEED = 500;
	private static final int gameWidth = 720;
	private static final int gameHeight = 960;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public static int getGameWidth(){
		return gameWidth;
	}

	public static int getGameHeight(){
		return gameHeight;
	}
}
