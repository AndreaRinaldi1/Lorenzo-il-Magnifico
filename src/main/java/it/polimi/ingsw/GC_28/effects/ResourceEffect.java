package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.FamilyMember;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

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

	@Override
	public void apply(FamilyMember familyMember, GameView game) {
		System.out.println("apply di ResourceEffect");

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
