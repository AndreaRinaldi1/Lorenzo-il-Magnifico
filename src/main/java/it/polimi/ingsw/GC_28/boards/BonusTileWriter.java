package it.polimi.ingsw.GC_28.boards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;

public class BonusTileWriter {
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Scanner scanner = new Scanner(System.in);
	int coin,wood,stone,servant,victoryPoint,militaryPoint,faithPoint;
	public static void main(String[] args) {
		BonusTileWriter writer = new BonusTileWriter();
		try{
			writer.start();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "input not valid"+e);
		}
	}
	
	private void start()throws IOException{
		List<BonusTile> bonusTile = new ArrayList<>();
		String procede = new String();
		do{
			BonusTile bonus = new BonusTile();
			ProductionEffect pe = new ProductionEffect();
			enterProductionEffect(pe);
			HarvestEffect he = new HarvestEffect();
			enterHarvestEffect(he);
			bonus.setProductionEffect(pe);
			bonus.setHarvestEffect(he);
			bonusTile.add(bonus);
			System.out.println("Do you want to continue?[y/end]");
			procede = scanner.nextLine();
		}while(!(procede).equals("end"));
		FileWriter writer = new FileWriter("bonusTile2.json",true);
		String content = gson.toJson(bonusTile);
		writer.write(content);
		writer.flush();
		writer.close();
	}
	
	
	private int enterBonus(String resourceType){
		System.out.print("Enter " + resourceType + " bonus: ");
		while(!scanner.hasNextInt()){
			System.out.println("Not valid input!");
			System.out.print("Enter " + resourceType + " bonus: ");
			scanner.nextLine();
			scanner.hasNextInt();
		}
		int cost = scanner.nextInt();
		scanner.nextLine();
		return cost;
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
	
	private HarvestEffect enterHarvestEffect(HarvestEffect harvEff){		
		System.out.println("Enter Harvest Value:");
		harvEff.setHarvestValue(scanner.nextInt());
		scanner.nextLine();
		
		System.out.println("Enter 'c' for councilPrivilege, 'r' for resource (harvest bonus): " );
		char harvestEffectType = scanner.nextLine().toLowerCase().charAt(0);

		switch(harvestEffectType){
		case('c'):
			PrivilegesEffect privEff = new PrivilegesEffect();
			harvEff.setCouncilPrivilegeBonus(enterPrivilegesEffect(privEff)); 
			break;
		case('r'):
			EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
			enterResourceBonus(bonus);
			Resource resourceBonus = Resource.of(bonus);
			ResourceEffect re = new ResourceEffect();
			re.setResourceBonus(resourceBonus);
			harvEff.setResourceHarvestBonus(re);
		}
		return harvEff;
	}
	
	private char enterProductionEffectType(){

		System.out.println("Enter effect type: ");
		System.out.println("Enter 'e' for exchange.");
		System.out.println("Enter 'm' for multiplier.");
		System.out.println("Enter 'r' for resource.");
		System.out.println("Enter 'p' for privilege");
		
		
		char productionEffectType = scanner.nextLine().toLowerCase().charAt(0);
		if(productionEffectType=='e' || productionEffectType=='m' || productionEffectType=='r' || productionEffectType == 'p'){
			return productionEffectType;
		}
		while(true){
			System.out.println("Not valid input!");
			System.out.print("Enter SpaceType: ");
			productionEffectType = scanner.nextLine().toLowerCase().charAt(0);
			if(productionEffectType=='m' || productionEffectType=='t' || productionEffectType=='c' || productionEffectType=='x' || productionEffectType == 'p'){
				return productionEffectType;
			}
		}
	}
	
	private ExchangeEffect enterExchangeEffect(ExchangeEffect exEff){
		EnumMap<ResourceType, Integer> cost = new EnumMap<ResourceType, Integer>(ResourceType.class);

		System.out.println("Is there a council Privilege bonus in the exchange? [y/n]");
		if(scanner.nextLine().toLowerCase().equals("y")){
			enterResourceCost(cost);
			Resource costForPrivBonus = Resource.of(cost);		
			exEff.setPrivilegeCost(costForPrivBonus);
			PrivilegesEffect privEff = new PrivilegesEffect();
			exEff.setPrivilegeBonus(enterPrivilegesEffect(privEff));
		}
		else{
			System.out.println("Enter resource Cost:");
			enterResourceCost(cost);
			Resource costForBonus = Resource.of(cost);	
			exEff.setFirstCost(costForBonus);
			
			System.out.println("Enter resource Bonus: ");
			EnumMap<ResourceType, Integer> bonusMap = new EnumMap<ResourceType, Integer>(ResourceType.class);
			enterResourceBonus(bonusMap);
			Resource bonus = Resource.of(bonusMap);
			exEff.setFirstBonus(bonus);
			
			System.out.println("Is there a second alternative exchange? [y/n]");
			if(scanner.nextLine().toLowerCase().equals("y")){
				exEff.setAlternative(true);
				System.out.println("Enter resource Cost:");
				EnumMap<ResourceType, Integer> cost2 = new EnumMap<ResourceType, Integer>(ResourceType.class);
				enterResourceCost(cost2);
				Resource costForBonus2 = Resource.of(cost2);	
				exEff.setSecondCost(costForBonus2);
				
				System.out.println("Enter resource Bonus: ");
				EnumMap<ResourceType, Integer> bonusMap2 = new EnumMap<ResourceType, Integer>(ResourceType.class);
				enterResourceBonus(bonusMap2);
				Resource bonus2 = Resource.of(bonusMap2);
				exEff.setSecondBonus(bonus2);
			}
			else{
				exEff.setAlternative(false);
			}
		}
		return exEff;
	}
	
	private CardType enterCardType(){
		System.out.println("Enter Card Type: ");
		scanner.hasNextLine();
		scanner.nextLine();
		String cardType = scanner.nextLine();
		for(CardType ct : CardType.values()){
			if(cardType.toUpperCase().equals(ct.name()))
				return ct;
		}
		while(true){
			System.out.println("Not valid input!");
			System.out.print("Enter Card Type: ");
			cardType = scanner.nextLine();
			for(CardType ct : CardType.values()){
				if(cardType.toUpperCase().equals(ct.name()))
					return ct;
			}
		}
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
	
	private void enterResourceCost(EnumMap<ResourceType, Integer> cost){
		
		coin = enterCost(ResourceType.COIN.name());
		wood = enterCost(ResourceType.WOOD.name());
		stone = enterCost(ResourceType.STONE.name());
		servant = enterCost(ResourceType.SERVANT.name());
		victoryPoint = enterCost(ResourceType.VICTORYPOINT.name());
		militaryPoint = enterCost(ResourceType.MILITARYPOINT.name());
		faithPoint = enterCost(ResourceType.FAITHPOINT.name());

		
		cost.put(ResourceType.COIN, coin);
		cost.put(ResourceType.WOOD, wood);
		cost.put(ResourceType.STONE, stone);
		cost.put(ResourceType.SERVANT, servant);
		cost.put(ResourceType.VICTORYPOINT, victoryPoint);
		cost.put(ResourceType.MILITARYPOINT, militaryPoint);
		cost.put(ResourceType.FAITHPOINT, faithPoint);

	}
	
	private ProductionEffect enterProductionEffect(ProductionEffect pe){
		
		System.out.println("Enter Production Value:");
		pe.setProductionValue(scanner.nextInt());
		scanner.nextLine();
		EnumMap<ResourceType, Integer> bonus;
		Resource resourceBonus;
		boolean x = false;
		do{
			
			char productionEffectType = enterProductionEffectType();
			switch(productionEffectType){
			case('e'):
				ExchangeEffect exEff = new ExchangeEffect();
				enterExchangeEffect(exEff);
				pe.setExchangeBonus(exEff);
				break;
			case('m'):
				MultiplierEffect me = new MultiplierEffect();
				me.setCardType(enterCardType());
				bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
				enterResourceBonus(bonus);
				resourceBonus = Resource.of(bonus);
				me.setResourceBonus(resourceBonus);
				pe.setMultiplierEffect(me);
				break;
			case('r'):
				bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
				enterResourceBonus(bonus);
				resourceBonus = Resource.of(bonus);
				ResourceEffect re = new ResourceEffect();
				re.setResourceBonus(resourceBonus);
				pe.setResourceBonus(re);
				break;
			case('p'):
				PrivilegesEffect privEffect = new PrivilegesEffect();
				pe.setPrivilegeEffect(enterPrivilegesEffect(privEffect));
				break;
			}
			System.out.println("Do you want to enter another effect[y/n]");
			if(scanner.nextLine().equals("n")){
				x = true;
			}
		}while(!x);
			
		return pe;
	}
}
