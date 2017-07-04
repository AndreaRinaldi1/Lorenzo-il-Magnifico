package it.polimi.ingsw.GC_28.spaces;

import it.polimi.ingsw.GC_28.cards.Building;
import it.polimi.ingsw.GC_28.cards.Territory;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.view.GameView;
/**
 * This class represent production and harvest space of gameboard.
 * @author andrearinaldi,nicoloscipione
 * @version 1.0 , 03/07/2017
 *
 */
public class ProdHarvSpace extends Space{
	private FamilyMember firstPlayer;
	private boolean secondarySpace;
	private ProdHarvType type;
	
	public ProdHarvSpace(boolean free, int actionValue) {
		super(free, actionValue);
	}
	
	/**
	 * This method return the player's family member which is in the single harvest or production space.
	 * @return FamilyMember
	 */
	public FamilyMember getFirstPlayer() {
		return firstPlayer;
	}
	
	/**
	 * This method check if is possible to go to the larger space
	 * @return boolean.(true if is available, false otherwise)  
	 */
	public boolean isSecondarySpace() {
		return secondarySpace;
	}
	/**
	 * This method set larger space availability
	 * @param secondarySpace
	 */
	public void setSecondarySpace(boolean secondarySpace) {
		this.secondarySpace = secondarySpace;
	}
	
	/**
	 * This method free the first space
	 */
	public void freeFirstPlayer(){
		this.firstPlayer = null;
	}
	
	/**
	 * This method get the specific type of the space(production or harvest type).
	 * @return ProdHarvType. The specific type of the space
	 */
	public ProdHarvType getType() {
		return type;
	}
	
	/**
	 * This method set the specific type of the space.
	 * @param type. ProdHarvType, specific type to set.
	 */
	public void setType(ProdHarvType type) {
		this.type = type;
	}
	/**
	 * This method add the family member to the right space. If the first space is free
	 * it will set the family member to that one, otherwise it will add the family member to the larger space
	 */
	@Override
	public void addPlayer(FamilyMember player){
		if(this.isFree()){
			this.firstPlayer = player;
			this.setFree(false);
		}
		else{
			if(secondarySpace){
				this.getPlayer().add(player);		
			}
		}
			
	}	
	/**
	 * This method apply the bonus of the specific type to the player, which family member is in this space.
	 */
	@Override
	public void applyBonus(GameView game, FamilyMember familyMember){
		if(type == ProdHarvType.HARVEST){
			for(Territory territory : familyMember.getPlayer().getBoard().getTerritories()){
				territory.getPermanentEffect().apply(familyMember, game);
			}
			familyMember.getPlayer().getBoard().getBonusTile().getHarvestEffect().apply(familyMember, game);
		}
		else if(type == ProdHarvType.PRODUCTION){
			for(Building building : familyMember.getPlayer().getBoard().getBuildings()){
				building.getPermanentEffect().apply(familyMember, game);
			}
			familyMember.getPlayer().getBoard().getBonusTile().getProductionEffect().apply(familyMember, game);
		}
	}
		
}
