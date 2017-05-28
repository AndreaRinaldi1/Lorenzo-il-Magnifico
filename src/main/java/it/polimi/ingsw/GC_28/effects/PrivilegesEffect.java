package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.Game;

public class PrivilegesEffect extends Effect{
	private int numberOfCouncilPrivileges;
	private boolean different;

	public final EffectType type = EffectType.PRIVILEGESEFFECT;

	public PrivilegesEffect(){
		super();
	}

	public boolean isDifferent() {
		return different;
	}

	public void setDifferent(boolean different) {
		this.different = different;
	}

	public int getNumberOfCouncilPrivileges() {
		return numberOfCouncilPrivileges;
	}

	public void setNumberOfCouncilPrivileges(int numberOfCouncilPrivileges) {
		this.numberOfCouncilPrivileges = numberOfCouncilPrivileges;
	}
	
	@Override
	public void apply(FamilyMember familyMember, Game game) {
		System.out.println("apply di PrivilegesEffect");
		ArrayList<Character> choices = game.askPrivilege(numberOfCouncilPrivileges, different);
		for(int i = 0; i < choices.size(); i++){
			familyMember.getPlayer().getBoard().addResource(CouncilPrivilege.instance().getOptions().get(choices.get(i)));
		}
	}
}
