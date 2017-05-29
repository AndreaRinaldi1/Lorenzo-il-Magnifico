package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.boards.BonusTile;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.Resource;


public class PlayerBoard {
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	

	
	private final BonusTile bonusTile;
	private List<Resource> resourceForTerritories = new ArrayList<>();
	private List<Resource> finalBonusTerritories = new ArrayList<>();
	private List<Resource> finalBonusCharacters = new ArrayList<>();
	private int finalBonusResourceFactor;
	private Resource resources;
	private ArrayList<ExcommunicationTile> excommunicationTile = new ArrayList<>();
	
	private String line = "---------------------------";
	private String retLine = "------------------------\n";
	
	public PlayerBoard(BonusTile bonusTile, Resource resources){
		this.bonusTile = bonusTile;
		this.resources = resources;
	}
	
	
	public String display(){
		String ret = "PLAYER BOARD\n";
		ret+="Territory Cards: ";
		for(int j = 0; j < territories.size(); j++){
			ret+= territories.get(j).toString();
			ret+=" | ";
		}
		ret+="\n";
		ret+= line;
		ret+= retLine;
		ret+="Building Cards: ";
		
		for(int j = 0; j < buildings.size(); j++){
			ret+= buildings.get(j).toString();
			ret+=" | ";
		}
		ret +="\n";
		ret += line; 
		ret += retLine;
		
		ret+="Character Cards: ";
		
		for(int j = 0; j < characters.size(); j++){
			ret+= characters.get(j).toString();
			ret+=" | ";
		}
		ret +="\n";
		ret += line;
		ret += retLine;
		
		ret+="Ventures Cards: ";
		
		for(int j = 0; j < ventures.size(); j++){
			ret+= ventures.get(j).toString();
			ret+=" | ";
		}
		ret+="\n";
		ret += line;
		ret += retLine;
		
		ret+="Resources: ";
		
		ret+= resources.toString(); 
		ret+=" | ";

		ret+="\n";
		ret += line;
		ret += retLine;
		
		ret+="\n";
		
		return ret;
		
	}
	
	//methods in overloading
	public void addCard(Territory t){ 
		territories.add(t);
	}
	
	public void addCard(Building b){
		buildings.add(b);
	}
	
	public void addCard(Character c){
		characters.add(c);
	}
	
	public void addCard(Venture v){
		ventures.add(v);
	}
	
	public void addResource(Resource amount){
		this.getResources().modifyResource(amount, true);
	}
	
	public void reduceResources(Resource amount){
		this.getResources().modifyResource(amount, false);
	}	

	public List<Territory> getTerritories() {
		return territories;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public List<Venture> getVentures() {
		return ventures;
	}

	public BonusTile getBonusTile() {
		return bonusTile;
	}

	public List<Resource> getFinalBonusTerritories() {
		return finalBonusTerritories;
	}

	public void setFinalBonusTerritories(List<Resource> finalBonusTerritories) {
		this.finalBonusTerritories = finalBonusTerritories;
	}

	public List<Resource> getFinalBonusCharacters() {
		return finalBonusCharacters;
	}

	public void setFinalBonusCharacters(List<Resource> finalBonusCharacters) {
		this.finalBonusCharacters = finalBonusCharacters;
	}

	public Resource getResources() {
		return resources;
	}

	public void setResources(Resource resources) {
		this.resources = resources;
	}

	public List<ExcommunicationTile> getExcommunicationTile() {
		return excommunicationTile;
	}
	
	public int getFinalBonusResourceFactor() {
		return finalBonusResourceFactor;
	}

	public void setFinalBonusResourceFactor(int finalBonusResourceFactor) {
		this.finalBonusResourceFactor = finalBonusResourceFactor;
	}


	public List<Resource> getResourceForTerritories() {
		return resourceForTerritories;
	}


	public void setResourceForTerritories(List<Resource> resourceForTerritories) {
		this.resourceForTerritories = resourceForTerritories;
	}	
	
	
	
}
