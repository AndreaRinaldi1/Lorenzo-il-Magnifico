package it.polimi.ingsw.GC_28.view;


import java.io.IOException;
import java.nio.BufferOverflowException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.core.Action;
import it.polimi.ingsw.GC_28.core.SpaceAction;
import it.polimi.ingsw.GC_28.core.SpecialAction;
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;
import it.polimi.ingsw.GC_28.server.Observer;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.spaces.Space;

public class GameView extends Observable<Action> implements  Observer<Message>{
	private GameModel gameModel;
	Timer timer;
	boolean modifiedWithServants = false;
	private Map<Player, ClientHandler> handlers = new HashMap<>();
	private boolean result;
	private List<Player> suspended = new ArrayList<>();
	private List<Player> skipped = new ArrayList<>();
	EnumMap<ResourceType, Integer> decrement = new EnumMap<>(ResourceType.class);
	Resource res ;
	int incrementThroughServants;	
	private Player currentPlayer;

	
	public GameView(GameModel gameModel){
		this.gameModel = gameModel;
	}
	
	
	public Map<Player, ClientHandler> getHandlers() {
		return handlers;
	}

	public List<Player> getSkipped() {
		return skipped;
	}


	public List<Player> getSuspended() {
		return suspended;
	}


	public GameModel getGameModel() {
		return gameModel;
	}
	
	public void setHandlers(Map<Player, ClientHandler> handlers) {
		this.handlers = handlers;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	
	

	private void display(){
		for(Player p: gameModel.getPlayers()){
			if(!suspended.contains(p)){
				String gb = gameModel.getGameBoard().display();
				String tracks = displayTracks();
				handlers.get(p).send(gb);
				handlers.get(p).send(tracks);
				handlers.get(p).send(p.getBoard().display());
				handlers.get(p).send(p.displayFamilyMember());
			}
		}
	}
	
	public void declareWinner() {
		handlers.get(gameModel.getPlayers().get(0)).send("YOU WIN!!!");
		displayFinalChart();
	}
	
	public String getChartTable(){
		StringBuilder ret = new StringBuilder();
		AsciiTable chart = new AsciiTable();
		chart.addRule();
		chart.addRow("Position", "Name", "Points");
		chart.addRule();
		for(int i = 0; i < gameModel.getPlayers().size(); i++){
			chart.addRow((i+1) , gameModel.getPlayers().get(i).getName(), gameModel.getPlayers().get(i).getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT));
			chart.addRule();
		}
		ret.append(chart.render() + "\n");
		
		return ret.toString();
	}
	
	public void displayFinalChart() {
		String chart = getChartTable();
		for(Player p : gameModel.getPlayers()){
			handlers.get(p).send(chart);
		}
	}
	
	private void showExcomm(){
		handlers.get(currentPlayer).send(currentPlayer.displayExcommunication());
	}
	
	private void showLeaders(){
		handlers.get(currentPlayer).send(currentPlayer.displayLeader());

	}
	
	private String displayTracks(){
		AsciiTable tracks = new AsciiTable();
		StringBuilder church = new StringBuilder();
		StringBuilder military = new StringBuilder();
		StringBuilder victory = new StringBuilder();
		for(Player p : gameModel.getPlayers()){
			church.append("{" + p.getColor() + ": " + p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT) + "} ");
			military.append("{" + p.getColor() + ": " + p.getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT) + "} ");
			victory.append("{" + p.getColor() + ": " + p.getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT) + "} ");
		}
		tracks.addRule();
		tracks.addRow(ResourceType.FAITHPOINT, ResourceType.MILITARYPOINT, ResourceType.VICTORYPOINT);
		tracks.addRule();
		tracks.addRow(church.toString(), military.toString(), victory.toString());
		tracks.addRule();
		String ret = tracks.render();
		return ret;
	}


	public void play() throws IOException, IndexOutOfBoundsException{
		
		if(suspended.contains(currentPlayer)){
			return;
		}
		display();
		do{
			handlers.get(currentPlayer).send("Which move do you want to undertake? [takeCard / goToSpace / skip / askcost / askLeaderCost / specialAction / showMyExcomm / showMyLeaders]");
			
			String line = handlers.get(currentPlayer).receive();
			
			if(line.equalsIgnoreCase("takeCard")){
				if(askCard(null)){
					break;
				}
			}
			else if(line.equalsIgnoreCase("goToSpace")){
				if(goToSpace(null)){
					break;
				}
			}
			else if(line.equalsIgnoreCase("skip")){
				break;
			}
			else if(line.equalsIgnoreCase("askcost")){
				askCost();
			}
			else if(line.equals("disconnect")){
				return;
			}
			else if(line.equalsIgnoreCase("specialaction")){
				specialAction();
			}
			else if(("askLeaderCost").equalsIgnoreCase(line)){
				askLeaderCost();
			}
			else if(line.equalsIgnoreCase("showMyExcomm")){
				showExcomm();
			}
			else if(line.equalsIgnoreCase("showMyLeaders")){
				showLeaders();
			}
			else{
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}while(true);
		handlers.get(currentPlayer).send("Do you want to do a special action[y/n]");
		String special = handlers.get(currentPlayer).receive();
		if(special.equalsIgnoreCase("y")){
			specialAction();
		}else{
			return;
		}
		
	}

	
	

	

	public boolean checkSkipped(int currentRound){
		if(skipped.contains(currentPlayer) && currentRound == 1){ //se è tra i giocatori in skipped allora salta turno
			handlers.get(currentPlayer).send("Skipped first turn due to excommunication");
			return true;
		}
		return false;
	}
	
	public Resource checkResourceExcommunication(Resource amount){
		if(amount == null){
			return amount;
		}
		EnumMap<ResourceType,Integer> resCopy = new EnumMap<>(ResourceType.class);
		for(ResourceType rt : amount.getResource().keySet()){
			resCopy.put(rt, amount.getResource().get(rt));
		}
		Resource amountCopy = Resource.of(resCopy);
		for(ExcommunicationTile t : currentPlayer.getExcommunicationTile()){ //guardo tra le scomuniche del currentPlayer
			if(t.getEffect() instanceof DiscountEffect){ //se trovo un discounteffect
				DiscountEffect eff = (DiscountEffect) t.getEffect();
				boolean disc = false;
				boolean altDisc = false;
				
				for(ResourceType resType : eff.getDiscount().getResource().keySet()){ //guardo tra i resourceType del discounteff
					if(!(eff.getDiscount().getResource().get(resType).equals(0))){ //se ne trovo uno diverso da 0
						System.out.println(amount.toString());
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
					EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
					for(ResourceType resType : ResourceType.values()){
						w.put(resType, 0);
					}
					Resource res = Resource.of(w);
					eff.setChosenAlternativeDiscount(res);
				}
				amountCopy.modifyResource(eff.getChosenAlternativeDiscount(), true);
				return amountCopy;
			}			
		}
		return amountCopy;
	}
	
	
	public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
		handlers.get(currentPlayer).send("Which council privilege do you want?");
		for(Character key : CouncilPrivilege.instance().getOptions().keySet()){
			handlers.get(currentPlayer).send("Type " + key + " if you want:");
			handlers.get(currentPlayer).send(CouncilPrivilege.instance().getOptions().get(key).toString());
		}
		ArrayList<Character> choices = new ArrayList<Character>();
		int counter = 0;
		while(counter < numberOfCouncilPrivileges){
			handlers.get(currentPlayer).send("Choose your council privilege #" + (counter+1));
			Character c = askPrivilege();
			if(!(choices.contains(c))){
				choices.add(c);
				counter++;
			}
			else{
				if(different){
					handlers.get(currentPlayer).send("Not valid choice"); 
				}
				else{
					choices.add(c);
					counter++;
				}
			}
		}
		return choices;
	}
	
	public Space askWhichSpace() {
		do{
			handlers.get(currentPlayer).send("Enter which space you want to go into [coinSpace / servantSpace / mixedSpace / privilegesSpace / councilPalace / productionSpace / harvestSpace]");
			String chosenSpace = handlers.get(currentPlayer).receive();
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
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}while(true);
	}
	
	public boolean goToSpace(GoToHPEffect throughEffect) {
		FamilyMember familyMember;
		
		SpaceAction spaceAction = new SpaceAction(this, gameModel);
		Space chosenSpace;
		if(!(throughEffect == null)){
			familyMember = new FamilyMember(currentPlayer, false, null); //familyMember fittizio
			familyMember.setValue(throughEffect.getActionValue());
			if(throughEffect.getSpecificType() == ProdHarvType.HARVEST){
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
		
		spaceAction.setFamilyMember(familyMember);
		spaceAction.setSpace(chosenSpace);
		spaceAction.setThroughEffect(throughEffect);
		
		this.notifyObserver(spaceAction);
		if(modifiedWithServants){
			familyMember.modifyValue((-1)*(incrementThroughServants));
		}
		
		return result;
		
		
	}
	
	
	public boolean askPermission() {
		while(true){
			handlers.get(currentPlayer).send("Do you want to apply this effect? [y/n]");
			String line = handlers.get(currentPlayer).receive();
			if(line.equals("y")){
				return true;
			}
			else if(line.equals("n")){
				return false;
			}
			handlers.get(currentPlayer).send("Not valid input!");
		}
	}
	
	public boolean askCard(TakeCardEffect throughEffect) { //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		TakeCardAction takeCardAction = new TakeCardAction(this, gameModel);
		
		if(!(throughEffect == null)){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType() == null){
				handlers.get(currentPlayer).send("You can take another card of any type of value " + throughEffect.getActionValue());
			}
			else{
				handlers.get(currentPlayer).send("You can take a card of type: " + throughEffect.getCardType().name() + " of value " + throughEffect.getActionValue());
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
		handlers.get(currentPlayer).send("Enter the name of the card you would like to take: ");
		return handlers.get(currentPlayer).receive();
		
	}
	
	public FamilyMember askFamilyMember(){
		boolean x = true;
		do{
			handlers.get(currentPlayer).send("Enter which familyMember you would like to use: [ Orange / Black / White / Neutral ]");
			String choice = handlers.get(currentPlayer).receive();
			for(DiceColor color : DiceColor.values()){
				if(choice.toUpperCase().equals(color.name())){
					for(FamilyMember familyMember : currentPlayer.getFamilyMembers()){
						if(familyMember.getDiceColor().equals(color)){
							if(!familyMember.isUsed()){
								return familyMember;
							}
						}
					}
					handlers.get(currentPlayer).send("The specified family member has already been used!");
					x = false;
					continue;
				}
			}
			if(x){
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}while(true);
	}
	
	public int askForServantsIncrement(){
		while(true){
			handlers.get(currentPlayer).send("Would you like to pay servants in order to increment the family member action value? [y/n]");
			String choice = handlers.get(currentPlayer).receive();
			if(choice.equals("y")){
				handlers.get(currentPlayer).send("How many servants would you like to pay?");
				int increment;
				try{
					increment = Integer.parseInt(handlers.get(currentPlayer).receive());
				}catch(NumberFormatException e){
					handlers.get(currentPlayer).send("Not valid input!");
					continue;
				}
				int numberOfServants = currentPlayer.getBoard().getResources().getResource().get(ResourceType.SERVANT);
				if(increment <= numberOfServants){
					currentPlayer.getBoard().getResources().getResource().put(ResourceType.SERVANT, (numberOfServants-increment));
					modifiedWithServants = true;
					
					for(ExcommunicationTile t : currentPlayer.getExcommunicationTile()){ //guardo se tra le scomuniche ha servanteffect
						if(t.getEffect() instanceof ServantEffect){
							ServantEffect eff = (ServantEffect) t.getEffect();
							return increment * eff.getIncrement() / eff.getNumberOfServant(); // se sì allora applico l'effetto
						}

					}
							
					return increment;
				}
				else{
					handlers.get(currentPlayer).send("You don't have so many servants!");
				}
			}
			else if (choice.equals("n")){
				modifiedWithServants = false;
				return 0;
			}
			else{
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}
	}
	
	public Character askPrivilege(){
		Character c = (Character) handlers.get(currentPlayer).receive().charAt(0);
		while(!(CouncilPrivilege.instance().getOptions().containsKey(c))){
			handlers.get(currentPlayer).send("Not valid input!");
			handlers.get(currentPlayer).send("Which council privilege do you want?");
			c = (Character) handlers.get(currentPlayer).receive().charAt(0);
		}
		return c;
	} 
	

	
	public Resource askAlternative(Resource discount1, Resource discount2, String type) {
		handlers.get(currentPlayer).send("Which of the two following " + type + " do you want to apply? [1/2]");
		handlers.get(currentPlayer).send(discount1.toString());
		handlers.get(currentPlayer).send(discount2.toString());
		int choice;
		while(true){
			try{
				choice = Integer.parseInt(handlers.get(currentPlayer).receive());
				if(choice == 1){
					return discount1;
				}
				else if(choice == 2){
					return discount2;
				}
				else{
					handlers.get(currentPlayer).send("Not valid input!");
					handlers.get(currentPlayer).send("Which of the two " + type + " do you want to apply? [1/2]");
				}
			}catch(NumberFormatException e){
				System.out.println("cannot convert from string to integer in askAlternative");
			}	
		}	
	}
	
	public int askAlternativeExchange(Resource firstCost, Resource firstBonus, Resource secondCost, Resource secondBonus){
		handlers.get(currentPlayer).send("Which of the following exchanges do you want to apply? [1/2]");
		handlers.get(currentPlayer).send("First Possibility Cost");
		handlers.get(currentPlayer).send(firstCost.toString());
		handlers.get(currentPlayer).send("First Possibility Bonus");
		handlers.get(currentPlayer).send(firstBonus.toString());
		handlers.get(currentPlayer).send("Second Possibility Cost");
		handlers.get(currentPlayer).send(secondCost.toString());
		handlers.get(currentPlayer).send("Second Possibility Bonus");
		handlers.get(currentPlayer).send(secondBonus.toString());
		int choice;
		while(true){
			try{
				choice = Integer.parseInt(handlers.get(currentPlayer).receive());
				if(choice == 1 || choice == 2){
					return choice;
				}
				else{
					handlers.get(currentPlayer).send("Not valid input!");
					handlers.get(currentPlayer).send("Which of the two discounts do you want to apply? [1/2]");
				}
			}catch(NumberFormatException e){
				System.out.println("cannot convert from string to integer in askAlternativeExchange");
			}
		}
	}
	
	void askCost(){
		String name = askCardName();
		Cell c;
		for(CardType ct : CardType.values()){
			if((c = gameModel.getGameBoard().getTowers().get(ct).findCard(name)) != null){
				handlers.get(currentPlayer).send(c.getCard().getCost().toString());
				return;
			}
		}	
	}
	
	private void askLeaderCost(){
		handlers.get(currentPlayer).send(currentPlayer.displayLeaderCost());
		return;
	}
	
	protected void giveExcommunication(int currentEra) {
		for(Player p : gameModel.getPlayers()){
				handlers.get(p).send(p.displayExcommunication());
				int faith = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
				if(faith < (2+ currentEra)){
					handlers.get(p).send("You receive an Excommunication, because you cannot pay to avoid it");
					p.getExcommunicationTile().get(currentEra -1).setEffect(gameModel.getGameBoard().getExcommunications()[currentEra-1].getEffect());
					//p.getExcommunicationTile().add(currentEra-1, gameBoard.getExcommunications()[currentEra-1]);
				}else{
					handlers.get(p).send("Do you want to pay to avoid Excommunication?[y/n]");
					if(handlers.get(p).receive().equalsIgnoreCase("n")){
						p.getExcommunicationTile().add(currentEra-1, gameModel.getGameBoard().getExcommunications()[currentEra-1]);
					}else{
						handlers.get(p).send("You paid to avoid Excommunication, your faith points have been reset to 0");
						int numberOfFaithPoint = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
						Resource bonusForFaithPoint = FinalBonus.instance().getFaithPointTrack().get(numberOfFaithPoint-1);
						p.addResource(bonusForFaithPoint);
						p.getBoard().getResources().getResource().put(ResourceType.FAITHPOINT, 0);
						for(LeaderCard lc : p.getLeaderCards()){
							if(CheckForPopeEffect(lc)){
								lc.getEffect().apply(p, this);
							}
						}
					}
				}
			}
	}
	void specialAction(){//TODO
		SpecialAction specialAction = new SpecialAction(currentPlayer,gameModel,this);
		handlers.get(currentPlayer).send(currentPlayer.displayLeader());
		String proceed;
		do{
			handlers.get(currentPlayer).send("Which special action do you want to undertake?[discard/play/activate]");
			String action = handlers.get(currentPlayer).receive();
			specialAction.setActionType(action);
			handlers.get(currentPlayer).send("On which of your LeaderCard would you like to undertake this action?[enter LeaderCard name]");
			String name = handlers.get(currentPlayer).receive();
			specialAction.setLeaderName(name);
			this.notifyObserver(specialAction);
			handlers.get(currentPlayer).send("Do you want to do another special action?[y/n]");
			proceed = handlers.get(currentPlayer).receive();
		}while(!proceed.equalsIgnoreCase("n"));
	}
	@Deprecated
	void specialActionOld() {//FIXME
		handlers.get(currentPlayer).send(currentPlayer.displayLeader());
		String proceed;
		do{
			handlers.get(currentPlayer).send("Which special action do you want to undertake?[discard/play/activate]");
			String line = handlers.get(currentPlayer).receive();
			if(line.equalsIgnoreCase("discard")){
				handlers.get(currentPlayer).send("Which Leader do you want to discard?");
				String card = handlers.get(currentPlayer).receive();
				for(LeaderCard l :currentPlayer.getLeaderCards()){
					if(l.getName().equalsIgnoreCase(card)){
						currentPlayer.getLeaderCards().remove(l);
						ArrayList<java.lang.Character> choices = askPrivilege(1, false);
						for(int i = 0; i < choices.size(); i++){//TODO test me 
							currentPlayer.addResource(checkResourceExcommunication(CouncilPrivilege.instance().getOptions().get(choices.get(i))));
						}
						break;
					}
				}	
			}else if(line.equalsIgnoreCase("play")){
				handlers.get(currentPlayer).send("Which Leader do you want to play?");
				String card = handlers.get(currentPlayer).receive();
				for(LeaderCard l : currentPlayer.getLeaderCards()){
					if(l.getName().equalsIgnoreCase(card)){
						Resource cardResourceCost = l.getResourceCost();
						Map<CardType,Integer> cardCost = l.getCardCost();
						if(checkForLucreziaBorgiaCost(l)){
							l.setPlayed(true);
							break;
						}
						else if(enoughResources(cardResourceCost) && enoughCard(cardCost)){
							l.setPlayed(true);
							break;
						}
					}
				}
			}else if(line.equalsIgnoreCase("activate")){
				handlers.get(currentPlayer).send("Which Leader do you want to active?");
				String card = handlers.get(currentPlayer).receive();
				for(LeaderCard l : currentPlayer.getLeaderCards()){
					if(l.getName().equalsIgnoreCase(card)){
						if(l.getPlayed()){
							if(!(l.getActive())){
								l.setActive(true);
								if(!(l.getName().equalsIgnoreCase("Sisto IV")) && !(l.getName().equalsIgnoreCase("Santa Rita"))){
									l.getEffect().apply(currentPlayer, this);//FIXME change all the possible apply(familyMember,game) to apply(player,game) if possible
									break;
									}
							}else{
								handlers.get(currentPlayer).send("You can't activate this card because you already played it in this turn");
								break;
							}
						}else{
							handlers.get(currentPlayer).send("You can't activate this card because you've not played it yet");
							break;
						}
					}
				}
			}
			else{
				handlers.get(currentPlayer).send("Not valid input!");
			}
			handlers.get(currentPlayer).send("Do you want to do another special action?[y/n]");
			proceed = handlers.get(currentPlayer).receive();
		}while(!proceed.equalsIgnoreCase("n"));
	}
	
	private boolean CheckForPopeEffect(LeaderCard lc){
			if(lc.getEffect().getClass().equals(PopeEffect.class) && lc.getPlayed() && lc.getActive()){
				return true;
			}
		return false;
	}
	
	boolean enoughResources(Resource resourceCost){
		if(resourceCost == null){
			return true;
		}
		for(ResourceType rt: resourceCost.getResource().keySet()){
			if(currentPlayer.getBoard().getResources().getResource().get(rt) < resourceCost.getResource().get(rt)){
				handlers.get(currentPlayer).send("You haven't enough resources to play this Leader");
				return false;
			}
		}
		return true;
	}
	
	boolean enoughCard(Map<CardType,Integer> cardCost) {
		if(cardCost == null){
			return true;
		}
		if(currentPlayer.getBoard().getTerritories().size() < cardCost.get(CardType.TERRITORY).intValue()){
			handlers.get(currentPlayer).send("You haven't enough Territories cards  to play this Leader");
			return false;
		}
		if(currentPlayer.getBoard().getBuildings().size() < cardCost.get(CardType.BUILDING).intValue()){
			handlers.get(currentPlayer).send("You haven't enough Building cards  to play this Leader");
			return false;
		}
		if(currentPlayer.getBoard().getCharacters().size() < cardCost.get(CardType.CHARACTER).intValue()){
			handlers.get(currentPlayer).send("You haven't enough Characters cards  to play this Leader");
			return false;
		}
		if(currentPlayer.getBoard().getVentures().size() < cardCost.get(CardType.VENTURE).intValue()){
			handlers.get(currentPlayer).send("You haven't enough Ventures cards  to play this Leader");
			return false;
		}
		return true;
	}
	
	private boolean checkForLucreziaBorgiaCost(LeaderCard lc) {
		if(lc.getName().equalsIgnoreCase("Lucrezia Borgia")){
			if(currentPlayer.getBoard().getTerritories().size() == 6){
				return true;
			}else if(currentPlayer.getBoard().getBuildings().size() == 6){
				return true;
			}else if(currentPlayer.getBoard().getCharacters().size() == 6){
				return true;
			}else if(currentPlayer.getBoard().getVentures().size() == 6){
				return true;
			}else{
				handlers.get(currentPlayer).send("You haven't enough card to play thi leader");
				return false;
			}
		}
		return false;
	}

	@Override
	public void update(Message m) {
		handlers.get(currentPlayer).send(m.getMessage());
		result = m.isResult();
		if(!(m.isResult())){	
			if(modifiedWithServants){
				currentPlayer.addResource(res);
			}
		}	
	}
	
	@Override
	public void update() {
		
	}
}