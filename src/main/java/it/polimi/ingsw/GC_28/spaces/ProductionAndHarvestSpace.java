package spaces;

import boards.FamilyMember;

public class ProductionAndHarvestSpace extends Space{
	private FamilyMember firstPlayer;
	private ProdHarvType pht;
	
	public ProductionAndHarvestSpace(boolean free, int actionValue, ProdHarvType pht) {
		super(free, actionValue);
		this.pht = pht;
	}

	public ProdHarvType getPht() {
		return pht;
	}

	public void setPht(ProdHarvType pht) {
		this.pht = pht;
	}

	public FamilyMember getFirstPlayer() {
		return firstPlayer;
	}
	
	@Override
	public void addPlayer(FamilyMember player){
		if(this.isFree() == true){
			this.firstPlayer = player;
			this.setFree(false);
		}
		else
			this.getPlayer().add(player);		
	}	
}
