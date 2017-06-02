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
	@Override
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
}
