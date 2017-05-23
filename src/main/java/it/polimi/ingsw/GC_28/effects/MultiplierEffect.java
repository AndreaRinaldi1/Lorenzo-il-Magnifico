package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;

public class MultiplierEffect extends Effect{
	private Resource resourceBonus;
	private CardType cardType;
	private Resource resourceCost;
	
	public final EffectType type = EffectType.MULTIPLIEREFFECT;

	public MultiplierEffect(){
		super();
	}
	
	public Resource getResourceCost() {
		return resourceCost;
	}

	public void setResourceCost(Resource resourceCost) {
		this.resourceCost = resourceCost;
	}

	public Resource getResourceBonus() {
		return resourceBonus;
	}

	public void setResourceBonus(Resource resourceBonus) {
		this.resourceBonus = resourceBonus;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	private Resource multiplyResource(Resource res, int times){
		EnumMap<ResourceType, Integer> resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : res.getResource().keySet()){
			resource.put(resType, res.getResource().get(resType) * times);
		}
		Resource amount = Resource.of(resource);
		return amount;
	}
	
	@Override
	public void apply(PlayerBoard playerBoard, GameBoard gameBoard, Game game) {
		System.out.println("apply di MultiplierEffect");
		if(resourceCost == null){
			switch(cardType){
			case TERRITORY:
				playerBoard.addResource(multiplyResource(resourceBonus, playerBoard.getTerritories().size()));
				break;
			case BUILDING:
				playerBoard.addResource(multiplyResource(resourceBonus, playerBoard.getBuildings().size()));
				break;
			case CHARACTER:
				playerBoard.addResource(multiplyResource(resourceBonus, playerBoard.getCharacters().size()));
				break;
			case VENTURE:
				playerBoard.addResource(multiplyResource(resourceBonus, playerBoard.getVentures().size()));
				break;
			}
		}
		else{
			int times = 0;
			for(ResourceType resType : resourceCost.getResource().keySet()){
				if(!(resourceCost.getResource().get(resType).equals(0))){
					times = playerBoard.getResources().getResource().get(resType) / 2;
					break;
				}
			}
			playerBoard.addResource(multiplyResource(resourceBonus, times));
		}
		
	}
	
}
