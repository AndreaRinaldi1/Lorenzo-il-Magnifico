package it.polimi.ingsw.GC_28.spaces;

public class EverySpace {
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private PrivilegesSpace privilegesSpace;
	private CouncilPalace councilPalace;
	private ProdHarvSpace productionSpace;
	private ProdHarvSpace harvestSpace;

	
	public PrivilegesSpace getPrivilegesSpace() {
		return privilegesSpace;
	}
	public void setPrivilegesSpace(PrivilegesSpace privilegesSpace) {
		this.privilegesSpace = privilegesSpace;
	}
	public CouncilPalace getCouncilPalace() {
		return councilPalace;
	}
	public void setCouncilPalace(CouncilPalace councilPalace) {
		this.councilPalace = councilPalace;
	}
	public ProdHarvSpace getProduction() {
		return productionSpace;
	}
	public void setProduction(ProdHarvSpace production) {
		this.productionSpace = production;
	}
	public ProdHarvSpace getHarvest() {
		return harvestSpace;
	}
	public void setHarvest(ProdHarvSpace harvest) {
		this.harvestSpace = harvest;
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
