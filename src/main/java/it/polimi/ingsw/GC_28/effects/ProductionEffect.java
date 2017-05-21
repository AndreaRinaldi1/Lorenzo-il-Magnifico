package effects;
import cards.*;

public class ProductionEffect extends Effect{
	private int productionValue;
	private Resource resourceProductionBonus;
	private ExchangeEffect exchangeBonus;
	private MultiplierEffect multiplierEffect;
	public final EffectType type = EffectType.PRODUCTIONEFFECT;

	
	public ProductionEffect(){
		super();
	}
	
	public MultiplierEffect getMultiplierEffect() {
		return multiplierEffect;
	}

	public void setMultiplierEffect(MultiplierEffect multiplierEffect) {
		this.multiplierEffect = multiplierEffect;
	}


	public int getProductionValue() {
		return productionValue;
	}

	public void setProductionValue(int productionValue) {
		this.productionValue = productionValue;
	}

	public Resource getResourceProductionBonus() {
		return resourceProductionBonus;
	}

	public void setResourceProductionBonus(Resource resourceProductionBonus) {
		this.resourceProductionBonus = resourceProductionBonus;
	}


	public ExchangeEffect getExchangeBonus() {
		return exchangeBonus;
	}

	public void setExchangeBonus(ExchangeEffect exchangeBonus) {
		this.exchangeBonus = exchangeBonus;
	}
}
