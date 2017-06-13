package it.polimi.ingsw.GC_28.effects;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;

public class CopyEffect extends Effect {
	
	private Boolean copied = false;
	private EffectType type = EffectType.COPYEFFECT;
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
	public void apply(Player player, Game game){
		if(!copied){
			List<LeaderCard> leader = new ArrayList<>();
			game.getHandlers().get(player).getOut().println("Which ability do you want to copy?[enter the name]");
			for(Player p: game.getHandlers().keySet()){
				String s = new String();
				for(LeaderCard lc : p.getLeaderCards()){
					if(lc.getPlayed()){
						leader.add(lc);
						s += lc.getName() + " " + lc.getEffect().toString() + "\n";
					}
				}
				game.getHandlers().get(player).getOut().println(s);
			}
			game.getHandlers().get(player).getOut().flush();
			boolean procede = false;
			do{
				String line = game.getHandlers().get(player).getIn().nextLine();
				for(LeaderCard lc : leader){
					if(line.equalsIgnoreCase(lc.getName())){
						setEffect(lc.getEffect());
						changeCardAttributes(player, lc);
						procede = true;
					}
				}if(this.effect == null){
					game.getHandlers().get(player).getOut().println("The name isn't valid, retry");
					game.getHandlers().get(player).getOut().flush();
				}
			}while(!procede);
			copied = true;
		}
		this.effect.apply(player, game);//attiva l'effetto anche se lo ha appena copiato
	}
}
