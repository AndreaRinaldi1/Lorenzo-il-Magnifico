package it.polimi.ingsw.GC_28.view;


import java.io.IOException;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.Cell;
import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;

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

import it.polimi.ingsw.GC_28.effects.GoToHPEffect;

import it.polimi.ingsw.GC_28.effects.PopeEffect;

import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;

import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;
import it.polimi.ingsw.GC_28.server.Observer;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.spaces.Space;

/**
 * This class
 * @author nicoloscipione,andrearinaldi
 * @version 1.0, 06/07/2017
 *
 */

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
	
	/**
	 * 
	 * @return a map containing the player and his client handler.
	 */
	public Map<Player, ClientHandler> getHandlers() {
		return handlers;
	}
	
	/**
	 * 
	 * @return a list of player
	 */
	public List<Player> getSkipped() {
		return skipped;
	}

	/**
	 * 
	 * @return a list of players that are suspended
	 */
	public List<Player> getSuspended() {
		return suspended;
	}

	/**
	 * 
	 * @return the gameModel
	 */
	public GameModel getGameModel() {
		return gameModel;
	}
	
	/**
	 * 
	 * @param handlers map containg a player link to his client handler.
	 */
	public void setHandlers(Map<Player, ClientHandler> handlers) {
		this.handlers = handlers;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * This method sends to every client a gameboard in all its components and to every 
	 * player it sends his family member and playerboard,Everything will be displayed to the player CLI.
	 * Every part is sent as a string. 
	 */
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
	
	/**
	 * This method show to the player if he won or not. 
	 */
	public void declareWinner() {
		handlers.get(gameModel.getPlayers().get(0)).send("YOU WIN!!!");
		displayFinalChart();
	}
	
	/**
	 *This method prepare the final chart, which contains player's position,name and points. It uses an AsciiTable that
	 *contains the necessary information.
	 * @return the final chart as String.
	 */
	public String getChartTable(){
		StringBuilder ret = new StringBuilder();
		AsciiTable chart = new AsciiTable();
		chart.addRule();
		chart.addRow("Position", "Name", "Points");
		chart.addRule();
		for(int i = 0; i < gameModel.getPlayers().size(); i++){
			chart.addRow( i+1 , gameModel.getPlayers().get(i).getName(), gameModel.getPlayers().get(i).getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT));
			chart.addRule();
		}
		ret.append(chart.render() + "\n");
		
		return ret.toString();
	}
	
	/**
	 *  This method is use to display the final chart of the game to every players. 
	 *  It sends the final chart as string to every player's client handler.
	 * 
	 */
	public void displayFinalChart() {
		String chart = getChartTable();
		for(Player p : gameModel.getPlayers()){
			handlers.get(p).send(chart);
		}
	}
	
	/**
	 * This method sends the actual situation of excommunications to the currentPlayer.
	 */
	private void showExcomm(){
		handlers.get(currentPlayer).send(currentPlayer.displayExcommunication());
	}
	
	/**
	 * This method sends the leader cards to the current player.
	 */
	private void showLeaders(){
		handlers.get(currentPlayer).send(currentPlayer.displayLeader());

	}
	
	/**
	 * This method build a chart that display faithpoint, militarypoint and victorypoint of every player.
	 * To build the chart it uses an AsciiTable and return a string.
	 * @return a string which is an asciitable.
	 */
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
		return tracks.render();
	}

	/**
	 * This method wait for currentplayer's input and with some if conditions it starts the method that
	 * corresponds to the user input.
	 * @throws IOException
	 */
	public void play() throws IOException{
		
		if(suspended.contains(currentPlayer)){
			return;
		}
		display();
		do{
			handlers.get(currentPlayer).send("Which move do you want to undertake? [takeCard / goToSpace / skip / askcost / askLeaderCost / specialAction / showMyExcomm / showMyLeaders]");
			
			String line = handlers.get(currentPlayer).receive();
			
			if(("takeCard").equalsIgnoreCase(line)){
				if(askCard(null)){
					break;
				}
			}
			else if(("goToSpace").equalsIgnoreCase(line)){
				if(goToSpace(null)){
					break;
				}
			}
			else if(("skip").equalsIgnoreCase(line)){
				break;
			}
			else if(("askcost").equalsIgnoreCase(line)){
				askCost();
			}
			else if(("specialaction").equalsIgnoreCase(line)){
				specialAction();
			}
			else if(("askLeaderCost").equalsIgnoreCase(line)){
				askLeaderCost();
			}
			else if(("showMyExcomm").equalsIgnoreCase(line)){
				showExcomm();
			}
			else if(("showMyLeaders").equalsIgnoreCase(line)){
				showLeaders();
			}
			else{
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}while(true);
		handlers.get(currentPlayer).send("Do you want to do a special action[y/n]");
		String special = handlers.get(currentPlayer).receive();
		if(("y").equalsIgnoreCase(special)){
			specialAction();
		}else{
			return;
		}
		
	}

	/**
	 * This method check if any player has to skip th first round due to an excommunication.
	 * @param currentRound. An int that represent the current round
	 * @return boolean. True if a player has to skip the first round, false otherwise.
	 */
	public boolean checkSkipped(int currentRound){
		if(skipped.contains(currentPlayer) && currentRound == 1){
			handlers.get(currentPlayer).send("Skipped first turn due to excommunication");
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if a player as an excommunication that is related to resource.
	 *  If an excommunication is active for a player this method applies it.
	 * @param amount. The resource to check for excommunication.
	 * @return The resource, which the same in input if there is no resource excommunication, 
	 * different if an excommunication has been applied 
	 */
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
					EnumMap<ResourceType, Integer> w = new EnumMap<>(ResourceType.class);
					for(ResourceType resType : ResourceType.values()){
						w.put(resType, 0);
					}
					Resource tmpRes = Resource.of(w);
					eff.setChosenAlternativeDiscount(tmpRes);
				}
				amountCopy.modifyResource(eff.getChosenAlternativeDiscount(), true);
				return amountCopy;
			}			
		}
		return amountCopy;
	}
	
	/**
	 * This method ask the current player which councilPrivilege wants to receive. 
	 * @param numberOfCouncilPrivileges. The number of council privilege to give
	 * @param different. If the number of privileges is bigger than two, this boolean is true if the council privileges has to be of a different type.
	 * @return the list of character that represent the council privilege.
	 */
	public List<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
		handlers.get(currentPlayer).send("Which council privilege do you want?");
		for(Character key : CouncilPrivilege.instance().getOptions().keySet()){
			handlers.get(currentPlayer).send("Type " + key + " if you want:");
			handlers.get(currentPlayer).send(CouncilPrivilege.instance().getOptions().get(key).toString());
		}
		ArrayList<Character> choices = new ArrayList<>();
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
	
	/**
	 * This method ask to current player in which space wants to go. Some if clauses check if the user input corresponds to a particolar space
	 * if it's presence in the gameboard it will be return, otherwise the current player has to give another input.
	 * @return space. It returns the chosen space.
	 */
	public Space askWhichSpace() {
		do{
			handlers.get(currentPlayer).send("Enter which space you want to go into [coinSpace / servantSpace / mixedSpace / privilegesSpace / councilPalace / productionSpace / harvestSpace]");
			String chosenSpace = handlers.get(currentPlayer).receive();
			
			if("coinspace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getCoinSpace();
			}
			if("servantspace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getServantSpace();
			}
			if("mixedspace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getMixedSpace();
			}
			if("privilegesspace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getPrivilegesSpace();
			}
			if("councilpalace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getCouncilPalace();
			}
			if("productionspace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getProductionSpace();
			}
			if("harvestspace".equalsIgnoreCase(chosenSpace)){
				return gameModel.getGameBoard().getHarvestSpace();
			}
			else{
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}while(true);
	}
	
	/**
	 * This method performs the action of going in to a particular space, and fill all the necessary information to do a 
	 * space action, when the action is ready it notifies to the observer, at the end of the method it returns the status of the 
	 * action, true if completed successfully, false otherwise.
	 * @param throughEffect
	 * @return boolean, which represent the status of the performed action.
	 */
	public boolean goToSpace(GoToHPEffect throughEffect) {
		FamilyMember familyMember;
		
		SpaceAction spaceAction = new SpaceAction(this, gameModel);
		Space chosenSpace;
		if(!(throughEffect == null)){
			familyMember = new FamilyMember(currentPlayer, false, null); 
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
	
	/**
	 * This method ask if the current player want to active a particular exchange effect
	 * @return boolean, which represent the player's decision.
	 */
	public boolean askPermission() {
		while(true){
			handlers.get(currentPlayer).send("Do you want to apply this effect? [y/n]");
			String line = handlers.get(currentPlayer).receive();
			if(("y").equals(line)){
				return true;
			}
			else if(("n").equals(line)){
				return false;
			}
			handlers.get(currentPlayer).send("Not valid input!");
		}
	}
	
	/**
	 * This method performs the action to take a card from the gameboard, it sets all the necessary information
	 * to do the takecard action. After it collects the informations, it notifies to the observer and at the end
	 * return a boolean that represent if the action has been completed or not.    
	 * @param throughEffect
	 * @return boolean. The status of the action.
	 */
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
	
	/**
	 * This method ask for card's name to the current player.
	 * @return String. the card name.
	 */
	public String askCardName(){
		handlers.get(currentPlayer).send("Enter the name of the card you would like to take: ");
		return handlers.get(currentPlayer).receive();
		
	}
	
	/**
	 * This method asks which family member the current player wants to use. If the input doesn't correspond to a family member 
	 * or the family member has already been used, this method will ask for another input.
	 * @return familyMember. The family member the player wants to use.
	 */
	public FamilyMember askFamilyMember(){
		boolean x = true;
		do{
			handlers.get(currentPlayer).send("Enter which familyMember you would like to use: [ Orange / Black / White / Neutral ]");
			String choice = handlers.get(currentPlayer).receive();
			for(DiceColor color : DiceColor.values()){
				if(choice.equalsIgnoreCase(color.name())){
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
	
	/**
	 * This method asks to the current player if it wants to use servants to increment the value of the family member,
	 * if the player want to increment the value, this method will ask how many servants the current player wants to pay.
	 * @return int. The value that increment the action value.
	 */
	public int askForServantsIncrement(){
		while(true){
			handlers.get(currentPlayer).send("Would you like to pay servants in order to increment the family member action value? [y/n]");
			String choice = handlers.get(currentPlayer).receive();
			if(("y").equals(choice)){
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
					currentPlayer.getBoard().getResources().getResource().put(ResourceType.SERVANT, numberOfServants-increment);
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
			else if (("n").equals(choice)){
				modifiedWithServants = false;
				return 0;
			}
			else{
				handlers.get(currentPlayer).send("Not valid input!");
			}
		}
	}
	
	/**
	 * This method waits to receive from the current player which council privileges would like to receive. 
	 * @return characater, that represent a certain council privilege.
	 */
	public Character askPrivilege(){
		Character c = (Character) handlers.get(currentPlayer).receive().charAt(0);
		while(!(CouncilPrivilege.instance().getOptions().containsKey(c))){
			handlers.get(currentPlayer).send("Not valid input!");
			handlers.get(currentPlayer).send("Which council privilege do you want?");
			c = (Character) handlers.get(currentPlayer).receive().charAt(0);
		}
		return c;
	} 
	

	/**
	 * This method is call if there are two alternative in a card. It waits for the user input and return 
	 * the chosen resource.
	 * @param discount1. First resource of the alternative.
	 * @param discount2. Second resource of the alternative.
	 * @param type. The type of the two alternative.
	 * @return resource. the chosen resource.
	 */
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
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot convert from string to integer in askAlternative");
			}	
		}	
	}
	
	/**
	 * This method is call when there's an alternative in exchange effect. It asks the player which of the two alternative 
	 * would like to receive. 
	 * @param firstCost. The first cost of resource of the two alternative.
	 * @param firstBonus. The resource bonus linked to the first cost.
	 * @param secondCost. The second cost of resource of the alternative.
	 * @param secondBonus. The resource bonus linked to the second cost.
	 * @return int,that represents player's choice. 
	 */
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
				Logger.getAnonymousLogger().log(Level.SEVERE, "cannot convert from string to integer in askAlternativeExchange");
			}
		}
	}
	
	/**
	 * This method sends to the player the cost of a development card chosen by the player.
	 */
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
	
	/**
	 * This method sends to the player the cost of a leader card chosen and owned by the player.
	 */
	private void askLeaderCost(){
		handlers.get(currentPlayer).send(currentPlayer.displayLeaderCost());
		return;
	}
	
	/**
	 * This method check if every player has the faith points to avoid an excommunication. If the player can pay to avoid it 
	 * the method wait the choice of the player as y/n, if the player decide not to pay he will get an excommunication, otherwise 
	 * he pays all his faith points.If the player hasn't enough faith point he will get the excommunication automatically.
	 * @param currentEra. The current era which correspond to a certain excommunication.
	 */
	protected void giveExcommunication(int currentEra) {
		for(Player p : gameModel.getPlayers()){
				handlers.get(p).send(p.displayExcommunication());
				int faith = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
				if(faith < (2+ currentEra)){
					handlers.get(p).send("You recive an Excommunication, because you cannot pay to avoid it");
					p.getExcommunicationTile().get(currentEra -1).setEffect(gameModel.getGameBoard().getExcommunications()[currentEra-1].getEffect());
				}else{
					handlers.get(p).send("Do you want to pay to avoid Excommunication?[y/n]");
					if("n".equalsIgnoreCase(handlers.get(p).receive())){
						p.getExcommunicationTile().add(currentEra-1, gameModel.getGameBoard().getExcommunications()[currentEra-1]);
					}else{
						handlers.get(p).send("You paid to avoid Excommunication, your faith points have been reset to 0");
						int numberOfFaithPoint = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
						Resource bonusForFaithPoint = FinalBonus.instance().getFaithPointTrack().get(numberOfFaithPoint-1);
						p.addResource(bonusForFaithPoint);
						p.getBoard().getResources().getResource().put(ResourceType.FAITHPOINT, 0);
						for(LeaderCard lc : p.getLeaderCards()){
							if(checkForPopeEffect(lc)){
								lc.getEffect().apply(p, this);
							}
						}
					}
				}
			}
	}
	
	/**
	 * This method asks which action related to leader cards, a player wants to undertake.
	 * It waits user input for a particular action and for a leader card's name, then create the special action 
	 * and notify it to the observer
	 */
	void specialAction(){
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
		}while(!("n").equalsIgnoreCase(proceed));
	}
	
	/**
	 * This method check a leader card has the pope effect, which has to be handle in a different way. 
	 * @param lc. The leader card.
	 * @return boolean. True if the leader card has the pope Effect, false otherwise.
	 */
	private boolean checkForPopeEffect(LeaderCard lc){
			if(lc.getEffect().getClass().equals(PopeEffect.class) && lc.getPlayed() && lc.getActive()){
				return true;
			}
		return false;
	}
	
	/**
	 * This method check if the current player has enough resources to play a certain leader card.
	 * @param resourceCost. The leader card's resource cost.
	 * @return boolean. true if the player can play the card, false otherwise.
	 */
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
	
	/**
	 * This method check if the current player has enough cards to play a certain leader card.
	 * @param cardCost. The leader card's card cost.
	 * @return boolean. true if the player can play the card, false otherwise.
	 */
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
	
	
}