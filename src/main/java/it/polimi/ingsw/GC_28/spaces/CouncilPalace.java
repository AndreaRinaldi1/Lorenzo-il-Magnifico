package it.polimi.ingsw.GC_28.spaces;

import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Game;

import java.util.List;

public class CouncilPalace extends Space{
	private ResourceEffect bonus1;
	private PrivilegesEffect bonus2;
	private static CouncilPalace instance;
	
	private CouncilPalace(boolean free, int actionValue) {
		super(true, actionValue);
	}
	
	private CouncilPalace(){}

	public static CouncilPalace instance(){
		if(instance == null){
			instance = new CouncilPalace();
		}
		return instance;
	}		
	
	public ResourceEffect getBonus1() {
		return bonus1;
	}

	public void setBonus1(ResourceEffect bonus1) {
		this.bonus1 = bonus1;
	}

	public PrivilegesEffect getBonus2() {
		return bonus2;
	}

	public void setBonus2(PrivilegesEffect bonus2) {
		this.bonus2 = bonus2;
	}

	public List<FamilyMember> getPlayerOrder(){
		return this.getPlayer();
	}
	
	@Override
	public void addPlayer(FamilyMember player){
		this.getPlayer().add(player);
	}
	
	
	@Override
	public void applyBonus(Game game, FamilyMember familyMember){
		bonus1.apply(familyMember, game);
		bonus2.apply(familyMember, game);
	}
	
}
