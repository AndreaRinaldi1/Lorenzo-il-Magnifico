package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.view.GameView;

public class CopyEffect extends Effect {
	
	private Boolean copied = false;
	public final EffectType type = EffectType.COPYEFFECT;
	private Effect effect;
	
	private void setEffect(Effect effect){
		this.effect = effect;
	}
	
	private void changeCardAttributes(Player player,LeaderCard toCopy){
		for(LeaderCard leader : player.getLeaderCards()){
			if(("Lorenzo de Medici").equalsIgnoreCase(leader.getName())){
				leader.setEffect(toCopy.getEffect());
				leader.setPermanent(toCopy.getPermanent());
				leader.setName(toCopy.getName());
			}
		}
	}
	
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
