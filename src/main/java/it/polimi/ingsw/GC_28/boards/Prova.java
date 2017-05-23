package it.polimi.ingsw.GC_28.boards;

import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.HashMap;
import java.lang.Character;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.model.Game;

public class Prova {
	public static void main(String[] args) throws FileNotFoundException {
		/*CouncilPrivilegeReader.startRead() ;
		for(Character c : GameBoardProva.getCouncilPrivilege().getOptions().keySet()){
    		System.out.println(c + "\n" + GameBoardProva.getCouncilPrivilege().getOptions().get(c).toString());
    	}*/
		
		
		EnumMap<ResourceType, Integer> m = new EnumMap<ResourceType, Integer>(ResourceType.class);
		m.put(ResourceType.COIN, 12);
		m.put(ResourceType.WOOD, 19);
		m.put(ResourceType.MILITARYPOINT, 1);
		m.put(ResourceType.FAITHPOINT, 2);
		m.put(ResourceType.VICTORYPOINT, 3);
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
		
		//System.out.println(res1.equals(res2));
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);

		PlayerBoard pb = new PlayerBoard();
		for(ResourceType rt : ResourceType.values()){
			w.put(rt, 0);
		}
		Resource r = Resource.of(w);
		pb.setResources(r);
		System.out.println(pb.getResources().toString());

		Game g = new Game();
		GameBoard gb = new GameBoard();
		//Effect.setGame(g);
		//Resource res = g.askAlternativeDiscount(res1, res2);
		//System.out.println(res.toString());
		
		/*
		DiscountEffect d = new DiscountEffect();
		d.setDiscount(res1);
		d.setAlternativeDiscount(res2);
		d.setAlternativeDiscountPresence(true);
		
		d.apply(pb, gb, g);
		System.out.println(pb.getResources().toString());
		*/
		CouncilPrivilege cp = CouncilPrivilege.instance();
		HashMap<Character, Resource> hm = new HashMap<Character, Resource>();
		hm.put(Character.valueOf('a'), res3);
		hm.put(Character.valueOf('b'), res2);
		cp.setOptions(hm);
		ExchangeEffect e = new ExchangeEffect();
		e.setAlternative(true);
		e.setFirstBonus(res1);
		e.setFirstCost(res2);
		e.setSecondCost(res3);
		e.setSecondBonus(res4);
		e.apply(pb, gb, g);
		System.out.println(pb.getResources().toString());
		/*
		SpacesReader r = new SpacesReader();
		EverySpace everySpace = r.startRead();
		GameBoard gameBoard = new GameBoard();
		Effect e = new Effect();
		e.gameBoard = gameBoard;
		
		System.out.println(gameBoard.getCoinSpace().getActionValue());
		PlayerBoard p = new PlayerBoard();
		for(ResourceType rt : ResourceType.values()){
			p.getResources().getResource().put(rt, 0);
		}
		
		CardReader c = new CardReader();
		Deck deck = c.startRead();
		//System.out.println(deck.getBuildings().get(0).toString());
		//Building g = deck.getBuildings().get(0);
		
		
		
		System.out.println("Prima");
		System.out.println(p.getResources().toString());
		//System.out.println(gb.getCoinSpace().getActionValue());
		//e.apply(p);
		//g.getImmediateEffect().apply(p);
		//System.out.println(p.res.toString());
		//System.out.println(gb.getCoinSpace().getActionValue());

		//Effect e = new NoCellBonusEffect();

		//Character x = new Character("ciao", 1,1);
		//x.setPermanentEffect(e);
		
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
		/*if(e instanceof PrivilegesEffect)
		{
			System.out.println("dentro");
			PrivilegesEffect ee = (PrivilegesEffect) e;
			ee.apply(p);
			System.out.println(p.res.toString());
		}*/

	}
}
