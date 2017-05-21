package it.polimi.ingsw.GC_28.effects;
import it.polimi.ingsw.GC_28.cards.*;


public class ExchangeEffect extends Effect{
	private Resource firstCost;
	private Resource secondCost;
	private boolean alternative;
	private Resource firstBonus;
	private Resource secondBonus;
	private Resource privilegeCost;
	private CouncilPrivilege privilegeBonus;
	public final EffectType type = EffectType.EXCHANGEEFFECT;

	
	public ExchangeEffect(){
		super();
	}
	
	
	public Resource getFirstCost() {
		return firstCost;
	}

	public void setFirstCost(Resource firstCost) {
		this.firstCost = firstCost;
	}

	public Resource getSecondCost() {
		return secondCost;
	}

	public void setSecondCost(Resource secondCost) {
		this.secondCost = secondCost;
	}

	public boolean isAlternative() {
		return alternative;
	}

	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}

	public Resource getFirstBonus() {
		return firstBonus;
	}

	public void setFirstBonus(Resource firstBonus) {
		this.firstBonus = firstBonus;
	}

	public Resource getSecondBonus() {
		return secondBonus;
	}

	public void setSecondBonus(Resource secondBonus) {
		this.secondBonus = secondBonus;
	}

	public Resource getPrivilegeCost() {
		return privilegeCost;
	}

	public void setPrivilegeCost(Resource privilegeCost) {
		this.privilegeCost = privilegeCost;
	}

	public CouncilPrivilege getPrivilegeBonus() {
		return privilegeBonus;
	}

	public void setPrivilegeBonus(CouncilPrivilege privilegeBonus) {
		this.privilegeBonus = privilegeBonus;
	}

	/*private HashMap<Resource, Resource> exchangeResources = new HashMap<Resource, Resource>();
	private HashMap<Resource, CouncilPrivilege> exchangePriviliges = new HashMap<Resource, CouncilPrivilege>();
	
	
	
	public HashMap<Resource, Resource> getExchangeResources() {
		return exchangeResources;
	}

	public void setExchangeResources(HashMap<Resource, Resource> exchangeResources) {
		this.exchangeResources = exchangeResources;
	}

	public HashMap<Resource, CouncilPrivilege> getExchangePriviliges() {
		return exchangePriviliges;
	}

	public void setExchangePriviliges(HashMap<Resource, CouncilPrivilege> exchangePriviliges) {
		this.exchangePriviliges = exchangePriviliges;
	}
*/
	
}
