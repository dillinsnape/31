package fearthepenguin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import fearthepenguin.UI.KnockButton;
import fearthepenguin.pokerdeck.Card;
import fearthepenguin.pokerdeck.PokerDeck;
import fearthepenguin.screens.MainMenuScreen;
import fearthepenguin.screens.OpponentScatScreen;
import fearthepenguin.screens.PlayerScatScreen;
import fearthepenguin.screens.PlayerWinsScreen;
import fearthepenguin.screens.OpponentWinsScreen;

public class GameStage extends Stage{
	
	Scat game;
	PokerDeck deck;
	public Player player;
	Opponent opponent;
	KnockButton knockButton;
	
	Array<Card>	discardPile;
	Card cardSelected;
	
	public boolean selectACard = true;
	public boolean pickDiscard = false;
	public boolean opponentsTurn = false;
	public boolean opponentsFirstTurn = true;
	public boolean opponentKnocked = false;
	public boolean lastTurn = false;
	
	public GameStage(Scat game){
		
		super();
		this.game = game;		

		deck = new PokerDeck(game);
		this.addActor(deck);
		
		discardPile = new Array<Card>();
		
		knockButton = new KnockButton(game);
		this.addActor(knockButton);
		
		player = new Player(game, discardPile, deck, this);
		opponent = new Opponent(game, discardPile, deck, this);
		
		init();
		
	}
	
	private void init() {
	
			//	Each hand draws 3 Cards
			for (int i = 0; i < 3; i++){
				player.hand.add(deck.drawACard());
				opponent.hand.add(deck.drawACard());
			}
			
			
			//	Draw 1 for discard pile.
			discardPile.add(deck.drawACard());
			
			//	Set position of Cards in each hand
			player.setHandPosition();
			opponent.setHandPosition();
			
			//	Set discardPile Position
			discardPile.peek().setPosition(
					game.width() / 2 + discardPile.peek().getWidth(),
					game.height() / 2 - discardPile.peek().getHeight() / 2
					);
			
			//  Add hands to stage
			for (Card card : player.hand)
				this.addActor(card);
			
			//	Set opponents hand invisible
			for (Card card : opponent.hand){
				card.setVisible(false);
				card.setTouchable(Touchable.disabled);
				this.addActor(card);
			}
			
			this.addActor(discardPile.peek());
			
		}

	@Override
	public void act() {
		super.act();
		
		//
		// set all but the top card in discardPile untouchable.
		//
		for (Card card : discardPile)
			card.setTouchable(Touchable.disabled);
		if(discardPile.size > 0)
			discardPile.peek().setTouchable(Touchable.enabled);
		//
		//	Make sure the deck isn't drained.
		//	If it is: end game.
		//
		if(deck.getSize() == 0)
			determineVictor();		
		//
		//	Game Logic
		//		
		if(noActionsRemaining()){
			//
			//	Opponents Turn
			//
			if(opponentsTurn){
				
				if(opponentKnocked){
					opponentsTurn = false;
					selectACard = true;
					lastTurn = true;
					game.knock.play();
				}
				else	{
					opponent.play();
				}					
				
				if(checkForScat(opponent.hand))
					game.setScreen(new OpponentScatScreen(game, opponent.hand));
			}
			//
			//	Players Turn
			//
			else if (Gdx.input.isTouched() && noActionsRemaining()){
				
				Vector2 screenClick = new Vector2(Gdx.input.getX(), Gdx.input.getY()); 
				screenClick = screenToStageCoordinates(screenClick);
				//
				//	Get card at screenClick.
				//
				Actor actorHit = hit(screenClick.x, screenClick.y, true);				
				
				if (actorHit != null)
					
					if(selectACard){
						//
						//	Make sure they didn't click on a card in the hand
						//
						for (Card card : player.hand)
							if (card.equals(actorHit))
								return;
						//
						//	Player knocking
						//
						if(actorHit == knockButton){
							game.knock.play();
							opponent.lastTurn();
							determineVictor();
							return;
						} 
						//
						// 	If the player picked a valid card.
						//
						if(player.selectACard(actorHit) != null);{
							selectACard = false;
							pickDiscard = true;
						}
							
					} else if (pickDiscard){
						//
						//	Make sure they picked a card in the hand
						//
						for (Card card : player.hand)
							
							if(card.equals(actorHit)){
								player.pickADiscardCard(actorHit);
								pickDiscard = false;
								opponentsTurn = true;
								//
								//	Check for 31
								//
								if(checkForScat(player.hand)){
									game.setScreen(new PlayerScatScreen(game, player.hand));
									return;
								}
								//
								//	If the opponent knocked
								//
								if(lastTurn)
									determineVictor();
								
							}
												
					}
					
			}
			
		}
		
	}

	@Override
	public void draw() {
		
		super.draw();
		
		//
		//	Set all of opponents cards invisible
		//
		for (Card card : opponent.hand)
			card.setVisible(false);
		
		getBatch().begin();
		//
		// Print tips for selecting a card or discarding a card.
		//
		if(selectACard){
			game.font.draw(
					getBatch(),
					"Pick a Card from the Deck -->",
					deck.getX() - 200,
					deck.getY() + 100
			);
			game.font.drawMultiLine(
					getBatch(),
					"<-- Or Pick a Card\nfrom the Discard Pile",
					deck.getX() + 600 ,
					deck.getY() + 100
			);
		} else if (pickDiscard){
			game.font.draw(
					getBatch(),
					"Pick a Card to Discard",
					game.width() / 2,
					deck.getY() - 20
			);		
		}
		//
		//	Draw back of Opponents Hand.
		//
		for(Card card : opponent.hand)
			getBatch().draw(
					Card.cardBack,
					card.getX(), card.getY(),
					card.getWidth(), card.getHeight()
			);			
		
		getBatch().end();
		
	}	

	private boolean noActionsRemaining(){
		for (Actor actor : this.getActors())
			if (actor.getActions().size > 0)
				return false;
		return true;
	}

	private boolean checkForScat(Array<Card> hand) {
		//
		//	If all the suits are the same.
		//
		if (hand.get(0).suit == hand.get(1).suit
			&& hand.get(0).suit == hand.get(2).suit){
			//
			//	If the cards add up to 31.
			//
			int score = 0;
			for(int i = 0; i < 3; i++)
				score += hand.get(i).faceValue;
			if(score == 31)
				return true;
		}
		return false;
	}

	private double determineScore(Array<Card> hand) {
		
		double finalScore = 0,
			   tempScore = 0;
		//
		//	Get mode suit.
		//
		int numH = 0,
			numS = 0,
			numC = 0,
			numD = 0;
		for(Card card : hand)
			switch (card.suit){
			case 'H':	numH++;
						break;
			case 'S':	numS++;
						break;
			case 'C':	numC++;
						break;
			case 'D':	numD++;
						break;
			}
		int modeSuit = Math.max(
					   		Math.max(numH, numS),
					   		Math.max(numC, numD)
				   	   );			
		//
		//	Find the suit with the highest score.
		//
		if (modeSuit == numH)
			for(Card card : hand)
				if(card.suit == 'H')
					tempScore += card.faceValue;
			if(tempScore > finalScore)
				finalScore = tempScore;
		tempScore = 0;	
		
		if (modeSuit == numS)
			for(Card card : hand)
				if(card.suit == 'S')
					tempScore += card.faceValue;
			if(tempScore > finalScore)
				finalScore = tempScore;
		tempScore = 0;	
		
		if (modeSuit == numC)
			for(Card card : hand)
				if(card.suit == 'C')
					tempScore += card.faceValue;
			if(tempScore > finalScore)
				finalScore = tempScore;
		tempScore = 0;	
		
		if (modeSuit == numD)
			for(Card card : hand)
				if(card.suit == 'D')
					tempScore += card.faceValue;
			if(tempScore > finalScore)
				finalScore = tempScore;
		//	
		//	Check for 30.5 (Three of a Kind)
		//
		if (hand.get(0).value == hand.get(1).value
				&& hand.get(0).value == hand.get(2).value)
			finalScore = 30.5;
		
		return finalScore;
	}

	private void determineVictor() {
		
		double playersScore = determineScore(player.hand),
			   opponentsScore = determineScore(opponent.hand);
		
		System.out.println("players Score: " + playersScore);
		System.out.println("opponents Score: " + opponentsScore);
		
		if (playersScore > opponentsScore){
			
			System.out.println("YOU WIN!");
			game.setScreen(new PlayerWinsScreen(game, player.hand, opponent.hand));
			
		} else if (opponentsScore > playersScore){
			
			System.out.println("YOU LOSE");
			game.setScreen(new OpponentWinsScreen(game, player.hand, opponent.hand));
			
		} else { // TIE
			
			System.out.println("TIED");
			game.setScreen(new MainMenuScreen(game));
			
		}
		
	}
	
}
