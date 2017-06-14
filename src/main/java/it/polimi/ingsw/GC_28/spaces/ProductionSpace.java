package it.polimi.ingsw.GC_28.spaces;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class ProductionSpace extends Space{
	private FamilyMember firstPlayer;
	private boolean secondarySpace;

	public ProductionSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}
	
	public FamilyMember getFirstPlayer() {
		return firstPlayer;
	}
	
	public boolean isSecondarySpace() {
		return secondarySpace;
	}

	public void setSecondarySpace(boolean secondarySpace) {
		this.secondarySpace = secondarySpace;
	}
	
	public void freeFirstPlayer(){
		this.firstPlayer = null;
	}
	
	
	@Override
	public void addPlayer(FamilyMember player){
		if(this.isFree() == true){
			this.firstPlayer = player;
			this.setFree(false);
		}
		else{
			if(secondarySpace == true){
				this.getPlayer().add(player);		
			}
		}
			
	}	
	
	
	@Override
	public void applyBonus(Game game, FamilyMember familyMember){
		for(Building building : familyMember.getPlayer().getBoard().getBuildings()){
			building.getPermanentEffect().apply(familyMember, game);
		}
		familyMember.getPlayer().getBoard().getBonusTile().getProductionEffect().apply(familyMember, game);
	}

}
