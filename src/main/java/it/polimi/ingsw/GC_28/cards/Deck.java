package it.polimi.ingsw.GC_28.cards;
import java.util.ArrayList;


public class Deck {
	private ArrayList<Territory> territories = new ArrayList<Territory>();
	private ArrayList<Building> buildings = new ArrayList<Building>();
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Venture> ventures = new ArrayList<Venture>();
	
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
	
	public String toString(){
		for(int i = 0; i < territories.size(); i++){
			System.out.println(this.territories.get(i).toString());
			for(int j = 0; j < this.territories.get(i).getImmediateEffect().size(); j++){
				System.out.println(this.territories.get(i).getImmediateEffect().get(j).getClass());
			}
			System.out.println(this.territories.get(i).getPermanentEffect().getClass());
		}
		for(int i = 0; i < buildings.size(); i++){
			System.out.println(this.buildings.get(i).toString());
			System.out.println(this.buildings.get(i).getImmediateEffect().getClass());
			System.out.println(this.buildings.get(i).getPermanentEffect().getClass());

		}
		for(int i = 0; i < characters.size(); i++){
			System.out.println(this.characters.get(i).toString());
			for(int j = 0; j < this.characters.get(i).getImmediateEffect().size(); j++){
				System.out.println(this.characters.get(i).getImmediateEffect().get(j).getClass());
			}
			System.out.println(this.characters.get(i).getPermanentEffect().getClass());

		}
		for(int i = 0; i < ventures.size(); i++){
			System.out.println(this.ventures.get(i).toString());
			for(int j = 0; j < this.ventures.get(i).getImmediateEffect().size(); j++){
				System.out.println(this.ventures.get(i).getImmediateEffect().get(j).getClass());
			}
			System.out.println(this.ventures.get(i).getPermanentEffect().getClass());

		}
		return "";
	}
	
	
	//public  Map<CardType, ArrayList<?>> deck = new EnumMap<CardType, ArrayList<?>>(CardType.class);
	
	/*public Deck(){
		for(CardType cardType : CardType.values()){
			switch(cardType){
			case TERRITORY:
				deck.put(cardType, new ArrayList<Territory>());
				break;
			case BUILDING: 
				deck.put(cardType, new ArrayList<Building>());
				break;
			case CHARACTER:
				deck.put(cardType, new ArrayList<Character>());
				break;
			case VENTURE:
				deck.put(cardType, new ArrayList<Venture>());
				break;
			}
		}
	}

	
	public String toString(){
		StringBuilder s = new StringBuilder();
		for(CardType cardtype : deck.keySet()){
			for(int i = 0; i < deck.get(cardtype).size() ; i++){
				s.append(deck.get(cardtype).get(i).toString());
				s.append("\n\n");
			}
    	}
		return s.toString();
	}

	public void setDeck() {
		
	}

	public Map<CardType, ArrayList<?>> getDeck() {
		return deck;
	}
	/*
	public void addCard(CardType cardType, Card card){
		deck.get(cardType).add(card);
	}
	*/
}
