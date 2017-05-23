package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.HashMap;

import it.polimi.ingsw.GC_28.boards.BonusTile;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;

public class PlayerBoard {
	private ArrayList<Territory> territories = new ArrayList<Territory>();
	private ArrayList<Building> buildings = new ArrayList<Building>();
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Venture> ventures = new ArrayList<Venture>();
	

	
	private final BonusTile bonusTile;//TODO ask if bonusTile final is ok
	private HashMap<Integer, Resource> finalBonusTerritories = new HashMap<Integer, Resource>();
	private HashMap<Integer, Resource> finalBonusCharacters = new HashMap<Integer, Resource>();
	private Resource resources;
	private ArrayList<ExcommunicationTile> excommunicationTile = new ArrayList<ExcommunicationTile>();
	//FIXME ho provato ad aggiungere anche un indicatore per il numero e il valore dei family members
	private ArrayList<FamilyMember> familyMember = new ArrayList<FamilyMember>();
	
	
	public PlayerBoard(BonusTile bonusTile, Resource resources){
		this.bonusTile = bonusTile;
		this.resources = resources;
	}
	
	
	//per il momento lasciare anche il costruttore vuoto per fare le prove 
	public PlayerBoard(){
		this.bonusTile = BonusTile.instance();
	}
	
	public String display(){
		String ret = "PLAYER BOARD\n";
		ret+="Territory Cards: ";
		for(int j = 0; j < territories.size(); j++){
			ret+= territories.get(j).toString();
			ret+=" | ";
		}
		ret+="\n";
		ret+="---------------------------";
		ret+="------------------------\n";
		ret+="Building Cards: ";
		
		for(int j = 0; j < buildings.size(); j++){
			ret+= buildings.get(j).toString();
			ret+=" | ";
		}
		ret+="\n";
		ret+="---------------------------";
		ret +="------------------------\n";
		
		ret+="Character Cards: ";
		
		for(int j = 0; j < characters.size(); j++){
			ret+= characters.get(j).toString();
			ret+=" | ";
		}
		ret+="\n";
		ret+="---------------------------";
		ret +="------------------------\n";
		
		ret+="Ventures Cards: ";
		
		for(int j = 0; j < ventures.size(); j++){
			ret+= ventures.get(j).toString();
			ret+=" | ";
		}
		ret+="\n";
		ret+="---------------------------";
		ret +="------------------------\n";
		
		ret+="Resources: ";
		
		for(ResourceType rscType : resources.getResource().keySet()){
			ret+= resources.toString(); //ask Rob because probably he wants to use rscType instead of resource
			ret+=" | ";
		}
		ret+="\n";
		ret+="---------------------------";
		ret +="------------------------\n";
		
		ret+="Family Members: ";
		for(int j = 0; j < familyMember.size(); j++){
			ret+= familyMember.get(j).toString();
			ret+= " | ";
		}
		ret+="\n";
		ret+="---------------------------";
		ret +="------------------------\n";
		
		ret+="\n";
		return ret;
		
	}
	
	//methods in overloading
	public void addCard(Territory t){ //TODO controllare il numero di punti military richiesti e che non abbia gia tot=MAX carte territorio
		territories.add(t);
	}
	
	public void addCard(Building b){//TODO controllare che non abbia gia tot=MAX carte building
		buildings.add(b);
	}
	
	public void addCard(Character c){
		characters.add(c);
	}
	
	public void addCard(Venture v){
		ventures.add(v);
	}
	
	public void addResource(Resource amount){
		for(ResourceType resourceType : amount.getResource().keySet()){
			Integer x = 0;
			if(resources.getResource().containsKey(resourceType)){
				x = resources.getResource().get(resourceType);
			}
			x += amount.getResource().get(resourceType);
			resources.getResource().put(resourceType, x);
		}
	}
	
	public void reduceResources(Resource amount){
		//TODO check disponibilità risorse in un altro metodo prima di chiamare questo metodo
		for(ResourceType resourceType : amount.getResource().keySet()){
			Integer x = 0;
			if(resources.getResource().containsKey(resourceType)){
				x = resources.getResource().get(resourceType);
			}
			x -= amount.getResource().get(resourceType);
			resources.getResource().put(resourceType, x);
		}
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

	public HashMap<Integer, Resource> getFinalBonusTerritories() {
		return finalBonusTerritories;
	}

	public void setFinalBonusTerritories(HashMap<Integer, Resource> finalBonusTerritories) {
		this.finalBonusTerritories = finalBonusTerritories;
	}

	public HashMap<Integer, Resource> getFinalBonusCharacters() {
		return finalBonusCharacters;
	}

	public void setFinalBonusCharacters(HashMap<Integer, Resource> finalBonusCharacters) {
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
	
	
}
