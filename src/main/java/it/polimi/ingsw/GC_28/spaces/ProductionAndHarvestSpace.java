package it.polimi.ingsw.GC_28.spaces;
import it.polimi.ingsw.GC_28.boards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;


public class ProductionAndHarvestSpace extends Space{
	private FamilyMember firstPlayer;
	private boolean secondarySpace;

	public ProductionAndHarvestSpace(boolean free, int actionValue) {
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
}
