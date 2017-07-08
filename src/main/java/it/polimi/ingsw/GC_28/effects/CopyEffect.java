package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

/**
 * This class represent the effect of Lorenzo il magnifico.
 * @author nicoloScipione
 * @version 1.0, 08/07/2017
 */

public class CopyEffect extends Effect {
	
	private Boolean copied = false;
	public final EffectType type = EffectType.COPYEFFECT;
	private Effect effect;
	
	private void setEffect(Effect effect){
		this.effect = effect;
	}
	
	/**
	 * This method changes the characteristic of Lorenzo il Magnifico's card. In this way it get the attributes of
	 * the leader card the player wants to copy. 
	 * @param player. The player that want to activate Lorenzo il Magnifico.
	 * @param toCopy. The leader card which it will copy the effect.
	 */
	private void changeCardAttributes(Player player,LeaderCard toCopy){
		for(LeaderCard leader : player.getLeaderCards()){
			if(("Lorenzo de Medici").equalsIgnoreCase(leader.getName())){
				leader.setEffect(toCopy.getEffect());
				leader.setPermanent(toCopy.getPermanent());
				leader.setName(toCopy.getName());
			}
		}
	}
	
	/**
	 * This method applies the effect. It asks to the player which card wants to copy showing him the available ones.
	 * After that it will copy the effect and activate it.
	 */
	@Override
	public void apply(Player player, GameView game){
		if(!copied){
			List<LeaderCard> leader = new ArrayList<>();
			game.getHandlers().get(player).send("Which ability do you want to copy?[enter the name]");
			for(Player p: game.getHandlers().keySet()){
				String s = new String();
				for(LeaderCard lc : p.getLeaderCards()){
					if(lc.getPlayed()){
						leader.add(lc);
						s += lc.getName() + " " + lc.getEffect().toString() + "\n";
					}
				}
				game.getHandlers().get(player).send(s);
			}
			boolean proceed = false;
			do{
				String line = game.getHandlers().get(player).receive();
				for(LeaderCard lc : leader){
					if(line.equalsIgnoreCase(lc.getName())){
						setEffect(lc.getEffect());
						changeCardAttributes(player, lc);
						proceed = true;
					}
				}if(this.effect == null){
					game.getHandlers().get(player).send("The name isn't valid, retry");
				}
			}while(!proceed);
			copied = true;
		}
		this.effect.apply(player, game);//attiva l'effetto anche se lo ha appena copiato
	}
}
