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
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.Action;
import it.polimi.ingsw.GC_28.core.SpaceAction;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoEffect;
import it.polimi.ingsw.GC_28.effects.NoFinalBonusEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.ReduceDiceEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;
import it.polimi.ingsw.GC_28.server.Observer;
import it.polimi.ingsw.GC_28.spaces.Space;



public class Game extends Observable<Action> implements Runnable, Observer<Message>{
	private GameModel gameModel;
	
	//Scanner currentPlayer.getIn() = new Scanner(System.in);
	private Player currentPlayer;
	boolean modifiedWithServants = false;
	private int currentEra = 1;
	private int currentPeriod = 1;
	private int currentRound = 1;
	private int currentTurn = 0;
	private Map<Player, ClientHandler> handlers = new HashMap<>();
	private List<Player> skipped = new ArrayList<>();
	private boolean result;
	//private List<Player> players;
	//private GameBoard gameBoard;
	EnumMap<ResourceType, Integer> decrement = new EnumMap<>(ResourceType.class);
	Resource res ;
	int incrementThroughServants;
	
	
	public Game(GameModel gameModel){
		//players = gameModel.getPlayers();
		//gameBoard = gameModel.getGameBoard();
		this.gameModel = gameModel;
	}
	
	
	@Override
	public void run(){
		setCurrentPlayer(gameModel.getPlayers().get(0));
		System.out.println(1);
		BoardSetup bs = new BoardSetup(this);
		for(currentEra = 1; currentEra <= 3; currentEra++){
			skipPlayers();
			for(currentPeriod = 1; currentPeriod <= 2; currentPeriod++){
				checkDiceReduction();				
				for(currentRound = 1; currentRound <= 4; currentRound++){					
					for(currentTurn = 0; currentTurn < gameModel.getPlayers().size(); currentTurn++){
						try {
							play();
						} catch (IOException e) {
							Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
						}
						if(currentTurn == (gameModel.getPlayers().size()-1)){
							currentPlayer = gameModel.getPlayers().get(0);
						}else{
							currentPlayer = gameModel.getPlayers().get((currentTurn+1));
						}
					} 	
				}
				checkSkippedPlayers();
				bs.setUpBoard();
			}
			giveExcommunication();
		}
		
		//FINE DEL GIOCO
		applyFinalMalus();
	}
	
	public void setHandlers(Map<Player, ClientHandler> handlers) {
		this.handlers = handlers;
	}
	
	
	public void applyFinalMalus(){
		for(Player p : gameModel.getPlayers()){
			ExcommunicationTile t = p.getExcommunicationTile().get(currentEra-1);
			if(t != null){
				t.getEffect().apply(p, this);
			}
		}
	}

	public void checkDiceReduction(){  //se i giocatori tra le scomuniche hanno reducedice applico effetto
		for(Player p : gameModel.getPlayers()){
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				if(t.getEffect() instanceof ReduceDiceEffect){
					t.getEffect().apply(p, this);
				}
			}
		}
		//System.out.println("6");
	}
	
	public void checkSkippedPlayers(){ // se i giocatori hanno saltato il primo turno ora possono rifare il turno che gli spetta alla fine
		for(Player p : skipped){
			currentPlayer = p;
			try{
				play();
			}
			catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
			}	
		}
	}
	
	
	public void skipPlayers(){
		for(Player p : gameModel.getPlayers()){
			//System.out.println("1");
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				//System.out.println("2");
				if(t.getEffect() instanceof OtherEffect){
					OtherEffect otherEffect = (OtherEffect) t.getEffect();
					if(otherEffect.getType().equals(EffectType.SKIPROUNDEFFECT)){
						skipped.add(p);
					}
				}
			}
			//System.out.println("3");
		}
		
	}
	
	public void display(){
		for(Player p: gameModel.getPlayers()){
			handlers.get(p).getOut().println(gameModel.getGameBoard().display());
			handlers.get(p).getOut().println(p.getBoard().display());
			handlers.get(p).getOut().println(p.displayExcommunication());
			
			for(int i = 0; i < 4; i++){
				handlers.get(p).getOut().println(p.getFamilyMembers()[i].toString());
			}
			handlers.get(p).getOut().flush();
		}
	}

	public void play() throws IOException{
		display();
		
		
		if(skipped.contains(currentPlayer) && currentRound == 1){ //se è tra i giocatori in skipped allora salta turno
			handlers.get(currentPlayer).getOut().println("Skipped first turn due to excommunication");
			return;
		}
		do{
			handlers.get(currentPlayer).getOut().println("Which move do you want to undertake? [takeCard / goToSpace / skip/ askcost]");
			handlers.get(currentPlayer).getOut().flush();	
			String line = handlers.get(currentPlayer).getIn().nextLine();
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
			else if(line.equalsIgnoreCase("askcost")){
				askCost();
			}
			else{
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
			}
		}while(true);
	
	}
	


	public Map<Player, ClientHandler> getHandlers() {
		return handlers;
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
	
	public GameModel getGameModel() {
		return gameModel;
	}

	public Resource checkResourceExcommunication(Resource amount){
		for(ExcommunicationTile t : currentPlayer.getExcommunicationTile()){ //guardo tra le scomuniche del currentPlayer
			if(t.getEffect() instanceof DiscountEffect){ //se trovo un discounteffect
				DiscountEffect eff = (DiscountEffect) t.getEffect();
				boolean disc = false;
				boolean altDisc = false;
				
				for(ResourceType resType : eff.getDiscount().getResource().keySet()){ //guardo tra i resourceType del discounteff
					if(!(eff.getDiscount().getResource().get(resType).equals(0))){ //se ne trovo uno diverso da 0
						if(!amount.getResource().get(resType).equals(0)){ // e se io l'ho preso quel tipo di risorsa
							disc = true; //allora setto un bool
							break;
						}
					}
				}
				
				if(eff.getAlternativeDiscountPresence()){ //se ho due alternative
					for(ResourceType resType : eff.getAlternativeDiscount().getResource().keySet()){ 
						if(!(eff.getAlternativeDiscount().getResource().get(resType).equals(0))){
							if(!amount.getResource().get(resType).equals(0)){
								altDisc = true; // se ho preso anche la risorsa diversa da zero dell'alternativediscount setto un bool
								break;
							}
						}
					}
				}
					
				if(disc && altDisc){ // se ho preso entrambi chiedo quale togliere
					eff.setChosenAlternativeDiscount(askAlternative(eff.getDiscount(), eff.getAlternativeDiscount(), "malus")); 
				}
				else if(disc){ //altrimenti tolgo disc..
					eff.setChosenAlternativeDiscount(eff.getDiscount());
				}
				else if(altDisc){ //o alternative disc
					eff.setChosenAlternativeDiscount(eff.getAlternativeDiscount());
				}
				else{ //se non ho preso niente di quei tipi non tolgo niente
					eff.setChosenAlternativeDiscount(null);
				}
				
				amount.modifyResource(eff.getChosenAlternativeDiscount(), true);
				return amount;
			}			
		}
		return amount;
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
					return gameModel.getGameBoard().getCoinSpace();
				}
				if(chosenSpace.equalsIgnoreCase("servantspace")){
					return gameModel.getGameBoard().getServantSpace();
				}
				if(chosenSpace.equalsIgnoreCase("mixedspace")){
					return gameModel.getGameBoard().getMixedSpace();
				}
				if(chosenSpace.equalsIgnoreCase("privilegesspace")){
					return gameModel.getGameBoard().getPrivilegesSpace();
				}
				if(chosenSpace.equalsIgnoreCase("councilpalace")){
					return gameModel.getGameBoard().getCouncilPalace();
				}
				if(chosenSpace.equalsIgnoreCase("productionspace")){
					return gameModel.getGameBoard().getProductionSpace();
				}
				if(chosenSpace.equalsIgnoreCase("harvestspace")){
					return gameModel.getGameBoard().getHarvestSpace();
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
		
		SpaceAction spaceAction = new SpaceAction(this);
		Space chosenSpace;
		
		if(!(throughEffect == null)){
			familyMember = new FamilyMember(currentPlayer, false, null); //familyMember fittizio
			familyMember.setValue(throughEffect.getActionValue());
			if(throughEffect.isHarvest()){
				chosenSpace = gameModel.getGameBoard().getHarvestSpace();
			}
			else{
				chosenSpace = gameModel.getGameBoard().getProductionSpace();
			}
		}
		else{
			chosenSpace = askWhichSpace();
			familyMember = askFamilyMember();
		}
		
		incrementThroughServants = askForServantsIncrement();
		familyMember.modifyValue(incrementThroughServants);
		
		decrement.put(ResourceType.SERVANT, incrementThroughServants);
		res = Resource.of(decrement);
		
		familyMember.modifyValue(incrementThroughServants);
		decrement.put(ResourceType.SERVANT, incrementThroughServants);
		res = Resource.of(decrement);
		
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(chosenSpace);
		spaceAction.setThroughEffect(throughEffect);
		
		this.notifyObserver(spaceAction);
		if(modifiedWithServants){
			familyMember.modifyValue((-1)*(incrementThroughServants));
		}
		
		return result;
		
		
	}
	
	public boolean askCard(TakeCardEffect throughEffect){ //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		TakeCardAction takeCardAction = new TakeCardAction(this, gameModel);
		
		if(!(throughEffect == null)){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType() == null){
				handlers.get(currentPlayer).getOut().println("You can take another card of any type of value " + throughEffect.getActionValue());
			}
			else{
				handlers.get(currentPlayer).getOut().println("You can take a card of type: " + throughEffect.getCardType().name() + " of value " + throughEffect.getActionValue());
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
		decrement.put(ResourceType.SERVANT, incrementThroughServants);
		res = Resource.of(decrement);
		
		takeCardAction.setFamilyMember(familyMember);
		takeCardAction.setName(name);
		takeCardAction.setThroughEffect(throughEffect);
		
		this.notifyObserver(takeCardAction);
		
		if(modifiedWithServants){
			familyMember.modifyValue((-1)*(incrementThroughServants));
		}
		
		return result;
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
							
							for(ExcommunicationTile t : currentPlayer.getExcommunicationTile()){ //guardo se tra le scomuniche ha servanteffect
								if(t.getEffect() instanceof ServantEffect){
									ServantEffect eff = (ServantEffect) t.getEffect();
									return increment * eff.getIncrement() / eff.getNumberOfServant(); // se sì allora applico l'effetto
								}

							}
							
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
			if((c = gameModel.getGameBoard().getTowers().get(ct).findCard(name)) != null){
				handlers.get(currentPlayer).getOut().println(c.getCard().getCost().toString());
				handlers.get(currentPlayer).getOut().flush();
				return;
			}
		}	
	}
	
	private void giveExcommunication(){
		for(Player p : gameModel.getPlayers()){
				int faith = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
				if(faith < (2+ currentEra)){
					handlers.get(p).getOut().println("You recive an Excommunication, because you cannot pay to avoid it");
					handlers.get(p).getOut().flush();
					p.getExcommunicationTile().get(currentEra -1).setEffect(gameModel.getGameBoard().getExcommunications()[currentEra-1].getEffect());
					//p.getExcommunicationTile().add(currentEra-1, gameBoard.getExcommunications()[currentEra-1]);
					return;
				}else{
					handlers.get(p).getOut().println("Do you want to pay to avoid Excommunication?[y/n]");
					handlers.get(p).getOut().flush();
					if(handlers.get(p).getIn().nextLine().equalsIgnoreCase("n")){
						p.getExcommunicationTile().add(currentEra-1, gameModel.getGameBoard().getExcommunications()[currentEra-1]);
						return;
					}else{
						handlers.get(p).getOut().println("You paid to avoid Excommunication, your faith points have been reset to 0");
						handlers.get(p).getOut().flush();
						int numberOfFaithPoint = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
						Resource bonusForFaithPoint = FinalBonus.instance().getFaithPointTrack().get(numberOfFaithPoint-1);
						p.addResource(bonusForFaithPoint);
						p.getBoard().getResources().getResource().put(ResourceType.FAITHPOINT, 0);
						return;
					}
				}
			}
	}

	@Override
	public void update(Message m) {
		handlers.get(currentPlayer).getOut().println(m.getMessage());
		result = m.isResult();
		if(m.isResult()){	
			if(modifiedWithServants){
				currentPlayer.addResource(res);
			}
		}	
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}