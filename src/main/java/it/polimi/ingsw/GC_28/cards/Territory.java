package it.polimi.ingsw.GC_28.cards;
import it.polimi.ingsw.GC_28.effects.*;

import java.awt.Color;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Territory extends Card{
	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private HarvestEffect permanentEffect;
	
	public Territory(String name, int IDNumber, int era){
		super(name, IDNumber, era);
		this.setColor(Color.green);
	}

	public ArrayList<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}

	public void setImmediateEffect(Gson gson, JsonObject j) {
		JsonArray array = j.get("immediateEffect").getAsJsonArray();
		for(int i = 0; i<array.size(); i++){
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
			default:
		    	System.out.println("lascio il tipo di immediate effect a effect");
		    	System.out.println(permanentEffect.getClass());
			}
		}
	}

	public HarvestEffect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(HarvestEffect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}

}
