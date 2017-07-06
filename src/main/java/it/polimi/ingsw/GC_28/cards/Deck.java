package it.polimi.ingsw.GC_28.cards;
import java.util.ArrayList;
import java.util.List;

/**
 * This class builds a deck that collects all the cards that are written on the json file "cards.json".
 * This deck is used to take the cards to put on the towers of the game board.
 * The deck is divided in four lists for each card type (Territory, building, Character and Venture).
 * @author robertoturi
 * @version 1.0, 07/06/2017
 */


public class Deck {
	private List<Territory> territories = new ArrayList<>();
	private List<Building> buildings = new ArrayList<>();
	private List<Character> characters = new ArrayList<>();
	private List<Venture> ventures = new ArrayList<>();
	
	/**
	 * @return the list of all the territory cards of the json file "cards.json"
	 */
	public List<Territory> getTerritories() {
		return territories;
	}
	
	/**
	 * @return the list of all the building cards of the json file "cards.json"
	 */
	public List<Building> getBuildings() {
		return buildings;
	}
	
	/**
	 * @return the list of all the character cards of the json file "cards.json"
	 */
	public List<Character> getCharacters() {
		return characters;
	}
	
	/**
	 * @return the list of all the venture cards of the json file "cards.json"
	 */
	public List<Venture> getVentures() {
		return ventures;
	}

}
