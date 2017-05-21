package spaces;
import cards.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cards.ResourceType;
import core.GameBoard;

public class SpacesCreator {
	Scanner scanner = new Scanner(System.in);
	int coin, servants, militaryPoints;
	EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
	Resource resource;
	CouncilPrivilege cp;
	GameBoard gameBoard = GameBoard.instance();
	
	public static void main(String[] args) {
		SpacesCreator sc = new SpacesCreator();
		sc.startCreating();
	}
	
	private void startCreating(){
		Gson obj = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter file = new FileWriter("spaces.json", true);
			String proceed;
			
			do{
				char spaceType = enterSpaceType();
				switch(spaceType){
				case('m'):
					String type = enterMarketSpaceType();
					this.setMarketSpace(new MarketSpace(true, 1), type);
					break;
				case('t'):
					cp = CouncilPrivilege.instance();
					TwoPrivilegesSpace tps = new TwoPrivilegesSpace(true, 1, cp);
					gameBoard.setTwoPrivilegesSpace(tps);
					break;
				case('c'):
					CouncilPalace palace = CouncilPalace.instance();
					cp = CouncilPrivilege.instance();
					coin = enterBonus("coin first");
					bonus.put(ResourceType.COIN, coin);
					resource = Resource.of(bonus);
					palace.setBonus1(resource);
					palace.setBonus2(cp);
					palace.setActionValue(1);
					palace.setFree(true);
					gameBoard.setCouncilPalace(palace);
					break;
				case('x'):
					String pht = enterProdHarvType();
					ProductionAndHarvestSpace phSpace = new ProductionAndHarvestSpace(true, 1);
					if(pht.equals("harvest")){
						gameBoard.setHarvest(phSpace);	
						}
					else{
						gameBoard.setProduction(phSpace);	
					}
					break;
				}
				
				System.out.println("Do you want to go on?");
				proceed = scanner.nextLine();
			}while(!(proceed.equals("end")));
			
			String tmp = obj.toJson(gameBoard);
			file.write(tmp);
			file.flush();
			file.close();
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private String enterProdHarvType(){
		System.out.println("Is it production or harvest space? ");
		
		scanner.hasNextLine();
		String prodHarvType = scanner.nextLine();

		while(!(prodHarvType.equals("harvest")) && !(prodHarvType.equals("production"))){
			System.out.println("Not valid input!");
			System.out.print("Is it production or harvest space? ");
			prodHarvType = scanner.nextLine();
			}
		return prodHarvType;
	}
		
	
	private String enterMarketSpaceType(){
		System.out.println("Enter the MarketSpaceType [servantspace/coinspace/mixedspace]: ");
		
		scanner.hasNextLine();
		String marketSpaceType = scanner.nextLine();

		while(!(marketSpaceType.equals("servantspace")) && !(marketSpaceType.equals("coinspace")) && !(marketSpaceType.equals("mixedspace"))){
			System.out.println("Not valid input!");
			System.out.print("Enter MarketSpaceType [servantspace/coinspace/mixedspace]: ");
			marketSpaceType = scanner.nextLine();
		}
		return marketSpaceType;
	}
	
	private void setMarketSpace(MarketSpace m, String type){
		bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
		switch(type){
		case "coinspace":
			coin = enterBonus("coin");
			bonus.put(ResourceType.COIN, coin);
			resource = Resource.of(bonus);
			m.setBonus(resource);
			gameBoard.setCoinSpace(m);
			break;
		case "servantspace":
			servants = enterBonus("servant");
			bonus.put(ResourceType.SERVANT, servants);
			resource = Resource.of(bonus);
			m.setBonus(resource);
			gameBoard.setServantSpace(m);
			break;
		case "mixedspace":
			coin = enterBonus("coin");
			militaryPoints = enterBonus("military points");
			bonus.put(ResourceType.COIN, coin);
			bonus.put(ResourceType.MILITARYPOINT, militaryPoints);
			resource = Resource.of(bonus);
			m.setBonus(resource);
			gameBoard.setMixedSpace(m);
			break;
		}
	}
	
	private int enterBonus(String resourceType){
		System.out.print("Enter " + resourceType + " bonus: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter " + resourceType + " bonus: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int bonus = scanner.nextInt();
		scanner.nextLine();
		return bonus;
	}
	
	private char enterSpaceType(){
		System.out.println("Enter the SpaceType");
		System.out.println("type 'm' for MarketSpace");
		System.out.println("type 't' for TwoPrivilegesSpace");
		System.out.println("type 'c' for CouncilPalace");
		System.out.println("type 'x' for Production&HarvestSpace");
		
		boolean y = false;
		char spaceType = scanner.nextLine().toLowerCase().charAt(0);
		if(spaceType=='m' || spaceType=='t' || spaceType=='c' || spaceType=='x'){
			return spaceType;
		}
		while(!y){
			System.out.println("Not valid input!");
			System.out.print("Enter SpaceType: ");
			spaceType = scanner.nextLine().toLowerCase().charAt(0);
			if(spaceType=='m' || spaceType=='t' || spaceType=='c' || spaceType=='x'){
				return spaceType;
			}
		}
		return ' ';
	}
}
