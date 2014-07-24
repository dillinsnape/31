package fearthepenguin.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import com.badlogic.gdx.utils.Array;

import fearthepenguin.pokerdeck.Card;
import fearthepenguin.pokerdeck.PokerDeck;

public class Opponent {
	
	Scat game;
	GameStage stage;
	PokerDeck deck;
	
	public Array<Card> hand;
	Array<Card> discardPile;
	Card cardSelected;
	Card discardCard;
	
	public char choosenSuit = 'q';
	public int maxFaceValue = 0;
	public int currentScore = 0;
	boolean firstTurn;
	boolean drawACard;
	public boolean discardACard;
	private boolean goForThreeOfAKind = false;
	private int pairValue = 0;
			
	public Opponent(Scat game, Array<Card> discardPile, PokerDeck deck, GameStage stage) {
		
		this.game = game;
		this.discardPile = discardPile;
		this.deck = deck;
		this.stage = stage;
		
		hand = new Array<Card>();
		firstTurn = true;
		drawACard = true;
		discardACard = false;
		
	}
	
	public void play(){
		
		//	Find the max face value.
		maxFaceValue = 0;
		for(Card card : hand)
			if(card.suit == choosenSuit)
				if(card.faceValue > maxFaceValue)
					maxFaceValue = card.faceValue;
		if (maxFaceValue > 10)
			maxFaceValue = 10;
		
		//	If it is the first turn, pick a suit.
		//	Check if there is a pair;
		if(firstTurn){
			choosenSuit = hand.peek().suit;
			for(Card card : hand)
				if (card.faceValue > maxFaceValue && card.faceValue >=9){
					choosenSuit = card.suit;
					maxFaceValue = card.faceValue;
					firstTurn = false;
				}
			
			if (goForThreeOfAKind = checkForPairs())
				pairValue = findPairValue();
		}
		
		if (drawACard)	{
			
			if (goForThreeOfAKind){
				//
				//	From discardPile
				//
				if(discardPile.peek().value == pairValue){
					cardSelected = discardPile.pop();
					hand.add(cardSelected);
					setHandPosition();
					//
					//	From the Deck
					//
				} else { 					
					cardSelected = deck.drawACard();
					cardSelected.setPosition(deck.getX(), deck.getY());
					hand.add(cardSelected);
					stage.addActor(cardSelected);
					setHandPosition();					
				}
			} else
			//
			//	From the discardPile
			//
			if (discardPile.peek().suit == choosenSuit
				&& discardPile.peek().faceValue >= maxFaceValue){ //Take from discard pile.
				
				cardSelected = discardPile.pop();
				hand.add(cardSelected);
				setHandPosition();
			//	
			// From the deck.
			//
			} else { 
				
				cardSelected = deck.drawACard();
				cardSelected.setPosition(deck.getX(), deck.getY());
				hand.add(cardSelected);
				stage.addActor(cardSelected);
				setHandPosition();
				
			}
			
			if(maxFaceValue < cardSelected.faceValue)
				maxFaceValue = cardSelected.faceValue;
			
			drawACard = false;
			
		} 
		
		if(numActionsRemaining() == 0)
			discardACard = true;
		
		
		if(discardACard){
			
			discardCard = cardSelected;
			//
			// Pick a Card to discard.
			//
			if (goForThreeOfAKind){
				for (Card card : hand)
					if (card.value != pairValue){
						discardCard = card;
						stage.opponentsTurn = false;
						stage.selectACard = true;						
					}
						
			} else {			
			
				// Any off suit card
				for (Card card : hand)
					if (card.suit != choosenSuit)
						discardCard = card;
				
				if (discardCard.suit == choosenSuit
				    && discardCard.faceValue >= (maxFaceValue - 1))
					//Find a lower Card.
					for (Card card: hand)
						if (card.faceValue < discardCard.faceValue)
							discardCard = card;
				
				stage.opponentsTurn = false;
				stage.selectACard = true;
				
			}
			//
			//  Discard the Card.
			//
			hand.removeValue(discardCard, true);							
			setHandPosition();
			moveToDiscardPile(discardCard);
			discardPile.add(discardCard);
			discardPile.peek().toFront();			
			discardPile.peek().setVisible(true);			
			
			discardACard = false;
			drawACard = true;
			
			
			stage.opponentKnocked = checkIfKnock();
			
		}
		
	}


	private int findPairValue() {
		if (hand.get(0).value == hand.get(1).value)
			return hand.get(0).value;
		else if (hand.get(0).value == hand.get(2).value)
			return hand.get(0).value;
		else if (hand.get(1).value == hand.get(2).value)
			return hand.get(1).value;
		return 0;
	}

	private boolean checkForPairs() {
		if (hand.get(0).value == hand.get(1).value
			|| hand.get(0).value == hand.get(2).value
			|| hand.get(1).value == hand.get(2).value)
			return true;
		return false;
		
	}

	public void lastTurn() {
		
		if (maxFaceValue > 10)
			maxFaceValue = 10;
		
		//	Draw A Card
		
		//	From discardPile
		if (discardPile.peek().suit == choosenSuit
			&& discardPile.peek().faceValue >= maxFaceValue){ //Take from discard pile.
			
			cardSelected = discardPile.pop();
			if(discardPile.size > 0)
				discardPile.peek().setVisible(true);
			hand.add(cardSelected);
			
		//	Or from deck.	
		} else { 
			
			cardSelected = deck.drawACard();
			hand.add(cardSelected);
			stage.addActor(cardSelected);
		}
		
		//	Pick a discard Card.
		discardCard = cardSelected;
		if (cardSelected.suit == choosenSuit &&
		    cardSelected.faceValue >= (maxFaceValue - 1))
			//	discard the lowest Card or an off suit card
			for (Card card: hand)
				if (card.faceValue < discardCard.faceValue)
					discardCard = card;
		hand.removeValue(discardCard, true);
		discardPile.add(discardCard);
		
	}

	public void setHandPosition(){
		
		float xSpacing = hand.peek().getWidth() * 3 / 2;
		float xStartPoint = game.width() / 2 - (xSpacing * hand.size) / 2 + 39;
		
		for (int i = 0;  i  < hand.size ; i++ )
			hand.get(i).addAction(moveTo(
						xStartPoint + i * xSpacing,
						game.height() - 33 - hand.peek().getHeight(),
						.4f)
			);
		
	}

	private int numActionsRemaining(){
		
		int numActionsRemaining = 0;
		for (Card card : hand)
			numActionsRemaining += card.getActions().size;
		return numActionsRemaining;
		
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

	public boolean checkIfKnock() {
		
		if (goForThreeOfAKind){
			//
			//	Check for Three of a Kind.
			//
			if(hand.get(0).value == hand.get(1).value
			   && hand.get(0).value == hand.get(2).value
			   && hand.get(1).value == hand.get(2).value)
				
				return true;
		}
		//
		// Check if cards add up to  27 or more.
		//
		currentScore = 0;
		for (Card card : hand)
			if(card.suit == choosenSuit)
				currentScore += card.faceValue;			
		return (currentScore >= 27);
			
		
		
	}

}
