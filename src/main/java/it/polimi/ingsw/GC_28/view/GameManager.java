package it.polimi.ingsw.GC_28.view;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.boards.FinalBonus;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.Venture;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.ModifyDiceEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.Listener;
import it.polimi.ingsw.GC_28.model.Player;

public class GameManager implements Runnable{
	private int currentEra = 1;
	private int currentPeriod = 1;
	private int currentRound = 1;
	private int currentTurn = 0;
	private Player currentPlayer;

	private GameView view;
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		setCurrentPlayer(view.getGameModel().getPlayers().get(0));
		BoardSetup bs = new BoardSetup(this);
		/*EnumMap<ResourceType, Integer> res = new EnumMap<>(ResourceType.class);//TODO remove this
		res.put(ResourceType.COIN, 20);
		res.put(ResourceType.WOOD, 20);
		res.put(ResourceType.STONE, 20);
		res.put(ResourceType.SERVANT, 20);
		res.put(ResourceType.VICTORYPOINT, 20);
		res.put(ResourceType.MILITARYPOINT, 20);
		res.put(ResourceType.FAITHPOINT, 10);
		Resource resources = Resource.of(res);
		currentPlayer.getBoard().setResources(resources);
		System.out.println(currentPlayer.getBoard().getResources().toString());*/
		for(currentEra = 1; currentEra <= 3; currentEra++){
			skipPlayers();
			for(currentPeriod = 1; currentPeriod <= 2; currentPeriod++){
				checkDiceReduction();				
				for(currentRound = 1; currentRound <= 4; currentRound++){					
					for(currentTurn = 0; currentTurn < view.getGameModel().getPlayers().size(); currentTurn++){
						/*try {
							view.setCurrentPlayer(currentPlayer);
							if(!view.checkSkipped(currentRound)){
								view.play();
							}
						} catch (IOException e) {
							Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
						}
						if(currentTurn == (view.getGameModel().getPlayers().size()-1)){
							currentPlayer = view.getGameModel().getPlayers().get(0);
						}else{
							currentPlayer = view.getGameModel().getPlayers().get((currentTurn+1));
						}*/
						
						// TIMER CHE "FUNZIONA" SOLO SU PRIMA DOMANDA 
						boolean timeEnded = false;
						long time = System.currentTimeMillis() + 16000;
						Thread t = new Thread(){
							public void run(){
								try {
									view.setCurrentPlayer(currentPlayer);
									view.play();
								} catch (IOException e) {
									Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
								}
								catch(IndexOutOfBoundsException ee){
									System.out.println(ee.getMessage());
									
								}
							}
						};
						t.start();
						while(t.isAlive()){
							if(System.currentTimeMillis() > time){
								timeEnded = true;
								break;
							}
						}
						if(timeEnded){
							t.interrupt();
							view.getHandlers().get(currentPlayer).send("suspended");
							view.getHandlers().get(currentPlayer).send("you have been suspended. Type 'reconnect' to play again");
							view.getSuspended().add(currentPlayer);
							new Thread(new Listener(view.getSuspended() ,currentPlayer, view.getHandlers().get(currentPlayer))).start();
							System.out.println("dentro if(x)");
							for(Player p : view.getSuspended()){
								System.out.println(p.getName());
							}
						}
						
						if(currentTurn == (view.getGameModel().getPlayers().size()-1)){
							currentPlayer = view.getGameModel().getPlayers().get(0);
						}else{
							currentPlayer = view.getGameModel().getPlayers().get((currentTurn+1));
						}
					
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
					currentPlayer = view.getGameModel().getPlayers().get(0);
				}
			}
			System.out.println("finito era"+ currentEra);
			view.giveExcommunication(currentEra);
		}
		System.out.println("inizio final bonus");
		applyFinalBonus();
		System.out.println("bonus dati, faccio i malus");
		applyFinalMalus();
		System.out.println("malus dati, inizio punti militari");
		sortBy(view.getGameModel().getPlayers(), ResourceType.MILITARYPOINT);
		assignBonusForMilitary();
		sortBy(view.getGameModel().getPlayers(), ResourceType.VICTORYPOINT);
		System.out.println("dichiaro vincitore");
		view.declareWinner();
		
		
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
	

	public GameView getView() {
		return view;
	}

	
	public void setView(GameView view) {
		this.view = view;
	}

	public int getCurrentEra() {
		return currentEra;
	}

	public void setCurrentEra(int currentEra) {
		this.currentEra = currentEra;
	}
	
	public void sortBy(List<Player> players, ResourceType type){
		players.sort((p1, p2) -> p2.getBoard().getResources().getResource().get(type) - p1.getBoard().getResources().getResource().get(type));
	}

	public void assignBonusForMilitary(){
		FinalBonus finalBonus = FinalBonus.instance();
		List<Player> players = view.getGameModel().getPlayers();
		int i = 0;
		int j = 0;
		while(j < players.size() && i < finalBonus.getFinalMilitaryTrack().size()){
			Resource x = finalBonus.getFinalMilitaryTrack().get(i);
			view.getGameModel().getPlayers().get(j).addResource(x);
			while((j < players.size()-1) && (players.get(j).getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT) ==
						players.get(j+1).getBoard().getResources().getResource().get(ResourceType.MILITARYPOINT))){
				players.get(j+1).addResource(finalBonus.getFinalMilitaryTrack().get(i));
				j++;
			}
			i++;
			j++;
		}
	}
	
	
	public void skipPlayers(){
		for(Player p : view.getGameModel().getPlayers()){
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				if(t.getEffect() instanceof OtherEffect){
					OtherEffect otherEffect = (OtherEffect) t.getEffect();
					if(otherEffect.getType().equals(EffectType.SKIPROUNDEFFECT)){
						view.getSkipped().add(p);
					}
				}
			}
		}
		
	}
	
	public void checkSkippedPlayers(){ // se i giocatori hanno saltato il primo turno ora possono rifare il turno che gli spetta alla fine
		for(Player p : view.getSkipped()){
			currentPlayer = p;
			try{
				view.setCurrentPlayer(currentPlayer);
				if(!view.checkSkipped(currentRound)){
					view.play();
				}
			}
			catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
			}	
		}
	}
	
	
	public void checkDiceReduction(){  //se i giocatori tra le scomuniche hanno reducedice applico effetto
		for(Player p : view.getGameModel().getPlayers()){
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				if(t.getEffect() instanceof ModifyDiceEffect){
					t.getEffect().apply(p, view);
				}
			}
		}
	}
	
	public void applyFinalBonus(){
		FinalBonus finalBonus = FinalBonus.instance();
		for(Player p : view.getGameModel().getPlayers()){
			p.getBoard().getResources().modifyResource(finalBonus.getFinalTerritoriesBonus().get(p.getBoard().getTerritories().size()), true);			
			p.getBoard().getResources().modifyResource(finalBonus.getFinalCharactersBonus().get(p.getBoard().getCharacters().size()), true);
			
			System.out.println("ciao: " + finalBonus.getFinalCharactersBonus().get(p.getBoard().getCharacters().size()));
			System.out.println("parapa: " +p.getBoard().getCharacters().size());
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
		System.out.println("entro malus");
		ExcommunicationTile t;
		System.out.println("malus loop");
		for(Player p : view.getGameModel().getPlayers()){
			t = p.getExcommunicationTile().get(currentEra-2);
			System.out.println("prima dell'if");
			if(t.getEffect() != null){
				System.out.println(t.getEffect());
				t.getEffect().apply(p, view);
			}
		}
	}

}
