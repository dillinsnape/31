package fearthepenguin.pokerdeck;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Card extends Actor {
	
	public final int faceValue;
    public final int value;
    public final char suit;
    public final TextureRegion texture;
    public static final Texture cardBack = new Texture("images/cardBack.png");
    
    
    public Card (int faceValue, int value, char suit, final TextureRegion texture, PokerDeck deck){ 
        this.faceValue = faceValue;
        this.value = value;
        this.suit = suit;
        this.texture = texture;        
        setPosition(deck.getX(), deck.getY());
        setBounds(
                getX(),
                getY(),
                texture.getRegionWidth() * 2,
                texture.getRegionHeight() * 2
        );
    }
    
    @Override
    public void draw(Batch batch, float deltaTime){   
        batch.draw(
                texture,
                getX(), getY(),
                getOriginX(), getOriginY(),
                texture.getRegionWidth() * 2, texture.getRegionHeight() * 2,
                getScaleX(), getScaleY(), 0
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    
    @Override
    public String toString() {
    	return faceValue + " of " + suit;
    }
    
    
}

