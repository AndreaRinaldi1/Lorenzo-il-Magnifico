package it.polimi.ingsw.GC_28.model;


import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.SpaceAction;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.spaces.Space;



public class Game implements Runnable {
	private GameBoard gameBoard;

	//Scanner currentPlayer.getIn() = new Scanner(System.in);
	private Player currentPlayer;
	boolean modifiedWithServants = false;
	private int currentEra  = 1;
	private int currentPeriod = 1;
	private List<Player> players = new ArrayList<>();
	private Map<Player, ClientHandler> handlers = new HashMap<>();
	
	public Game(){
		
	}
	
	
	@Override
	public void run(){
		BoardSetup bs = new BoardSetup(this);
		for(Player p : players){
			System.out.println(p.getName());
			System.out.println(handlers.get(p).getClass());
		}
		for(; currentEra <= 3; currentEra++){
			for(; currentPeriod <= 2; currentPeriod++){
				for(int round = 1; round <= 4; round++){
					for(int turn = 0; turn < players.size(); turn++){
						try {
							System.out.println("rifaccio play");
							play();
							System.out.println("tornato");
						} catch (IOException e) {
							Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
						}
						if(turn == (players.size()-1)){
							currentPlayer = players.get(0);
						}else{
							currentPlayer = players.get((turn+1));
						}
					}	
				}
				bs.setUpBoard();
			}
			bs.setUpBoard();
		}
	}
	
	public void setHandlers(Map<Player, ClientHandler> handlers) {
		this.handlers = handlers;
	}


	public void play() throws IOException{
		for(Player p: players){
			handlers.get(p).getOut().println(gameBoard.display());
			handlers.get(p).getOut().println(p.getBoard().display());
			for(int i = 0; i < 4; i++){
				handlers.get(p).getOut().println(p.getFamilyMembers()[i].toString());
			}
			handlers.get(p).getOut().flush();
		}
		do{
			handlers.get(currentPlayer).getOut().println("Which move do you want to undertake? [takeCard / goToSpace / skip/ askcost]");
			handlers.get(currentPlayer).getOut().flush();	
			String line = handlers.get(currentPlayer).getIn().nextLine();
			if(line.equalsIgnoreCase("takeCard")){
				if(askCard(null)){
					System.out.println("fatto tutto ");
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
			else if(line.equalsIgnoreCase("askcost")){
				askCost();
			}
			else{
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
				line = handlers.get(currentPlayer).getIn().nextLine();
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
		handlers.get(currentPlayer).getOut().println("Which council privilege do you want?");
		handlers.get(currentPlayer).getOut().flush();
		for(Character key : CouncilPrivilege.instance().getOptions().keySet()){
			handlers.get(currentPlayer).getOut().println("Type " + key + " if you want:");
			handlers.get(currentPlayer).getOut().println(CouncilPrivilege.instance().getOptions().get(key).toString());
			handlers.get(currentPlayer).getOut().flush();
		}
		ArrayList<Character> choices = new ArrayList<Character>();
		int counter = 0;
		while(counter < numberOfCouncilPrivileges){
			handlers.get(currentPlayer).getOut().println("Choose your council privilege #" + (counter+1));
			handlers.get(currentPlayer).getOut().flush();
			Character c = askPrivilege();
			if(!(choices.contains(c))){
				choices.add(c);
				counter++;
			}
			else{
				if(different){
					handlers.get(currentPlayer).getOut().println("Not valid choice"); 
					handlers.get(currentPlayer).getOut().flush();
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
			handlers.get(currentPlayer).getOut().println("Enter which space you want to go into [coinSpace / servantSpace / mixedSpace / privilegesSpace / councilPalace / productionSpace / harvestSpace]");
			handlers.get(currentPlayer).getOut().flush();
			if(handlers.get(currentPlayer).getIn().hasNextInt()){
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
				handlers.get(currentPlayer).getIn().nextLine();
			}
			else{
				String chosenSpace = handlers.get(currentPlayer).getIn().nextLine();
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
					handlers.get(currentPlayer).getOut().println("Not valid input!");
					handlers.get(currentPlayer).getOut().flush();
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
			handlers.get(currentPlayer).getOut().println("Not valid action!");
			handlers.get(currentPlayer).getOut().flush();
			return false;
		}
		
	}
	
	public boolean askCard(TakeCardEffect throughEffect){ //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		int incrementThroughServants;
		TakeCardAction takeCardAction = new TakeCardAction(this, gameBoard);
		
		if(!(throughEffect == null)){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType() == null){
				handlers.get(currentPlayer).getOut().println("You can take another card of any type");
			}
			else{
				handlers.get(currentPlayer).getOut().println("You can take a card of type: " + throughEffect.getCardType().name());
			}
			handlers.get(currentPlayer).getOut().flush();
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
			System.out.println("tutto bene");
			return true;
		}
		else{
			if(modifiedWithServants){
				familyMember.modifyValue((-1)*(incrementThroughServants));
				currentPlayer.getBoard().getResources().modifyResource(res, true);
			}
			handlers.get(currentPlayer).getOut().println("Not valid action!");
			handlers.get(currentPlayer).getOut().flush();
			System.out.println("tutto male");

			return false;
		}
	}
	
	public String askCardName(){
		do{
			handlers.get(currentPlayer).getOut().println("Enter the name of the card you would like to take: ");
			handlers.get(currentPlayer).getOut().flush();
			if(handlers.get(currentPlayer).getIn().hasNextInt()){
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
				handlers.get(currentPlayer).getIn().nextLine();
			}
			else{
				return handlers.get(currentPlayer).getIn().nextLine();
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
			handlers.get(currentPlayer).getOut().println("Enter which familyMember you would like to use: [ Orange / Black / White / Neutral ]");
			handlers.get(currentPlayer).getOut().flush();
			String choice = handlers.get(currentPlayer).getIn().nextLine();
			for(DiceColor color : DiceColor.values()){
				if(choice.toUpperCase().equals(color.name())){
					for(FamilyMember familyMember : currentPlayer.getFamilyMembers()){
						if(familyMember.getDiceColor().equals(color)){
							if(!familyMember.isUsed()){
								return familyMember;
							}
						}
					}
					handlers.get(currentPlayer).getOut().println("The specified family member has already been used!");
					handlers.get(currentPlayer).getOut().flush();
					x = false;
					continue;
				}
			}
			if(x){
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
			}
		}while(true);
	}
	
	public int askForServantsIncrement(){
		while(true){
			handlers.get(currentPlayer).getOut().println("Would you like to pay servants in order to increment the family member action value? [y/n]");
			handlers.get(currentPlayer).getOut().flush();
			if(handlers.get(currentPlayer).getIn().hasNextInt()){
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
				handlers.get(currentPlayer).getIn().nextLine();
				continue;
			}
			else if(handlers.get(currentPlayer).getIn().hasNextLine()){
				String choice = handlers.get(currentPlayer).getIn().nextLine();
				if(choice.equals("y")){
					handlers.get(currentPlayer).getOut().println("How many servants would you like to pay?");
					handlers.get(currentPlayer).getOut().flush();
					if(handlers.get(currentPlayer).getIn().hasNextInt()){
						int increment = handlers.get(currentPlayer).getIn().nextInt();
						int numberOfServants = currentPlayer.getBoard().getResources().getResource().get(ResourceType.SERVANT);
						if(increment <= numberOfServants){
							currentPlayer.getBoard().getResources().getResource().put(ResourceType.SERVANT, (numberOfServants-increment));
							modifiedWithServants = true;
							handlers.get(currentPlayer).getIn().nextLine();
							return increment;
						}
						else{
							handlers.get(currentPlayer).getOut().println("You don't have so many servants!");
							handlers.get(currentPlayer).getOut().flush();
							handlers.get(currentPlayer).getIn().nextLine();
						}
					}
					else{
						handlers.get(currentPlayer).getOut().println("Not valid input!");
						handlers.get(currentPlayer).getOut().flush();
					}
				}
				else if (choice.equals("n")){
					return 0;
				}
				else{
					handlers.get(currentPlayer).getOut().println("Not valid input!");
					handlers.get(currentPlayer).getOut().flush();
				}
			}
		}
	}
	
	public Character askPrivilege(){
		Character c = (Character) handlers.get(currentPlayer).getIn().nextLine().charAt(0);
		while(!(CouncilPrivilege.instance().getOptions().containsKey(c))){
			handlers.get(currentPlayer).getOut().println("Not valid input!");
			handlers.get(currentPlayer).getOut().println("Which council privilege do you want?");
			handlers.get(currentPlayer).getOut().flush();
			c = (Character) handlers.get(currentPlayer).getIn().nextLine().charAt(0);
		}
		return c;
	} 
	

	
	public Resource askAlternative(Resource discount1, Resource discount2, String type){
		handlers.get(currentPlayer).getOut().println("Which of the two following " + type + " do you want to apply? [1/2]");
		handlers.get(currentPlayer).getOut().println(discount1.toString());
		handlers.get(currentPlayer).getOut().println(discount2.toString());
		handlers.get(currentPlayer).getOut().flush();
		int choice;
		while(true){
			if(handlers.get(currentPlayer).getIn().hasNextInt()){
				choice = handlers.get(currentPlayer).getIn().nextInt();
				if(choice == 1){
					handlers.get(currentPlayer).getIn().nextLine();
					return discount1;
				}
				else if(choice == 2){
					handlers.get(currentPlayer).getIn().nextLine();
					return discount2;
				}
				else{
					handlers.get(currentPlayer).getOut().println("Not valid input!");
					handlers.get(currentPlayer).getOut().println("Which of the two " + type + " do you want to apply? [1/2]");
					handlers.get(currentPlayer).getOut().flush();
					handlers.get(currentPlayer).getIn().nextLine();
					continue;
				}
			}
			else{
				handlers.get(currentPlayer).getIn().nextLine();
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().println("Which of the two discounts do you want to apply? [1/2]");
				handlers.get(currentPlayer).getOut().flush();
			}
			
		}
		
	}
	
	public int askAlternativeExchange(Resource firstCost, Resource firstBonus, Resource secondCost, Resource secondBonus){
		handlers.get(currentPlayer).getOut().println("Which of the following exchanges do you want to apply? [1/2]");
		handlers.get(currentPlayer).getOut().println("First Possibility Cost");
		handlers.get(currentPlayer).getOut().println(firstCost.toString());
		handlers.get(currentPlayer).getOut().println("First Possibility Bonus");
		handlers.get(currentPlayer).getOut().println(firstBonus.toString());
		handlers.get(currentPlayer).getOut().println("Second Possibility Cost");
		handlers.get(currentPlayer).getOut().println(secondCost.toString());
		handlers.get(currentPlayer).getOut().println("Second Possibility Bonus");
		handlers.get(currentPlayer).getOut().println(secondBonus.toString());
		handlers.get(currentPlayer).getOut().flush();
		int choice;
		while(true){
			if(handlers.get(currentPlayer).getIn().hasNextInt()){
				choice = handlers.get(currentPlayer).getIn().nextInt();
				if(choice == 1 || choice == 2){
					return choice;
				}
				else{
					handlers.get(currentPlayer).getOut().println("Not valid input!");
					handlers.get(currentPlayer).getOut().println("Which of the two discounts do you want to apply? [1/2]");
					handlers.get(currentPlayer).getOut().flush();
					handlers.get(currentPlayer).getIn().nextLine();
					continue;
				}
			}
			else{
				handlers.get(currentPlayer).getIn().nextLine();
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().println("Which of the two discounts do you want to apply? [1/2]");
				handlers.get(currentPlayer).getOut().flush();
			}
			
		}
	}
	
	private void askCost(){
		String name = askCardName();
		Cell c;
		for(CardType ct : CardType.values()){
			if((c = gameBoard.getTowers().get(ct).findCard(name)) != null){
				handlers.get(currentPlayer).getOut().println(c.getCard().getCost().toString());
				handlers.get(currentPlayer).getOut().flush();
				return;
			}
		}	
	}
	
}
