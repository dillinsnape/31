package fearthepenguin.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import fearthepenguin.pokerdeck.Card;
import fearthepenguin.pokerdeck.PokerDeck;

public class Player {
	
	Scat game;
	GameStage stage;
	PokerDeck deck;
	
	public Array<Card> hand;
	Array<Card> discardPile;
	Card cardSelected;
	Card discardCard;
	
	public Player(Scat game, Array<Card> discardPile, PokerDeck deck, GameStage stage) {
		
		this.game = game;
		this.discardPile = discardPile;
		this.deck = deck;
		this.stage = stage;		
		hand = new Array<Card>();
		
	}
	public void setHandPosition(){
		
		float xSpacing = hand.peek().getWidth() * 3 / 2;
		float xStartPoint = game.width() / 2 - (xSpacing * hand.size) / 2 + 39;
		
		for (int i = 0;  i  < hand.size ; i++ )
			hand.get(i).addAction(moveTo(
						xStartPoint + i * xSpacing,
						33,
						.4f)
			);

		game.slide.play();
		
	}
	
	private void moveToDiscardPile(Card cardSelected) {
		
		cardSelected.addAction(
				moveTo(
						game.width() / 2 + discardPile.peek().getWidth(),
						game.height() / 2 - discardPile.peek().getHeight() / 2,
						.25f
					  )
		);
		game.slide.play();
	}
	
	public Card selectACard(Actor actorHit) {	
		
		cardSelected = null;
		
		if (actorHit instanceof PokerDeck){ 	
			
			cardSelected = deck.drawACard();
			hand.add(cardSelected);
			setHandPosition();	
			stage.addActor(cardSelected);
			
		} else if (actorHit instanceof Card 
				   && (Card)(actorHit) == discardPile.peek()
				   ){			
			
			cardSelected = (Card) actorHit;
			discardPile.removeValue(cardSelected, true);
			hand.add(cardSelected);
			setHandPosition();
			
		}	
		
		return cardSelected;
	}

	public void pickADiscardCard(Actor actorHit) {			
				
				cardSelected = (Card) actorHit;
				discardPile.add(cardSelected);
				cardSelected.toFront();
				moveToDiscardPile(cardSelected);
				
				hand.removeValue(cardSelected, true);
				setHandPosition();			
		
	}

}
