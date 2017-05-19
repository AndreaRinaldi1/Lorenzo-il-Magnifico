package cards;

import java.awt.Color;
import java.util.Scanner;

public class Card {
	private String name;
	private int IDNumber;
	private int era;
	private Color color;
	private CardType cardType;
	private Resource cost;
	
	public Card(String name, int IDNumber, int era, CardType cardType){
		this.setName(name);
		this.setIDNumber(IDNumber);
		this.setEra(era);
		this.setCardType(cardType);
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

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
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

	public CardType getCardType() {
		return cardType;
	}

	public Resource getCost() {
		return cost;
	}
	@Override
	public String toString(){
		return "Name: " + this.name + "\n" +
				"CardType: " + this.cardType.name() + "\n" +
				"Color: " + this.color.toString() + "\n" + 
				this.cost.toString();
	}
}
