package it.polimi.ingsw.GC_28.model;


import java.io.IOException;
import java.nio.BufferOverflowException;
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
import it.polimi.ingsw.GC_28.core.TakeCardAction;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;
import it.polimi.ingsw.GC_28.server.Observer;
import it.polimi.ingsw.GC_28.spaces.ProdHarvType;
import it.polimi.ingsw.GC_28.spaces.Space;

public class Game extends Observable<Action> implements Runnable, Observer<Message>{
	private GameModel gameModel;
	Timer timer;
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
	private List<Player> suspended = new ArrayList<>();
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
						
						// TIMER CHE "FUNZIONA" SOLO SU PRIMA DOMANDA 
						/*boolean x = false;
						long time = System.currentTimeMillis() + 16000;
						Thread t = new Thread(){
							public void run(){
								System.out.println(2);
								try {
									play();
								} catch (IOException e) {
									Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
								}
								catch(IndexOutOfBoundsException ee){
									
									System.out.println("dentro exception");
									for(Player p : suspended){
										System.out.println(p.getName());
									}
								}
								System.out.println(3);
							}
						};
						t.start();
						System.out.println(4);
						while(t.isAlive()){
							if(System.currentTimeMillis() > time){
								
								x = true;
								break;
							}
						}
						if(x){
							t.interrupt();
							
							handlers.get(currentPlayer).getOut().println("sospeso");
							handlers.get(currentPlayer).getOut().flush();
							System.out.println("sono passati 15 sec");
							suspended.add(currentPlayer);
							handlers.get(currentPlayer).getOut().println("you have been suspended. Type 'reconnect' to play again");
							handlers.get(currentPlayer).getOut().flush();

							new Thread(new Listener(suspended,currentPlayer, handlers.get(currentPlayer))).start();
							System.out.println("dentro if(x)");
							for(Player p : suspended){
								System.out.println(p.getName());
							}
						}
						
						if(currentTurn == (gameModel.getPlayers().size()-1)){
							currentPlayer = gameModel.getPlayers().get(0);
						}else{
							currentPlayer = gameModel.getPlayers().get((currentTurn+1));
						}*/
					
						/*
						t.start();
						try {
							t.join(15000);
						} catch (InterruptedException e) {
							System.out.println("sono passati 15 sec");
							suspended.add(currentPlayer);
							for(Player p : suspended){
								System.out.println(p.getName());
							}
							handlers.get(currentPlayer).getOut().println("you have been suspended");
						}
						finally{
							System.out.println(5);

							if(currentTurn == (gameModel.getPlayers().size()-1)){
								currentPlayer = gameModel.getPlayers().get(0);
							}else{
								currentPlayer = gameModel.getPlayers().get((currentTurn+1));
							}
						}
							*/	
						
					} 	
				}
				checkSkippedPlayers();
				if(!(currentEra == 3 && currentPeriod == 2)){//if it's the third Era and the second period it's evaluate as false;
					bs.setUpBoard();
					currentPlayer = gameModel.getPlayers().get(0);
				}
			}
			giveExcommunication();
		}
		applyFinalBonus();
		applyFinalMalus();
		sortBy(gameModel.getPlayers(), ResourceType.MILITARYPOINT);
		assignBonusForMilitary();
		sortBy(gameModel.getPlayers(), ResourceType.VICTORYPOINT);
		declareWinner();
		
	}
	
	public void declareWinner(){
		handlers.get(gameModel.getPlayers().get(0)).getOut().println("YOU WIN!!!");
		handlers.get(gameModel.getPlayers().get(0)).getOut().flush();
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
	
	public void displayFinalChart(){
		String chart = getChartTable();
		for(Player p : gameModel.getPlayers()){
			handlers.get(p).getOut().println(chart);
			handlers.get(p).getOut().flush();
		}
	}
	
	public void assignBonusForMilitary(){
		FinalBonus finalBonus = FinalBonus.instance();
		int i = 0;
		int j = 0;
		while(j < gameModel.getPlayers().size() && i < finalBonus.getFinalMilitaryTrack().size()){
			Resource x = finalBonus.getFinalMilitaryTrack().get(i);
			gameModel.getPlayers().get(j).addResource(x);
			while((j < gameModel.getPlayers().size()-1) && (gameModel.getPlayers().get(j).getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT) ==
						gameModel.getPlayers().get(j+1).getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT))){
				gameModel.getPlayers().get(j+1).addResource(finalBonus.getFinalMilitaryTrack().get(i));
				j++;
			}
			i++;
			j++;
		}
	}
	
	public void setHandlers(Map<Player, ClientHandler> handlers) {
		this.handlers = handlers;
	}
	
	public void sortBy(List<Player> players, ResourceType type){
		players.sort((p1, p2) -> p2.getBoard().getResources().getResource().get(type) - p1.getBoard().getResources().getResource().get(type));
	}

	
	public void applyFinalBonus(){
		FinalBonus finalBonus = FinalBonus.instance();
		for(Player p : gameModel.getPlayers()){
			p.getBoard().getResources().modifyResource(finalBonus.getFinalTerritoriesBonus().get(p.getBoard().getTerritories().size()), true);			
			p.getBoard().getResources().modifyResource(finalBonus.getFinalCharactersBonus().get(p.getBoard().getCharacters().size()), true);
			
			for(Venture v : p.getBoard().getVentures()){
				p.addResource(v.getPermanentEffect().getResourceBonus());
			}
			
			int finalResources = 0;
			for(ResourceType type : p.getBoard().getResources().getResource().keySet()){
				if(!type.equals(ResourceType.VICTORYPOINT) && !type.equals(ResourceType.MILITARYPOINT) && !type.equals(ResourceType.FAITHPOINT)){
					finalResources += p.getBoard().getResources().getResource().get(type);
				}
			}
			int actualVictoryPoints = p.getBoard().getResources().getResource().get(ResourceType.VICTORYPOINT);
			
			p.getBoard().getResources().getResource().put(ResourceType.VICTORYPOINT, actualVictoryPoints + (finalResources/finalBonus.getResourceFactor()));
			
		}
	}
	
	
	public void applyFinalMalus(){
		ExcommunicationTile t;
		for(Player p : gameModel.getPlayers()){
			t = p.getExcommunicationTile().get(currentEra-2);
			if(t != null){
				t.getEffect().apply(p, this);
			}
		}
	}

	public void checkDiceReduction(){  //se i giocatori tra le scomuniche hanno reducedice applico effetto
		for(Player p : gameModel.getPlayers()){
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				if(t.getEffect() instanceof ModifyDiceEffect){
					t.getEffect().apply(p, this);
				}
			}
		}
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
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				if(t.getEffect() instanceof OtherEffect){
					OtherEffect otherEffect = (OtherEffect) t.getEffect();
					if(otherEffect.getType().equals(EffectType.SKIPROUNDEFFECT)){
						skipped.add(p);
					}
				}
			}
		}
		
	}
	
	private void display(){
		for(Player p: gameModel.getPlayers()){
			String gb = gameModel.getGameBoard().display();
			String tracks = displayTracks();
			handlers.get(p).getOut().println(gb);
			handlers.get(p).getOut().println(tracks);
			handlers.get(p).getOut().println(p.getBoard().display());
			handlers.get(p).getOut().println(p.displayFamilyMember());
			handlers.get(p).getOut().flush();
		}
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
		display();
		if(suspended.contains(currentPlayer)){
			return;
		}
		if(skipped.contains(currentPlayer) && currentRound == 1){ //se è tra i giocatori in skipped allora salta turno
			handlers.get(currentPlayer).getOut().println("Skipped first turn due to excommunication");
			return;
		}
		do{
			handlers.get(currentPlayer).getOut().println("Which move do you want to undertake? [takeCard / goToSpace / skip / askcost / askLeaderCost / specialAction / showMyExcomm / showMyLeaders]");
			handlers.get(currentPlayer).getOut().flush();	
			
			String line = handlers.get(currentPlayer).getIn().nextLine();
			
			if(line.equalsIgnoreCase("takeCard")){
				if(askCard(null)){
					break;
					//return;
				}
			}
			else if(line.equalsIgnoreCase("goToSpace")){
				if(goToSpace(null)){
					break;
					//return;
				}
			}
			else if(line.equalsIgnoreCase("skip")){
				break;
				//return;
			}
			else if(line.equalsIgnoreCase("askcost")){
				askCost();
			}
			/*else if(line.equals("disconnect")){
				return;
			}*/
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
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
			}
		}while(true);
		handlers.get(currentPlayer).getOut().println("Do you want to do a special action[y/n]");
		handlers.get(currentPlayer).getOut().flush();	
		String special = handlers.get(currentPlayer).getIn().nextLine();
		if(special.equalsIgnoreCase("y")){
			specialAction();
		}else{
			return;
		}
		
	}

	private void showExcomm(){
		handlers.get(currentPlayer).getOut().println(currentPlayer.displayExcommunication());
	}
	
	private void showLeaders(){
		handlers.get(currentPlayer).getOut().println(currentPlayer.displayLeader());

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
				System.out.println("checkEx 1");
				DiscountEffect eff = (DiscountEffect) t.getEffect();
				boolean disc = false;
				boolean altDisc = false;
				
				for(ResourceType resType : eff.getDiscount().getResource().keySet()){ //guardo tra i resourceType del discounteff
					System.out.println("checkEx 2");
					if(!(eff.getDiscount().getResource().get(resType).equals(0))){ //se ne trovo uno diverso da 0
						System.out.println("checkEx 3");
						System.out.println(amount.toString());
						if(!amount.getResource().get(resType).equals(0)){ // e se io l'ho preso quel tipo di risorsa
							System.out.println("checkEx 4");
							disc = true; //allora setto un bool
							break;
						}
					}
				}
				System.out.println("check 5");
				if(eff.getAlternativeDiscountPresence()){ //se ho due alternative
					System.out.println("check 6");
					for(ResourceType resType : eff.getAlternativeDiscount().getResource().keySet()){ 
						if(!(eff.getAlternativeDiscount().getResource().get(resType).equals(0))){
							System.out.println("check 7");
							if(!amount.getResource().get(resType).equals(0)){
								System.out.println("check 8");
								altDisc = true; // se ho preso anche la risorsa diversa da zero dell'alternativediscount setto un bool
								break;
							}
						}
					}
				}
					
				System.out.println("check 9");
				System.out.println("disc " + disc);
				System.out.println("altDisc "+ altDisc);
				if(disc && altDisc){ // se ho preso entrambi chiedo quale togliere
					System.out.println("check 10");
					eff.setChosenAlternativeDiscount(askAlternative(eff.getDiscount(), eff.getAlternativeDiscount(), "malus")); 
				}
				else if(disc){ //altrimenti tolgo disc..
					System.out.println("check 11");
					eff.setChosenAlternativeDiscount(eff.getDiscount());
				}
				else if(altDisc){ //o alternative disc
					System.out.println("check 11");
					eff.setChosenAlternativeDiscount(eff.getAlternativeDiscount());
				}
				else{ //se non ho preso niente di quei tipi non tolgo niente
					System.out.println("check 12");
					EnumMap<ResourceType, Integer> w = new EnumMap<ResourceType, Integer>(ResourceType.class);
					for(ResourceType resType : ResourceType.values()){
						w.put(resType, 0);
					}
					Resource res = Resource.of(w);
					System.out.println("check 13");
					eff.setChosenAlternativeDiscount(res);
				}
				System.out.println("check 14");
				System.out.println(amount.toString());
				amountCopy.modifyResource(eff.getChosenAlternativeDiscount(), true);
				System.out.println(amount.toString());
				return amountCopy;
			}			
		}
		return amountCopy;
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
	
	
	public boolean askPermission(){
		while(true){
			handlers.get(currentPlayer).getOut().println("Do you want to apply this effect? [y/n]");
			handlers.get(currentPlayer).getOut().flush();
			String line = handlers.get(currentPlayer).getIn().nextLine();
			if(line.equals("y")){
				return true;
			}
			else if(line.equals("n")){
				return false;
			}
			handlers.get(currentPlayer).getOut().println("Not valid input!");
			handlers.get(currentPlayer).getOut().flush();
		}
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
		System.out.println(5);
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
					modifiedWithServants = false;
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
	
	void askCost(){
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
	
	private void askLeaderCost(){
		handlers.get(currentPlayer).getOut().println(currentPlayer.displayLeaderCost());
		handlers.get(currentPlayer).getOut().flush();
		return;
	}
	
	private void giveExcommunication(){
		for(Player p : gameModel.getPlayers()){
				handlers.get(p).getOut().println(p.displayExcommunication());
				handlers.get(p).getOut().flush();
				int faith = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
				if(faith < (2+ currentEra)){
					handlers.get(p).getOut().println("You recive an Excommunication, because you cannot pay to avoid it");
					handlers.get(p).getOut().flush();
					p.getExcommunicationTile().get(currentEra -1).setEffect(gameModel.getGameBoard().getExcommunications()[currentEra-1].getEffect());
					//p.getExcommunicationTile().add(currentEra-1, gameBoard.getExcommunications()[currentEra-1]);
				}else{
					handlers.get(p).getOut().println("Do you want to pay to avoid Excommunication?[y/n]");
					handlers.get(p).getOut().flush();
					if(handlers.get(p).getIn().nextLine().equalsIgnoreCase("n")){
						p.getExcommunicationTile().add(currentEra-1, gameModel.getGameBoard().getExcommunications()[currentEra-1]);
					}else{
						handlers.get(p).getOut().println("You paid to avoid Excommunication, your faith points have been reset to 0");
						handlers.get(p).getOut().flush();
						int numberOfFaithPoint = p.getBoard().getResources().getResource().get(ResourceType.FAITHPOINT);
						Resource bonusForFaithPoint = FinalBonus.instance().getFaithPointTrack().get(numberOfFaithPoint-1);
						p.addResource(bonusForFaithPoint);
						p.getBoard().getResources().getResource().put(ResourceType.FAITHPOINT, 0);
						for(LeaderCard lc : p.getLeaderCards()){
							if(CheckForPopeEffect(lc)){//FIXME
								lc.getEffect().apply(p, this);
							}
						}
					}
				}
			}
	}
	
	void specialAction(){//FIXME
		handlers.get(currentPlayer).getOut().println(currentPlayer.displayLeader());
		handlers.get(currentPlayer).getOut().flush();
		String procede;
		do{
			handlers.get(currentPlayer).getOut().println("Which special action do you want to undertake?[discard/play/activate]");
			handlers.get(currentPlayer).getOut().flush();
			String line = handlers.get(currentPlayer).getIn().nextLine();
			if(line.equalsIgnoreCase("discard")){
				handlers.get(currentPlayer).getOut().println("Which Leader do you want to discard?");
				handlers.get(currentPlayer).getOut().flush();
				String card = handlers.get(currentPlayer).getIn().nextLine();
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
				handlers.get(currentPlayer).getOut().println("Which Leader do you want to play?");
				handlers.get(currentPlayer).getOut().flush();
				String card = handlers.get(currentPlayer).getIn().nextLine();
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
				handlers.get(currentPlayer).getOut().println("Which Leader do you want to active?");
				handlers.get(currentPlayer).getOut().flush();
				String card = handlers.get(currentPlayer).getIn().nextLine();
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
								handlers.get(currentPlayer).getOut().println("You can't activate this card because you already played it in this turn");
								handlers.get(currentPlayer).getOut().flush();
								break;
							}
						}else{
							handlers.get(currentPlayer).getOut().println("You can't activate this card because you've not played it yet");
							handlers.get(currentPlayer).getOut().flush();
							break;
						}
					}
				}
			}
			else{
				handlers.get(currentPlayer).getOut().println("Not valid input!");
				handlers.get(currentPlayer).getOut().flush();
			}
			handlers.get(currentPlayer).getOut().println("Do you want to do another special action?[y/n]");
			handlers.get(currentPlayer).getOut().flush();
			procede = handlers.get(currentPlayer).getIn().nextLine();
		}while(!procede.equalsIgnoreCase("n"));
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
				handlers.get(currentPlayer).getOut().println("You haven't enough resources to play this Leader");
				handlers.get(currentPlayer).getOut().flush();
				return false;
			}
		}
		return true;
	}
	
	boolean enoughCard(Map<CardType,Integer> cardCost){
		if(cardCost == null){
			return true;
		}
		if(currentPlayer.getBoard().getTerritories().size() < cardCost.get(CardType.TERRITORY).intValue()){
			handlers.get(currentPlayer).getOut().println("You haven't enough Territories cards  to play this Leader");
			handlers.get(currentPlayer).getOut().flush();
			return false;
		}
		if(currentPlayer.getBoard().getBuildings().size() < cardCost.get(CardType.BUILDING).intValue()){
			handlers.get(currentPlayer).getOut().println("You haven't enough Building cards  to play this Leader");
			handlers.get(currentPlayer).getOut().flush();
			return false;
		}
		if(currentPlayer.getBoard().getCharacters().size() < cardCost.get(CardType.CHARACTER).intValue()){
			handlers.get(currentPlayer).getOut().println("You haven't enough Characters cards  to play this Leader");
			handlers.get(currentPlayer).getOut().flush();
			return false;
		}
		if(currentPlayer.getBoard().getVentures().size() < cardCost.get(CardType.VENTURE).intValue()){
			handlers.get(currentPlayer).getOut().println("You haven't enough Ventures cards  to play this Leader");
			handlers.get(currentPlayer).getOut().flush();
			return false;
		}
		return true;
	}
	
	private boolean checkForLucreziaBorgiaCost(LeaderCard lc){
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
				handlers.get(currentPlayer).getOut().println("You haven't enough card to play thi leader");
				handlers.get(currentPlayer).getOut().flush();
				return false;
			}
		}
		return false;
	}

	@Override
	public void update(Message m) {
		handlers.get(currentPlayer).getOut().println(m.getMessage());
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