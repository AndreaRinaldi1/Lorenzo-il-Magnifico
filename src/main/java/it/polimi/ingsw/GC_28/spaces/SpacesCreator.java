package it.polimi.ingsw.GC_28.spaces;

import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;


public class SpacesCreator {
	Scanner scanner = new Scanner(System.in);
	EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
	Resource resource;
	CouncilPrivilege cp;
	EverySpace everySpace = new EverySpace();
	int coin, wood, stone, servant, militaryPoint, victoryPoint, faithPoint;

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
					PrivilegesEffect pe = new PrivilegesEffect();
					PrivilegesSpace tps = new PrivilegesSpace(true, 1);
					tps.setBonus(enterPrivilegesEffect(pe));
					everySpace.setPrivilegesSpace(tps);
					break;
				case('c'):
					CouncilPalace palace = CouncilPalace.instance();
					bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceBonus(bonus);
					Resource resourceBonus = Resource.of(bonus);
					ResourceEffect re = new ResourceEffect();
					re.setResourceBonus(resourceBonus);
					palace.setBonus1(re);
					PrivilegesEffect priveff = new PrivilegesEffect();
					palace.setBonus2(enterPrivilegesEffect(priveff));
					palace.setActionValue(1);
					palace.setFree(true);
					everySpace.setCouncilPalace(palace);
					break;
				case('x'):
					String pht = enterProdHarvType();
					ProductionAndHarvestSpace phSpace = new ProductionAndHarvestSpace(true, 1);
					if(pht.equals("harvest")){
						phSpace.setHarvest(true);
						everySpace.setHarvest(phSpace);	
						}
					else{
						phSpace.setHarvest(false);
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
			Logger.getAnonymousLogger().log(Level.FINE, "Input not valide " + e);
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
		ResourceEffect re = new ResourceEffect();
		switch(type){
		case "coinspace":
			coin = enterBonus("coin");
			bonus.put(ResourceType.COIN, coin);
			resource = Resource.of(bonus);
			re.setResourceBonus(resource);
			m.setBonus(re);
			everySpace.setCoinSpace(m);
			break;
		case "servantspace":
			servant = enterBonus("servant");
			bonus.put(ResourceType.SERVANT, servant);
			resource = Resource.of(bonus);
			re.setResourceBonus(resource);
			m.setBonus(re);
			everySpace.setServantSpace(m);
			break;
		case "mixedspace":
			coin = enterBonus("coin");
			militaryPoint = enterBonus("military points");
			bonus.put(ResourceType.COIN, coin);
			bonus.put(ResourceType.MILITARYPOINT, militaryPoint);
			resource = Resource.of(bonus);
			re.setResourceBonus(resource);
			m.setBonus(re);
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
		
		char spaceType = scanner.nextLine().toLowerCase().charAt(0);
		if(spaceType=='m' || spaceType=='t' || spaceType=='c' || spaceType=='x'){
			return spaceType;
		}
		while(true){
			System.out.println("Not valid input!");
			System.out.print("Enter SpaceType: ");
			spaceType = scanner.nextLine().toLowerCase().charAt(0);
			if(spaceType=='m' || spaceType=='t' || spaceType=='c' || spaceType=='x'){
				return spaceType;
			}
		}
	}
	
private void enterResourceBonus(EnumMap<ResourceType, Integer> bonus){
		
		coin = enterBonus(ResourceType.COIN.name());
		wood = enterBonus(ResourceType.WOOD.name());
		stone = enterBonus(ResourceType.STONE.name());
		servant = enterBonus(ResourceType.SERVANT.name());
		victoryPoint = enterBonus(ResourceType.VICTORYPOINT.name());
		militaryPoint = enterBonus(ResourceType.MILITARYPOINT.name());
		faithPoint = enterBonus(ResourceType.FAITHPOINT.name());
		
		bonus.put(ResourceType.COIN, coin);
		bonus.put(ResourceType.WOOD, wood);
		bonus.put(ResourceType.STONE, stone);
		bonus.put(ResourceType.SERVANT, servant);
		bonus.put(ResourceType.VICTORYPOINT, victoryPoint);
		bonus.put(ResourceType.MILITARYPOINT, militaryPoint);
		bonus.put(ResourceType.FAITHPOINT, faithPoint);

}
	
	public PrivilegesEffect enterPrivilegesEffect(PrivilegesEffect privEffect){
		System.out.println("Enter number of privileges: ");
		int numOfPriv = scanner.nextInt();
		scanner.nextLine();
		privEffect.setNumberOfCouncilPrivileges(numOfPriv);
		if(numOfPriv == 1){
			privEffect.setDifferent(false);
		}
		else{
			System.out.println("Are they different one from the others?[y/n]");
			if(scanner.nextLine().equalsIgnoreCase("y")){
				privEffect.setDifferent(true);
			}
			else{
				privEffect.setDifferent(false);
			}
		}
		return privEffect;
	}
}
