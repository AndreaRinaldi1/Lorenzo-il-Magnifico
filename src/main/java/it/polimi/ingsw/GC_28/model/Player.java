package it.polimi.ingsw.GC_28.model;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;

public class Player {
	private String name;
	private PlayerColor color;
	private PlayerBoard board;
	private FamilyMember familyMembers[] = new FamilyMember[4];
	

	public Player(String name, PlayerColor color){
		this.name = name;
		this.color = color;
	}
		
	public String getName() {
		return name;
	}

	public PlayerBoard getBoard() {
		return this.board;
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
	
	public String displayFamilyMembers(){
		StringBuilder s = new StringBuilder();
		System.out.println("Family Members:");
		for(FamilyMember f : familyMembers){
			s.append("Color: " + f.getDiceColor().name() + "  ");
			s.append("Value: " + f.getValue() + "\n");
		}
		return s.toString();
	}
	
}
