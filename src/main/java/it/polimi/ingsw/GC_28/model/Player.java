package it.polimi.ingsw.GC_28.model;

import java.io.PrintStream;
import java.net.Socket;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;

public class Player {
	private String name;
	private PlayerColor color;
	private PlayerBoard board;
	private FamilyMember[] familyMembers = new FamilyMember[4];
	private Socket socket ;
	transient private PrintStream ps;
	
	public Player(String name, PlayerColor color){ //Used for local test. KEEP IT!
		this.name = name;
		this.color = color;
	}
	
	public Player(String name, PlayerColor color, Socket s){
		this.name = name;
		this.color = color;
		this.socket = s;
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

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public String displayFamilyMembers(){
		StringBuilder s = new StringBuilder();
		s.append("Family Members:\n");
		for(FamilyMember f : familyMembers){
			s.append("Color: " + f.getDiceColor().name() + "  ");
			s.append("Value: " + f.getValue() + "\n");
		}
		return s.toString();
	}
	
}
