package it.polimi.ingsw.GC_28.cards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoFinalBonusEffect;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;

public class ExcommunicationWriter {
	Scanner scanner = new Scanner(System.in);
	ExcommunicationTile ex = new ExcommunicationTile();
	EnumMap<ResourceType, Integer> cost;
	EnumMap<ResourceType, Integer> bonus;
	Resource resourceBonus;
	Resource resourceCost;
	int coin, wood, stone, servant, militaryPoint, victoryPoint, faithPoint;

			
	public static void main(String[] args) {
		ExcommunicationWriter excom = new ExcommunicationWriter();
		excom.startWrite();
	}
	
	public void startWrite() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter file = new FileWriter("excommunication.json",true);
			String procede = new String();
			do{
				System.out.println("Enter the EffectType for the  ExcomunicationCard");
				System.out.println("Type 'incrcard' for INCREMENTCARDEFFECT" );
				System.out.println("Type 'incrhp' for INCREMENTHPFFECT" );
				System.out.println("Type 'nf' for NOFINALBONUS" );
				System.out.println("Type 'm' for MULTIPLIEREFFECT");
				System.out.println("Type 'd' for DISCOUNTEFFECT");
				String et = scanner.nextLine();
				switch(et){
				case "incrcard":
					IncrementCardEffect ice = new IncrementCardEffect();
					ex.setEffect(enterIncrementCardEffect(ice));
					break;
				case "incrhp":
					IncrementHPEffect iHP = new IncrementHPEffect();
					ex.setEffect(enterHPEffect(iHP));
					break;
				case "nf":
					NoFinalBonusEffect nf = new NoFinalBonusEffect();
					nf.setCardType(enterCardType());
					ex.setEffect(nf);
					break;
				case "m":
					MultiplierEffect me = new MultiplierEffect();
					me.setCardType(null);
					bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceBonus(bonus);
					resourceBonus = Resource.of(bonus);
					me.setResourceBonus(resourceBonus);
					cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceBonus(cost);
					resourceCost = Resource.of(cost);
					me.setResourceCost(resourceCost);
					ex.setEffect(me);
					break;
				case "d":
					DiscountEffect de = new DiscountEffect();
					ex.setEffect(enterDiscountEffect(de));
					break;
				}
				
				System.out.println("Do you want to continue?[y/end]");
				procede = scanner.nextLine();
			}while(!(procede.equals("end")));
			
			file.close();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "error"+e);
		}
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
	
	private IncrementCardEffect enterIncrementCardEffect(IncrementCardEffect incAV){
		System.out.println("Enter value: ");
		incAV.setIncrement(scanner.nextInt());
		scanner.nextLine();
		
		incAV.setCardType(enterCardType());
		incAV.setDiscountPresence(false);
		incAV.setDiscount(null);
		
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
}
	
