package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.Resource;

public class FinalBonus {
	
	private List<Resource> finalTerritoriesBonus = new ArrayList<>();
	private List<Resource> finalCharactersBonus = new ArrayList<>();
	private List<Resource> resourceForTerritories = new ArrayList<>();
	private List<Resource> finalMilitaryTrack = new ArrayList<>();
	
	private int resourceFactor;
	
	private static FinalBonus instance;
	
	private FinalBonus(){}
	
	public static FinalBonus instance(){
		if(instance == null){
			instance = new FinalBonus();
		}
		return instance;
	}
	
	public List<Resource> getFinalMilitaryTrack() {
		return finalMilitaryTrack;
	}

	public void setFinalMilitaryTrack(List<Resource> finalMilitaryTrack2) {
		finalMilitaryTrack = finalMilitaryTrack2;
	}

	public int getResourceFactor() {
		return resourceFactor;
	}
	
	
	public List<Resource> getFinalTerritoriesBonus() {
		return finalTerritoriesBonus;
	}
	
	public List<Resource> getFinalCharactersBonus() {
		return finalCharactersBonus;
	}

	public List<Resource> getResourceForTerritories() {
		return resourceForTerritories;
	}
	
	public void setResourceFactor(int r){
		resourceFactor = r;
	}

	public static void setFinalBonus(FinalBonus f){
		if(instance == null){
			instance = f;
		}
	}

	public void setFinalTerritoriesBonus(List<Resource> finalTerritoriesBonus) {
		this.finalTerritoriesBonus = finalTerritoriesBonus;
	}

	public void setFinalCharactersBonus(List<Resource> finalCharactersBonus) {
		this.finalCharactersBonus = finalCharactersBonus;
	}

	public void setResourceForTerritories(List<Resource> resourceForTerritories) {
		this.resourceForTerritories = resourceForTerritories;
	}
	
	

}
