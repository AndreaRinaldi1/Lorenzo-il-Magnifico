package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;

import it.polimi.ingsw.GC_28.components.Resource;

public class FinalBonus {
	
	private ArrayList<Resource> finalTerritoriesBonus = new ArrayList<>();
	private ArrayList<Resource> finalCharactersBonus = new ArrayList<>();
	private int resourceFactor;
	
	public int getResourceFactor() {
		return resourceFactor;
	}
	
	public void setResourceFactor(int resourceFactor) {
		this.resourceFactor = resourceFactor;
	}
	
	public ArrayList<Resource> getFinalTerritoriesBonus() {
		return finalTerritoriesBonus;
	}
	
	public ArrayList<Resource> getFinalCharactersBonus() {
		return finalCharactersBonus;
	}
	
	
	
}
