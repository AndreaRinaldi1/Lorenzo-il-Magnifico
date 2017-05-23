package it.polimi.ingsw.GC_28.boards;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;

public class BoardSetup {
	
	//CardReader cardReader = new CardReader();
	public static GameBoard gameBoard = BoardsInitializer.gameBoard;
	static Deck deck = new Deck(); //once initialize it will not change
	private static ArrayList<Card> usedCard = new ArrayList<Card>();
	private static int era = 1;
	
	public static void firstSetUpCards() {
		BoardSetup.prepareDeck();
		BoardSetup.prepareTowers();
		//System.out.println(deck.getTerritories().toString());
	}
	
	private static void prepareDeck(){
		try{
			CardReader cardReader = new CardReader();
			deck =  cardReader.startRead();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
	}
	
	private static void setUpTerritoriesTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.TERRITORY).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getTerritories().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				//System.out.println(randomInt);
				if(deck.getTerritories().get(randomInt).getEra() == era && 
						!(usedCard.contains(deck.getTerritories().get(randomInt))) ){ 
					Territory t = deck.getTerritories().get(randomInt);
					cell[i].setCard(t);
					usedCard.add(t);
					x = true;
				}
			}
		}
	}
	
	private static void setUpBuildingsTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.BUILDING).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getBuildings().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getBuildings().get(randomInt).getEra() == era && 
						!(usedCard.contains(deck.getBuildings().get(randomInt))) ){ 
					Building b = deck.getBuildings().get(randomInt);
					cell[i].setCard(b);
					usedCard.add(b);
					x = true;
				}
			}
		}
	}
	
	private static void setUpCharacterTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.CHARACTER).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getCharacters().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getCharacters().get(randomInt).getEra() == era && 
						!(usedCard.contains(deck.getCharacters().get(randomInt))) ){ 
					Character c = deck.getCharacters().get(randomInt);
					cell[i].setCard(c);
					usedCard.add(c);
					x = true;
				}
			}
		}
	}
	
	private static void setUpVentureTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.VENTURE).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getVentures().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getVentures().get(randomInt).getEra() == era && 
						!(usedCard.contains(deck.getVentures().get(randomInt))) ){ 
					Venture v = deck.getVentures().get(randomInt);
					cell[i].setCard(v);
					usedCard.add(v);
					x = true;
				}
			}
		}
	}
	
	public static void prepareTowers(){
		setUpTerritoriesTower();
		setUpBuildingsTower();
		setUpCharacterTower();
		setUpVentureTower();
	}
	
	private ArrayList<Player> getNextPlayerOrder(){
		ArrayList<FamilyMember> inCouncil = gameBoard.getCouncilPalace().getPlayer();
		ArrayList<Player> nextOrder = new ArrayList<Player>();
		for(FamilyMember fm : inCouncil){
			if(!(nextOrder.contains(fm))){
				nextOrder.add(fm.getPlayer());
			}
		}
		if(nextOrder.size() == 0){
			nextOrder = BoardsInitializer.players; //FIXME set nextOrder to the actual game order
		}
		return nextOrder;
	}
	
}
