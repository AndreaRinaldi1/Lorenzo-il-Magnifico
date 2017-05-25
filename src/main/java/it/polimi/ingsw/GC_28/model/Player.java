package it.polimi.ingsw.GC_28.model;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;

public class Player {
	private PlayerColor color;
	private PlayerBoard board;
	private FamilyMember familyMembers[] = new FamilyMember[4];
	

	public PlayerBoard getBoard() {
		return board;
	}
	
	

	public FamilyMember[] getFamilyMembers() {
		return familyMembers;
	}



	public void setFamilyMembers(FamilyMember[] familyMembers) {
		this.familyMembers = familyMembers;
	}



	public void setBoard(PlayerBoard board) {
		this.board = board;
	}

	public Player(PlayerColor color) {
		this.color = color;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}
	
	
	
}
