package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;

import it.polimi.ingsw.GC_28.boards.BonusTile;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;


public class PlayerBoard {
	private ArrayList<Territory> territories = new ArrayList<>();
	private ArrayList<Building> buildings = new ArrayList<>();
	private ArrayList<Character> characters = new ArrayList<>();
	private ArrayList<Venture> ventures = new ArrayList<>();
	

	
	private final BonusTile bonusTile;
	private ArrayList<Resource> finalBonusTerritories = new ArrayList<>();
	private ArrayList<Resource> finalBonusCharacters = new ArrayList<>();
	private int finalBonusResourceFactor;
	private Resource resources;
	private ArrayList<ExcommunicationTile> excommunicationTile = new ArrayList<>();

	private ArrayList<FamilyMember> familyMember = new ArrayList<>();
	
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
		
		/*ret+="Family Members: ";
		for(int j = 0; j < familyMember.size(); j++){
			ret+= familyMember.get(j).getDiceColor().name() + ": ";
			ret+= familyMember.get(j).getValue().toString();
			ret+= " | ";
		}
		ret+="\n";
		ret += line;
		ret += retLine;*/
		
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
		//TODO check disponibilità risorse in un altro metodo prima di chiamare questo metodo
		this.getResources().modifyResource(amount, false);
	}
	
	
	

	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public ArrayList<Character> getCharacters() {
		return characters;
	}

	public ArrayList<Venture> getVentures() {
		return ventures;
	}

	public BonusTile getBonusTile() {
		return bonusTile;
	}

	public ArrayList<Resource> getFinalBonusTerritories() {
		return finalBonusTerritories;
	}

	public void setFinalBonusTerritories(ArrayList<Resource> finalBonusTerritories) {
		this.finalBonusTerritories = finalBonusTerritories;
	}

	public ArrayList<Resource> getFinalBonusCharacters() {
		return finalBonusCharacters;
	}

	public void setFinalBonusCharacters(ArrayList<Resource> finalBonusCharacters) {
		this.finalBonusCharacters = finalBonusCharacters;
	}

	public Resource getResources() {
		return resources;
	}

	public void setResources(Resource resources) {
		this.resources = resources;
	}

	public ArrayList<ExcommunicationTile> getExcommunicationTile() {
		return excommunicationTile;
	}


	public ArrayList<FamilyMember> getFamilyMember() {
		return familyMember;
	}


	public void setFamilyMember(ArrayList<FamilyMember> familyMember) {
		this.familyMember = familyMember;
	}
	
	public int getFinalBonusResourceFactor() {
		return finalBonusResourceFactor;
	}

	public void setFinalBonusResourceFactor(int finalBonusResourceFactor) {
		this.finalBonusResourceFactor = finalBonusResourceFactor;
	}	
	
}
