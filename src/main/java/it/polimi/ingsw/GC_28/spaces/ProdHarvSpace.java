package it.polimi.ingsw.GC_28.spaces;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;

public class ProdHarvSpace extends Space{
	private FamilyMember firstPlayer;
	private boolean secondarySpace;
	private ProdHarvType type;
	
	public ProdHarvSpace(boolean free, int actionValue) {
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
	
	public ProdHarvType getType() {
		return type;
	}

	public void setType(ProdHarvType type) {
		this.type = type;
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
	public void applyBonus(GameView game, FamilyMember familyMember){
		if(type == ProdHarvType.HARVEST){
			for(Territory territory : familyMember.getPlayer().getBoard().getTerritories()){
				territory.getPermanentEffect().apply(familyMember, game);
			}
			familyMember.getPlayer().getBoard().getBonusTile().getHarvestEffect().apply(familyMember, game);
		}
		else if(type == ProdHarvType.PRODUCTION){
			for(Building building : familyMember.getPlayer().getBoard().getBuildings()){
				building.getPermanentEffect().apply(familyMember, game);
			}
			familyMember.getPlayer().getBoard().getBonusTile().getProductionEffect().apply(familyMember, game);
		}
	}
		
}
