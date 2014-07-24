package fearthepenguin.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fearthepenguin.game.Scat;

public class KnockButton extends Actor {
	
	Scat game;
	float buttonTextX,
		  buttonTextY;
	
	String buttonText = "Knock";
	
	Texture buttonUp = new Texture("images/ui/knock_button_up.png");
	Texture buttonDown = new Texture("images/ui/knock_button_down.png");
	Texture currentTexture;
	
	public KnockButton(Scat game) {
		
		super();
		this.game = game;
		
		currentTexture = buttonUp;
		setBounds(0, 0, currentTexture.getWidth() * 2, currentTexture.getHeight() * 2);
		setPosition(
				Gdx.graphics.getWidth()/ 2 -  getWidth()/ 2 + 15,
				Gdx.graphics.getHeight()/ 2 -  getHeight()/ 2
				);
		buttonTextX = getX() + getWidth() / 2 - game.font.getBounds(buttonText).width / 2 ;
		buttonTextY = getY() + getHeight() / 2 + game.font.getBounds(buttonText).height / 2;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(currentTexture, getX(), getY(), getWidth(), getHeight());
		game.font.draw(batch, buttonText, buttonTextX, buttonTextY);
	}
	
	public void setTexture(boolean isClicked){
		if(isClicked)
			currentTexture = buttonDown;
		else
			currentTexture = buttonUp;
	}

}
