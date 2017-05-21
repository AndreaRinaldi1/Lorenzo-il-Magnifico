package it.polimi.ingsw.GC_28.spaces;

import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.cards.CouncilPrivilege;
import it.polimi.ingsw.GC_28.cards.Resource;
import it.polimi.ingsw.GC_28.cards.ResourceType;

import it.polimi.ingsw.GC_28.spaces.MarketSpace;


public class SpacesCreator {
	Scanner scanner = new Scanner(System.in);
	int coin, servants, militaryPoints;
	EnumMap<ResourceType, Integer> bonus;
	Resource resource;
	CouncilPrivilege cp;
	EverySpace everySpace = new EverySpace();
	
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
					MarketSpaceType type = enterMarketSpaceType();
					this.setMarketSpace(new MarketSpace(true, 1, type));
					break;
				case('t'):
					cp = CouncilPrivilege.instance();
					TwoPrivilegesSpace tps = new TwoPrivilegesSpace(true, 1, cp);
					everySpace.setTwoPrivilegesSpace(tps);
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
					everySpace.setCouncilPalace(palace);
					break;
				case('x'):
					ProdHarvType pht = enterProdHarvType();
					ProductionAndHarvestSpace phSpace = new ProductionAndHarvestSpace(true, 1, pht);
					if(pht == ProdHarvType.HARVEST){
						everySpace.setHarvest(phSpace);	
						}
					else{
						everySpace.setProduction(phSpace);	
					}
					break;
				}
				
				System.out.println("Do you want to go on?");
				proceed = scanner.nextLine();
			}while(!(proceed.equals("end")));
			
			String tmp = obj.toJson(everySpace);
			file.write(tmp);
			file.flush();
			file.close();
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private ProdHarvType enterProdHarvType(){
		System.out.println("Is it production or harvest space? ");
		
		scanner.hasNextLine();
		boolean y = false;
		String prodHarvType = scanner.nextLine();
		
		for(ProdHarvType pht : ProdHarvType.values()){
			if(prodHarvType.toUpperCase().equals(pht.name()))
				return pht;
		}
		while(!y){
			System.out.println("Not valid input!");
			System.out.print("Is it production or harvest space? ");
			prodHarvType = scanner.nextLine();
			for(ProdHarvType pht : ProdHarvType.values()){
				if(prodHarvType.toUpperCase().equals(pht.name()))
					return pht;
			}
		}
		return null;
	}
	
	
	private MarketSpaceType enterMarketSpaceType(){
		System.out.println("Enter the MarketSpaceType [servantspace/coinspace/mixedspace]: ");
		
		scanner.hasNextLine();
		boolean y = false;
		String marketSpaceType = scanner.nextLine();
		
		for(MarketSpaceType mst : MarketSpaceType.values()){
			if(marketSpaceType.toUpperCase().equals(mst.name()))
				return mst;
		}
		while(!y){
			System.out.println("Not valid input!");
			System.out.print("Enter MarketSpaceType [servantspace/coinspace/mixedspace]: ");
			marketSpaceType = scanner.nextLine();
			for(MarketSpaceType mst : MarketSpaceType.values()){
				if(marketSpaceType.toUpperCase().equals(mst.name()))
					return mst;
			}
		}
		return null;
	}
	
	private void setMarketSpace(MarketSpace m){
		bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
		switch(m.getType()){
		case COINSPACE:
			coin = enterBonus("coin");
			bonus.put(ResourceType.COIN, coin);
			resource = Resource.of(bonus);
			m.setBonus(resource);
			everySpace.setCoinSpace(m);
			break;
		case SERVANTSPACE:
			servants = enterBonus("servant");
			bonus.put(ResourceType.SERVANT, servants);
			resource = Resource.of(bonus);
			m.setBonus(resource);
			everySpace.setServantSpace(m);
			break;
		case MIXEDSPACE:
			coin = enterBonus("coin");
			militaryPoints = enterBonus("military points");
			bonus.put(ResourceType.COIN, coin);
			bonus.put(ResourceType.MILITARYPOINT, militaryPoints);
			resource = Resource.of(bonus);
			m.setBonus(resource);
			everySpace.setMixedSpace(m);
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
