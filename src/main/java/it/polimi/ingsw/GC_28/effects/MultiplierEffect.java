package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

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
	

	public Resource multiplyResource(int times){
		EnumMap<ResourceType, Integer> resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : resourceBonus.getResource().keySet()){
			resource.put(resType, resourceBonus.getResource().get(resType) * times);
		}
		Resource amount = Resource.of(resource);
		return amount;
	}
	
	@Override
	public void apply(FamilyMember familyMember, Game game){
		apply(familyMember.getPlayer(), game);
	}
	
	@Override
	public void apply(Player player, Game game) {
		System.out.println("apply di MultiplierEffect");
		if(resourceCost == null){
			switch(cardType){
			case TERRITORY:
				player.addResource(game.checkResourceExcommunication(multiplyResource(player.getBoard().getTerritories().size())));
				break;
			case BUILDING:
				player.addResource(game.checkResourceExcommunication(multiplyResource(player.getBoard().getBuildings().size())));
				break;
			case CHARACTER:
				player.addResource(game.checkResourceExcommunication(multiplyResource(player.getBoard().getCharacters().size())));
			case VENTURE:
				player.addResource(game.checkResourceExcommunication(multiplyResource(player.getBoard().getVentures().size())));
				break;
			}
		}
		else{
			int times = 0;
			for(ResourceType resType : resourceCost.getResource().keySet()){
				if(!(resourceCost.getResource().get(resType).equals(0))){
					times += player.getBoard().getResources().getResource().get(resType) / resourceCost.getResource().get(resType);
				}
			}
			player.addResource(game.checkResourceExcommunication(multiplyResource(times)));
		}
		
	}
	
	
	
}
