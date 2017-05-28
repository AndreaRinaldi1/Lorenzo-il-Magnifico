package it.polimi.ingsw.GC_28.model;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;


import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;

public class Game {
	private GameBoard gameBoard;
	private List<Player> players; //it's static because is the only way i can access to it from another class in a useful way
	
	Scanner input = new Scanner(System.in);
	private Player currentPlayer;
	boolean modifiedWithServants = false;
	private BoardSetup bs;
	private int currentEra  = 1;
	private int currentPeriod = 1;
	
	public Game(){
		//lasciare costruttore vuoto per prove
	}
	

	
	
	public void start(){
		
	}


	public int getCurrentPeriod() {
		return currentPeriod;
	}

	public void setPeriod(int period) {
		this.currentPeriod = period;
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
	
	public void setGameBoard(GameBoard gb){
		this.gameBoard = gb;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<Player> p){
		players = p;
	}
	
	
	public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
		System.out.println("Which council privilege do you want?");
		for(Character key : CouncilPrivilege.instance().getOptions().keySet()){
			System.out.println("Type " + key + " if you want:");
			System.out.println(CouncilPrivilege.instance().getOptions().get(key).toString());
		}
		ArrayList<Character> choices = new ArrayList<Character>();
		int counter = 0;
		while(counter < numberOfCouncilPrivileges){
			System.out.println("Choose your council privilege #" + (counter+1));
			Character c = askPrivilege();
			if(!(choices.contains(c))){
				choices.add(c);
				counter++;
			}
			else{
				if(different){
					System.out.println("Not valid choice"); 
				}
				else{
					choices.add(c);
					counter++;
				}
			}
		}
		return choices;
	}
	
	
	
	public boolean askCard(TakeCardEffect throughEffect){ //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		int incrementThroughServants;
		TakeCardAction takeCardAction = new TakeCardAction(this, gameBoard);
		
		if(!(throughEffect.equals(null))){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType().equals(null)){
				System.out.println("You can take another card of any type");
			}
			else{
				System.out.println("You can take a card of type: " + throughEffect.getCardType().name());
			}
		}
		
		String name = askCardName();
		
		if((throughEffect.equals(null))){ //se viene da mossa gli chiedo quale fm vuole usare
			familyMember = askFamilyMember();
		}
		else{
			familyMember = new FamilyMember(currentPlayer, false, null); //altrimenti uno fittizio
			familyMember.setValue(throughEffect.getActionValue());
		}
		incrementThroughServants = askForServantsIncrement();
		familyMember.modifyValue(incrementThroughServants);
		
		if(takeCardAction.isApplicable(name, familyMember, throughEffect)){
			takeCardAction.apply(name, familyMember, throughEffect);
			return true;
		}
		else{
			if(modifiedWithServants){
				familyMember.modifyValue((-1)*(incrementThroughServants));
				EnumMap<ResourceType, Integer> decrement = new EnumMap<>(ResourceType.class);
				decrement.put(ResourceType.SERVANT, incrementThroughServants);
				Resource res = Resource.of(decrement);
				currentPlayer.getBoard().getResources().modifyResource(res, true);
			}
			System.out.println("Not valid action!");
			return false;
		}
	}
	
	public String askCardName(){
		System.out.println("Enter the name of the card you would like to take: ");
		while(input.hasNextInt()){
			System.out.println("Not valid input!");
		}
		return input.nextLine();
	}
	
	/*public CardType askCardType(){
		do{
			System.out.println("Enter CardType [ Territory / Building / Character / Venture ]");
			String choice = input.nextLine();
			for(CardType ct : CardType.values()){
				if(choice.toUpperCase().equals(ct.name()))
					return ct;
			}
			System.out.println("Not valid input!");
		}while(true);
	}*/
	
	public FamilyMember askFamilyMember(){
		boolean x = true;
		do{
			System.out.println("Enter which familyMember you would like to use: [ Orange / Black / White / Neutral ]");
			String choice = input.nextLine();
			for(DiceColor color : DiceColor.values()){
				if(choice.toUpperCase().equals(color.name())){
					for(FamilyMember familyMember : currentPlayer.getFamilyMembers()){
						if(!familyMember.isUsed()){
							return familyMember;
						}
					}
					System.out.println("The specified family member has already been used!");
					x = false;
					continue;
				}
			}
			if(x){
				System.out.println("Not valid input!");
			}
		}while(true);
	}
	
	public int askForServantsIncrement(){
		while(true){
			System.out.println("Would you like to pay servants in order to increment the family member action value? [y/n]");
			if(input.hasNextInt()){
				System.out.println("Not valid input!");
				continue;
			}
			else if(input.hasNextLine()){
				if(input.nextLine().equals("y")){
					System.out.println("How many servants would you like to pay?");
					if(input.hasNextInt()){
						int increment = input.nextInt();
						int numberOfServants = currentPlayer.getBoard().getResources().getResource().get(ResourceType.SERVANT);
						if(increment <= numberOfServants){
							currentPlayer.getBoard().getResources().getResource().put(ResourceType.SERVANT, (numberOfServants-increment));
							modifiedWithServants = true;
							return increment;
						}
						else{
							System.out.println("You don't have so many servants!");
						}
					}
					else{
						System.out.println("Not valid inupt!");
					}
				}
				else{
					return 0;
				}
			}
		}
	}
	
	public Character askPrivilege(){
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
