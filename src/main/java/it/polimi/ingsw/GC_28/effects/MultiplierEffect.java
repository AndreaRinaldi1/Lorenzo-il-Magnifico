package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
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
	

	private Resource multiplyResource(int times){
		EnumMap<ResourceType, Integer> resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : resourceBonus.getResource().keySet()){
			resource.put(resType, resourceBonus.getResource().get(resType) * times);
		}
		Resource amount = Resource.of(resource);
		return amount;
	}
	
	@Override
	public void apply(FamilyMember familyMember, GameBoard gameBoard, Game game) {
		System.out.println("apply di MultiplierEffect");
		if(resourceCost == null){
			switch(cardType){
			case TERRITORY:
				familyMember.getPlayer().getBoard().addResource(multiplyResource(familyMember.getPlayer().getBoard().getTerritories().size()));
				break;
			case BUILDING:
				familyMember.getPlayer().getBoard().addResource(multiplyResource(familyMember.getPlayer().getBoard().getBuildings().size()));
				break;
			case CHARACTER:
				familyMember.getPlayer().getBoard().addResource(multiplyResource(familyMember.getPlayer().getBoard().getCharacters().size()));
				break;
			case VENTURE:
				familyMember.getPlayer().getBoard().addResource(multiplyResource(familyMember.getPlayer().getBoard().getVentures().size()));
				break;
			}
		}
		else{
			int times = 0;
			for(ResourceType resType : resourceCost.getResource().keySet()){
				if(!(resourceCost.getResource().get(resType).equals(0))){
					times = familyMember.getPlayer().getBoard().getResources().getResource().get(resType) / 2;
					break;
				}
			}
			familyMember.getPlayer().getBoard().addResource(multiplyResource(times));
		}
		
	}
	
}
