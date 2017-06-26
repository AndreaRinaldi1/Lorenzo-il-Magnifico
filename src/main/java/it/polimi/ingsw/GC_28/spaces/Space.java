package it.polimi.ingsw.GC_28.spaces;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;

public abstract class Space {
	private boolean free;
	private List<FamilyMember> players = new ArrayList<>();
	private int actionValue;
	
	public Space(){}
	
	public Space(boolean free, int actionValue){
		this.free = free;
		this.actionValue = actionValue;
	}
	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public List<FamilyMember> getPlayer() {
		return players;
	}
	
	public void addPlayer(FamilyMember player){
		if(this.isFree() == true){
			this.players.add(player);
			this.free = false;
		}
	}

	public int getActionValue() {
		return actionValue;
	}

	public void setActionValue(int actionValue) {
		this.actionValue = actionValue;
	} 
	
	
	public void applyBonus(GameView game, FamilyMember familyMember){}
}
