package fearthepenguin.pokerdeck;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import fearthepenguin.game.Scat;

public class PokerDeck extends Actor {
	
    public static final int SIZE = 52,
    						CARD_WIDTH = 73,
    						CARD_HEIGHT = 98;
	
    private final Array<Card> cardArray;
    private final Texture deckTexture;
    private final TextureRegion[][] deckRegion;
    
    public PokerDeck(Scat game){
    	
        deckTexture = new Texture("images/cards.png");
        deckRegion = TextureRegion.split(deckTexture, CARD_WIDTH, CARD_HEIGHT);
        
        cardArray = new Array<Card>(SIZE);

        //	Set bounds.
        setBounds(0, 0, CARD_WIDTH * 2, CARD_HEIGHT * 2);
        
        //  Set position.
        float x = game.width() / 2 - this.getWidth() * 2,
			  y = game.height() / 2 - this.getHeight() / 2;
        setPosition(x, y);
        
        //	Build deck with spritesheet.
        for (int i = 1; i < 14; i ++){
            int faceValue = i;
            if (i > 10) faceValue = 10; //	Set Face cards face value
            if (i == 1) faceValue = 11; //	Set Aces face value
            
            cardArray.add(new Card(faceValue, i, 'C', deckRegion[0][i - 1], this)); 
            cardArray.add(new Card(faceValue, i, 'S', deckRegion[1][i - 1], this));
            cardArray.add(new Card(faceValue, i, 'H', deckRegion[2][i - 1], this));
            cardArray.add(new Card(faceValue, i, 'D', deckRegion[3][i - 1], this));
        }
               
    }
    
    public Card drawACard(){    	
        return cardArray.removeIndex(MathUtils.random(cardArray.size - 1));
    }
    
    public int getSize(){
    	return cardArray.size;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	
    	super.draw(batch, parentAlpha);
    	
    	for (int i = 0; i < getSize(); i++)
			batch.draw(
					Card.cardBack,
					(i / 3) + this.getX() ,
					(i / 3) + this.getY(),
					this.getWidth(),
					this.getHeight()
					);
		
    	
    }
}

