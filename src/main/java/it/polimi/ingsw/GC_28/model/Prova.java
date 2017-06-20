package it.polimi.ingsw.GC_28.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.view.GameManager;
import it.polimi.ingsw.GC_28.view.GameView;

public class Prova {
	public static void main(String[] args) throws IOException {
		BoardsInitializer bi = new BoardsInitializer();
		bi.initFinalBonus();
		
		EnumMap<ResourceType, Integer> p = new EnumMap<ResourceType, Integer>(ResourceType.class);
		p.put(ResourceType.COIN, 4);
		p.put(ResourceType.SERVANT, 5);
		p.put(ResourceType.WOOD, 1);
		p.put(ResourceType.MILITARYPOINT, 1);
		p.put(ResourceType.FAITHPOINT, 2);
		Resource res2 = Resource.of(p);
		
		EnumMap<ResourceType, Integer> t = new EnumMap<ResourceType, Integer>(ResourceType.class);
		t.put(ResourceType.COIN, 2);
		t.put(ResourceType.WOOD, 9);
		t.put(ResourceType.MILITARYPOINT, 3);
		t.put(ResourceType.FAITHPOINT, 2);
		t.put(ResourceType.VICTORYPOINT,3);
		Resource res3 = Resource.of(t);
		
		EnumMap<ResourceType, Integer> c = new EnumMap<ResourceType, Integer>(ResourceType.class);
		c.put(ResourceType.COIN, 4);
		c.put(ResourceType.WOOD, 8);
		c.put(ResourceType.MILITARYPOINT, 3);
		c.put(ResourceType.FAITHPOINT, 3);
		c.put(ResourceType.VICTORYPOINT, 100);
		Resource res4 = Resource.of(c);
		
		EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		EnumMap<ResourceType, Integer> y = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			y.put(resType, 0);
		}
		Resource res = Resource.of(w);
		Resource res1 = Resource.of(y);
		PlayerBoard pb1 = new PlayerBoard(null, res);
		PlayerBoard pb2 = new PlayerBoard(null, res1);
		pb1.getResources().getResource().put(ResourceType.VICTORYPOINT, 4);
		
		Player p1 = new Player("andrea", PlayerColor.RED);
		Player p2 = new Player("marco", PlayerColor.BLUE);
		Player p3 = new Player("ludDSDFDSSdassdfgfdshhfdshcia", PlayerColor.GREEN);
		p1.setBoard(pb1);
		p2.setBoard(pb2);
		p3.setBoard(new PlayerBoard(null, Resource.of(new EnumMap<>(ResourceType.class))));
		List<Player> l = new ArrayList<>();
		l.add(p1);
		l.add(p2);
		l.add(p3);
		
		System.out.println("prima");
		for(Player player : l){
			System.out.println(player.getName());
			System.out.println(player.getBoard().getResources().toString());
		}
		
		GameModel gm = new GameModel(new GameBoard(), l);
		Venture v = new Venture("bel", 1, 2);
		ResourceEffect e = new ResourceEffect();
		e.setResourceBonus(res2);
		v.setPermanentEffect(e);
		p1.getBoard().addCard(v);
		pb2.addCard(new Character("sca", 3,3 ));

		GameView g = new GameView(gm);
		GameManager gameM = new GameManager();
		gameM.setView(g);
		gameM.applyFinalBonus();
		System.out.println("dopo");
		for(Player player : l){
			System.out.println(player.getName());
			System.out.println(player.getBoard().getResources().toString());
		}
		p2.addResource(res4);
		p3.addResource(res3);
		gameM.sortBy(l, ResourceType.MILITARYPOINT);
		System.out.println("Dopo sort");
		for(Player player : l){
			System.out.println(player.getName());
			System.out.println(player.getBoard().getResources().toString());
		}
		gameM.assignBonusForMilitary();
		System.out.println("Dopo assign");
		for(Player player : l){
			System.out.println(player.getName());
			System.out.println(player.getBoard().getResources().toString());
		}
		
		
		gameM.sortBy(l, ResourceType.VICTORYPOINT);
		System.out.println("winner: " + l.get(0).getName());

		System.out.println("\nend");
		System.out.println(g.getChartTable());
		
	}
	
	
}
