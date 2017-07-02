package it.polimi.ingsw.GC_28.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;


import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationReader;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.LeaderCardReader;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.spaces.EverySpace;
import it.polimi.ingsw.GC_28.view.GameView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;


/**
 * This class initialize gameBoard and playerBoard for the given game and players
 * @author nicoloscipione
 * @version 1.0, 05/23/2017
 * @see GameView,GameManager, GameModel, GameBoard, Excommunication, BonusTile, Dice,Space,PlayerBoard 
 */

public class BoardsInitializer {
	
	private BonusTile bonusTile = new BonusTile();
	private Dice[] dices = new Dice[3];
	
	private CouncilPrivilege councilPrivilege;
	private FinalBonus finalBonus;
	private List<Player> players = new ArrayList<>();
	public GameBoard gameBoard = new GameBoard();
	private GameModel gameModel;
	
	/**
	 * This method initialize a gameBoard in every aspect. It is used before
	 *  the beginning of every game. It return a gameView with a gameModel 
	 *  already set.
	 * @param players The list of the players playing the game.
	 * @return a new gameView every time it's call. 
	 */
	
	public GameView initializeBoard(List<Player> players)throws FileNotFoundException,IOException{
		this.players  = players;
		initDices();
		initCouncilPrivilege();
		initGameBoard();
		initSpaces();
		initExcommunication();
		initPlayerBoard();
		initFinalBonus();
		initFamilyMember();
		gameModel = new GameModel(gameBoard, players);
		placeBonusTile();
		completeExcommunicationArray();
		initLeaderCard();
		return new GameView(gameModel);
	}
	
	/**
	 * This method prepare every cell of the Tower of the given card Type.
	 * If necessary the method set the bonus reading it from a specific json file.   
	 * @param ct Card type of the tower it's going to set. 
	 * @return The array of 4 cells that compose the tower structure.
	 * @throws FileNotFoundException
	 */
	
	private static Cell[] prepareCell(CardType ct) throws FileNotFoundException{ 
		/*This gson attribute is used to convert an EnumMap from a file, because gson library is
		 *not able to deserialize Collectors that don't have a default constructor.
		 *The provided file must contain a Map of the tower type and arrayList of resourceBonus given to 
		 * the 3rd and 4th level of the tower*/
		Gson gsonForEnum  = new GsonBuilder().registerTypeAdapter(new TypeToken<EnumMap<CardType, ArrayList<Resource>>>(){}.getType(),
				new EnumMapInstanceCreator<CardType, ArrayList<Resource>>(CardType.class)).create();
		JsonReader reader = new JsonReader(new FileReader("cell.json"));
		Type cellResourceBonus = new TypeToken<EnumMap<CardType,ArrayList<Resource>>>() {}.getType();
		EnumMap<CardType,ArrayList<Resource>> cellsBonus = gsonForEnum.fromJson(reader, cellResourceBonus);
		LinkedList<Cell> cells = new LinkedList<>();//LinkedList allow the order of elements
		for(int i = 1; i < 8; i += 2){
			if(i == 5 ){
				/*the first line take the CardType of the tower and set the bonus as asked*/
				Resource resourceBonus = cellsBonus.get(ct).get(0);
				Cell cell = new Cell(resourceBonus,i,true);
				cells.addLast(cell);
			}else if(i == 7){
				Resource resourceBonus = cellsBonus.get(ct).get(1);
				Cell cell = new Cell(resourceBonus,i,true);
				cells.addLast(cell);
			}else{
				Cell cell = new Cell(null,i,true);
				cells.addLast(cell);
			}
		}
		 return cells.toArray(new Cell[cells.size()]); // convert the linkedList in Array
	}
	
	/**
	 * This method prepare a tower of the given card type, it builds the tower preparing the four
	 * cells necessary.
	 * @param ct Card type of the tower to prepare
	 * @return The tower with its cells and bonus
	 */
	private static Tower prepareTower(CardType ct){
		Tower tower = null;
		try{
			tower = new Tower(prepareCell(ct));
		}catch(FileNotFoundException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}
		return tower;		
	}
	
	/**
	 * This method set every tower in the gameBoard. It prepare every tower, before setting it to the gameboard.
	 * @throws FileNotFoundException
	 */
	private void initGameBoard()throws FileNotFoundException{
		EnumMap<CardType,Tower> mapTower = new EnumMap<>(CardType.class);
		for(CardType ct : CardType.values()){
			mapTower.put(ct, prepareTower(ct));			
		}
		gameBoard.setTowers(mapTower);
	}
	
	/**
	 * This method instance all council privileges. It reads the property of each privilege from a specific json file. 
	 * @throws FileNotFoundException
	 */
	private void initCouncilPrivilege()throws FileNotFoundException{
		Gson gson = new GsonBuilder().create();
		try{
			JsonReader reader = new JsonReader(new FileReader("priv.json"));
			councilPrivilege = gson.fromJson(reader, CouncilPrivilege.class);
			CouncilPrivilege.setCouncilPrivilege(councilPrivilege);
			reader.close();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}
	}
	
	/**
	 * This method initialize all different types of spaces of the gameboard. Since the presence of some space type 
	 * depends upon the number of players in game, this method set different space depending on the number of players.
	 * @throws FileNotFoundException
	 */
	private void initSpaces()throws FileNotFoundException{
		Gson gson = new GsonBuilder().create(); 
        try{
        	JsonReader reader = new JsonReader(new FileReader("spaces.json"));
        	EverySpace everySpace = gson.fromJson(reader, EverySpace.class);
        	reader.close();
        	gameBoard.setCouncilPalace(everySpace.getCouncilPalace());
        	gameBoard.getCouncilPalace().setFree(true);
        	gameBoard.setCoinSpace(everySpace.getCoinSpace());
        	gameBoard.getCoinSpace().setFree(true);
        	
        	gameBoard.setServantSpace(everySpace.getServantSpace());
        	gameBoard.getServantSpace().setFree(true);
        	
        	gameBoard.setProductionSpace(everySpace.getProduction());
        	gameBoard.setHarvestSpace(everySpace.getHarvest());
        	gameBoard.getProductionSpace().setFree(true);
    		gameBoard.getHarvestSpace().setFree(true);
        	if(players.size() > 2){ 
        		//checking if other spaces are available according to the game rules
            	gameBoard.getProductionSpace().setSecondarySpace(true);
            	gameBoard.getHarvestSpace().setSecondarySpace(true);
            	if(players.size() == 4){ 
            		gameBoard.setMixedSpace(everySpace.getMixedSpace());
            		gameBoard.getMixedSpace().setFree(true);
            		
            		gameBoard.setPrivilegesSpace(everySpace.getPrivilegesSpace());
            		gameBoard.getPrivilegesSpace().setFree(true);
            	}else{
            		gameBoard.setMixedSpace(everySpace.getMixedSpace());
            		gameBoard.getMixedSpace().setFree(false);
            		
            		gameBoard.setPrivilegesSpace(everySpace.getPrivilegesSpace());
            		gameBoard.getPrivilegesSpace().setFree(false);
            		}
        	}else{
        		gameBoard.setMixedSpace(everySpace.getMixedSpace());
        		gameBoard.getMixedSpace().setFree(false);
        		
        		gameBoard.setPrivilegesSpace(everySpace.getPrivilegesSpace());
        		gameBoard.getPrivilegesSpace().setFree(false);
        		
        		gameBoard.getProductionSpace().setSecondarySpace(false);
        		gameBoard.getHarvestSpace().setSecondarySpace(false);
        	}
    	}catch(IOException e){
    		throw new IllegalStateException("Error in spaces.json",e);
    	}
	}
	
	/**
	 * This method initialize the three dices. It sets the color and the value and sets them in the gameboard. 
	 */
	void initDices(){
		for(int i = 0; i < 3 ; i++){
			dices[i] = new Dice(DiceColor.values()[i]);
			dices[i].rollDice();
		}
		gameBoard.setDices(dices);
	}
	
	
	/**
	 * This method set bonusTile to every player in game.
	 * @throws FileNotFoundException
	 */
	private void placeBonusTile()throws FileNotFoundException{
		for(Player p : players){
			p.getBoard().setBonusTile(bonusTile);
		}
	}
	
	/**
	 * This method prepare the FamilyMembers for every player. It sets the color  of the corresponding 
	 * diceColor.
	 */
	void initFamilyMember(){
		for(Player p : players){
			ArrayList<FamilyMember> fm = new ArrayList<>();
			for(DiceColor dc : DiceColor.values()){
				FamilyMember member = new FamilyMember(p, false, dc);
				if(dc.equals(DiceColor.NEUTRAL)){
					member = new FamilyMember(p, true, DiceColor.NEUTRAL);
					member.setValue(0);
					}
				member.setValue(dices);
				fm.add(member);
			}
			p.setFamilyMembers(fm.toArray(new FamilyMember[4]));
		}
	}
	
	/**
	 * This method instance the bonus given at the end of the game. It reads a specific json file
	 * @throws IOException
	 */
	public void initFinalBonus() throws IOException{
		Gson gson = new GsonBuilder().create();
		JsonReader readerFinalBonus = new JsonReader(new FileReader("finalBonus.json"));
		finalBonus = gson.fromJson(readerFinalBonus, FinalBonus.class);
		FinalBonus.setFinalBonus(finalBonus);
		readerFinalBonus.close();
	}
	
	/**
	 * This method prepare the playerBoard for every player. It sets the resource according to the game rules
	 * and sets the board to the player.
	 */
	private void initPlayerBoard(){
		int i = 0;
		for(Player p: players){
			EnumMap<ResourceType,Integer> resourceMap = new EnumMap<>(ResourceType.class);
			resourceMap.put(ResourceType.STONE, 2);
			resourceMap.put(ResourceType.WOOD, 2);
			resourceMap.put(ResourceType.SERVANT, 3);
			resourceMap.put(ResourceType.MILITARYPOINT, 0);
			resourceMap.put(ResourceType.VICTORYPOINT, 0);
			resourceMap.put(ResourceType.FAITHPOINT, 0);
			resourceMap.put(ResourceType.COIN, 5+i);
			Resource resource = Resource.of(resourceMap);
			PlayerBoard pb = new PlayerBoard(bonusTile,resource);
			p.setBoard(pb);
			i++;
		}
	}
	
	/**
	 * This method choose randomly which excommunications will be used in game and set them in gameboard. 
	 * @throws IOException
	 */
	private void initExcommunication()throws IOException{
		ExcommunicationReader exReader = new ExcommunicationReader();
		List<ExcommunicationTile> ex = exReader.startRead();
		for(int i = 0; i < 3; i++){
			ArrayList<ExcommunicationTile> tmp = new ArrayList<>();
			for(ExcommunicationTile e : ex){
				if(e.getEra() == (i+1)){
					tmp.add(e);
				}
			}
			Random rand = new Random();
			int randomInt = rand.nextInt(tmp.size());
			gameBoard.getExcommunications()[i] = tmp.get(randomInt);
		}
		
	}
	/**
	 * This method complete the excommunication array in every player.It fills the array with excommunication with null effect,
	 * thus it's possible to display the array even if the players has no excommunication. 
	 */
	private void completeExcommunicationArray(){
		for(Player p : gameModel.getPlayers()){
			for(int i = 0; i < 3; i++){
				ExcommunicationTile e = new ExcommunicationTile();
				e.setEffect(null);
				p.getExcommunicationTile().add(e);
			}
		}
	}
	
	/**
	 * This method set randomly the 4 leader card to each player.
	 */
	private void initLeaderCard(){
		LeaderCardReader reader = new LeaderCardReader();
		List<LeaderCard> leaders = reader.start();
		for(Player p : players){
			for(int i = 0; i < 4; i++){
				int randomInt = ThreadLocalRandom.current().nextInt(0, leaders.size());
				p.getLeaderCards().add(leaders.get(randomInt));
				leaders.remove(randomInt);
			}
		}
	}
}

	

class EnumMapInstanceCreator<K extends Enum<K>, V> implements InstanceCreator<EnumMap<K, V>> {
private final Class<K> enumClazz;

	public EnumMapInstanceCreator(final Class<K> enumClazz) {
		super();
		this.enumClazz = enumClazz;
	}

	@Override
	public EnumMap<K, V> createInstance(final Type type) {
		return new EnumMap<>(enumClazz);
	}
}
