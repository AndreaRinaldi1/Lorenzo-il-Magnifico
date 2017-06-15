package it.polimi.ingsw.GC_28.effects;

import java.io.IOException;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.SpaceAction;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class GoToHPEffect extends Effect{
	private int actionValue;
	private boolean production;
	private boolean harvest;
	public final EffectType type = EffectType.GOTOHP;

	public GoToHPEffect(){
		super();
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}

	public boolean isProduction() {
		return production;
	}

	public void setProduction(boolean production) {
		this.production = production;
	}

	public boolean isHarvest() {
		return harvest;
	}

	public void setHarvest(boolean harvest) {
		this.harvest = harvest;
	}
	
	@Override
	public void apply(FamilyMember familyMember, Game game){
		System.out.println("apply di GoToHPEffect");
		//game.goToSpace(this);
		try {
			game.getHandlers().get(familyMember.getPlayer()).getOut().writeUTF("goToHP");
			game.getHandlers().get(familyMember.getPlayer()).getOut().flush();
			game.getHandlers().get(familyMember.getPlayer()).getOut().writeObject(this);
			game.getHandlers().get(familyMember.getPlayer()).getOut().flush();
			game.getHandlers().get(familyMember.getPlayer()).getOut().reset();
			Object obj = game.getHandlers().get(familyMember.getPlayer()).getIn().readObject();
			SpaceAction action = (SpaceAction)obj;
			if(action.isApplicable()){
				action.apply();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void apply(Player player, Game game) {
		System.out.println("Leader Effect");
		//game.goToSpace(this);
		try {
			game.getHandlers().get(player).getOut().writeUTF("goToHP");
			game.getHandlers().get(player).getOut().flush();
			game.getHandlers().get(player).getOut().writeObject(this);
			game.getHandlers().get(player).getOut().flush();
			game.getHandlers().get(player).getOut().reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
