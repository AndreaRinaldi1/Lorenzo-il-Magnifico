package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.Resource;
/**
 * This class wrap all bonus that need to be handle and given at the end of the game
 * @author nicolo
 * @version 1.0, 30/06/2017
 */
public class FinalBonus {
	
	private List<Resource> finalTerritoriesBonus = new ArrayList<>();
	private List<Resource> finalCharactersBonus = new ArrayList<>();
	private List<Resource> resourceForTerritories = new ArrayList<>();
	private List<Resource> finalMilitaryTrack = new ArrayList<>();
	private List<Resource> faithPointTrack = new ArrayList<>();
	
	private int resourceFactor;
	
	private static FinalBonus instance;
	
	private FinalBonus(){}
	
	public static FinalBonus instance(){
		if(instance == null){
			instance = new FinalBonus();
		}
		return instance;
	}
	
	/**
	 * 
	 * @return The bonus for the position in military track
	 */
	public List<Resource> getFinalMilitaryTrack() {
		return finalMilitaryTrack;
	}

	/**
	 * 
	 * @param finalMilitaryTrack2 The bonus to give at the end of the game for the military track position
	 */
	public void setFinalMilitaryTrack(List<Resource> finalMilitaryTrack2) {
		finalMilitaryTrack = finalMilitaryTrack2;
	}
	
	/**
	 * 
	 * @return The factor to use,when compute the final bonus for resources
	 */
	public int getResourceFactor() {
		return resourceFactor;
	}
	
	/**
	 * 
	 * @return The bonus for the territories card owned
	 */
	public List<Resource> getFinalTerritoriesBonus() {
		return finalTerritoriesBonus;
	}
	
	public List<Resource> getFinalCharactersBonus() {
		return finalCharactersBonus;
	}
	
	/**
	 * 
	 * @return the quantity of resources requested to get another territory card
	 */
	public List<Resource> getResourceForTerritories() {
		return resourceForTerritories;
	}
	
	/**
	 * 
	 * @param r the factor to use ,when compute the final bonus for resources
	 */
	public void setResourceFactor(int r){
		resourceFactor = r;
	}
	
	/**
	 * 
	 * @param f the final bonus
	 */
	public static void setFinalBonus(FinalBonus f){
		if(instance == null){
			instance = f;
		}
	}
	
	/**
	 * 
	 * @param finalTerritoriesBonus The List of Resource  to give,depending number of territory cards, 
	 * at the end of the game 
	 */
	public void setFinalTerritoriesBonus(List<Resource> finalTerritoriesBonus) {
		this.finalTerritoriesBonus = finalTerritoriesBonus;
	}
	
	/**
	 * 
	 * @param finalCharactersBonus The List of Resource  to give,depending number of character cards, 
	 * at the end of the game 
	 */
	public void setFinalCharactersBonus(List<Resource> finalCharactersBonus) {
		this.finalCharactersBonus = finalCharactersBonus;
	}
	
	/**
	 * 
	 * @param resourceForTerritories The list of Resource bonus at the end of game, depending on the number of territory card.
	 */
	public void setResourceForTerritories(List<Resource> resourceForTerritories) {
		this.resourceForTerritories = resourceForTerritories;
	}
	
	/**
	 * 
	 * @return The List of Resource bonus for faithpoint paid after the end of an era.
	 */
	public List<Resource> getFaithPointTrack() {
		return faithPointTrack;
	}
	
	/**
	 * 
	 * @param faithPointTrack The List of Resource bonus for faithpoint paid after the end of an era.
	 */
	public void setFaithPointTrack(List<Resource> faithPointTrack) {
		this.faithPointTrack = faithPointTrack;
	}


}
