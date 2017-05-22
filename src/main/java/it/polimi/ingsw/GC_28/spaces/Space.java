package it.polimi.ingsw.GC_28.spaces;

import java.io.IOException;
import java.util.ArrayList;

import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;

public abstract class Space {
	private boolean free;
	private ArrayList<FamilyMember> players = new ArrayList<FamilyMember>();
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

	public ArrayList<FamilyMember> getPlayer() {
		return players;
	}
	
	public void addPlayer(FamilyMember player) throws IOException{
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
}
