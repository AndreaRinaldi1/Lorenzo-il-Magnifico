package it.polimi.ingsw.GC_28.effects;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;


public class ExchangeEffect extends Effect{
	private Resource firstCost;
	private Resource secondCost;
	private boolean alternative;
	private Resource firstBonus;
	private Resource secondBonus;
	private Resource privilegeCost;
	private PrivilegesEffect privilegeBonus;
	public final EffectType type = EffectType.EXCHANGEEFFECT;

	
	public ExchangeEffect(){
		super();
	}
	
	
	public Resource getFirstCost() {
		return firstCost;
	}

	public void setFirstCost(Resource firstCost) {
		this.firstCost = firstCost;
	}

	public Resource getSecondCost() {
		return secondCost;
	}

	public void setSecondCost(Resource secondCost) {
		this.secondCost = secondCost;
	}

	public boolean isAlternative() {
		return alternative;
	}

	public void setAlternative(boolean alternative) {
		this.alternative = alternative;
	}

	public Resource getFirstBonus() {
		return firstBonus;
	}

	public void setFirstBonus(Resource firstBonus) {
		this.firstBonus = firstBonus;
	}

	public Resource getSecondBonus() {
		return secondBonus;
	}

	public void setSecondBonus(Resource secondBonus) {
		this.secondBonus = secondBonus;
	}

	public Resource getPrivilegeCost() {
		return privilegeCost;
	}

	public void setPrivilegeCost(Resource privilegeCost) {
		this.privilegeCost = privilegeCost;
	}


	public PrivilegesEffect getPrivilegeBonus() {
		return privilegeBonus;
	}


	public void setPrivilegeBonus(PrivilegesEffect privilegeBonus) {
		this.privilegeBonus = privilegeBonus;
	}


	@Override
	public void apply(FamilyMember familyMember, Game game){
		System.out.println("apply di ExchangeEffect");
		if(game.askPermission()){
			
			if(!this.alternative){
				if(this.privilegeBonus != null){
					if(familyMember.getPlayer().getBoard().getResources().greaterOrEqual(privilegeCost)){
						familyMember.getPlayer().reduceResources(privilegeCost);
						privilegeBonus.apply(familyMember, game);
					}
				}
				else{
					if(familyMember.getPlayer().getBoard().getResources().greaterOrEqual(firstCost)){
						familyMember.getPlayer().reduceResources(firstCost);
						familyMember.getPlayer().addResource(game.checkResourceExcommunication(firstBonus));
					}
				}
			}
			else{
				while(true){
					if(game.askAlternativeExchange(firstCost, firstBonus, secondCost, secondBonus) == 1){
						if(familyMember.getPlayer().getBoard().getResources().greaterOrEqual(firstCost)){
							familyMember.getPlayer().reduceResources(firstCost);
							familyMember.getPlayer().addResource(game.checkResourceExcommunication(firstBonus));
							return;
						}
					}
					else{
						if(familyMember.getPlayer().getBoard().getResources().greaterOrEqual(secondCost)){
							familyMember.getPlayer().reduceResources(secondCost);
							familyMember.getPlayer().addResource(game.checkResourceExcommunication(secondBonus));
							return;
						}
					}
					game.getHandlers().get(familyMember.getPlayer()).getIn().nextLine();
					game.getHandlers().get(familyMember.getPlayer()).getOut().println("Are you unable to pay for anything and you want to skip? [y/n]");
					game.getHandlers().get(familyMember.getPlayer()).getOut().flush();
					if (game.getHandlers().get(familyMember.getPlayer()).getIn().nextLine().equals("y")){
						return;
					}
				}
				
			}
		}
	}
}
