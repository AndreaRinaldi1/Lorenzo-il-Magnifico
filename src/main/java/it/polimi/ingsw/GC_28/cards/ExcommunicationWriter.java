package it.polimi.ingsw.GC_28.cards;

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

import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.FinalReduceEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;

import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoFinalBonusEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;

public class ExcommunicationWriter {
	Scanner scanner = new Scanner(System.in);
	EnumMap<ResourceType, Integer> cost;
	EnumMap<ResourceType, Integer> bonus;
	Resource resourceBonus;
	Resource resourceCost;
	List<ExcommunicationTile> exList = new ArrayList<>();
	
	int coin, wood, stone, servant, militaryPoint, victoryPoint, faithPoint;

			
	public static void main(String[] args) {
		ExcommunicationWriter excom = new ExcommunicationWriter();
		excom.startWrite();
	}
	
	public void startWrite() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter file = new FileWriter("excommunication2.json",true);
			String procede = new String();
			do{
				ExcommunicationTile ex = new ExcommunicationTile();
				System.out.println("Enter era:");
				Integer era = scanner.nextInt();
				scanner.nextLine();
				ex.setEra(era);
				System.out.println("Enter the EffectType for the  ExcomunicationCard");
				System.out.println("Type 'incrcard' for INCREMENTCARDEFFECT" );
				System.out.println("Type 'incrhp' for INCREMENTHPFFECT" );
				System.out.println("Type 'nf' for NOFINALBONUS" );
				System.out.println("Type 'mult' for MULTIPLIEREFFECT");
				System.out.println("Type 'd' for DISCOUNTEFFECT");
				System.out.println("Type 'rd' for MODIFYDICEEFFECT");
				System.out.println("Type 'skip' for SKIPROUNDEFFECT");
				System.out.println("Type 'nm' for NOMARKETEFFECT");
				System.out.println("Type 'se' for SERVANTEFFECT");
				System.out.println("Type 'fe' for  FINALREDUCEEFFECT");
				String et = scanner.nextLine();
				switch(et){
				case "incrcard":
					IncrementCardEffect ice = new IncrementCardEffect();
					ex.setEffect(enterIncrementCardEffect(ice));
					exList.add(ex);
					break;
				case "incrhp":
					System.out.println("Harvest or Production:");
					String choice = scanner.nextLine();
					if(choice.toLowerCase().equals("harvest")){
						IncrementHPEffect harvEffect = new IncrementHPEffect();
						harvEffect.setType(EffectType.INCREMENTHARVESTEFFECT);
						System.out.println("Enter increment:");
						harvEffect.setIncrement(scanner.nextInt());
						ex.setEffect(harvEffect);
					}else{
						IncrementHPEffect prodEffect = new IncrementHPEffect();
						prodEffect.setType(EffectType.INCREMENTPRODUCTIONEFFECT);
						System.out.println("Enter increment:");
						prodEffect.setIncrement(scanner.nextInt());
						ex.setEffect(prodEffect);
					}
					scanner.nextLine();
					
					exList.add(ex);
					break;
				case "nf":
					NoFinalBonusEffect nf = new NoFinalBonusEffect();
					nf.setCardType(enterCardType());
					ex.setEffect(nf);
					exList.add(ex);
					break;
				case "mult":
					MultiplierEffect me = new MultiplierEffect();
					me.setCardType(null);
					bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceBonus(bonus);
					resourceBonus = Resource.of(bonus);
					me.setResourceBonus(resourceBonus);
					cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceCost(cost);
					resourceCost = Resource.of(cost);
					me.setResourceCost(resourceCost);
					ex.setEffect(me);
					exList.add(ex);
					break;
				case "d":
					DiscountEffect de = new DiscountEffect();
					ex.setEffect(enterDiscountEffect(de));
					exList.add(ex);
					break;
				case "rd":
					ModifyDiceEffect rd = new ModifyDiceEffect();
					rd.setReduce(enterReduceValue());

					rd.setDiceColor(enterDiceColor());
					ex.setEffect(rd);
					exList.add(ex);
					break;
				case "skip":
					OtherEffect oe = new OtherEffect();
					oe.setType(EffectType.SKIPROUNDEFFECT);
					ex.setEffect(oe);
					exList.add(ex);
					break;
				case "nm":
					OtherEffect nm = new OtherEffect();
					nm.setType(EffectType.NOMARKETEFFECT);
					ex.setEffect(nm);
					exList.add(ex);
					break;
				case "se":
					ServantEffect se = new ServantEffect();
					se.setNumberOfServant(2);
					se.setIncrement(1);
					ex.setEffect(se);
					exList.add(ex);
					break;
				case "fe":
					FinalReduceEffect fe = new FinalReduceEffect();
					fe.setCardType(enterCardType());
					cost = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceCost(cost);
					resourceCost = Resource.of(cost);
					fe.setResourceCost(resourceCost);
					bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
					enterResourceBonus(bonus);
					resourceBonus = Resource.of(bonus);
					fe.setResourceBonus(resourceBonus);
					ex.setEffect(fe);
					exList.add(ex);
					break;
				}
				System.out.println(exList.get(0).getEffect());
				System.out.println("Do you want to continue?[y/end]");
				procede = scanner.nextLine();
			}while(!(procede.equals("end")));
			
			String tmp = gson.toJson(exList);
			file.write(tmp);
			file.flush();
			file.close();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "error"+e);
		}
	}
	
	int enterReduceValue(){
		System.out.println("Enter the reduce value");
		return scanner.nextInt();
	}
	
	List<DiceColor> enterDiceColor(){
		List<DiceColor> list = new ArrayList<>();
		while(true){
			System.out.println("enter the dice color. Type end to finish");
			String color = scanner.nextLine();
			if(color.equals("end")){
				break;
			}
			for(DiceColor c : DiceColor.values()){
				if(color.equalsIgnoreCase(c.name())){
					list.add(c);
					break;
				}
			}
		}
		return list;
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
	
