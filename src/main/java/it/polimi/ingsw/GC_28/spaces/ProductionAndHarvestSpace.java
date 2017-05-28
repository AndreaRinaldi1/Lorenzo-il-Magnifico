package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;


public class ProductionAndHarvestSpace extends Space{
	private FamilyMember firstPlayer;
	private boolean secondarySpace;
	private boolean harvest;

	public ProductionAndHarvestSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}

	public boolean isHarvest() {
		return harvest;
	}

	public void setHarvest(boolean harvest) {
		this.harvest = harvest;
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
		if(harvest){
			for(Territory territory : familyMember.getPlayer().getBoard().getTerritories()){
				territory.getPermanentEffect().apply(familyMember, game);
			}
		}
		else{
			for(Building building : familyMember.getPlayer().getBoard().getBuildings()){
				building.getPermanentEffect().apply(familyMember, game);
			}
		}
	}
	
	
	
}
