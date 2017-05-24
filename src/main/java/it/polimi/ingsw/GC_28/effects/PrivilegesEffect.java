package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.Game;

public class PrivilegesEffect extends Effect{
	private int numberOfCouncilPrivileges;
	public final EffectType type = EffectType.PRIVILEGESEFFECT;

	public PrivilegesEffect(){
		super();
	}

	public int getNumberOfCouncilPrivileges() {
		return numberOfCouncilPrivileges;
	}

	public void setNumberOfCouncilPrivileges(int numberOfCouncilPrivileges) {
		this.numberOfCouncilPrivileges = numberOfCouncilPrivileges;
	}
	
	@Override
	public void apply(FamilyMember familyMember, GameBoard gameBoard, Game game) {
		System.out.println("apply di PrivilegesEffect");
		ArrayList<Character> choices = game.askPrivilege(numberOfCouncilPrivileges);
		for(int i = 0; i < choices.size(); i++){
			familyMember.getPlayer().getBoard().addResource(CouncilPrivilege.instance().getOptions().get(choices.get(i)));
		}
	}
}
