package it.polimi.ingsw.GC_28.cards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.HashMap;
//import java.util.Map;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.EnumMap;

//import java.lang.Character; --> imported locally when needed

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.*;

public class CardWriter1 {
	Scanner scanner = new Scanner(System.in);
	EnumMap<ResourceType, Integer> cost;
	Resource resourceCost;
	int coin, wood, stone, servant, militaryPoint, victoryPoint, faithPoint;
	Deck deck = new Deck();
	private Logger log = Logger.getAnonymousLogger();
	public static void main(String[] args){
		CardWriter1 writer = new CardWriter1();
		writer.startReadCard();
	}
	

	private void startReadCard(){
		
		Gson obj = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter file = new FileWriter("cards2.json", true);
			

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
					this.setTerritory(new Territory(name, IDNumber, era));
					break;
				case BUILDING: 
					this.setBuilding(new Building(name,IDNumber,era));
					break;
				case CHARACTER:
					this.setCharacter(new Character(name,IDNumber, era));
					break;
				case VENTURE:
					this.setVentures(new Venture(name,IDNumber, era));
					break;
				}
			
				System.out.println("Do you want to go on?");
				proceed = scanner.nextLine();
			
			}while(!(proceed.equals("end")));
			
			String tmp = obj.toJson(deck);
			file.write(tmp);
			file.flush();
			file.close();
			
		}catch(IOException e){
			log.log(Level.SEVERE, "input not valide" + e);
		}
		scanner.close();
	}

	private void setTerritory(Territory territory){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);

		resourceCost = Resource.of(cost);
		
		ArrayList<Effect> effects = new ArrayList<>(); 
		effects = enterEffectArray(effects);
		territory.setImmediateEffect(effects);
		
		HarvestEffect harvEff = new HarvestEffect();
		enterHarvestEffect(harvEff);
		territory.setPermanentEffect(harvEff);
		territory.setCost(resourceCost);
		

		deck.getTerritories().add(territory);
	}
	
	private void setBuilding(Building building){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		enterResourceCost(cost);
		resourceCost = Resource.of(cost);
		
		building.setCost(resourceCost);
		
		ResourceEffect effect = new ResourceEffect();
		EnumMap<ResourceType, Integer>bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
		enterResourceBonus(bonus);
		Resource resourceBonus = Resource.of(bonus);
		effect.setResourceBonus(resourceBonus);
		building.setImmediateEffect(effect);
		
		ProductionEffect pe = new ProductionEffect(); 
		enterProductionEffect(pe);
		building.setPermanentEffect(pe);
		
		deck.getBuildings().add(building);
		//return building;
	}

	private void setCharacter(Character character){
		cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
		
		coin = enterCost(ResourceType.COIN.name());
		cost.put(ResourceType.COIN, coin);
		resourceCost = Resource.of(cost);
		
		character.setCost(resourceCost);
		
		ArrayList<Effect> effects = new ArrayList<Effect>(); 
		enterEffectArray(effects);
		character.setImmediateEffect(effects);
		boolean z = false;
		do{
		System.out.println("Enter the EffectType for the Permanent Effect");
		System.out.println("Type 'incrcard' for INCREMENTCARDEFFECT" );
		System.out.println("Type 'incrhp' for INCREMENTHPFFECT" );
		System.out.println("Type 'nc' for NOCELLBONUS" );
		System.out.println("Type 'null' if there is no bonus");
		String et = scanner.nextLine();
		//EffectType et = enterEffect();
		if(et.equals("incrcard")){
			IncrementCardEffect incAV = new IncrementCardEffect();
			enterIncrementCardEffect(incAV);
			//character.type = EffectType.INCREMENTCARDEFFECT;
			character.setPermanentEffect(incAV);
			deck.getCharacters().add(character);
			z = true;
		}else if(et.equals("incrhp")){
			IncrementHPEffect hopEffect = new IncrementHPEffect();
			enterHPEffect(hopEffect);
			//character.type = EffectType.INCREMENTHPEFFECT;
			character.setPermanentEffect(hopEffect);
			deck.getCharacters().add(character);
			z = true;
		}else if(et.equals("nc")){
			NoCellBonusEffect noCell = new NoCellBonusEffect(true); 
			//noCell.setPresence(true);
			//character.type = EffectType.NOCELLBONUS;
			character.setPermanentEffect(noCell);
			deck.getCharacters().add(character);
			z = true;
		}
		else if(et.equals("null")){
			Effect noEffect = new Effect();
			character.setPermanentEffect(noEffect);
			deck.getCharacters().add(character);
			z = true;
		}
		else{
			System.out.println("Not Valid Input!");
		}
		
		}while(z == false);
	}
	
	private void setVentures(Venture venture){
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
			setVentureAlternativeCost(venture);
		}
		else{
			venture.setAlternativeCostPresence(false);
		}
		
		ArrayList<Effect> effects = new ArrayList<Effect>(); 
		enterEffectArray(effects);
		venture.setImmediateEffect(effects);
		
		/*creation of the resourceEffect for the PermanentEffect of venture cards*/
		System.out.println("Enter how many VICTORYPOINTS for the permanent effect?");
		EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
		
		int victoryPts = enterBonus(ResourceType.VICTORYPOINT.name());
		bonus.put(ResourceType.VICTORYPOINT, victoryPts);
		Resource resourceBonus = Resource.of(bonus);
		ResourceEffect re = new ResourceEffect();
		re.setResourceBonus(resourceBonus);
		venture.setPermanentEffect(re);
		
		deck.getVentures().add(venture);
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
	
	private void setVentureAlternativeCost(Venture venture){
		venture.setAlternativeCostPresence(true);
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
	
	private ArrayList<Effect> enterEffectArray(ArrayList<Effect> effects){ //Allow to insert an arraylist of effectType
		boolean x = false;
		//scanner.nextLine();
		while(!x){ 
			System.out.println("Enter Effect Type for the Immediate Effect: ");
			System.out.println("Type 'r' for ResourceEffect");
			System.out.println("Type 'p' for PrivilegesEffect");
			System.out.println("Type 'm' for MultiplierEffect");
			System.out.println("Type 'h' for GoToHPEffect");
			System.out.println("Type 't' for TakeCardEffect");
			System.out.println("Type 'n' if there is no effect");

			scanner.hasNextLine();//exec this till user input and add the user's input to the returned array
			String effectType = scanner.nextLine();
			
					if(effectType.equals("r")){
						ResourceEffect re = new ResourceEffect();
						//re.setEffectType(et);
							/*
							 * Next 3 lines used to create a resource to instance the ResourceEffect
							 * */
						EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
						enterResourceBonus(bonus);
						Resource resourceBonus = Resource.of(bonus);
						re.setResourceBonus(resourceBonus);
						effects.add(re);
					}
					else if(effectType.equals("n")){
						Effect e = new Effect();
						effects.add(e);
					}
					else if(effectType.equals("p")){
						PrivilegesEffect privEff = new PrivilegesEffect();
						effects.add(enterPrivilegesEffect(privEff));
					}else if(effectType.equals("m")){
						MultiplierEffect me = new MultiplierEffect();
						System.out.println("Type 'r' if the multiplier has two resources");
						
						if(scanner.nextLine().equals("r")){
							EnumMap<ResourceType, Integer> multRes = new EnumMap<ResourceType, Integer>(ResourceType.class);
							enterResourceCost(multRes);
							Resource resourceCost = Resource.of(multRes);
							me.setResourceCost(resourceCost);
						}
						else{
							me.setCardType(enterCardType());
						}	
						EnumMap<ResourceType, Integer>bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
						enterResourceBonus(bonus);
						Resource resourceBonus = Resource.of(bonus);
						me.setResourceBonus(resourceBonus);
						effects.add(me);
					}
					else if(effectType.equals("h")){
						GoToHPEffect harvOrProdAct = new GoToHPEffect();
						System.out.println("Is it an harvest or a production action? ");
						if(scanner.nextLine().equals("harvest")){
							harvOrProdAct.setHarvest(true);
							harvOrProdAct.setProduction(false);
						}
						else{
							harvOrProdAct.setProduction(true);
							harvOrProdAct.setHarvest(false);
						}
						System.out.println("Enter action value: ");
						while(!scanner.hasNextInt()){
							System.out.println("Not valid input!");
							System.out.print("Enter action value: ");
							scanner.nextLine();
							scanner.hasNextInt();
						}
						int actionValue = scanner.nextInt();
						scanner.nextLine();
						harvOrProdAct.setActionValue(actionValue);
						effects.add(harvOrProdAct);
					}
					else if(effectType.equals("t")){
						TakeCardEffect takeCE = new TakeCardEffect();
						enterTakeCardEffect(takeCE);
						effects.add(takeCE);
					}
					else{
						System.out.println("Not valid input!");
					}

			System.out.println("Do you want to enter another Effect?[y/n]");
			String proceed = scanner.nextLine();
			if(proceed.equals("n"))
				return effects;
		}
		return effects;
	}
	
	private char enterProductionEffectType(){

		System.out.println("Enter effect type: ");
		System.out.println("Enter 'e' for exchange.");
		System.out.println("Enter 'm' for multiplier.");
		System.out.println("Enter 'r' for resource.");
		System.out.println("Enter 'p' for privilege");
		
		
		boolean y = false;
		char productionEffectType = scanner.nextLine().toLowerCase().charAt(0);
		if(productionEffectType=='e' || productionEffectType=='m' || productionEffectType=='r' || productionEffectType == 'p'){
			return productionEffectType;
		}
		while(!y){
			System.out.println("Not valid input!");
			System.out.print("Enter SpaceType: ");
			productionEffectType = scanner.nextLine().toLowerCase().charAt(0);
			if(productionEffectType=='m' || productionEffectType=='t' || productionEffectType=='c' || productionEffectType=='x' || productionEffectType == 'p'){
				return productionEffectType;
			}
		}
		return ' ';
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
	
	
	private PrivilegesEffect enterPrivilegesEffect(PrivilegesEffect privEffect){
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
	
	private TakeCardEffect enterTakeCardEffect(TakeCardEffect tce){
		System.out.println("Enter minimum card value: ");
		tce.setActionValue(scanner.nextInt());
		scanner.nextLine();
		System.out.println("Is the card of a specific CardType?[y/n]");
		char specific = scanner.nextLine().toLowerCase().charAt(0);
		switch(specific){
		case('y'):
			tce.setCardType(enterCardType());
			break;
		case('n'):
			tce.setCardType(null);
			break;
		}
						
		System.out.println("is there any discount? [y/n]");
		String hasBonus = scanner.nextLine();
		if(!hasBonus.equals("n")){
			tce.setDiscountPresence(true);
			DiscountEffect de = new DiscountEffect();
			tce.setDiscount(enterDiscountEffect(de));
		}
		else{
			tce.setDiscountPresence(false);
		}
		return tce;
	}
	
	private DiscountEffect enterDiscountEffect(DiscountEffect dEff){
		EnumMap<ResourceType, Integer>bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
		enterResourceBonus(bonus);
		Resource resourceBonus = Resource.of(bonus);
		dEff.setDiscount(resourceBonus);
		
		System.out.println("has another discountEffect? [y/n] ");
		String hasAlternativeDiscount = scanner.nextLine();
		if(hasAlternativeDiscount.equals("n")){
			dEff.setAlternativeDiscountPresence(false);
		}
		else{
			dEff.setAlternativeDiscountPresence(true);
			EnumMap<ResourceType, Integer> bonus2 = new EnumMap<ResourceType, Integer>(ResourceType.class);
			enterResourceBonus(bonus2);
			Resource resourceBonus2 = Resource.of(bonus2);
			dEff.setAlternativeDiscount(resourceBonus2);
		}
		return dEff;
	}
	
	private IncrementCardEffect enterIncrementCardEffect(IncrementCardEffect incAV){
		System.out.println("Enter value: ");
		incAV.setIncrement(scanner.nextInt());
		scanner.nextLine();
		
		incAV.setCardType(enterCardType());
		
		System.out.println("is there any discount? [y/n]");
		String hasBonus = scanner.nextLine();
		if(!hasBonus.equals("n")){
			incAV.setDiscountPresence(true);
			DiscountEffect de = new DiscountEffect();
			incAV.setDiscount(enterDiscountEffect(de));
		}
		else{
			incAV.setDiscountPresence(false);
		}
		return incAV;
	} 
	
	private IncrementHPEffect enterHPEffect(IncrementHPEffect hopEff){
		System.out.println("Harvest or Production:");
		String choice = scanner.nextLine();
		if(choice.toLowerCase().equals("harvest")){
			hopEff.setHarvest(true);
			hopEff.setProduction(false);
		}else{
			hopEff.setHarvest(false);
			hopEff.setProduction(true);
		}
		System.out.println("Enter increment:");
		hopEff.setIncrement(scanner.nextInt());
		scanner.nextLine();
		return hopEff;
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
	
	
}

