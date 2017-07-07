package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the effect that allows to gain a certain amount of resources depending on how many cards of a certain type or resources he have.
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
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
	

	/**
	 * This method multiplies the resources that has to be added (per unit) 
	 * for the gain factor represented in the specification of the effect.
	 * @param times the multiplication factor
	 * @return the total amount of resources to be added
	 */
	public Resource multiplyResource(int times){
		EnumMap<ResourceType, Integer> resource = new EnumMap<>(ResourceType.class);
		for(ResourceType resType : resourceBonus.getResource().keySet()){
			resource.put(resType, resourceBonus.getResource().get(resType) * times);
		}
		return Resource.of(resource);
	}
	
	@Override
	public void apply(FamilyMember familyMember, GameView game){
		apply(familyMember.getPlayer(), game);
	}
	
	/**
	 * This method applies the effect by adding the resources specified by the effect, according to the amount of cards of a certain type I have.
	 * Otherwise also resources can be used, instead of card types, as for multiplication factor.
	 */
	@Override
	public void apply(Player player, GameView game) {
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
				break;
			case VENTURE:
				player.addResource(game.checkResourceExcommunication(multiplyResource(player.getBoard().getVentures().size())));
				break;
			default:
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
