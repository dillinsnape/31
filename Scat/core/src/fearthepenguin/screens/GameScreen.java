package fearthepenguin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import fearthepenguin.game.GameStage;
import fearthepenguin.game.Scat;

public class GameScreen implements Screen{
	
	Scat game;
	GameStage stage;	
	Texture background;
	
	public GameScreen(Scat game){
		
		this.game = game;
		stage = new GameStage(game);
		background = new Texture("images/FeltGreen.jpg");
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.camera.update();
		stage.act();
		
		
		game.batch.begin();
		
		game.batch.draw(background, 0, 0);
//		
//		game.font.setColor(Color.BLACK);
//		game.font.draw(game.batch, "X: " + Gdx.input.getX(), 30, 30);
//		game.font.draw(game.batch, "y: " + Gdx.input.getY(), 75, 30);
//		game.font.draw(game.batch, "Clicked:  " + Gdx.input.isTouched(), 10, game.height() - 15);
//		game.font.draw(game.batch, "Select A Card:  " + stage.selectACard, 10, game.height() - 30);
//		game.font.draw(game.batch, "Discard A Card  " + stage.pickDiscard, 10, game.height() - 45);
//		game.font.draw(game.batch, "Opponents Turn:  " + stage.opponentsTurn, 10, game.height() - 60);
//		game.font.draw(game.batch, "Number Of Scats:  " + game.numberOfScats, 10, game.height() - 75);
//		game.font.draw(game.batch, "Number Of Wins:  " + game.numberOfWins, 10, game.height() - 90);
//		game.font.draw(game.batch, "Number Of Loses:  " + game.numberOfLoses, 10, game.height() - 105);
//		game.font.draw(game.batch, "Player Hand Size:  " + stage.player.hand.size, 10, game.height() - 120);
//		game.font.draw(game.batch, "Opponent Knocked:  " + stage.opponentKnocked, 10, game.height() - 135);

//		game.font.draw(game.batch, "Opponents Suit:  " + stage.opponent.choosenSuit, 10, game.height() - 105);
//		game.font.draw(game.batch, "Opponents MaxFaceValue:  " + stage.opponent.maxFaceValue, 10, game.height() - 120);
//		game.font.draw(game.batch, "Opponents Current Score:  " + stage.opponent.currentScore, 10, game.height() - 150);


		game.batch.end();
		
		stage.draw();
		
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
