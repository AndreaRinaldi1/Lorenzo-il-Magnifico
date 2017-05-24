package it.polimi.ingsw.GC_28.model;


import java.util.ArrayList;
import java.util.Scanner;


import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;

public class Game {
	private GameBoard gameBoard;
	private static ArrayList<Player> players; //it's static because is the only way i can access to it from another class in a useful way
	private int currentEra;
	Scanner input = new Scanner(System.in);
	
	public Game(){
		//lasciare costruttore vuoto per prove
	}
	
	public static void main(String[] args) {
		ProvaSetUp.prova();
		Game g1 = new Game(BoardsInitializer.gameBoard, ProvaSetUp.getPlayer());
		System.out.println(g1.getGameBoard().display());
		System.out.println("era:" + g1.getGameBoard().getTowers().get(CardType.TERRITORY).getCells()[0].getCard().getEra());
		ProvaSetUp.prova2();
		System.out.println(g1.getGameBoard().display());
		System.out.println("era:" + g1.getGameBoard().getTowers().get(CardType.TERRITORY).getCells()[0].getCard().getEra());
		ProvaSetUp.prova2();
		System.out.println(g1.getGameBoard().display());
		System.out.println("era:" + g1.getGameBoard().getTowers().get(CardType.TERRITORY).getCells()[0].getCard().getEra());
	}
	
	
	public Game(GameBoard gameBoard, ArrayList<Player> players2) {
		this.gameBoard = gameBoard;
		players = players2;
	}

	public int getCurrentEra() {
		return currentEra;
	}

	public void setCurrentEra(int currentEra) {
		this.currentEra = currentEra;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public static ArrayList<Player> getPlayers() {
		return players;
	}
	
	public static void setPlayers(ArrayList<Player> p){
		players = p;
	}
	
	public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges){
		ArrayList<Character> choices = new ArrayList<Character>();
		int counter = 0;
		while(counter < numberOfCouncilPrivileges){
			Character c = askPrivilege();
			if(!(choices.contains(c))){
				choices.add(c);
				counter++;
			}
			else{
				System.out.println("Not valid choice"); 
			}
		}
		return choices;
	}
	
	public Character askPrivilege(){
		System.out.println("Which council privilege do you want?");
		for(Character key : CouncilPrivilege.instance().getOptions().keySet()){
			System.out.println("Type " + key + " if you want");
			System.out.println(CouncilPrivilege.instance().getOptions().get(key).toString());
		}
		Character c = (Character) input.nextLine().charAt(0);
		while(!(CouncilPrivilege.instance().getOptions().containsKey(c))){
			System.out.println("Not valid input!");
			System.out.println("Which council privilege do you want?");
			c = (Character) input.nextLine().charAt(0);
		}
		return c;
	} 
	
	
	public Resource askAlternativeDiscount(Resource discount1, Resource discount2){
		System.out.println("Which of the two following discounts do you want to apply? [1/2]");
		System.out.println(discount1.toString());
		System.out.println(discount2.toString());
		int choice;
		while(true){
			if(input.hasNextInt()){
				choice = input.nextInt();
				if(choice == 1){
					return discount1;
				}
				else if(choice == 2){
					return discount2;
				}
				else{
					System.out.println("Not valid input!");
					System.out.println("Which of the two discounts do you want to apply? [1/2]");
					input.nextLine();
					continue;
				}
			}
			else{
				input.nextLine();
				System.out.println("Not valid input!");
				System.out.println("Which of the two discounts do you want to apply? [1/2]");
			}
			
		}
		
	}
	
	public int askAlternativeExchange(Resource firstCost, Resource firstBonus, Resource secondCost, Resource secondBonus){
		System.out.println("Which of the following exchanges do you want to apply? [1/2]");
		System.out.println("First Possibility Cost");
		System.out.println(firstCost.toString());
		System.out.println("First Possibility Bonus");
		System.out.println(firstBonus.toString());
		System.out.println("Second Possibility Cost");
		System.out.println(secondCost.toString());
		System.out.println("Second Possibility Bonus");
		System.out.println(secondBonus.toString());
		int choice;
		while(true){
			if(input.hasNextInt()){
				choice = input.nextInt();
				if(choice == 1 || choice == 2){
					return choice;
				}
				else{
					System.out.println("Not valid input!");
					System.out.println("Which of the two discounts do you want to apply? [1/2]");
					input.nextLine();
					continue;
				}
			}
			else{
				input.nextLine();
				System.out.println("Not valid input!");
				System.out.println("Which of the two discounts do you want to apply? [1/2]");
			}
			
		}
	}
	
}
