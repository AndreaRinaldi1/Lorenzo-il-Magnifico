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

	//Scanner currentPlayer.getIn() = new Scanner(System.in);
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
					}
				}
			}
			
		}
	}

	public void play() throws IOException{

		for(Player p: players){
			p.getOut().println(gameBoard.display());
			p.getOut().println("test");
			p.getOut().println(p.getBoard().display());
			for(int i = 0; i < 4; i++){
				p.getOut().println(p.getFamilyMembers()[i].toString());
			}
			p.getOut().flush();
		}

		do{
			currentPlayer.getOut().println("Which move do you want to undertake? [takeCard / goToSpace / skip]");
			currentPlayer.getOut().flush();	
			String line = currentPlayer.getIn().nextLine();
			if(line.equalsIgnoreCase("takeCard")){
				if(askCard(null)){
					return;
				}
			}
			else if(line.equalsIgnoreCase("goToSpace")){
				if(goToSpace(null)){
					return;
				}
			}
			else if(line.equalsIgnoreCase("skip")){
				return;
			}
			else{
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().flush();
				//line = currentPlayer.getIn().nextLine();
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
		currentPlayer.getOut().println("Which council privilege do you want?");
		currentPlayer.getOut().flush();
		for(Character key : CouncilPrivilege.instance().getOptions().keySet()){
			currentPlayer.getOut().println("Type " + key + " if you want:");
			currentPlayer.getOut().println(CouncilPrivilege.instance().getOptions().get(key).toString());
			currentPlayer.getOut().flush();
		}
		ArrayList<Character> choices = new ArrayList<Character>();
		int counter = 0;
		while(counter < numberOfCouncilPrivileges){
			currentPlayer.getOut().println("Choose your council privilege #" + (counter+1));
			currentPlayer.getOut().flush();
			Character c = askPrivilege();
			if(!(choices.contains(c))){
				choices.add(c);
				counter++;
			}
			else{
				if(different){
					currentPlayer.getOut().println("Not valid choice"); 
					currentPlayer.getOut().flush();
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
			currentPlayer.getOut().println("Enter which space you want to go into [coinSpace / servantSpace / mixedSpace / privilegesSpace / councilPalace / productionSpace / harvestSpace]");
			currentPlayer.getOut().flush();
			if(currentPlayer.getIn().hasNextInt()){
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().flush();
				currentPlayer.getIn().nextLine();
			}
			else{
				String chosenSpace = currentPlayer.getIn().nextLine();
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
					currentPlayer.getOut().println("Not valid input!");
					currentPlayer.getOut().flush();
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
			currentPlayer.getOut().println("Not valid action!");
			currentPlayer.getOut().flush();
			return false;
		}
		
	}
	
	public boolean askCard(TakeCardEffect throughEffect){ //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		int incrementThroughServants;
		TakeCardAction takeCardAction = new TakeCardAction(this, gameBoard);
		
		if(!(throughEffect == null)){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType() == null){
				currentPlayer.getOut().println("You can take another card of any type");
			}
			else{
				currentPlayer.getOut().println("You can take a card of type: " + throughEffect.getCardType().name());
			}
			currentPlayer.getOut().flush();
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
			currentPlayer.getOut().println("Not valid action!");
			currentPlayer.getOut().flush();
			return false;
		}
	}
	
	public String askCardName(){
		do{
			currentPlayer.getOut().println("Enter the name of the card you would like to take: ");
			currentPlayer.getOut().flush();
			if(currentPlayer.getIn().hasNextInt()){
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().flush();
				currentPlayer.getIn().nextLine();
			}
			else{
				return currentPlayer.getIn().nextLine();
			}
		}while(true);
		
	}
	
	/*public CardType askCardType(){
		do{
			System.out.println("Enter CardType [ Territory / Building / Character / Venture ]");
			String choice = currentPlayer.getIn().nextLine();
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
			currentPlayer.getOut().println("Enter which familyMember you would like to use: [ Orange / Black / White / Neutral ]");
			currentPlayer.getOut().flush();
			String choice = currentPlayer.getIn().nextLine();
			for(DiceColor color : DiceColor.values()){
				if(choice.toUpperCase().equals(color.name())){
					for(FamilyMember familyMember : currentPlayer.getFamilyMembers()){
						if(familyMember.getDiceColor().equals(color)){
							if(!familyMember.isUsed()){
								return familyMember;
							}
						}
					}
					currentPlayer.getOut().println("The specified family member has already been used!");
					currentPlayer.getOut().flush();
					x = false;
					continue;
				}
			}
			if(x){
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().flush();
			}
		}while(true);
	}
	
	public int askForServantsIncrement(){
		while(true){
			currentPlayer.getOut().println("Would you like to pay servants in order to increment the family member action value? [y/n]");
			currentPlayer.getOut().flush();
			if(currentPlayer.getIn().hasNextInt()){
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().flush();
				currentPlayer.getIn().nextLine();
				continue;
			}
			else if(currentPlayer.getIn().hasNextLine()){
				String choice = currentPlayer.getIn().nextLine();
				if(choice.equals("y")){
					currentPlayer.getOut().println("How many servants would you like to pay?");
					currentPlayer.getOut().flush();
					if(currentPlayer.getIn().hasNextInt()){
						int increment = currentPlayer.getIn().nextInt();
						int numberOfServants = currentPlayer.getBoard().getResources().getResource().get(ResourceType.SERVANT);
						if(increment <= numberOfServants){
							currentPlayer.getBoard().getResources().getResource().put(ResourceType.SERVANT, (numberOfServants-increment));
							modifiedWithServants = true;
							currentPlayer.getIn().nextLine();
							return increment;
						}
						else{
							currentPlayer.getOut().println("You don't have so many servants!");
							currentPlayer.getOut().flush();
							currentPlayer.getIn().nextLine();
						}
					}
					else{
						currentPlayer.getOut().println("Not valid input!");
						currentPlayer.getOut().flush();
					}
				}
				else if (choice.equals("n")){
					return 0;
				}
				else{
					currentPlayer.getOut().println("Not valid input!");
					currentPlayer.getOut().flush();
				}
			}
		}
	}
	
	public Character askPrivilege(){
		Character c = (Character) currentPlayer.getIn().nextLine().charAt(0);
		while(!(CouncilPrivilege.instance().getOptions().containsKey(c))){
			currentPlayer.getOut().println("Not valid input!");
			currentPlayer.getOut().println("Which council privilege do you want?");
			currentPlayer.getOut().flush();
			c = (Character) currentPlayer.getIn().nextLine().charAt(0);
		}
		return c;
	} 
	

	
	public Resource askAlternative(Resource discount1, Resource discount2, String type){
		currentPlayer.getOut().println("Which of the two following " + type + " do you want to apply? [1/2]");
		currentPlayer.getOut().println(discount1.toString());
		currentPlayer.getOut().println(discount2.toString());
		currentPlayer.getOut().flush();
		int choice;
		while(true){
			if(currentPlayer.getIn().hasNextInt()){
				choice = currentPlayer.getIn().nextInt();
				if(choice == 1){
					currentPlayer.getIn().nextLine();
					return discount1;
				}
				else if(choice == 2){
					currentPlayer.getIn().nextLine();
					return discount2;
				}
				else{
					currentPlayer.getOut().println("Not valid input!");
					currentPlayer.getOut().println("Which of the two " + type + " do you want to apply? [1/2]");
					currentPlayer.getOut().flush();
					currentPlayer.getIn().nextLine();
					continue;
				}
			}
			else{
				currentPlayer.getIn().nextLine();
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().println("Which of the two discounts do you want to apply? [1/2]");
				currentPlayer.getOut().flush();
			}
			
		}
		
	}
	
	public int askAlternativeExchange(Resource firstCost, Resource firstBonus, Resource secondCost, Resource secondBonus){
		currentPlayer.getOut().println("Which of the following exchanges do you want to apply? [1/2]");
		currentPlayer.getOut().println("First Possibility Cost");
		currentPlayer.getOut().println(firstCost.toString());
		currentPlayer.getOut().println("First Possibility Bonus");
		currentPlayer.getOut().println(firstBonus.toString());
		currentPlayer.getOut().println("Second Possibility Cost");
		currentPlayer.getOut().println(secondCost.toString());
		currentPlayer.getOut().println("Second Possibility Bonus");
		currentPlayer.getOut().println(secondBonus.toString());
		currentPlayer.getOut().flush();
		int choice;
		while(true){
			if(currentPlayer.getIn().hasNextInt()){
				choice = currentPlayer.getIn().nextInt();
				if(choice == 1 || choice == 2){
					return choice;
				}
				else{
					currentPlayer.getOut().println("Not valid input!");
					currentPlayer.getOut().println("Which of the two discounts do you want to apply? [1/2]");
					currentPlayer.getOut().flush();
					currentPlayer.getIn().nextLine();
					continue;
				}
			}
			else{
				currentPlayer.getIn().nextLine();
				currentPlayer.getOut().println("Not valid input!");
				currentPlayer.getOut().println("Which of the two discounts do you want to apply? [1/2]");
				currentPlayer.getOut().flush();
			}
			
		}
	}
	
}
