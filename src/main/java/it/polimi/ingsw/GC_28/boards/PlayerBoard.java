package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.List;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.BonusTile;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;


/**
 * This class represents the playerboard of each player along with his/her cards, the resources and the lateral bonus tile 
 * @author andrearinaldi, nicoloscipione
 * @version 1.0, 07/03/2017
 * @see Territory, Venture, Building, Character, BonusTile, Resource
 */
public class PlayerBoard {
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	private BonusTile bonusTile;
	private Resource resources;
	private String retLine = "------------------------\n";
	
	/**
	 * @param bonusTile the lateral Bonus tile that gives bonuses when the player goes to production or harvest spaces
	 * @param resources the initial resources available to the player
	 */
	public PlayerBoard(BonusTile bonusTile, Resource resources){
		this.bonusTile = bonusTile;
		this.resources = resources;
	}
	
	/**
	 * This method displays the playerboard. It uses several AsciiTables to represent all the cards and resources
	 * and appends them all as strings to a StringBuilder object that is returned as a string.
	 * @return The representation of the playerboard 
	 */
	public String display(){
		StringBuilder ret = new StringBuilder();
		ret.append("\n" + retLine +"\n");
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
		res.addRow(ResourceType.COIN, ResourceType.WOOD, ResourceType.STONE, ResourceType.SERVANT);
		res.addRule();
		res.addRow(resources.getResource().get(ResourceType.COIN), 
				resources.getResource().get(ResourceType.WOOD), 
				resources.getResource().get(ResourceType.STONE), 
				resources.getResource().get(ResourceType.SERVANT));
		res.addRule();
		ret.append(res.render() + "\n");
		ret.append("\n" + retLine);
		
		return ret.toString();
		
	}
	
	/**
	 * @param t the Territory card that we want to add to the playerboard
	 */
	public void addCard(Territory t){ 
		territories.add(t);
	}
	
	/**
	 * @param b the Building card that we want to add to the playerboard
	 */
	public void addCard(Building b){
		buildings.add(b);
	}
	
	/**
	 * @param c the Character card that we want to add to the playerboard
	 */
	public void addCard(Character c){
		characters.add(c);
	}
	
	/**
	 * @param v the Venture card that we want to add to the playerboard
	 */
	public void addCard(Venture v){
		ventures.add(v);
	}
	
	/**
	 * @return the Territory cards the player has
	 */
	public List<Territory> getTerritories() {
		return territories;
	}

	/**
	 * @return the Building cards the player has
	 */
	public List<Building> getBuildings() {
		return buildings;
	}

	/**
	 * @return the Character cards the player has
	 */
	public List<Character> getCharacters() {
		return characters;
	}

	/**
	 * @return the Venture cards the player has
	 */
	public List<Venture> getVentures() {
		return ventures;
	}

	/**
	 * @return the bonusTile of the player
	 */
	public BonusTile getBonusTile() {
		return bonusTile;
	}

	/**
	 * @return the resources of the player
	 */
	public Resource getResources() {
		return resources;
	}

	/**
	 * @param resources the resources we want to set in the player's playerboard
	 */
	public void setResources(Resource resources) {
		this.resources = resources;
	}
	
	/**
	 * @param bonusTile the Bonus Tile we want to set for the player
	 */
	public void setBonusTile(BonusTile bonusTile){
		this.bonusTile = bonusTile;
	}

}
