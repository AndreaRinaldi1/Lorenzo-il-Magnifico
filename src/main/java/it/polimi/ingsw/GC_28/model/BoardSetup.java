package it.polimi.ingsw.GC_28.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Dice;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;


/**
 * This class prepare the board to the next period. It has two main method firstSetUpCards and setUpBoard, the first one
 * has to be used before the first turn of the first period of the game, the second one is necessary after every period of game 
 * @author nicoloscipione
 * @version 1.0, 06/28/2017
 * @see GameView,GameManager, GameModel, GameBoard, Excommunication, BonusTile, Dice,Space,PlayerBoard 
 */
public class BoardSetup {
	private GameManager gameManager;
	private GameView game ;
	private GameBoard gameBoard;
	private GameModel gameModel;
	private static Deck deck = new Deck(); 
	
	/**
	 * The constructor sets gameManager, gameView, gameModel and gameBoard. 
	 * @param gameManager The gameManager on which the class' method will effect
	 */
	public BoardSetup(GameManager gameManager){
		this.gameManager = gameManager;
		this.game = gameManager.getView();
		this.gameModel = game.getGameModel();
		gameBoard = gameModel.getGameBoard();
		
	}
	
	/**
	 * This method prepare the deck for the game and set the cards to the tower for the first time in game
	 * @throws FileNotFoundException
	 */
	public void firstSetUpCards()throws FileNotFoundException {
		prepareDeck();
		prepareTowers();
	}
	
	/**
	 * This method prepare the board to the next period. It clears the board from the left of the last period, resets
	 * family members value, dices values, free the spaces, deactivates leader card and set the new order for players. 
	 */
	public void setUpBoard(){
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
	
	/**
	 * This method read all cards from a file and set the deck
	 * @throws FileNotFoundException
	 */
	private static void prepareDeck()throws FileNotFoundException{
		CardReader cardReader = new CardReader();
		deck =  cardReader.startRead();
	}
	
	/**
	 * This method reset the territories card Type tower. It frees cells and sets randomly a new card.
	 */
	private void setUpTerritoriesTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.TERRITORY).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = new Random().nextInt(deck.getTerritories().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if((deck.getTerritories().get(randomInt).getEra() == gameManager.getCurrentEra() && gameManager.getCurrentPeriod() == 1)
						|| (deck.getTerritories().get(randomInt).getEra() == (gameManager.getCurrentEra()+1) && gameManager.getCurrentPeriod() == 2)){
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
	
	/**
	 * This method reset the buildings card Type tower. It frees cells and sets randomly a new card.
	 */
	private void setUpBuildingsTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.BUILDING).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getBuildings().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if((deck.getBuildings().get(randomInt).getEra() == gameManager.getCurrentEra() && gameManager.getCurrentPeriod() == 1)
						|| (deck.getBuildings().get(randomInt).getEra() == (gameManager.getCurrentEra()+1) && gameManager.getCurrentPeriod() == 2)){ 
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
	
	/**
	 * This method reset the characters card Type tower. It frees cells and sets randomly a new card.
	 */
	private  void setUpCharacterTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.CHARACTER).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getCharacters().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if((deck.getCharacters().get(randomInt).getEra() == gameManager.getCurrentEra() && gameManager.getCurrentPeriod() == 1)
						|| (deck.getCharacters().get(randomInt).getEra() == (gameManager.getCurrentEra()+1) && gameManager.getCurrentPeriod() == 2)){ 
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
	
	/**
	 * This method reset the ventures card Type tower. It frees cells and sets randomly a new card.
	 */
	private void setUpVentureTower(){
		Cell [] cell = gameBoard.getTowers().get(CardType.VENTURE).getCells();
		for(int i = 0; i < cell.length; i++){
			boolean x = false;
			while(!x){
				int randomInt = ThreadLocalRandom.current().nextInt(0, deck.getVentures().size());
				/*the condition check if the era of the randomly selected card is correct and
				 *  if the card has already been drafted, otherwise the choice of the card is repeated*/
				if((deck.getVentures().get(randomInt).getEra() == gameManager.getCurrentEra() && gameManager.getCurrentPeriod() == 1)
						|| (deck.getVentures().get(randomInt).getEra() == (gameManager.getCurrentEra()+1) && gameManager.getCurrentPeriod() == 2)){ 
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
	
	
	/**
	 * This method recall all setUp tower method, thus to prepare all towers for the new game period.
	 */
	private void prepareTowers(){
		setUpTerritoriesTower();
		setUpBuildingsTower();
		setUpCharacterTower();
		setUpVentureTower();	
	}
	
	/**
	 * This method take the new player order from the council palace and set it as move order for the new period, at the end
	 * if a players is not in the next order list it's add as the last one.
	 */
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
	
	/**
	 * This method frees all space in gameboard that are used in every game independently from players number in game.
	 */
	private void freeSpace(){
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
	
	/**
	 * This method frees spaces which presence depends upon the number of players in game
	 */
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
	
	/**
	 * This method resets each family member as not used for every player
	 */
	private void freeFamilyMember(){
		for(Player p : gameModel.getPlayers()){
			for(int i = 0; i < p.getFamilyMembers().length; i++){
				p.getFamilyMembers()[i].setUsed(false);
			}
		}
	}
	
	/**
	 * This method rolls dices for the new period and set them in gameboard.
	 * @param dices The gameboard Dices
	 */
	private void setDicesValue(Dice[] dices){
		for(int i = 0; i < 3 ; i++){
			dices[i].rollDice();
		}
		gameBoard.setDices(dices);
	}
	
	/**
	 * This method set the value of family members according to dice value.
	 */
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
	
	/**
	 * This method reset as not activate the value of all played leader cards which effect is not permanent
	 */
	private void deActiveLeaderCard(){
		for(Player p : gameModel.getPlayers()){
			for(LeaderCard lc : p.getLeaderCards()){
				if(!(lc.getPermanent()) && lc.getPlayed()){
					lc.setActive(false);
				}
				else if(lc.getPermanent() && lc.getPlayed() && !("Sisto IV").equalsIgnoreCase(lc.getName()) && !("Santa Rita").equalsIgnoreCase(lc.getName())){
					lc.getEffect().apply(p, game);
				}
			}
		}
	}
	
	
	
}
