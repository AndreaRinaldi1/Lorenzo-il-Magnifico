package it.polimi.ingsw.GC_28.boards;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
//import java.lang.Character;


import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Card;
import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;

import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class Prova {
	public static void main(String[] args) throws FileNotFoundException {
		
		EnumMap<ResourceType, Integer> m = new EnumMap<ResourceType, Integer>(ResourceType.class);
		m.put(ResourceType.COIN, 0);
		m.put(ResourceType.WOOD, 0);
		m.put(ResourceType.MILITARYPOINT, 0);
		m.put(ResourceType.FAITHPOINT, 3);
		m.put(ResourceType.VICTORYPOINT, 0);
		Resource res1 = Resource.of(m);
	
		EnumMap<ResourceType, Integer> p = new EnumMap<ResourceType, Integer>(ResourceType.class);
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
		PlayerBoard pb = new PlayerBoard();
		pb.setResources(res);
		
		CouncilPrivilege cp = CouncilPrivilege.instance();
		HashMap<java.lang.Character, Resource> hm = new HashMap<java.lang.Character, Resource>();
		hm.put(java.lang.Character.valueOf('a'), res3);
		hm.put(java.lang.Character.valueOf('b'), res2);
		cp.setOptions(hm);
		
		Game g = new Game();
		GameBoard gb = new GameBoard();
		
		
		Player player = new Player(PlayerColor.BLUE);
		player.setBoard(pb);
		FamilyMember fm = new FamilyMember(player, false, DiceColor.ORANGE);
		
		
		//PROVA DI DISCOUNTEFFECT
		/*DiscountEffect d = new DiscountEffect();
		d.setDiscount(res1);
		d.setAlternativeDiscount(res2);
		d.setAlternativeDiscountPresence(true);
		d.apply(fm, gb, g);
		System.out.println(pb.getResources().toString());*/
		
		/*CouncilPrivilege cp = CouncilPrivilege.instance();
		HashMap<Character, Resource> hm = new HashMap<Character, Resource>();
		hm.put(Character.valueOf('a'), res3);
		hm.put(Character.valueOf('b'), res2);
		cp.setOptions(hm);
		ExchangeEffect e = new ExchangeEffect();*/

		
	}
}
