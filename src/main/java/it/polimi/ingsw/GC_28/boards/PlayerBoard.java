package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.List;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.BonusTile;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;


public class PlayerBoard {
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	

	
	private final BonusTile bonusTile;
	private Resource resources;
	
	
	private String retLine = "------------------------\n";
	
	public PlayerBoard(BonusTile bonusTile, Resource resources){
		this.bonusTile = bonusTile;
		this.resources = resources;
	}
	
	
	public String display(){
		StringBuilder ret = new StringBuilder();
		ret.append("PLAYER BOARD\n");
		
		AsciiTable territoryTab = new AsciiTable();
		territoryTab.addRule();
		territoryTab.addRow("Territory Cards: ");
		for(int j = 0; j < territories.size(); j++){
			territoryTab.addRule();
			territoryTab.addRow(territories.get(j).toString());
		}
		territoryTab.addRule();
		String terTab = territoryTab.render() + "\n";
		ret.append(terTab);
		
		AsciiTable buildTab = new AsciiTable();
		buildTab.addRule();
		buildTab.addRow("Building Cards: ");
		for(int j = 0; j < buildings.size(); j++){
			buildTab.addRule();
			buildTab.addRow(buildings.get(j).toString());
		}
		buildTab.addRule();
		String build = buildTab.render() + "\n";
		ret.append(build);
		
		AsciiTable charTab = new AsciiTable();
		charTab.addRule();
		charTab.addRow("Character Cards: ");
		for(int j = 0; j < characters.size(); j++){
			charTab.addRule();
			charTab.addRow(characters.get(j).toString());
		}
		charTab.addRule();
		String charT = charTab.render() + "\n";
		ret.append(charT);
		
		AsciiTable ventTab = new AsciiTable();
		ventTab.addRule();
		ventTab.addRow("Ventures Cards: ");
		for(int j = 0; j < ventures.size(); j++){
			ventTab.addRule();
			ventTab.addRow(ventures.get(j).toString());
		}
		ventTab.addRule();
		String vent = ventTab.render() + "\n";
		ret.append(vent);
		
		ret.append("Resources: \n");
		AsciiTable res = new AsciiTable();
		res.addRule();
		res.addRow(ResourceType.COIN, ResourceType.WOOD, ResourceType.STONE, ResourceType.SERVANT, ResourceType.MILITARYPOINT, ResourceType.VICTORYPOINT, ResourceType.FAITHPOINT );
		res.addRule();
		res.addRow(resources.getResource().get(ResourceType.COIN), 
				resources.getResource().get(ResourceType.WOOD), 
				resources.getResource().get(ResourceType.STONE), 
				resources.getResource().get(ResourceType.SERVANT), 
				resources.getResource().get(ResourceType.MILITARYPOINT), 
				resources.getResource().get(ResourceType.VICTORYPOINT), 
				resources.getResource().get(ResourceType.FAITHPOINT));
		res.addRule();
		ret.append(res.render() + "\n");
		//ret.append(resources.toString()); 
		ret.append("\n" + retLine);
		
		return ret.toString();
		
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

	public Resource getResources() {
		return resources;
	}

	public void setResources(Resource resources) {
		this.resources = resources;
	}

}
