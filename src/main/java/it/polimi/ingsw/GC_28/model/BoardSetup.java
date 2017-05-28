package it.polimi.ingsw.GC_28.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;
import java.util.logging.Level;

import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;

public class BoardSetup {
	
	private Game game ;
	private GameBoard gameBoard;
	private static Deck deck = new Deck(); //once initialize it will not change
	private BoardsInitializer bi = new BoardsInitializer(); 
	
	public BoardSetup(Game g){
		this.game = g;
		gameBoard = game.getGameBoard();
	}
	
	public void firstSetUpCards() {
		prepareDeck();
		prepareTowers();
	}
	
	public void setUpBoard(){
		game.setPlayers(getNextPlayerOrder());
		freeFamilyMember();
		freeSpace();
		if(game.getPlayers().size() > 2){
			freeSpaceMoreThanTwoPlayer();
		}
		prepareTowers();
		setDicesValue(gameBoard.getDices());
		bi.initFamilyMember();
	}
	
	private static void prepareDeck(){
		try{
			CardReader cardReader = new CardReader();
			deck =  cardReader.startRead();
			}catch(FileNotFoundException e){
				Logger.getAnonymousLogger().log(Level.SEVERE, "deck not found" + e);
			}
	}
	
	private void setUpTerritoriesTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.TERRITORY).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getTerritories().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getTerritories().get(randomInt).getEra() == game.getCurrentEra()){ 
					Territory t = deck.getTerritories().get(randomInt);
					cell[i].setCard(t);
					deck.getTerritories().remove(t);
					x = true;
				}
			}
		}
	}
	
	private void setUpBuildingsTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.BUILDING).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getBuildings().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getBuildings().get(randomInt).getEra() == game.getCurrentEra()){ 
					Building b = deck.getBuildings().get(randomInt);
					cell[i].setCard(b);
					deck.getBuildings().remove(b);
					x = true;
				}
			}
		}
	}
	
	private  void setUpCharacterTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.CHARACTER).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getCharacters().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getCharacters().get(randomInt).getEra() == game.getCurrentEra()){ 
					Character c = deck.getCharacters().get(randomInt);
					cell[i].setCard(c);
					deck.getCharacters().remove(c);
					x = true;
				}
			}
		}
	}
	
	private void setUpVentureTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.VENTURE).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getVentures().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getVentures().get(randomInt).getEra() == game.getCurrentEra()){ 
					Venture v = deck.getVentures().get(randomInt);
					cell[i].setCard(v);
					deck.getVentures().remove(v);
					x = true;
				}
			}
		}
	}
	
	
	public void prepareTowers(){
		setUpTerritoriesTower();
		setUpBuildingsTower();
		setUpCharacterTower();
		setUpVentureTower();
		if(game.getCurrentPeriod() == 2){
			game.setCurrentEra(game.getCurrentEra()+1);
			game.setPeriod(1);
		}else{
			game.setPeriod(2);
		}
	}
	
	private List<Player> getNextPlayerOrder(){
		ArrayList<FamilyMember> inCouncil = gameBoard.getCouncilPalace().getPlayerOrder();
		List<Player> nextOrder = new ArrayList<>();
		if(!(inCouncil.isEmpty())){
			for(FamilyMember fm : inCouncil){
				if(!(nextOrder.contains(fm.getPlayer()))){
					nextOrder.add(fm.getPlayer());
				}
			}
		}
		if(nextOrder.isEmpty()){
			nextOrder = game.getPlayers();
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
	
	private void freeSpaceMoreThanTwoPlayer(){
		if(game.getPlayers().size() > 2){
			for(FamilyMember fm : gameBoard.getProductionSpace().getPlayer()){
				gameBoard.getProductionSpace().getPlayer().remove(fm);
			}
			for(FamilyMember fm : gameBoard.getHarvestSpace().getPlayer()){
				gameBoard.getHarvestSpace().getPlayer().remove(fm);
			}
		}
		if(game.getPlayers().size() == 4){
			if(!(gameBoard.getMixedSpace().isFree())){
				gameBoard.getMixedSpace().getPlayer().remove(0);
				gameBoard.getMixedSpace().setFree(true);
			}
			if(!(gameBoard.getTwoPrivilegesSpace().isFree())){
				gameBoard.getTwoPrivilegesSpace().getPlayer().remove(0);
				gameBoard.getTwoPrivilegesSpace().setFree(true);
			}
		}
	}
		
	private void freeFamilyMember(){
		for(Player p : game.getPlayers()){
			for(int i = 0; i < p.getFamilyMembers().length; i++){
				p.getFamilyMembers()[i].setUsed(false);
			}
		}
	}
	
	private void setDicesValue(Dice[] dices){
		for(int i = 0; i < 3 ; i++){
			dices[i].rollDice();
		}
		gameBoard.setDices(dices);
	}
	
	
}
