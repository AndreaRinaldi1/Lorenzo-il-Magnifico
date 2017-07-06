package it.polimi.ingsw.GC_28.cards;

import java.awt.Color;

import it.polimi.ingsw.GC_28.components.Resource;

/**
 * This class allows to create a new card. Every card has a name, an ID number, an era, a resource cost
 * (that has to be paied to take the card), and a color that define the card's type. 
 * @author robertoturi
 * @version 1.0, 07/04/2017
 */


public class Card {
	private String name;
	private int idNumber;
	private int era;
	private Color color;
	private Resource cost;
	
	
	/**
	 * This constructor creates a card out of the name, the ID number and the era
	 * @param name of the card selected
	 * @param idNumber of the card selected
	 * @param era of the card selected
	 */
	public Card(String name, int idNumber, int era){
		this.setName(name);
		this.setIDNumber(idNumber);
		this.setEra(era);
	}
	
	/** 
	 * @return the name of the card
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @param name of the card
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param iDNumber of the card
	 */
	public void setIDNumber(int iDNumber) {
		this.idNumber = iDNumber;
	}
	
	/**
	 * @param era of the card
	 */
	public void setEra(int era) {
		this.era = era;
	}

	/**
	 * @param color of the card
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param cost of the card
	 */
	public void setCost(Resource cost) {
		this.cost = cost;
	}

	/**
	 * @return the IDNumber of the card  
	 */
	public int getIDNumber() {
		return idNumber;
	}

	/**
	 * @return the era of the card
	 */
	public int getEra() {
		return era;
	}

	/**
	 * @return the color of the card
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the resource cost of the card 
	 */
	public Resource getCost() {
		return cost;
	}
	
	/**
	 * It allows to describe the selected card with its name
	 */
	@Override
	public String toString(){
		return "Name: " + this.name + "\n";
	}
}
