package it.polimi.ingsw.GC_28.boards;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.cards.Character;

import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.view.GameView;


public class Prova {
	public static void main(String[] args)  {
		
		
		/*EnumMap<ResourceType, Integer> p = new EnumMap<ResourceType, Integer>(ResourceType.class);
		p.put(ResourceType.COIN, 1);
		p.put(ResourceType.SERVANT, 5);
		p.put(ResourceType.WOOD, 1);
		p.put(ResourceType.MILITARYPOINT, 1);
		p.put(ResourceType.FAITHPOINT, 2);
		Resource res2 = Resource.of(p);
		
		EnumMap<ResourceType, Integer> t = new EnumMap<ResourceType, Integer>(ResourceType.class);
		t.put(ResourceType.COIN, 2);
		t.put(ResourceType.WOOD, 9);
		t.put(ResourceType.MILITARYPOINT, 1);
		t.put(ResourceType.FAITHPOINT, 2);
		t.put(ResourceType.VICTORYPOINT,3);
		Resource res3 = Resource.of(t);
		
		EnumMap<ResourceType, Integer> c = new EnumMap<ResourceType, Integer>(ResourceType.class);
		c.put(ResourceType.COIN, 4);
		c.put(ResourceType.WOOD, 8);
		c.put(ResourceType.MILITARYPOINT, 0);
		c.put(ResourceType.FAITHPOINT, 3);
		c.put(ResourceType.VICTORYPOINT, 2);
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
		
		
		Player p1 = new Player("jhonny",PlayerColor.RED);
		Player p2 = new Player("karl", PlayerColor.BLUE);
		Player p3 = new Player("fd", PlayerColor.GREEN);

		EnumMap<ResourceType, Integer> en1 = new EnumMap<>(ResourceType.class);
		en1.put(ResourceType.MILITARYPOINT, 3);
		en1.put(ResourceType.VICTORYPOINT,0);
		Resource r1 = Resource.of(en1);
		PlayerBoard pb1 = new PlayerBoard(null, r1);
		p1.setBoard(pb1);
		EnumMap<ResourceType, Integer> en2 = new EnumMap<>(ResourceType.class);
		en2.put(ResourceType.MILITARYPOINT, 4);
		en2.put(ResourceType.VICTORYPOINT,0);
		Resource r2 = Resource.of(en2);
		PlayerBoard pb2 = new PlayerBoard(null, r2);
		p2.setBoard(pb2);
		EnumMap<ResourceType, Integer> en3 = new EnumMap<>(ResourceType.class);
		en3.put(ResourceType.MILITARYPOINT, 3);
		en3.put(ResourceType.VICTORYPOINT,0);
		Resource r3 = Resource.of(en3);
		PlayerBoard pb3 = new PlayerBoard(null, r3);
		p3.setBoard(pb3);
		List<Player> players = new ArrayList<>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		Gson gson = new GsonBuilder().create();
		//try {
			JsonReader readerFinalBonus;
			try {
				readerFinalBonus = new JsonReader(new FileReader("finalBonus.json"));
				//Type hashMapType = new TypeToken<HashMap<String,ArrayList<Resource>>>() {}.getType();
				FinalBonus finalBonus = gson.fromJson(readerFinalBonus, FinalBonus.class);
				FinalBonus.setFinalBonus(finalBonus);
				readerFinalBonus.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		/*} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "file not found" + e);
		}
			
		GameBoard gb = new GameBoard();
		GameModel gameModel = new GameModel(gb, players);
		Game g = new Game(gameModel);
		System.out.println("p1 " +p1.getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT));
		System.out.println("p2" +p2.getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT));
		System.out.println("p3" +p3.getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT));
		g.sortBy(gameModel.getPlayers(), ResourceType.MILITARYPOINT);

		g.assignBonusForMilitary();
		System.out.println("p1 " +p1.getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT));
		System.out.println("p2" +p2.getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT));
		System.out.println("p3" +p3.getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT));
*/
	}


}
