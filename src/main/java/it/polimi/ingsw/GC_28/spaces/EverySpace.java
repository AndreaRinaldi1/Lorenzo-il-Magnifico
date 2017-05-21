package spaces;

public class EverySpace {
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private TwoPrivilegesSpace twoPrivilegesSpace;
	private CouncilPalace councilPalace;
	private ProductionAndHarvestSpace production;
	private ProductionAndHarvestSpace harvest;

	
	public TwoPrivilegesSpace getTwoPrivilegesSpace() {
		return twoPrivilegesSpace;
	}
	public void setTwoPrivilegesSpace(TwoPrivilegesSpace twoPrivilegesSpace) {
		this.twoPrivilegesSpace = twoPrivilegesSpace;
	}
	public CouncilPalace getCouncilPalace() {
		return councilPalace;
	}
	public void setCouncilPalace(CouncilPalace councilPalace) {
		this.councilPalace = councilPalace;
	}
	public ProductionAndHarvestSpace getProduction() {
		return production;
	}
	public void setProduction(ProductionAndHarvestSpace production) {
		this.production = production;
	}
	public ProductionAndHarvestSpace getHarvest() {
		return harvest;
	}
	public void setHarvest(ProductionAndHarvestSpace harvest) {
		this.harvest = harvest;
	}

	public MarketSpace getCoinSpace() {
		return coinSpace;
	}
	public MarketSpace getServantSpace() {
		return servantSpace;
	}
	public MarketSpace getMixedSpace() {
		return mixedSpace;
	}
	public void setCoinSpace(MarketSpace coinSpace) {
		this.coinSpace = coinSpace;
	}
	public void setServantSpace(MarketSpace servantSpace) {
		this.servantSpace = servantSpace;
	}
	public void setMixedSpace(MarketSpace mixedSpace) {
		this.mixedSpace = mixedSpace;
	}
	
	
}
