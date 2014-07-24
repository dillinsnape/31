package fearthepenguin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

import fearthepenguin.game.Scat;
import fearthepenguin.pokerdeck.Card;

public class PlayerWinsScreen implements Screen {
	
	Scat game;
	Array<Card> playersHand;
	Array<Card> opponentsHand;
	float xSpacing;
	float xStartPoint;
	Card currentCard;
		
	public PlayerWinsScreen(Scat game, Array<Card> playersHand, Array<Card> opponentsHand) {
		Gdx.audio.newSound(Gdx.files.internal("audio/playerScat.mp3")).play(.5f);
		this.game = game;
		this.playersHand = playersHand;
		this.opponentsHand = opponentsHand;
		xSpacing = playersHand.peek().getWidth() * 3 / 2;
		xStartPoint = game.width() / 2 - (xSpacing * playersHand.size) / 2 + 39;
		
		game.numberOfWins++;

		game.font.setColor(Color.BLACK);
		
	}
	float newGameCounter = 0;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		
		game.batch.begin();
		//
		//Render playersHand
		//
		for (int i = 0;  i  < playersHand.size ; i++ ){
			currentCard = playersHand.get(i);
			game.batch.draw(currentCard.texture,
						xStartPoint + i * xSpacing,
						game.height() / 2 + currentCard.getHeight() / 2,
						currentCard.getWidth(), currentCard.getHeight()
			);
		}
		game.font.draw(game.batch, "YOUR HAND", 300, game.height() / 2 + currentCard.getTop() + 20);
		//
		//Render opponentsHand
		//
		for (int i = 0;  i  < opponentsHand.size ; i++ ){
			currentCard = opponentsHand.get(i);
			game.batch.draw(currentCard.texture,
						xStartPoint + i * xSpacing,
						game.height() / 2 - currentCard.getHeight() / 2,
						currentCard.getWidth(), currentCard.getHeight()
			);
		}
		game.font.draw(game.batch, "OPPONENTS HAND", 300, 200);
		game.font.draw(game.batch, "You Win!", 400, 100);
		//
		// Countdown
		//
		newGameCounter += delta;
		game.font.draw(game.batch, " " + (int) (5 - newGameCounter), 600, 50);
		
		game.batch.end();
		
		if (newGameCounter > 5)
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
