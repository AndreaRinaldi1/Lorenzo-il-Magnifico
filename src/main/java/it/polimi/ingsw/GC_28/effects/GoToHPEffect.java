package it.polimi.ingsw.GC_28.effects;

import java.io.IOException;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.ResourceType;
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
	public void apply(FamilyMember familyMember, ClientWriter writer){
		System.out.println("apply di GoToHPEffect");
		try {
			writer.goToSpace(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void apply(Player player, ClientWriter writer) {
		System.out.println("Leader Effect");
		try {
			writer.goToSpace(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
