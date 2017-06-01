package it.polimi.ingsw.GC_28.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;


public class Player {
	private String name;
	private PlayerColor color;
	private PlayerBoard board;
	private FamilyMember[] familyMembers = new FamilyMember[4];

	
	public Player(String name, PlayerColor color){ //Used for local test. KEEP IT!
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

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
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
