package it.polimi.ingsw.GC_28.spaces;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.core.*;
import java.util.ArrayList;
import java.lang.Character;

public class CouncilPalace extends Space{
	private Resource bonus1;
	private CouncilPrivilege bonus2;
	private static CouncilPalace instance;
	
	private CouncilPalace(boolean free, int actionValue, CouncilPrivilege bonus2) {
		super(true, actionValue);
		this.bonus2 = bonus2;
	}
	
	private CouncilPalace(){}

	public static CouncilPalace instance(){
		if(instance == null){
			instance = new CouncilPalace();
		}
		return instance;
	}	
	
	public Resource getBonus1() {
		return bonus1;
	}
	
	public void setBonus1(Resource bonus1) {
		this.bonus1 = bonus1;
	}

	public Resource getBonus2(Character choice) {
		return this.bonus2.choose(choice);
	}
	
	public void setBonus2(CouncilPrivilege bonus2) {
		this.bonus2 = bonus2;
	}
	
	public ArrayList<FamilyMember> getPlayerOrder(){
		return this.getPlayer();
	}
	
	@Override
	public void addPlayer(FamilyMember player){
		this.getPlayer().add(player);
	}
}
