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
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;
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
	private GameModel gameModel;
	private static Deck deck = new Deck(); //once initialize it will not change 
	
	public BoardSetup(Game g){
		this.game = g;
		this.gameModel = g.getGameModel();
		gameBoard = gameModel.getGameBoard();
		
	}
	
	public void firstSetUpCards()throws FileNotFoundException {
		prepareDeck();
		prepareTowers();
	}
	
	public void setUpBoard(){
		//gameModel.setPlayers(getNextPlayerOrder());
		getNextPlayerOrder();
		freeFamilyMember();
		freeSpace();
		if(gameModel.getPlayers().size() > 2){
			freeSpaceMoreThanTwoPlayer();
		}
		prepareTowers();
		setDicesValue(gameBoard.getDices());
		setFamilyMember();
		deActiveLeaderCard();
	}
	
	private static void prepareDeck()throws FileNotFoundException{
		//try{
			CardReader cardReader = new CardReader();
			deck =  cardReader.startRead();
			/*}catch(FileNotFoundException e){
				Logger.getAnonymousLogger().log(Level.SEVERE, "deck not found" + e);
			}*/
	}
	
	private void setUpTerritoriesTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.TERRITORY).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getTerritories().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if(deck.getTerritories().get(randomInt).getEra() == game.getCurrentEra() && game.getCurrentPeriod() == 1
						|| deck.getTerritories().get(randomInt).getEra() == (game.getCurrentEra()+1) && game.getCurrentPeriod() == 2){
					Territory t = deck.getTerritories().get(randomInt);
					cell[i].setCard(t);
					cell[i].setFree(true);
					cell[i].setFamilyMember(null);
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
				if(deck.getBuildings().get(randomInt).getEra() == game.getCurrentEra() && game.getCurrentPeriod() == 1
						|| deck.getBuildings().get(randomInt).getEra() == (game.getCurrentEra()+1) && game.getCurrentPeriod() == 2){ 
					Building b = deck.getBuildings().get(randomInt);
					cell[i].setCard(b);
					cell[i].setFree(true);
					cell[i].setFamilyMember(null);
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
				if(deck.getCharacters().get(randomInt).getEra() == game.getCurrentEra() && game.getCurrentPeriod() == 1
						|| deck.getCharacters().get(randomInt).getEra() == (game.getCurrentEra()+1) && game.getCurrentPeriod() == 2){ 
					Character c = deck.getCharacters().get(randomInt);
					cell[i].setCard(c);
					cell[i].setFree(true);
					cell[i].setFamilyMember(null);
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
				if(deck.getVentures().get(randomInt).getEra() == game.getCurrentEra() && game.getCurrentPeriod() == 1
						|| deck.getVentures().get(randomInt).getEra() == (game.getCurrentEra()+1) && game.getCurrentPeriod() == 2){ 
					Venture v = deck.getVentures().get(randomInt);
					cell[i].setCard(v);
					cell[i].setFree(true);
					cell[i].setFamilyMember(null);
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
	}
	
	private void getNextPlayerOrder(){
		List<FamilyMember> inCouncil = gameBoard.getCouncilPalace().getPlayerOrder();
		List<Player> nextOrder = new ArrayList<>();
		if(!(inCouncil.isEmpty())){
			for(FamilyMember fm : inCouncil){
				if(!(nextOrder.contains(fm.getPlayer()))){
					nextOrder.add(fm.getPlayer());
				}
			}
		}
		for(Player p : gameModel.getPlayers()){
			if(!(nextOrder.contains(p))){
				nextOrder.add(p);
			}
		}
		for(int i = 0; i < nextOrder.size(); i++){
			gameModel.getPlayers().set(i, nextOrder.get(i));
		}
	}
	
	public void freeSpace(){
		if(!(gameBoard.getCoinSpace().isFree())){
			gameBoard.getCoinSpace().getPlayer().remove(0);
			gameBoard.getCoinSpace().setFree(true);
		}
		
		if(!gameBoard.getServantSpace().isFree()){
			gameBoard.getServantSpace().getPlayer().remove(0);
			gameBoard.getServantSpace().setFree(true);
		}
		
		if(!(gameBoard.getProductionSpace().isFree())){
			gameBoard.getProductionSpace().freeFirstPlayer();
			gameBoard.getProductionSpace().setFree(true);
		}
		if(!(gameBoard.getHarvestSpace().isFree())){
			gameBoard.getHarvestSpace().freeFirstPlayer();
			gameBoard.getHarvestSpace().setFree(true);
		}
		while(gameBoard.getCouncilPalace().getPlayerOrder().size() > 0){
			if(gameBoard.getCouncilPalace().getPlayerOrder().size() != 0){
				gameBoard.getCouncilPalace().getPlayerOrder().remove(0);
			}
		}
	}
	
	private void freeSpaceMoreThanTwoPlayer(){
		if(gameModel.getPlayers().size() > 2){
			for(FamilyMember fm : gameBoard.getProductionSpace().getPlayer()){
				gameBoard.getProductionSpace().getPlayer().remove(fm);
			}
			for(FamilyMember fm : gameBoard.getHarvestSpace().getPlayer()){
				gameBoard.getHarvestSpace().getPlayer().remove(fm);
			}
		}
		if(gameModel.getPlayers().size() == 4){
			if(!(gameBoard.getMixedSpace().isFree())){
				gameBoard.getMixedSpace().getPlayer().remove(0);
				gameBoard.getMixedSpace().setFree(true);
			}
			if(!(gameBoard.getPrivilegesSpace().isFree())){
				gameBoard.getPrivilegesSpace().getPlayer().remove(0);
				gameBoard.getPrivilegesSpace().setFree(true);
			}
		}
	}
		
	private void freeFamilyMember(){
		for(Player p : gameModel.getPlayers()){
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
	
	private void setFamilyMember(){
		for(Player p : gameModel.getPlayers()){
			FamilyMember[] f = p.getFamilyMembers();
			for(FamilyMember fm : f){
				if(fm.isNeutral()){
					fm.setValue(0);
				}
				else{
					fm.setValue(gameBoard.getDices());
				}
				
			}
		}
	}
	
	private void deActiveLeaderCard(){
		List<Player> players = new ArrayList<>();
		players = gameModel.getPlayers();
		for(Player p : players){
			for(LeaderCard lc : p.getLeaderCards()){
				if(!(lc.getPermanent()) && lc.getPlayed()){
					lc.setActive(false);
				}
			}
		}
	}
	
	
	
}
