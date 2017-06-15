package it.polimi.ingsw.GC_28.client;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import it.polimi.ingsw.GC_28.boards.GameBoard;
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
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.ServantEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.server.Controller;
import it.polimi.ingsw.GC_28.server.Message;
import it.polimi.ingsw.GC_28.server.Observable;
import it.polimi.ingsw.GC_28.server.Observer;
import it.polimi.ingsw.GC_28.spaces.SpaceType;

public class ClientWriter extends Observable<Action> implements Runnable, Observer<Message>{
	
	Socket socket;
	Player player;
	GameBoard gameBoard;
	Scanner stdin;
	EnumMap<ResourceType, Integer> decrement = new EnumMap<>(ResourceType.class);
	CouncilPrivilege councilPrivilege;
	Resource res ;
	int incrementThroughServants;
	boolean modifiedWithServants = false;
	ObjectOutputStream socketOut;
	//ReentrantLock lock = new ReentrantLock();
	Boolean result;
	public ClientWriter(Socket socket,Player player){
		this.socket = socket;
		this.player = player;
	}
	
	@Override
	public void run(){
		Scanner stdin = null;
		try {
			boolean init = false;
			socketOut = new ObjectOutputStream(socket.getOutputStream());//new PrintWriter(socket.getOutputStream());
			stdin = new Scanner(System.in);
			//System.out.println("prova");
			String prova = new String();
			do{
				//prova = stdin.next();
				if(stdin.hasNextLine()){
					prova = stdin.nextLine();
					if(!("start").equalsIgnoreCase(prova)){
						socketOut.writeUTF(prova);
						socketOut.flush();
					}
				}
				if(stdin.hasNextInt()){
					Integer choice = stdin.nextInt();
					socketOut.writeInt(choice);
					socketOut.flush();
					break;
				}
			}while(!("start".equalsIgnoreCase(prova)));
			//System.out.println(this.player.toString());
			this.registerObserver(new Controller());
			Scanner scan = new Scanner(System.in);
			while(true){
				//System.out.println(lock.isLocked());
				//System.out.println(gameBoard.getCoinSpace().isFree());
				String line = scan.nextLine();
				//Scanner scan = new Scanner(line);
				//System.out.println(player.toString());
				if(line.equalsIgnoreCase("takeCard")){
					askCard(null);
					socketOut.writeObject(gameBoard);
					socketOut.flush();
					socketOut.reset();
					/*socketOut.writeObject(player);
					socketOut.flush();
					socketOut.reset();*/
					/*System.out.println("wait for lock");
					lock.lock();
					System.out.println("unlock");
					lock.unlock();*/
				}
				else if(line.equalsIgnoreCase("goToSpace")){
					goToSpace(null);
					socketOut.writeObject(gameBoard);
					socketOut.flush();
					socketOut.reset();
					/*socketOut.writeObject(player);
					socketOut.flush();
					socketOut.reset();*/
					/*System.out.println("wait for lock");
					lock.lock();
					System.out.println("unlock");
					lock.unlock();*/
				}
				else if(line.equalsIgnoreCase("skip")){
					socketOut.writeObject(null);
					socketOut.flush();
					socketOut.reset();
					//return;
				}
				else if(line.equalsIgnoreCase("privileges")){
					System.out.println("passato");
				}
				/*else if(line.equals("disconnect")){
					return;
				}*/
				/*else if(line.equalsIgnoreCase("specialaction")){
					specialAction();
				}*/
				else{
					System.out.println("input not valid!");
				}
				
				
				//socketOut.writeObject(obj);
				//socketOut.flush();
				if(line.equals("close")){
					socket.close();
					break;
				}
			} 
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}	
		finally{
			try{
				socket.close();
			}catch (IOException e) {
				System.out.println(e.getMessage());			
			}
			if(stdin != null){
				stdin.close();
			}

		}
	}
	
	/*public void setLock(){
		this.lock.lock();
	}
	
	public void setUnLock(){
		this.lock.unlock();
	}*/
	
	public void setGameBoard(GameBoard gameBoard){
		this.gameBoard = gameBoard;
	}
	
	public void setPlayer(Player p){
		this.player = p;
	}
	
	public void setCouncilPrivilege(CouncilPrivilege privilege){
		this.councilPrivilege = privilege;
	}
	
	public CouncilPrivilege getCouncilPrivilege(){
		return councilPrivilege;
	}
	
	public boolean askCard(TakeCardEffect throughEffect) throws IOException{ //throughEffect = null se non è un askcard che viene da effetto ma da mossa normale
		/*if(throughEffect == null){
			socketOut.writeUTF("lock");
			socketOut.flush();
			socketOut.reset();
		}*/
		FamilyMember familyMember;							// if it's null the first condition will throw a null pointer exception(N)
		TakeCardAction takeCardAction = new TakeCardAction(gameBoard,this);
		stdin = new Scanner(System.in);
		if(!(throughEffect == null)){ //se viene da effetto gli dico cosa può prendere 
			if(throughEffect.getCardType() == null){
				System.out.println("You can take another card of any type of value " + throughEffect.getActionValue()+"[if can't take any card just press enter]");
			}
			else{
				System.out.println("You can take a card of type: " + throughEffect.getCardType().name() + " of value " + throughEffect.getActionValue()+"[if can't take any card just press enter]");
			}
		}
		
		String name = askCardName();
		
		if(throughEffect == null){ //se viene da mossa gli chiedo quale fm vuole usare
			familyMember = askFamilyMember();
		}
		else{
			familyMember = new FamilyMember(player, false, null); //altrimenti uno fittizio
			familyMember.setValue(throughEffect.getActionValue());
		}

		
		
		incrementThroughServants = askForServantsIncrement();
		familyMember.modifyValue(incrementThroughServants);
		decrement.put(ResourceType.SERVANT, incrementThroughServants);
		res = Resource.of(decrement);
		
		takeCardAction.setFamilyMember(familyMember);
		takeCardAction.setName(name);
		takeCardAction.setThroughEffect(throughEffect);
		takeCardAction.setWriter(this);
		
		if(takeCardAction.isApplicable()){
			takeCardAction.apply();
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public int askForServantsIncrement(){
		stdin = new Scanner(System.in);
		while(true){
			System.out.println("Would you like to pay servants in order to increment the family member action value? [y/n]");
			if(stdin.hasNextInt()){
				System.out.println("Not valid input!");
				stdin.nextLine();
				continue;
			}
			else if(stdin.hasNextLine()){
				String choice = stdin.nextLine();
				if(choice.equals("y")){
					System.out.println("How many servants would you like to pay?");
					if(stdin.hasNextInt()){
						int increment = stdin.nextInt();
						int numberOfServants = player.getBoard().getResources().getResource().get(ResourceType.SERVANT);
						if(increment <= numberOfServants){
							player.getBoard().getResources().getResource().put(ResourceType.SERVANT, (numberOfServants-increment));
							modifiedWithServants = true;
							stdin.nextLine();
							
							for(ExcommunicationTile t : player.getExcommunicationTile()){ //guardo se tra le scomuniche ha servanteffect
								if(t.getEffect() instanceof ServantEffect){
									ServantEffect eff = (ServantEffect) t.getEffect();
									return increment * eff.getIncrement() / eff.getNumberOfServant(); // se sì allora applico l'effetto
								}

							}
							
							return increment;
						}
						else{
							System.out.println("You don't have so many servants!");
							stdin.nextLine();
						}
					}
					else{
						System.out.println("Not valid input!");
					}
				}
				else if (choice.equals("n")){
					return 0;
				}
				else{
					System.out.println("Not valid input!");
				}
			}
		}
	}
	
	public String askCardName(){
		stdin = new Scanner(System.in);
		do{
			System.out.println("Enter the name of the card you would like to take: ");
			if(stdin.hasNextInt()){
				System.out.println("Not valid input!");
				stdin.nextLine();
			}
			else{
				return stdin.nextLine();
			}
		}while(true);
		
	}
	
	public FamilyMember askFamilyMember(){
		stdin = new Scanner(System.in);
		boolean x = true;
		do{
			System.out.println(player.getFamilyMembers()[0].toString());
			System.out.println("Enter which familyMember you would like to use: [ Orange / Black / White / Neutral ]");
			String choice = stdin.nextLine();
			for(DiceColor color : DiceColor.values()){
				if(choice.toUpperCase().equals(color.name())){
					for(FamilyMember familyMember : player.getFamilyMembers()){
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
	
	public void goToSpace(GoToHPEffect throughEffect) throws IOException{
		/*socketOut.writeUTF("lock");
		socketOut.flush();
		socketOut.reset();*/
		FamilyMember familyMember;
		
		SpaceAction spaceAction = new SpaceAction(gameBoard);
		SpaceType chosenSpace;
		
		if(!(throughEffect == null)){
			familyMember = new FamilyMember(player, false, null); //familyMember fittizio
			familyMember.setValue(throughEffect.getActionValue());
			if(throughEffect.isHarvest()){
				chosenSpace = SpaceType.HARVESTSPACE;
			}
			else{
				chosenSpace = SpaceType.PRODUCTIONSPACE;
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
		spaceAction.setSpaceName(chosenSpace);
		spaceAction.setWriter(this);
		spaceAction.setThroughEffect(throughEffect);
		//System.out.println(familyMember.toString());
		if(spaceAction.isApplicable()){
			spaceAction.apply();
		}
	}
	
	public SpaceType askWhichSpace(){
		stdin = new Scanner(System.in);
		do{
			System.out.println("Enter which space you want to go into [coinSpace / servantSpace / mixedSpace / privilegesSpace / councilPalace / productionSpace / harvestSpace]");
			if(stdin.hasNextInt()){
				System.out.println("Not valid input!");
				stdin.nextLine();
			}
			else{
				String chosenSpace = stdin.nextLine();
				if(chosenSpace.equalsIgnoreCase(SpaceType.COINSPACE.name())){
					return SpaceType.COINSPACE;
				}
				if(chosenSpace.equalsIgnoreCase(SpaceType.SERVANTSPACE.name())){
					return SpaceType.SERVANTSPACE;
				}
				if(chosenSpace.equalsIgnoreCase(SpaceType.COUNCILPALACE.name())){
					return SpaceType.COUNCILPALACE;
				}
				if(chosenSpace.equalsIgnoreCase(SpaceType.MIXEDSPACE.name())){
					return SpaceType.MIXEDSPACE;
				}
				if(chosenSpace.equalsIgnoreCase(SpaceType.PRIVILEGESSPACE.name())){
					return SpaceType.PRIVILEGESSPACE;
				}
				if(chosenSpace.equalsIgnoreCase(SpaceType.PRODUCTIONSPACE.name())){
					return SpaceType.PRODUCTIONSPACE;
				}
				if(chosenSpace.equalsIgnoreCase(SpaceType.HARVESTSPACE.name())){
					return SpaceType.HARVESTSPACE;
				}
				else{
					System.out.println("Not valid input!");
				}
			}
		}while(true);
	}
	
	public Resource askAlternative(Resource discount1, Resource discount2,String type){
		stdin = new Scanner(System.in);
		System.out.println("Which of the two following " + type + " do you want to apply? [1/2]");
		System.out.println(discount1.toString());
		System.out.println(discount2.toString());
		int choice;
		while(true){
			if(stdin.hasNextInt()){
				choice = stdin.nextInt();
				if(choice == 1){
					stdin.nextLine();
					stdin.close();
					return discount1;
				}
				else if(choice == 2){
					stdin.nextLine();
					stdin.close();
					return discount2;
				}
				else{
					System.out.println("Not valid input!");
					System.out.println("Which of the two " + type + " do you want to apply? [1/2]");
					stdin.nextLine();
					continue;
				}
			}
			else{
				stdin.nextLine();
				System.out.println("Not valid input!");
				System.out.println("Which of the two discounts do you want to apply? [1/2]");
			}
			
		}
	}
	
	public ArrayList<Character> askPrivilege(int numberOfCouncilPrivileges, boolean different){
		System.out.println("Which council privilege do you want?");
		for(Character key : councilPrivilege.getOptions().keySet()){
			System.out.println("in here");
			System.out.println("Type " + key + " if you want:");
			System.out.println(councilPrivilege.getOptions().get(key).toString());
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
		//System.out.println(choices.toString());
		//socketOut.reset();
		return choices;
	}
	
	public Character askPrivilege(){
		stdin = new Scanner(System.in);
		//Character c = (Character) stdin.nextLine().charAt(0);
		String in = stdin.nextLine();
		Character c = (Character)in.charAt(0);
		while(!(councilPrivilege.getOptions().containsKey(c))){
			System.out.println("Not valid input!");
			System.out.println("Which council privilege do you want?");
			String in2 = stdin.nextLine();
			c = (Character) in2.charAt(0);
		}
		return c;
	}
	public Resource checkResourceExcommunication(Resource amount, Player player){
		for(ExcommunicationTile t : player.getExcommunicationTile()){ //guardo tra le scomuniche del currentPlayer
			if(t.getEffect() instanceof DiscountEffect){ //se trovo un discounteffect
				System.out.println("checkEx 1");
				DiscountEffect eff = (DiscountEffect) t.getEffect();
				boolean disc = false;
				boolean altDisc = false;
				
				for(ResourceType resType : eff.getDiscount().getResource().keySet()){ //guardo tra i resourceType del discounteff
					System.out.println("checkEx 2");
					if(!(eff.getDiscount().getResource().get(resType).equals(0))){ //se ne trovo uno diverso da 0
						System.out.println("checkEx 3");
						if(!amount.getResource().get(resType).equals(0)){ // e se io l'ho preso quel tipo di risorsa
							System.out.println("checkEx 4");
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
	
	
	@Override
	public void update(Message m) {
		System.out.println(m.getMessage());
		result = m.isResult();
		if(!(m.isResult())){	
			if(modifiedWithServants){
				player.addResource(res);
			}
		}	
	}
	
	@Override
	public void update() {
		
	}
	
}