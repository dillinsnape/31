package fearthepenguin.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fearthepenguin.screens.GameScreen;
import fearthepenguin.screens.MainMenuScreen;

public class Scat extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	public OrthographicCamera camera;
	
	public static MainMenuScreen mainMenuScreen;
	public static GameScreen gameScreen;
	
	Sound slide;
	Sound knock;
	
	public int numberOfScats = 0,
			   numberOfWins  = 0,
			   numberOfLoses = 0;
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width(), height());
		
		slide = Gdx.audio.newSound(Gdx.files.internal("audio/cardSlide1.ogg"));
		knock = Gdx.audio.newSound(Gdx.files.internal("audio/3knocks.mp3"));
		
		this.setScreen(new MainMenuScreen(this));
		
	}

	@Override
	public void render () {
		super.render();
		
	}
	
	public int width(){
		return Gdx.graphics.getWidth();
	}
	
	public int height(){
		return Gdx.graphics.getHeight();
	}
}
