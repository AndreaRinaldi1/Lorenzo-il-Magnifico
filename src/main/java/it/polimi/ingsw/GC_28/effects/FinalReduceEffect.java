package it.polimi.ingsw.GC_28.effects;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.cards.Venture;

public class FinalReduceEffect  extends Effect{
	private CardType cardType;
	private Resource resourceCost;
	private Resource resourceBonus;
	
	
	public Resource multiplyResource(int times){
		EnumMap<ResourceType, Integer> resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : resourceBonus.getResource().keySet()){
			resource.put(resType, resourceBonus.getResource().get(resType) * times);
		}
		Resource amount = Resource.of(resource);
		return amount;
	}
	
	public void apply(Player player, Game game){
		int times = 0;
		for(ResourceType resType : resourceCost.getResource().keySet()){
			if(!(resourceCost.getResource().get(resType).equals(0))){
				switch(cardType){
				case BUILDING:
					for(Building b : player.getBoard().getBuildings()){
						times +=  b.getCost().getResource().get(resType) / resourceCost.getResource().get(resType);
					}
					break;
				case CHARACTER:
					for(Character c : player.getBoard().getCharacters()){
						times +=  c.getCost().getResource().get(resType) / resourceCost.getResource().get(resType);
					}
					break;
				case TERRITORY:
					for(Territory t : player.getBoard().getTerritories()){
						times +=  t.getCost().getResource().get(resType) / resourceCost.getResource().get(resType);
					}
					break;
				case VENTURE:
					for(Venture v : player.getBoard().getVentures()){
						times +=  v.getCost().getResource().get(resType) / resourceCost.getResource().get(resType);
					}
					break;
				default:
					break;
				
				}
			}
		}
		player.getBoard().addResource(multiplyResource(times));
	}
	
	

}
