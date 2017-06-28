package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
import it.polimi.ingsw.GC_28.cards.Character;

public class FinalReduceEffect  extends Effect{
	private CardType cardType;
	private Resource resourceCost;
	private Resource resourceBonus;
	public final EffectType type = EffectType.FINALREDUCEEFFECT;
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public void setResourceCost(Resource resourceCost) {
		this.resourceCost = resourceCost;
	}

	public void setResourceBonus(Resource resourceBonus) {
		this.resourceBonus = resourceBonus;
	}

	public Resource multiplyResource(int times){
		EnumMap<ResourceType, Integer> resource = new EnumMap<>(ResourceType.class);
		for(ResourceType resType : resourceBonus.getResource().keySet()){
			resource.put(resType, resourceBonus.getResource().get(resType) * times);
		}
		return Resource.of(resource);
	}
	
	@Override
	public void apply(Player player, GameView game){
		int times = 0;
		
		switch(cardType){
		case BUILDING:
			for(Building b : player.getBoard().getBuildings()){
				for(ResourceType resType : resourceCost.getResource().keySet()){
					if(!(resourceCost.getResource().get(resType).equals(0))){
						times +=  b.getCost().getResource().get(resType) / resourceCost.getResource().get(resType);
					}
				}
			}
			break;
		case CHARACTER:
			for(Character c : player.getBoard().getCharacters()){
				for(ResourceType resType : resourceCost.getResource().keySet()){
					if(!(resourceCost.getResource().get(resType).equals(0))){
						times +=  c.getCost().getResource().get(resType) / resourceCost.getResource().get(resType);
					}
				}
			}
			break;
		default:
			break;
		
		}
		player.addResource(multiplyResource(times));
	}

	//FOR TEST 
	public CardType getCardType() {
		return cardType;
	}

	public Resource getResourceCost() {
		return resourceCost;
	}

	public Resource getResourceBonus() {
		return resourceBonus;
	}

	public EffectType getType() {
		return type;
	}
	
	

}
