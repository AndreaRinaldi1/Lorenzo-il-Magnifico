package cards;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CardWriter {
	Scanner scanner = new Scanner(System.in);
	EnumMap<ResourceType, Integer> cost;
	Resource resourceCost;
	int coin, wood, stone, servant, militaryPoint, victoryPoint, faithPoint;
	Deck deck = new Deck();


	public static void main(String[] args){
		CardWriter writer = new CardWriter();
		writer.startReadCard();
	}
	

	private void startReadCard(){
		
		Gson obj = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter file = new FileWriter("cards.json", true);
			

			String name;
			int IDNumber;
			int era;
			CardType cardType;
			String proceed;
			do{
				name = enterName();
				IDNumber = enterIDNumber();
				era = enterEra();
				cardType = enterCardType();
				
				//card = new Card();
				
				switch(cardType){
				case TERRITORY:
					this.setTerritory(new Territory(name, IDNumber, era, cardType));
					break;
				case BUILDING: 
					this.setBuilding(new Building(name, IDNumber, era, cardType));
					break;
				case CHARACTER:
					this.setCharacter(new Character(name, IDNumber, era, cardType));
					break;
				case VENTURE:
					this.setVenture(new Venture(name, IDNumber, era, cardType));
					break;
				}
			/*
				card.setName(name);
				card.setIDNumber(IDNumber);
				card.setEra(era);
				card.setCardType(cardType);
			*/
				
				//deck.addCard(cardType, card);
				System.out.println("Do you want to go on?");
				proceed = scanner.nextLine();
			
			}while(!(proceed.equals("end")));
			
			String tmp = obj.toJson(deck);
			file.write(tmp);
			file.flush();
			file.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		scanner.close();
	}

	private void setTerritory(Territory territory){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		/*cost.put(ResourceType.COIN, 0);
		cost.put(ResourceType.WOOD, 0);
		cost.put(ResourceType.STONE, 0);
		cost.put(ResourceType.SERVANT, 0);
		cost.put(ResourceType.MILITARYPOINT, 0);
		cost.put(ResourceType.VICTORYPOINT, 0);
		cost.put(ResourceType.FAITHPOINT, 0);*/

		resourceCost = Resource.of(cost);
		territory.setCost(resourceCost);
		deck.getTerritories().add(territory);
	}
	
	private void setBuilding(Building building){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		enterResourceCost(cost);
		resourceCost = Resource.of(cost);
		building.setCost(resourceCost);
		deck.getBuildings().add(building);
	}

	private void setCharacter(Character character){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		coin = enterCost(ResourceType.COIN.name());
		cost.put(ResourceType.COIN, coin);
		resourceCost = Resource.of(cost);
		character.setCost(resourceCost);
		deck.getCharacters().add(character);
	}
	
	private void setVenture(Venture venture){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		enterResourceCost(cost);
		resourceCost = Resource.of(cost);
		venture.setCost(resourceCost);
		
		System.out.println("Is there an alternative cost?[y/n] ");
		scanner.hasNextLine();
		String alternative = scanner.nextLine();
		while(!alternative.equals("y") && !alternative.equals("n")){
			System.out.println("Not valid input!");
			System.out.println("Is there an alternative cost?[y/n] ");
			alternative = scanner.nextLine();
		}
		if(alternative.equals("y")){
			venture.setAlternativeCostPresence(true);
			setVentureAlternativeCost(venture);
		}
		else{
			venture.setAlternativeCostPresence(false);
		}
		deck.getVentures().add(venture);
	}
	
	private void enterResourceCost(EnumMap<ResourceType, Integer> cost){
		
		coin = enterCost(ResourceType.COIN.name());
		wood = enterCost(ResourceType.WOOD.name());
		stone = enterCost(ResourceType.STONE.name());
		servant = enterCost(ResourceType.SERVANT.name());
		
		cost.put(ResourceType.COIN, coin);
		cost.put(ResourceType.WOOD, wood);
		cost.put(ResourceType.STONE, stone);
		cost.put(ResourceType.SERVANT, servant);
	}
	
	private void setVentureAlternativeCost(Venture venture){
		System.out.println("How many military points are required? ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("How many military points are required? ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int minimumRequiredMilitaryPoints = scanner.nextInt();
		venture.setMinimumRequiredMilitaryPoints(minimumRequiredMilitaryPoints);
		
		
		System.out.println("How many military points are subtracted? ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("How many military points are subtracted? ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int militaryPointCost = scanner.nextInt();
		scanner.nextLine();
		EnumMap<ResourceType, Integer> alternativeCost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		alternativeCost.put(ResourceType.MILITARYPOINT, militaryPointCost);
		Resource alternativeResource = Resource.of(alternativeCost);
		venture.setAlternativeCost(alternativeResource);

	}
	
	
	private String enterName(){
		System.out.print("Enter card name:");
		String name = scanner.nextLine();
		return name;
	}
	
	private int enterIDNumber() {
		System.out.print("Enter IDNumber: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter IDNumber: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int IDNumber = scanner.nextInt();
		return IDNumber;
	}

	
	private int enterEra() {
		System.out.print("Enter Era: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter Era: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int era = scanner.nextInt();
		return era;
	}
	
	
	private int enterCost(String resourceType){
		System.out.print("Enter " + resourceType + " cost: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter " + resourceType + " cost: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int cost = scanner.nextInt();
		scanner.nextLine();
		return cost;
	}

	
	private CardType enterCardType(){
		System.out.println("Enter Card Type: ");
		scanner.hasNextLine();
		boolean x = false;
		scanner.nextLine();
		String cardType = scanner.nextLine();
		for(CardType ct : CardType.values()){
			if(cardType.toUpperCase().equals(ct.name()))
				return ct;
		}
		while(!x){
			System.out.println("Not valid input!");
			System.out.print("Enter Card Type: ");
			cardType = scanner.nextLine();
			for(CardType ct : CardType.values()){
				if(cardType.toUpperCase().equals(ct.name()))
					return ct;
			}
		}
		return null;
	}
}


