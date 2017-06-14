package it.polimi.ingsw.GC_28.cards;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.effects.SetFamilyMemberValueEffect;

public class LeaderCardWriter {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Scanner scan = new Scanner(System.in);
	private CardWriter1 cardWriter = new CardWriter1();
	private List<LeaderCard> l = new ArrayList<>();
	private ExcommunicationWriter excommunicationWriter = new ExcommunicationWriter();
	
	public static void main(String[] args) {
		LeaderCardWriter writer = new LeaderCardWriter();
		writer.start();
	}
	
	private void start(){
		try{
			FileWriter file = new FileWriter("leader.json",true);
			Boolean stop = false;
			
			//String name;
			Resource resourceCost;
			Map<CardType,Integer> cardCost;
			
			do{
				LeaderCard leader = new LeaderCard();
				System.out.println("Enter leader name: ");
				leader.setName(scan.nextLine());
				System.out.println("Does the card cost any resource?[y/n]");
				String hasCost = scan.nextLine();
				if(hasCost.equalsIgnoreCase("y")){
					EnumMap<ResourceType,Integer> tmp = new EnumMap<ResourceType, Integer>(ResourceType.class);
					cardWriter.enterResourceCost(tmp);
					resourceCost = Resource.of(tmp);
					leader.setResourceCost(resourceCost);
				}else{
					leader.setResourceCost(null);
				}
				System.out.println("Does the Leader require any amount of action card?[y/n]");
				hasCost = scan.nextLine();
				if(hasCost.equalsIgnoreCase("y")){
					cardCost = new EnumMap<CardType, Integer>(CardType.class);
					enterCardCost(cardCost);
					leader.setCardCost(cardCost);
				}else{
					leader.setCardCost(null);
				}
				leader.setPlayed(false);
				leader.setActive(false);
				System.out.println("is the effect permanent?[y/n]");
				String string = scan.nextLine();
				if(string.equalsIgnoreCase("y")){
					leader.setPermanent(true);
				}else{
					leader.setPermanent(false);
				}
				leader.setEffect(enterEffect());
				l.add(leader);
				System.out.println("Enter another card?[y/n]");
				if(scan.nextLine().equalsIgnoreCase("n")){
					stop = true;
				}
			}while(!stop);
			
			String s = gson.toJson(l);
			file.write(s);
			file.flush();
			file.close();
			
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot write the file " + e);
		}
	}
	
	private void enterCardCost(Map<CardType, Integer> cardCost){
		System.out.println("Enter how many card does the leader require for every type: ");
		for(CardType ct : CardType.values()){
			System.out.println("how many "+ct+"the leader costs?");
			int i = scan.nextInt();
			scan.nextLine();
			cardCost.put(ct, i);
		}
	}
	
	private Effect enterEffect(){
		do{
			System.out.println("Enter Effect Type for the Effect: ");
			System.out.println("Type 'r' for ResourceEffect");
			System.out.println("Type 'p' for PrivilegesEffect");
			System.out.println("Type 'f' for FreeSpaceEffect");
			System.out.println("Type 'h' for GoToHPEffect");
			System.out.println("Type 'no' for NoExtraCostINTowerEffect");
			System.out.println("Type 'c' for NoMilitaryForTerrytory");
			System.out.println("Type 'cd' for CoinDiscountEffect");
			System.out.println("Type 'sf' for SetFamilyMemberValueEffect");
			System.out.println("Type 'pope' for PopeEffect");
			System.out.println("Type 'db' for DoubleResourceEffect");
			System.out.println("Type 'rd' for ModifyDiceValueEffect");
			scan.hasNextLine();
			String line = scan.nextLine();
			if(line.equalsIgnoreCase("r")){
				ResourceEffect re = new ResourceEffect();
				EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
				cardWriter.enterResourceBonus(bonus);
				Resource resourceBonus = Resource.of(bonus);
				re.setResourceBonus(resourceBonus);
				return re;
			}else if(line.equals("p")){
				PrivilegesEffect privEff = new PrivilegesEffect();
				cardWriter.enterPrivilegesEffect(privEff);
				return privEff;
			}else if(line.equals("h")){
				GoToHPEffect harvOrProdAct = new GoToHPEffect();
				System.out.println("Is it an harvest or a production action? ");
				if(scan.nextLine().equals("harvest")){
					harvOrProdAct.setHarvest(true);
					harvOrProdAct.setProduction(false);
				}
				else{
					harvOrProdAct.setProduction(true);
					harvOrProdAct.setHarvest(false);
				}
				System.out.println("Enter action value: ");
				while(!scan.hasNextInt()){
					System.out.println("Not valid input!");
					System.out.print("Enter action value: ");
					scan.nextLine();
					scan.hasNextInt();
				}
				int actionValue = scan.nextInt();
				scan.nextLine();
				harvOrProdAct.setActionValue(actionValue);
				return harvOrProdAct;
			}else if(line.equals("f")){
				OtherEffect oe = new OtherEffect();
				oe.setType(EffectType.FREESPACEEFFECT);
				return oe;
			}else if(line.equals("no")){
				OtherEffect oe = new OtherEffect();
				oe.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
				return oe;
			}else if(line.equals("c")){
				OtherEffect oe = new OtherEffect();
				oe.setType(EffectType.NOMILITARYFORTERRITORYEFFECT);
				return oe;
			}else if(line.equals("cd")){
				OtherEffect oe = new OtherEffect();
				oe.setType(EffectType.COINDISCOUNTEFFECT);
				return oe;
			}else if(line.equals("sf")){
				SetFamilyMemberValueEffect se = new SetFamilyMemberValueEffect();
				System.out.println("is it for colored familymember?[y/n]");
				String b = scan.nextLine();
				if(b.equals("y")){
					se.setColored(true);
				}else{
					se.setColored(false);
				}
				System.out.println("What's the value?");
				int v = scan.nextInt();
				scan.nextLine();
				se.setValue(v);
				return se;
			}else if(line.equals("pope")){
				PopeEffect pope = new PopeEffect();
				EnumMap<ResourceType, Integer> bonus = new EnumMap<ResourceType, Integer>(ResourceType.class);
				cardWriter.enterResourceBonus(bonus);
				Resource resourceBonus = Resource.of(bonus);
				pope.setBonus(resourceBonus);
				return pope;
			}else if(line.equals("db")){
				OtherEffect oe = new OtherEffect();
				oe.setType(EffectType.DOUBLERESOURCEEFFECT);
				return oe;
			}else if(line.equalsIgnoreCase("rd")){
				ModifyDiceEffect rd = new ModifyDiceEffect();
				rd.setReduce(excommunicationWriter.enterReduceValue());
				rd.setDiceColor(excommunicationWriter.enterDiceColor());
				return rd;
			}
		}while(true);
	}	
}














