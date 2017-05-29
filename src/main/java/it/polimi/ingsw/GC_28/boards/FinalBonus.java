package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.Resource;

public class FinalBonus {
	
	private List<Resource> finalTerritoriesBonus = new ArrayList<>();
	private List<Resource> finalCharactersBonus = new ArrayList<>();
	private List<Resource> resourceForTerritories = new ArrayList<>();
	
	private int resourceFactor;
	
	public int getResourceFactor() {
		return resourceFactor;
	}
	
	public void setResourceFactor(int resourceFactor) {
		this.resourceFactor = resourceFactor;
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

	public void setResourceForTerritories(List<Resource> resourceForTerritories) {
		this.resourceForTerritories = resourceForTerritories;
	}

}
