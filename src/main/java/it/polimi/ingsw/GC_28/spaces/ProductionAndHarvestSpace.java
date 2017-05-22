package it.polimi.ingsw.GC_28.spaces;
import components.FamilyMember;
import it.polimi.ingsw.GC_28.boards.*;


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
