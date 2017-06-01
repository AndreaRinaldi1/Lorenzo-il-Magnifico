package it.polimi.ingsw.GC_28.boards;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;


public class Prova {
	public static void main(String[] args) {
		
		Deck d = new Deck();
		CardReader c = new CardReader();
		try{
			d = c.startRead();
			for(Character x : d.getCharacters()){
				System.out.println(x.getPermanentEffect().getClass());
			}
		}catch(FileNotFoundException e){
			Logger.getAnonymousLogger().log(Level.SEVERE,"error" + e);
		}
		/*EnumMap<ResourceType, Integer> p = new EnumMap<ResourceType, Integer>(ResourceType.class);
		p.put(ResourceType.COIN, 12);
		p.put(ResourceType.SERVANT, 5);
		p.put(ResourceType.WOOD, 19);
		p.put(ResourceType.MILITARYPOINT, 1);
		p.put(ResourceType.FAITHPOINT, 2);
		Resource res2 = Resource.of(p);
		
		EnumMap<ResourceType, Integer> t = new EnumMap<ResourceType, Integer>(ResourceType.class);
		t.put(ResourceType.COIN, 42);
		t.put(ResourceType.WOOD, 99);
		t.put(ResourceType.MILITARYPOINT, 51);
		t.put(ResourceType.FAITHPOINT, 23);
		t.put(ResourceType.VICTORYPOINT,83);
		Resource res3 = Resource.of(t);
		
		EnumMap<ResourceType, Integer> c = new EnumMap<ResourceType, Integer>(ResourceType.class);
		c.put(ResourceType.COIN, 4);
		c.put(ResourceType.WOOD, 9);
		c.put(ResourceType.MILITARYPOINT, 8);
		c.put(ResourceType.FAITHPOINT, 13);
		c.put(ResourceType.VICTORYPOINT, 93);
		Resource res4 = Resource.of(c);
		
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		Resource res = Resource.of(w);
	//	PlayerBoard pb = new PlayerBoard();
	//	pb.setResources(res);
		
		CouncilPrivilege cp = CouncilPrivilege.instance();
		HashMap<java.lang.Character, Resource> hm = new HashMap<java.lang.Character, Resource>();
		hm.put(java.lang.Character.valueOf('a'), res3);
		hm.put(java.lang.Character.valueOf('b'), res2);
		cp.setOptions(hm);
		
		Game g = new Game();
		GameBoard gb = new GameBoard();
		
		
		Player player = new Player("ciao", PlayerColor.BLUE);
	//	player.setBoard(pb);
		FamilyMember fm = new FamilyMember(player, false, DiceColor.ORANGE);
		player.getFamilyMembers()[0] = fm;
		g.setCurrentPlayer(player);
		
		g.askCard(null);
		
		//PROVA DI DISCOUNTEFFECT
		/*DiscountEffect d = new DiscountEffect();
		d.setDiscount(res1);
		d.setAlternativeDiscount(res2);
		d.setAlternativeDiscountPresence(true);
		d.apply(fm, gb, g);
		System.out.println(pb.getResources().toString());*/
		

		//PROVA DI EXCHANGEEFFECT
		/*ExchangeEffect e = new ExchangeEffect();
		e.setAlternative(true);
		e.setFirstBonus(res1);
		e.setFirstCost(res2);
		e.setSecondCost(res3);
		e.setSecondBonus(res4);
		e.apply(fm, gb, g);
		System.out.println(pb.getResources().toString());*/
		
		
		//PROVA DI MULTIPLIEREFFECT
		/*MultiplierEffect me = new MultiplierEffect();
		me.setResourceCost(res1);
		me.setResourceBonus(res3);
		pb.addCard(new Building("cis", 1, 2));
		pb.addCard(new Building("cfsais", 4, 2));
		me.apply(fm, gb, g);		
		System.out.println(pb.getResources().toString());*/
		
		
		//PROVA DI HARVESTEFFECT
		/*HarvestEffect he = new HarvestEffect();
		he.setHarvestValue(3);
		he.setResourceHarvestBonus(res3);
		he.setCouncilPrivilegeBonus(CouncilPrivilege.instance());
		he.apply(fm, gb, g);
		System.out.println(pb.getResources().toString());*/
		
		//PROVA DI INCREMENTCARDEFFECT
		/*System.out.println(fm.getValue());
		System.out.println(pb.getResources().toString());
		IncrementCardEffect ic = new IncrementCardEffect();
		ic.setDiscountPresence(true);
		ic.setDiscount(d);
		ic.setIncrement(2);
		ic.apply(fm, gb, g);
		System.out.println(fm.getValue());
		System.out.println(pb.getResources().toString());*/

		
		//PROVA DI PRIVILEGESEFFECT
		/*PrivilegesEffect pe = new PrivilegesEffect();
		pe.setDifferent(false);
		pe.setNumberOfCouncilPrivileges(2);
		pe.apply(fm, gb, g);*/
		
		
		
		
		
		/*CardReader c = new CardReader();
		Deck deck = c.startRead();
		
		System.out.println("Characters");
		for(Character x : deck.getCharacters()){
			x.getPermanentEffect().apply(p);
			for(int i = 0; i < x.getImmediateEffect().size(); i++){
				x.getImmediateEffect().get(i).apply(p);
			}
		}
		
		System.out.println("Territories");
		for(Territory x : deck.getTerritories()){
			x.getPermanentEffect().apply(p);
			for(int i = 0; i < x.getImmediateEffect().size(); i++){
				x.getImmediateEffect().get(i).apply(p);
			}
		}
		
		System.out.println("Buildings");
		for(Building x : deck.getBuildings()){
			x.getPermanentEffect().apply(p);
			x.getImmediateEffect().apply(p);
		}
		
		System.out.println("Ventures");
		for(Venture x : deck.getVentures()){
			x.getPermanentEffect().apply(p);
			for(int i = 0; i < x.getImmediateEffect().size(); i++){
				x.getImmediateEffect().get(i).apply(p);
			}
		}
		
		*/
	}


}
