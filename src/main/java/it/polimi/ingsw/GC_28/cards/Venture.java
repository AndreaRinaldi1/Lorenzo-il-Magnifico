package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Venture extends Card{
	private List<Effect> immediateEffect = new ArrayList<>();
	private ResourceEffect permanentEffect;
	private boolean alternativeCostPresence;
	private Resource alternativeCost;
	private int minimumRequiredMilitaryPoints;
	


	public Venture(String name, int IDNumber, int era) {
		super(name, IDNumber, era);
		this.setColor(Color.pink);
	}

	public List<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public void setImmediateEffect(Gson gson, JsonObject j) {
		JsonArray array = j.get("immediateEffect").getAsJsonArray();
		for(int i = 0; i< array.size(); i++){
			JsonObject e = array.get(i).getAsJsonObject();
			Effect v;
			switch(e.get("type").getAsString()){
			case("RESOURCEEFFECT"):
        		//System.out.println("cambio il tipo di immediate effect #" + i + " in ResourceEffect venture ");
        		v = immediateEffect.get(i);
        		v = gson.fromJson(e.toString(), ResourceEffect.class);
        		immediateEffect.set(i, v);
        		break;
			case("PRIVILEGESEFFECT"):
        		//System.out.println("cambio il tipo di immediate effect #" + i + " in PrivilegesEffect venture");
        		v = immediateEffect.get(i);
        		v = gson.fromJson(e.toString(), PrivilegesEffect.class);
        		immediateEffect.set(i, v);
        		break;
			case("GOTOHP"):
        		//System.out.println("cambio il tipo di immediate effect #" + i + " in GoToHP venture");
	        	v = immediateEffect.get(i);
	    		v = gson.fromJson(e.toString(), GoToHPEffect.class);
	    		immediateEffect.set(i, v);
        		break;
			case("TAKECARDEFFECT"):
        		//System.out.println("cambio il tipo di immediate effect #" + i + " in TakeCardEffect venture");
        		v = immediateEffect.get(i);
        		v = gson.fromJson(e.toString(), TakeCardEffect.class);
        		immediateEffect.set(i, v);
        		break;	
			case("NOEFFECT"):
        		//System.out.println("lascio il tipo dell'effetto a effect");
        		//System.out.println(immediateEffect.get(i).getClass());
        		break;
			}
		}
	}

	public ResourceEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(ResourceEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

	public boolean getAlternativeCostPresence() {
		return alternativeCostPresence;
	}

	public void setAlternativeCostPresence(boolean alternativeCostPresence) {
		this.alternativeCostPresence = alternativeCostPresence;
	}

	public Resource getAlternativeCost() {
		return alternativeCost;
	}

	public void setAlternativeCost(Resource alternativeCost) {
		this.alternativeCost = alternativeCost;
	}

	public int getMinimumRequiredMilitaryPoints() {
		return minimumRequiredMilitaryPoints;
	}

	public void setMinimumRequiredMilitaryPoints(int minimumRequiredMilitaryPoints) {
		this.minimumRequiredMilitaryPoints = minimumRequiredMilitaryPoints;
	}
		
}
