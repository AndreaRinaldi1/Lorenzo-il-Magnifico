package it.polimi.ingsw.GC_28.effects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class PrivilegesEffect extends Effect{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public void apply(FamilyMember familyMember, ClientWriter writer) {
		System.out.println("apply di PrivilegesEffect");
		ArrayList<Character> choices = writer.askPrivilege(numberOfCouncilPrivileges, different);
		for(int i = 0; i < choices.size(); i++){
			familyMember.getPlayer().addResource(writer.checkResourceExcommunication(writer.getCouncilPrivilege().getOptions().get(choices.get(i)),familyMember.getPlayer()));
		}
	}
	
	@Override
	public void apply(Player player, ClientWriter writer) {
		System.out.println("Ludovico Gonzaga Effect");
		ArrayList<Character> choices = writer.askPrivilege(numberOfCouncilPrivileges, different);
		for(int i = 0; i<choices.size(); i++ ){
			player.addResource(writer.checkResourceExcommunication(CouncilPrivilege.instance().getOptions().get(choices.get(i)),player));
		}
	}
	
	
	
	
}
