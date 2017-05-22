package it.polimi.ingsw.GC_28.boards;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;
import java.io.FileNotFoundException;

import components.ResourceType;
import it.polimi.ingsw.GC_28.effects.*;
import it.polimi.ingsw.GC_28.spaces.*;

public class Prova {
	public static void main(String[] args) throws FileNotFoundException {
		/*CouncilPrivilegeReader.startRead() ;
		for(Character c : GameBoardProva.getCouncilPrivilege().getOptions().keySet()){
    		System.out.println(c + "\n" + GameBoardProva.getCouncilPrivilege().getOptions().get(c).toString());
    	}*/
		SpacesReader r = new SpacesReader();
		EverySpace everySpace = r.startRead();
		GameBoard gameBoard = new GameBoard();
		Effect.gameBoard = gameBoard;
		
		System.out.println(gameBoard.getCoinSpace().getActionValue());
		PlayerBoard p = new PlayerBoard();
		for(ResourceType rt : ResourceType.values()){
			p.getResource().getResource().put(rt, 0);
		}
		
		CardReader c = new CardReader();
		Deck deck = c.startRead();
		//System.out.println(deck.getBuildings().get(0).toString());
		//Building g = deck.getBuildings().get(0);
		
		
		
		System.out.println("Prima");
		System.out.println(p.getResource().toString());
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
		/*if(e instanceof PrivilegesEffect)
		{
			System.out.println("dentro");
			PrivilegesEffect ee = (PrivilegesEffect) e;
			ee.apply(p);
			System.out.println(p.res.toString());
		}*/

	}
}
