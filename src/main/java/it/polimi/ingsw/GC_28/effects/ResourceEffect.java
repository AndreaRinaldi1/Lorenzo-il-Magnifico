package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent the basic effect that increases the amount of resources of a player.
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 07/04/2017
 */
public class ResourceEffect extends Effect{	
	private Resource resourceBonus;
	public final EffectType type = EffectType.RESOURCEEFFECT;

	public ResourceEffect(){
		super();
	}

	public Resource getResourceBonus() {
		return resourceBonus;
	}

	public void setResourceBonus(Resource resourceBonus) {
		this.resourceBonus = resourceBonus;
	}

	/**
	 * This method increases the resources of the player by the specified amount and, if the Leader card Santa Rita is played and active, 
	 * the increment is done twice due to this leader effect.
	 */
	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		familyMember.getPlayer().addResource(game.checkResourceExcommunication(resourceBonus));
		//check for Santa Rita LeaderCard
		for(LeaderCard lc : familyMember.getPlayer().getLeaderCards()){
			if(("Santa Rita").equalsIgnoreCase(lc.getName()) && lc.getPlayed() && lc.getActive()){
				for(ResourceType rt : resourceBonus.getResource().keySet()){
					if(rt.equals(ResourceType.COIN) || rt.equals(ResourceType.WOOD) || rt.equals(ResourceType.STONE) || rt.equals(ResourceType.SERVANT)){
						/*
						 * build a temporary resource used to double the bonus only for the given ResourceType
						 */
						EnumMap<ResourceType, Integer> tmp = new EnumMap<>(ResourceType.class);
						tmp.put(rt, resourceBonus.getResource().get(rt));
						Resource tmpResource = Resource.of(tmp);
						familyMember.getPlayer().addResource(tmpResource);
					}
				}
			}
		}
	}
	
	@Override
	public void apply(Player player, GameView game) {
		player.addResource(game.checkResourceExcommunication(resourceBonus));
	}
}
