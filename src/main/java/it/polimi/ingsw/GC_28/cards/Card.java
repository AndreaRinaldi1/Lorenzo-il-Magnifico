package it.polimi.ingsw.GC_28.cards;

import java.awt.Color;

import it.polimi.ingsw.GC_28.components.Resource;

public class Card {
	private String name;
	private int idNumber;
	private int era;
	private Color color;
	private Resource cost;
	
	public Card(String name, int idNumber, int era){
		this.setName(name);
		this.setIDNumber(idNumber);
		this.setEra(era);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setIDNumber(int iDNumber) {
		this.idNumber = iDNumber;
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
		return idNumber;
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
		return "Name: " + this.name + "\n";
	}
}
