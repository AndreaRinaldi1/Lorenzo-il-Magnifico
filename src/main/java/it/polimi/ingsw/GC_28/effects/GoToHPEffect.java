package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;

public class GoToHPEffect extends Effect{
	private int actionValue;
	public final EffectType type = EffectType.GOTOHP;
	private ProdHarvType specificType;


	public GoToHPEffect(){
		super();
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	}

	
	public ProdHarvType getSpecificType() {
		return specificType;
	}

	public void setSpecificType(ProdHarvType specificType) {
		this.specificType = specificType;
	}

	@Override
	public void apply(FamilyMember familyMember, Game game){
		System.out.println("apply di GoToHPEffect");
		game.goToSpace(this);
	}
	
	@Override
	public void apply(Player player, Game game) {
		System.out.println("Leader Effect");
		game.goToSpace(this);
	}
	
	
	
}
