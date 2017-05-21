package it.polimi.ingsw.GC_28.cards;

import java.awt.Color;

public class Card {
	private String name;
	private int IDNumber;
	private int era;
	private Color color;
	private Resource cost;
	
	public Card(){};
	
	public Card(String name, int IDNumber, int era){
		this.setName(name);
		this.setIDNumber(IDNumber);
		this.setEra(era);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setIDNumber(int IDNumber) {
		this.IDNumber = IDNumber;
	}

	public void setEra(int era) {
		this.era = era;
	}

	public void setColor(Color color) {
		this.color = color;
	}


	public void setCost(Resource cost) {
		this.cost = cost;
	}

	public int getIDNumber() {
		return IDNumber;
	}

	public int getEra() {
		return era;
	}

	public Color getColor() {
		return color;
	}


	public Resource getCost() {
		return cost;
	}
	@Override
	public String toString(){
		return "Name: " + this.name + "\n" +
				"Color: " + this.color.toString() + "\n" + 
				this.cost.toString();
	}
}
