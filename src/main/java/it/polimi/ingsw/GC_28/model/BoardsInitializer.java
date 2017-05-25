package it.polimi.ingsw.GC_28.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.boards.Tower;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Deck;
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
	
	
	private static BonusTile bonusTile;
	private static Dice[] dices =  new Dice[3]; //ask if dice could be static,anyway the colors will not change during the game
	private Timer timer = new Timer();
	private static CouncilPrivilege councilPrivilege;
	public ArrayList<Player> players = ProvaSetUp.getPlayer();
	static Deck deck = new Deck();
	public static final GameBoard gameBoard = new GameBoard();
	
	public  void initializeBoard(){
		try {
			initDices();
			initCouncilPrivilege();
			setDeck();
			initGameBoard();
			initSpaces();
			initBonusTile();
			initPlayerBoard();
			initFamilyMember();
		} catch (FileNotFoundException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}
	}
	
	private static void setDeck(){
		try{
			CardReader cardReader = new CardReader();
			deck = cardReader.startRead();
			}catch(FileNotFoundException e){
				Logger.getAnonymousLogger().log(Level.SEVERE, "Deck file not found" + e);
			}
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
			tower = new Tower(prepareCell(ct),false);
		}catch(FileNotFoundException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}
		return tower;		
	}
	
	private static void initGameBoard()throws FileNotFoundException{
		EnumMap<CardType,Tower> mapTower = new EnumMap<>(CardType.class);
		for(CardType ct : CardType.values()){
			mapTower.put(ct, prepareTower(ct));			
		}
		gameBoard.setTowers(mapTower);
	}
	

	private static void initCouncilPrivilege()throws FileNotFoundException{
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
            		
            		gameBoard.setTwoPrivilegesSpace(everySpace.getTwoPrivilegesSpace());
            		gameBoard.getTwoPrivilegesSpace().setFree(true);
            	}else{
            		gameBoard.setMixedSpace(everySpace.getMixedSpace());
            		gameBoard.getMixedSpace().setFree(false);
            		
            		gameBoard.setTwoPrivilegesSpace(everySpace.getTwoPrivilegesSpace());
            		gameBoard.getTwoPrivilegesSpace().setFree(false);
            		}
        	}else{
        		gameBoard.setMixedSpace(everySpace.getMixedSpace());
        		gameBoard.getMixedSpace().setFree(false);
        		
        		gameBoard.setTwoPrivilegesSpace(everySpace.getTwoPrivilegesSpace());
        		gameBoard.getTwoPrivilegesSpace().setFree(false);
        		
        		gameBoard.getProductionSpace().setSecondarySpace(false);
        		gameBoard.getHarvestSpace().setSecondarySpace(false);
        	}
    	}catch(IOException e){
    		throw new IllegalStateException("Error in spaces.json",e);
    	}
	}
	
	private static void initDices(){
		for(int i = 0; i < 3 ; i++){
			dices[i] = new Dice(DiceColor.values()[i]);
			dices[i].rollDice();
		}
	}
	
	public void rollDices(){
		for(int i = 0; i < 3; i++){
			dices[i].setValue();
		}
	}
	
	public static Dice[] getDices() {
		return dices;
	}

	private static void initBonusTile(){
		Gson gson = new GsonBuilder().create();
		try {
			JsonReader jRead = new JsonReader(new FileReader("bonusTile.json"));
			bonusTile = gson.fromJson(jRead, BonusTile.class);
			BonusTile.setBonusTile(bonusTile);
		}catch(FileNotFoundException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot start initialize" + e);
		}
	}
	
	private void initFamilyMember(){
		for(Player p : players){
			ArrayList<FamilyMember> fm = new ArrayList<>();
			for(DiceColor dc : DiceColor.values()){
				FamilyMember member = new FamilyMember(p, false, dc);
				if(dc.equals(DiceColor.NEUTRAL)){
					member = new FamilyMember(p, true, DiceColor.NEUTRAL);
					}
				member.setValue(dices);
				fm.add(member);
			}
			p.getBoard().setFamilyMember(fm);
		}
	}
	
	private void initPlayerBoard(){
		int i = 0;
		EnumMap<ResourceType,Integer> resourceMap = new EnumMap<>(ResourceType.class);
		resourceMap.put(ResourceType.STONE, 2);
		resourceMap.put(ResourceType.WOOD, 2);
		resourceMap.put(ResourceType.SERVANT, 3);
		resourceMap.put(ResourceType.COIN, 5);
		resourceMap.put(ResourceType.MILITARYPOINT, 0);
		resourceMap.put(ResourceType.VICTORYPOINT, 0);
		resourceMap.put(ResourceType.FAITHPOINT, 0);
		for(Player p: players){
			resourceMap.put(ResourceType.COIN, resourceMap.get(ResourceType.COIN)+i);
			Resource resource = Resource.of(resourceMap);
			PlayerBoard pb = new PlayerBoard(BonusTile.instance(),resource);
			p.setBoard(pb);
			i++;
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
