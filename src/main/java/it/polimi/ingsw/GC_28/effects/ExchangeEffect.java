package it.polimi.ingsw.GC_28.effects;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.Game;


public class ExchangeEffect extends Effect{
	private Resource firstCost;
	private Resource secondCost;
	private boolean alternative;
	private Resource firstBonus;
	private Resource secondBonus;
	private Resource privilegeCost;
	private CouncilPrivilege privilegeBonus;
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

	public CouncilPrivilege getPrivilegeBonus() {
		return privilegeBonus;
	}

	public void setPrivilegeBonus(CouncilPrivilege privilegeBonus) {
		this.privilegeBonus = privilegeBonus;
	}

	@Override
	public void apply(PlayerBoard playerBoard, GameBoard gameBoard, Game game){
		System.out.println("apply di ExchangeEffect");
		if(this.alternative == false){
			if(this.privilegeBonus != null){
				playerBoard.reduceResources(privilegeCost);
				System.out.println(playerBoard.getResources().toString());
				playerBoard.addResource(game.askPrivilege());
			}
			else{
				playerBoard.reduceResources(firstCost);
				System.out.println(playerBoard.getResources().toString());
				playerBoard.addResource(firstBonus);
			}
		}
		else{
			if(game.askAlternativeExchange(firstCost, firstBonus, secondCost, secondBonus) == 1){
				playerBoard.reduceResources(firstCost);
				System.out.println(playerBoard.getResources().toString());
				playerBoard.addResource(firstBonus);
			}
			else{
				playerBoard.reduceResources(secondCost);
				System.out.println(playerBoard.getResources().toString());

				playerBoard.addResource(secondBonus);
			}
		}
	}
}
