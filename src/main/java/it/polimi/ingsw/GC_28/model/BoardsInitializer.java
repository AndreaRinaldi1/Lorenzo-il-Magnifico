package it.polimi.ingsw.GC_28.model;

/**
 * Initialize gameBoard and playerBoard for the given game and players
 * @author Nick
 * @version 1.0, 05/23/2017
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;


public class BoardsInitializer {
	
	private BonusTile bonusTile = new BonusTile();
	private Dice[] dices = new Dice[3];
	
	private Timer timer = new Timer();
	private CouncilPrivilege councilPrivilege;
	private FinalBonus finalBonus;
	private List<Player> players = new ArrayList<>();
	public GameBoard gameBoard = new GameBoard();
	//private Game g = new Game();
	private GameModel gameModel;
	
	public Game initializeBoard(List<Player> players)throws FileNotFoundException,IOException{
		//try {
			this.players  = players;
			initDices();
			initCouncilPrivilege();
			initGameBoard();
			initSpaces();
			//gameModel.setGameBoard(gameBoard);
			initExcommunication();
			initPlayerBoard();
			initFinalBonus();
			initFamilyMember();
			gameModel = new GameModel(gameBoard, players);
			placeBonusTile();
			//gameModel.setPlayers(players);
			completeExcommunicationArray();
			initLeaderCard();
		/*} catch (FileNotFoundException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}*/
		return new Game(gameModel);
	}
	
	private static Cell[] prepareCell(CardType ct) throws FileNotFoundException{ //LinkedList allow the order of elements
		/*This gson attribute is used to convert an EnumMap from a file, because gson library is
		 *not able to deserialize Collectors that don't have a default constructor.
		 *The provided file must contain a Map of the tower type and arrayList of resourceBonus given to 
		 * the 3rd and 4th level of the tower*/
		Gson gsonForEnum  = new GsonBuilder().registerTypeAdapter(new TypeToken<EnumMap<CardType, ArrayList<Resource>>>(){}.getType(),
				new EnumMapInstanceCreator<CardType, ArrayList<Resource>>(CardType.class)).create();
		//Gson gson = new GsonBuilder().create()
		JsonReader reader = new JsonReader(new FileReader("cell.json"));
		Type cellResourceBonus = new TypeToken<EnumMap<CardType,ArrayList<Resource>>>() {}.getType();
		EnumMap<CardType,ArrayList<Resource>> cellsBonus = gsonForEnum.fromJson(reader, cellResourceBonus);
		LinkedList<Cell> cells = new LinkedList<>();
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
	
	private static Tower prepareTower(CardType ct){
		Tower tower = null;
		try{
			tower = new Tower(prepareCell(ct));
		}catch(FileNotFoundException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}
		return tower;		
	}
	
	private void initGameBoard()throws FileNotFoundException{
		EnumMap<CardType,Tower> mapTower = new EnumMap<>(CardType.class);
		for(CardType ct : CardType.values()){
			mapTower.put(ct, prepareTower(ct));			
		}
		gameBoard.setTowers(mapTower);
	}
	

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
	
	void initDices(){
		for(int i = 0; i < 3 ; i++){
			dices[i] = new Dice(DiceColor.values()[i]);
			dices[i].rollDice();
		}
		gameBoard.setDices(dices);
	}
	
	

	private void placeBonusTile()throws FileNotFoundException{
		for(Player p : players){
			p.getBoard().setBonusTile(bonusTile);
		}
		/*Gson gson = new GsonBuilder().create();
		try {
			JsonReader jRead = new JsonReader(new FileReader("bonusTile.json"));
			BonusTile bonusTi = gson.fromJson(jRead, BonusTile.class);
			bonusTile.setHarvestEffect(bonusTi.getHarvestEffect());
			bonusTile.setProductionEffect(bonusTi.getProductionEffect());
		}catch(FileNotFoundException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}*/
	}
	
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
	
	public void initFinalBonus() throws IOException{
		Gson gson = new GsonBuilder().create();
		//try {
			JsonReader readerFinalBonus = new JsonReader(new FileReader("finalBonus.json"));
			//Type hashMapType = new TypeToken<HashMap<String,ArrayList<Resource>>>() {}.getType();
			finalBonus = gson.fromJson(readerFinalBonus, FinalBonus.class);
			FinalBonus.setFinalBonus(finalBonus);
			readerFinalBonus.close();
		/*} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "file not found" + e);
		}*/
		
	}
	
	private void initPlayerBoard(){
		int i = 0;
		for(Player p: players){
			EnumMap<ResourceType,Integer> resourceMap = new EnumMap<>(ResourceType.class);
			resourceMap.put(ResourceType.STONE, 2);
			resourceMap.put(ResourceType.WOOD, 2);
			resourceMap.put(ResourceType.SERVANT, 3);
			//resourceMap.put(ResourceType.COIN, 5); non credo che serva
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
	
	private void initExcommunication()throws IOException{
		System.out.println(1);
		ExcommunicationReader exReader = new ExcommunicationReader();
		List<ExcommunicationTile> ex = new ArrayList<>();
		ex = exReader.startRead();
		for(int i = 0; i < 3; i++){
			ArrayList<ExcommunicationTile> tmp = new ArrayList<>();
			for(ExcommunicationTile e : ex){
				if(e.getEra() == (i+1)){
					tmp.add(e);
				}
			}
			int randomInt = ThreadLocalRandom.current().nextInt(0, tmp.size());
			gameBoard.getExcommunications()[i] = tmp.get(randomInt);
			System.out.println(gameBoard.getExcommunications()[i].getEffect());
		}
		
	}
	
	private void completeExcommunicationArray(){
		for(Player p : gameModel.getPlayers()){
			for(int i = 0; i < 3; i++){
				ExcommunicationTile e = new ExcommunicationTile();
				e.setEffect(null);
				p.getExcommunicationTile().add(e);
			}
		}
	}
	
	private void initLeaderCard(){
		List<LeaderCard> leaders = new ArrayList<>();
		LeaderCardReader reader = new LeaderCardReader();
		leaders = reader.start();
		for(Player p : players){
			for(int i = 0; i < 4; i++){
				int randomInt = ThreadLocalRandom.current().nextInt(0, leaders.size());
				p.getLeaderCards().add(leaders.get(randomInt));
				leaders.remove(randomInt);
			}
			System.out.println(p.getLeaderCards().toString());
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
