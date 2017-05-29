package it.polimi.ingsw.GC_28.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import it.polimi.ingsw.GC_28.core.SpaceAction;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.spaces.Space;


public class Game implements Runnable {
	private GameBoard gameBoard;

	Scanner input = new Scanner(System.in);
	private Player currentPlayer;
	boolean modifiedWithServants = false;
	private int currentEra  = 1;
	private int currentPeriod = 1;
	private List<Player> players = new ArrayList<>();
	
	public Game(){
		
	}
	
	
	@Override
	public void run(){
		for(; currentEra <= 3; currentEra++){
			for(; currentPeriod <= 2; currentPeriod++){
				for(int round = 1; round <= 4; round++){
					for(int turn = 0; turn < players.size(); turn++){
						try {
							play();
						} catch (IOException e) {
							e.printStackTrace();
						}
						currentPlayer = players.get((turn+1));
						System.out.println(currentPlayer.getName());
					}
				}
			}
			
		}
	}

	public void play() throws IOException{
		currentPlayer.getOut().println("Which move do you want to undertake? [takeCard / goToSpace / skip]");
		currentPlayer.getOut().flush();
		
			String line = currentPlayer.getIn().readLine();
			do{
				if(line.equalsIgnoreCase("takeCard")){
					if(askCard(null)){
						return;
					}
				}
				else if(line.equalsIgnoreCase("goToSpace")){
					if(goToSpace(null)){
						return;
					}
					currentPlayer.getOut().println("bravo");
					currentPlayer.getOut().flush();
					return;
				}
				else if(line.equalsIgnoreCase("skip")){
					return;
				}
				else{
					System.out.println("Not valid input");
					line = input.nextLine();
				}
			}while(true);
		
		
	}
	


	public int getCurrentPeriod() {
		return currentPeriod;
	}
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player player){
		this.currentPlayer = player;
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
	
	public Space askWhichSpace(){
		do{
			System.out.println("Enter which space you want to go into [coinSpace / servantSpace / mixedSpace / privilegesSpace / councilPalace / productionSpace / harvestSpace]");
			if(input.hasNextInt()){
				System.out.println("Not valid input!");
				input.nextLine();
			}
			else{
				String chosenSpace = input.nextLine();
				if(chosenSpace.equalsIgnoreCase("coinspace")){
					return gameBoard.getCoinSpace();
				}
				if(chosenSpace.equalsIgnoreCase("servantspace")){
					return gameBoard.getServantSpace();
				}
				if(chosenSpace.equalsIgnoreCase("mixedspace")){
					return gameBoard.getMixedSpace();
				}
				if(chosenSpace.equalsIgnoreCase("privilegesspace")){
					return gameBoard.getPrivilegesSpace();
				}
				if(chosenSpace.equalsIgnoreCase("councilpalace")){
					return gameBoard.getCouncilPalace();
				}
				if(chosenSpace.equalsIgnoreCase("productionspace")){
					return gameBoard.getProductionSpace();
				}
				if(chosenSpace.equalsIgnoreCase("harvestspace")){
					return gameBoard.getHarvestSpace();
				}
				else{
					System.out.println("Not valid input!");
				}
			}
		}while(true);
	}
	
	public boolean goToSpace(GoToHPEffect throughEffect){
		FamilyMember familyMember;
		int incrementThroughServants;
		SpaceAction spaceAction = new SpaceAction(this);
		Space chosenSpace;
		
		if(!(throughEffect == null)){
			familyMember = new FamilyMember(currentPlayer, false, null); //familyMember fittizio
			familyMember.setValue(throughEffect.getActionValue());
			if(throughEffect.isHarvest()){
				chosenSpace = gameBoard.getHarvestSpace();
			}
			else{
				chosenSpace = gameBoard.getProductionSpace();
			}
		}
		else{
			chosenSpace = askWhichSpace();
			familyMember = askFamilyMember();
		}
		
		incrementThroughServants = askForServantsIncrement();
		familyMember.modifyValue(incrementThroughServants);
		EnumMap<ResourceType, Integer> decrement = new EnumMap<>(ResourceType.class);
		decrement.put(ResourceType.SERVANT, incrementThroughServants);
		Resource res = Resource.of(decrement);

		if(spaceAction.isApplicable(familyMember, chosenSpace, throughEffect)){
			spaceAction.apply(familyMember, chosenSpace, throughEffect);
			if(modifiedWithServants){
				familyMember.modifyValue((-1)*(incrementThroughServants));
			}
			return true;
		}
		else{
			if(modifiedWithServants){
				familyMember.modifyValue((-1)*(incrementThroughServants));
				currentPlayer.getBoard().getResources().modifyResource(res, true);
			}
			System.out.println("Not valid action!");
			return false;
		}
		
	}
	
	public boolean askCard(TakeCardEffect throughEffect){ //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		int incrementThroughServants;
		TakeCardAction takeCardAction = new TakeCardAction(this, gameBoard);
		
		if(!(throughEffect == null)){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType() == null){
				System.out.println("You can take another card of any type");
			}
			else{
				System.out.println("You can take a card of type: " + throughEffect.getCardType().name());
			}
		}
		
		String name = askCardName();
		
		if(throughEffect == null){ //se viene da mossa gli chiedo quale fm vuole usare
			familyMember = askFamilyMember();
		}
		else{
			familyMember = new FamilyMember(currentPlayer, false, null); //altrimenti uno fittizio
			familyMember.setValue(throughEffect.getActionValue());
		}
		incrementThroughServants = askForServantsIncrement();
		familyMember.modifyValue(incrementThroughServants);
		EnumMap<ResourceType, Integer> decrement = new EnumMap<>(ResourceType.class);
		decrement.put(ResourceType.SERVANT, incrementThroughServants);
		Resource res = Resource.of(decrement);
		
		if(takeCardAction.isApplicable(name, familyMember, throughEffect)){
			takeCardAction.apply(name, familyMember, throughEffect);
			if(modifiedWithServants){
				familyMember.modifyValue((-1)*(incrementThroughServants));
			}
			return true;
		}
		else{
			if(modifiedWithServants){
				familyMember.modifyValue((-1)*(incrementThroughServants));
				currentPlayer.getBoard().getResources().modifyResource(res, true);
			}
			System.out.println("Not valid action!");
			return false;
		}
	}
	
	public String askCardName(){
		do{
			System.out.println("Enter the name of the card you would like to take: ");
			if(input.hasNextInt()){
				System.out.println("Not valid input!");
				input.nextLine();
			}
			else{
				return input.nextLine();
			}
		}while(true);
		
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
						if(familyMember.getDiceColor().equals(color)){
							if(!familyMember.isUsed()){
								return familyMember;
							}
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
				input.nextLine();
				continue;
			}
			else if(input.hasNextLine()){
				String choice = input.nextLine();
				if(choice.equals("y")){
					System.out.println("How many servants would you like to pay?");
					if(input.hasNextInt()){
						int increment = input.nextInt();
						int numberOfServants = currentPlayer.getBoard().getResources().getResource().get(ResourceType.SERVANT);
						if(increment <= numberOfServants){
							currentPlayer.getBoard().getResources().getResource().put(ResourceType.SERVANT, (numberOfServants-increment));
							modifiedWithServants = true;
							input.nextLine();
							return increment;
						}
						else{
							System.out.println("You don't have so many servants!");
							input.nextLine();
						}
					}
					else{
						System.out.println("Not valid inupt!");
					}
				}
				else if (choice.equals("n")){
					return 0;
				}
				else{
					System.out.println("Not valid inupt!");
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
	

	
	public Resource askAlternative(Resource discount1, Resource discount2, String type){
		System.out.println("Which of the two following " + type + " do you want to apply? [1/2]");
		System.out.println(discount1.toString());
		System.out.println(discount2.toString());
		int choice;
		while(true){
			if(input.hasNextInt()){
				choice = input.nextInt();
				if(choice == 1){
					input.nextLine();
					return discount1;
				}
				else if(choice == 2){
					input.nextLine();
					return discount2;
				}
				else{
					System.out.println("Not valid input!");
					System.out.println("Which of the two " + type + " do you want to apply? [1/2]");
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
