package it.polimi.ingsw.GC_28.core;

import java.io.IOException;

import de.vandermeer.skb.interfaces.coin.Coin;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.Space;
import it.polimi.ingsw.GC_28.spaces.SpaceType;

public class SpaceAction extends Action{
	
	private SpaceController spaceController;
	private FamilyMember familyMember;
	private Space space;
	private GoToHPEffect throughEffect;
	private SpaceType spaceName;
	private GameModel gameModel;
	public SpaceAction(){
		/*this.gameModel = gameModel;
		this.game = game;
		spaceController = new SpaceController(gameModel);*/

	}

	public boolean isApplicable(){
		System.out.println(familyMember.getPlayer());
		spaceController = new SpaceController(super.gameModel);
		this.gameModel = super.gameModel;
		space = chosenSpace(spaceName);
		return spaceController.check(familyMember, space, throughEffect);
	}
	
	public void setSpaceName(SpaceType spaceName) {
		this.spaceName = spaceName;
	}

	public void setFamilyMember(FamilyMember familyMember) {
		this.familyMember = familyMember;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public void setThroughEffect(GoToHPEffect throughEffect) {
		this.throughEffect = throughEffect;
	}
	
	public Space chosenSpace(SpaceType spaceName){
		System.out.println(spaceName);
		switch(spaceName){
		case COINSPACE:
			return gameModel.getGameBoard().getCoinSpace();
		case COUNCILPALACE:
			return gameModel.getGameBoard().getCouncilPalace();
		case HARVESTSPACE:
			return gameModel.getGameBoard().getHarvestSpace();
		case MIXEDSPACE:
			return gameModel.getGameBoard().getMixedSpace();
		case PRIVILEGESSPACE:
			return gameModel.getGameBoard().getPrivilegesSpace();
		case PRODUCTIONSPACE:
			return gameModel.getGameBoard().getProductionSpace();
		case SERVANTSPACE:
			return gameModel.getGameBoard().getServantSpace();
		default:
			return null;
		}
	}

	public void apply(){
		ProductionAndHarvestSpace prodHarv;
		
		if(space instanceof ProductionAndHarvestSpace){
			prodHarv = (ProductionAndHarvestSpace) space;
			for(Character character : familyMember.getPlayer().getBoard().getCharacters()){
				if(character.getPermanentEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) character.getPermanentEffect();
					if((eff.isHarvest() && prodHarv.isHarvest()) || (eff.isProduction() && !prodHarv.isHarvest())){
						familyMember.modifyValue(eff.getIncrement());
					}
				}
			}
			
			for(ExcommunicationTile t : familyMember.getPlayer().getExcommunicationTile()){ //guardo se tra le scomuniche ha incrementhpeffect
				if(t.getEffect() instanceof IncrementHPEffect){
					IncrementHPEffect eff = (IncrementHPEffect) t.getEffect();
					if((eff.isHarvest() && prodHarv.isHarvest()) || (eff.isProduction() && !prodHarv.isHarvest())){ //se production e ho prod o se harvest e ho har
						familyMember.modifyValue(eff.getIncrement()); // allora applico effetto
					}
				}		
			}
			
		}
		
		
		if(throughEffect == null){
			if(space instanceof ProductionAndHarvestSpace){
				prodHarv = (ProductionAndHarvestSpace) space;
				if(!prodHarv.isFree() && prodHarv.isSecondarySpace()){
					familyMember.modifyValue(-3);
				}
			}
			space.addPlayer(familyMember);	
		}
		space.applyBonus(game, familyMember);
		System.out.println("quasi fatto");
		familyMember.setUsed(true);
		System.out.println("family");
		
		game.getCurrentPlayer().setBoard(familyMember.getPlayer().getBoard());
		game.getCurrentPlayer().setFamilyMembers(familyMember.getPlayer().getFamilyMembers());
		
		
		gameModel.notifyObserver(new Message("Action completed successfully!", true));

	}
}
