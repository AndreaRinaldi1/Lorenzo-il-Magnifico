package it.polimi.ingsw.GC_28.model;

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

import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;

public class BoardSetup {
	
	public static final GameBoard gameBoard = BoardsInitializer.gameBoard;
	static Deck deck = new Deck(); //once initialize it will not change
	private static ArrayList<Card> usedCard = new ArrayList<>();
	private static int era = 1;
	private static int period = 1;

	public BoardSetup(){
	}
	
	public void firstSetUpCards() {
		prepareDeck();
		prepareTowers();
	}
	
	public void setUpBoard(){
		Game.setPlayers(getNextPlayerOrder());
		freeFamilyMember();
		freeSpace();
		if(Game.getPlayers().size() > 2){
			freeSpaceMoreThanTwoPlayer();
		}
		prepareTowers();
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
		if(period == 2){
			era = era + 1;
			period = 1;
		}else{
			period = 2;
		}
	}
	
	private ArrayList<Player> getNextPlayerOrder(){
		ArrayList<FamilyMember> inCouncil = gameBoard.getCouncilPalace().getPlayerOrder();
		ArrayList<Player> nextOrder = new ArrayList<>();
		if(!(inCouncil.isEmpty())){
			for(FamilyMember fm : inCouncil){
				if(!(nextOrder.contains(fm.getPlayer()))){
					nextOrder.add(fm.getPlayer());
				}
			}
		}
		if(nextOrder.isEmpty()){
			nextOrder = Game.getPlayers();
		} 
		return nextOrder;
	}
	
	public void freeSpace(){
		if(!gameBoard.getCoinSpace().isFree()){
			gameBoard.getCoinSpace().getPlayer().remove(0);
			gameBoard.getCoinSpace().setFree(true);
		}
		
		if(!gameBoard.getServantSpace().isFree()){
			gameBoard.getServantSpace().getPlayer().remove(0);
			gameBoard.getServantSpace().setFree(true);
		}
		
		if(!gameBoard.getProductionSpace().isFree()){
			gameBoard.getProductionSpace().freeFirstPlayer();
			gameBoard.getProductionSpace().setFree(true);
		}
		if(!gameBoard.getHarvestSpace().isFree()){
			gameBoard.getHarvestSpace().freeFirstPlayer();
			gameBoard.getHarvestSpace().setFree(true);
		}
		
		for(FamilyMember fm : gameBoard.getCouncilPalace().getPlayerOrder()){
			gameBoard.getCouncilPalace().getPlayerOrder().remove(fm);
		}
	}
	
	private static void freeSpaceMoreThanTwoPlayer(){
		
		if(Game.getPlayers().size() > 2){
			for(FamilyMember fm : gameBoard.getProductionSpace().getPlayer()){
				gameBoard.getProductionSpace().getPlayer().remove(fm);
			}
			for(FamilyMember fm : gameBoard.getHarvestSpace().getPlayer()){
				gameBoard.getHarvestSpace().getPlayer().remove(fm);
			}
		}
		if(Game.getPlayers().size() == 4){
			if(!gameBoard.getMixedSpace().isFree()){
				gameBoard.getMixedSpace().getPlayer().remove(0);
				gameBoard.getMixedSpace().setFree(true);
			}
			if(!gameBoard.getTwoPrivilegesSpace().isFree()){
				gameBoard.getTwoPrivilegesSpace().getPlayer().remove(0);
				gameBoard.getTwoPrivilegesSpace().setFree(true);
			}
		}
	}
		
	private static void freeFamilyMember(){
		for(Player p : Game.getPlayers()){
			for(int i = 0; i < p.getBoard().getFamilyMember().size(); i++){
				p.getBoard().getFamilyMember().get(i).setUsed(false);
			}
		}
	}
	
}
