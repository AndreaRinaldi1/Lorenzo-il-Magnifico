package it.polimi.ingsw.GC_28.view;


import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
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

/**
 * This class contains methods that handle the flow of the game
 * @author andreaRinaldi,nicoloScipione
 * @version 1.0, 06/07/2017
 *
 */

public class GameManager implements Runnable{
	private int currentEra = 1;
	private int currentPeriod = 1;
	private int currentRound = 1;
	private int currentTurn = 0;
	private int waitMoveTimer;
	private Player currentPlayer;
	
	private GameView view;
	
	public GameManager(int timer){
		super();
		this.waitMoveTimer = timer;
	}
	
	/**
	 * This is the implementation of the method run. In this method is handled the flow of the game,
	 * the sequence of turn, period and era. It calls the method necessary to update the gameboard and reset 
	 * all players characteristic that change during the game.
	 */
	@Override
	public void run() {
		setCurrentPlayer(view.getGameModel().getPlayers().get(0));
		BoardSetup bs = new BoardSetup(this);
		for(currentEra = 1; currentEra <= 3; currentEra++){
			skipPlayers();
			for(currentPeriod = 1; currentPeriod <= 2; currentPeriod++){
				checkDiceReduction();				
				for(currentRound = 1; currentRound <= 4; currentRound++){					
					for(currentTurn = 0; currentTurn < view.getGameModel().getPlayers().size(); currentTurn++){
					
						boolean timeEnded = false;
						long time = System.currentTimeMillis() + (waitMoveTimer*1000);
						Thread t = new Thread(){
							@Override
							public void run(){
								try {
									view.setCurrentPlayer(currentPlayer);
									if(!view.checkSkipped(currentRound)){
										view.play();
									}
								} catch (IOException e) {
									Logger.getAnonymousLogger().log(Level.SEVERE,"Cannot play that move in method run()" + e);
								} catch (IndexOutOfBoundsException e){
									view.getHandlers().get(currentPlayer).send("Sorry, the last message was not for you. Please go on!");
								} catch(NoSuchElementException e){
									view.getSuspended().add(currentPlayer);
									for(Player p : view.getHandlers().keySet()){
										view.getHandlers().get(p).send("Player " + currentPlayer.getName() + " left the game!");
									}
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
							for(Player p : view.getHandlers().keySet()){
								if(p.equals(currentPlayer)){
									view.getHandlers().get(p).send("\nYou have been suspended.");
									view.getHandlers().get(p).send("suspended");
								}
								else{
									view.getHandlers().get(p).send("Player " + currentPlayer.getName() + " has been suspended");
								}
							}
							view.getSuspended().add(currentPlayer);
							new Thread(new Listener(view.getSuspended() ,currentPlayer, view.getHandlers().get(currentPlayer))).start();
						}
						
						if(currentTurn == (view.getGameModel().getPlayers().size()-1)){
							currentPlayer = view.getGameModel().getPlayers().get(0);
						}else{
							currentPlayer = view.getGameModel().getPlayers().get(currentTurn+1);
						}
					} 	
				}
				checkSkippedPlayers();
				if(!(currentEra == 3 && currentPeriod == 2)){
					bs.setUpBoard();
					currentPlayer = view.getGameModel().getPlayers().get(0);
				}
			}
			
			view.giveExcommunication(currentEra);
			
		}
		applyFinalBonus();
		applyFinalMalus();
		sortBy(view.getGameModel().getPlayers(), ResourceType.MILITARYPOINT);
		assignBonusForMilitary();
		sortBy(view.getGameModel().getPlayers(), ResourceType.VICTORYPOINT);
		view.declareWinner();	
		for(Player p : view.getHandlers().keySet()){
			view.getHandlers().get(p).send("close");
		}
	}
	
	/**
	 * 
	 * @return int, that represent the current period
	 */
	public int getCurrentPeriod() {
		return currentPeriod;
	}
	
	/**
	 * 
	 * @return player. The player from whom the server is waiting the input.
	 */
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	/**
	 * 
	 * @param player. The player who has to play his turn
	 */
	public void setCurrentPlayer(Player player){
		this.currentPlayer = player;
	}
	
	/**
	 * 
	 * @param period. The period to play.
	 */
	public void setPeriod(int period) {
		this.currentPeriod = period;
	}
	
	/**
	 * 
	 * @return the gameview related to this class.
	 */
	public GameView getView() {
		return view;
	}

	/**
	 * 
	 * @param view. set the view related to this class.
	 */
	public void setView(GameView view) {
		this.view = view;
	}

	/**
	 * 
	 * @return int. The current era
	 */
	public int getCurrentEra() {
		return currentEra;
	}

	/**
	 * 
	 * @param currentEra. The era to play in the next period.
	 */
	public void setCurrentEra(int currentEra) {
		this.currentEra = currentEra;
	}
	
	/**
	 * This method sort players from max to min, in the list players, by a certain Resource type
	 * @param players. List of players of the game
	 * @param type. The resource type which the order is requested.
	 */
	public void sortBy(List<Player> players, ResourceType type){
		players.sort((p1, p2) -> p2.getBoard().getResources().getResource().get(type) - p1.getBoard().getResources().getResource().get(type));
	}
	
	/**
	 * This method assign the bonus for the military points' rank. It uses two while loops to check if more players
	 * has the same position in rank.
	 */
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
	
	/**
	 * This method check if a player has a particular excommunication that force him to skip the first turn of every round,
	 * if so, the method adds the player to a List that contains this players.
	 */
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
	
	/**
	 * This method checks if any players has to skip the first move of the round, checking the list of skipped players.
	 */
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
	
	/**
	 * This method checks if a player has an excommunication that reduce his family member value.
	 */
	public void checkDiceReduction(){  //se i giocatori tra le scomuniche hanno reducedice applico effetto
		for(Player p : view.getGameModel().getPlayers()){
			for(ExcommunicationTile t : p.getExcommunicationTile()){
				if(t.getEffect() instanceof ModifyDiceEffect){
					t.getEffect().apply(p, view);
				}
			}
		}
	}
	
	/**
	 * This method apply the final bonus to every player. It reads the number of cards of each type and the resources
	 * of every player and attributes the right bonus of victory points decided at the beginning of the game and read from a json file. 
	 */
	public void applyFinalBonus(){
		FinalBonus finalBonus = FinalBonus.instance();
		for(Player p : view.getGameModel().getPlayers()){
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
	
	/**
	 * This method checks if any player has an excommunication that change the bonus that the player would receive,
	 * if so it apply the malus.
	 */
	public void applyFinalMalus(){
		ExcommunicationTile t;
		for(Player p : view.getGameModel().getPlayers()){
			t = p.getExcommunicationTile().get(currentEra-2);
			if(t.getEffect() != null){
				t.getEffect().apply(p, view);
			}
		}
	}

}
