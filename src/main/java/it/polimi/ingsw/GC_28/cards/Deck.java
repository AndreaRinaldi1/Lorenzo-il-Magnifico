package it.polimi.ingsw.GC_28.cards;
import java.util.ArrayList;
import java.util.List;


public class Deck {
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	
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

}
