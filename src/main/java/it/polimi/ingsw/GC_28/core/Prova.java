package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.cards.*;

import java.io.FileNotFoundException;

import it.polimi.ingsw.GC_28.effects.*;
import it.polimi.ingsw.GC_28.spaces.*;

public class Prova {
	public static void main(String[] args) throws FileNotFoundException {
		/*CouncilPrivilegeReader.startRead() ;
		for(Character c : GameBoardProva.getCouncilPrivilege().getOptions().keySet()){
    		System.out.println(c + "\n" + GameBoardProva.getCouncilPrivilege().getOptions().get(c).toString());
    	}*/
		SpacesReader r = new SpacesReader();
		GameBoard gb = r.startRead();
		Effect.gameBoard = gb;
		
		PlayerBoard p = new PlayerBoard();
		for(ResourceType rt : ResourceType.values()){
			p.res.getResource().put(rt, 0);
		}
		
		CardReader c = new CardReader();
		Deck deck = c.startRead();
		//System.out.println(deck.getBuildings().get(0).toString());
		//Building g = deck.getBuildings().get(0);
		
		
		
		System.out.println("Prima");
		System.out.println(p.res.toString());
		//System.out.println(gb.getCoinSpace().getActionValue());
		//e.apply(p);
		//g.getImmediateEffect().apply(p);
		//System.out.println(p.res.toString());
		//System.out.println(gb.getCoinSpace().getActionValue());

		//Effect e = new NoCellBonusEffect();

		//Character x = new Character("ciao", 1,1);
		//x.setPermanentEffect(e);
		for(it.polimi.ingsw.GC_28.cards.Character x : deck.getCharacters()){
			x.getPermanentEffect().apply(p);
			//System.out.println(p.res.toString());
			for(int i = 0; i < x.getImmediateEffect().size(); i++){
				x.getImmediateEffect().get(i).apply(p);
			}
		}
		
		
		

		/*if(e instanceof PrivilegesEffect)
		{
			System.out.println("dentro");
			PrivilegesEffect ee = (PrivilegesEffect) e;
			ee.apply(p);
			System.out.println(p.res.toString());
		}*/

	}
}
