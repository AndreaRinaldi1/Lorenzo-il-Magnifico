package it.polimi.ingsw.GC_28.cards;

import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Venture extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private ResourceEffect permanentEffect;
	private boolean alternativeCostPresence;
	private Resource alternativeCost;
	private int minimumRequiredMilitaryPoints;
	


	public Venture(String name, int IDNumber, int era) {
		super(name, IDNumber, era);
		this.setColor(Color.pink);
	}

	public ArrayList<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(Gson gson, JsonObject j) {
		JsonArray array = j.get("immediateEffect").getAsJsonArray();
		for(int i = 0; i< array.size(); i++){
			JsonObject e = array.get(i).getAsJsonObject();
			Effect v;
			switch(e.get("type").getAsString()){
			case("RESOURCEEFFECT"):
        		System.out.println("cambio il tipo di immediate effect #" + i + " in ResourceEffect");
        		v = immediateEffect.get(i);
        		v = gson.fromJson(e.toString(), ResourceEffect.class);
        		immediateEffect.set(i, v);
        		System.out.println(immediateEffect.get(i).getClass());
        		break;
			case("PRIVILEGESEFFECT"):
        		System.out.println("cambio il tipo di immediate effect #" + i + " in PrivilegesEffect");
        		v = immediateEffect.get(i);
        		v = gson.fromJson(e.toString(), PrivilegesEffect.class);
        		immediateEffect.set(i, v);
        		System.out.println(immediateEffect.get(i).getClass());
        		break;
			case("GOTOHP"):
        		System.out.println("cambio il tipo di immediate effect #" + i + " in GoToHP");
	        	v = immediateEffect.get(i);
	    		v = gson.fromJson(e.toString(), GoToHPEffect.class);
	    		immediateEffect.set(i, v);
        		System.out.println(immediateEffect.get(i).getClass());
        		break;
			case("TAKECARDEFFECT"):
        		System.out.println("cambio il tipo di immediate effect #" + i + " in TakeCardEffect");
        		v = immediateEffect.get(i);
        		v = gson.fromJson(e.toString(), TakeCardEffect.class);
        		immediateEffect.set(i, v);
        		System.out.println(immediateEffect.get(i).getClass());
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
