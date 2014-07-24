package fearthepenguin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fearthepenguin.game.Scat;

public class MainMenuScreen implements Screen {
	Scat game;
	InputAdapter input;
	
	Texture BACKGROUND;	
	
	
	public MainMenuScreen (Scat game){
		
		this.game = game;		
		BACKGROUND = new Texture("images/MainMenuBackground.jpg");		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);		
		
		
		game.batch.begin();		
		game.batch.draw(BACKGROUND, 0, 0);		
		game.batch.end();
		
		if(Gdx.input.isTouched())
			game.setScreen(new GameScreen(game));
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
