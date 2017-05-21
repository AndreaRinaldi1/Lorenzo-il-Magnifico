package cards;
import effects.*;

import java.awt.Color;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Character extends Card{

	private ArrayList<Effect> immediateEffect = new ArrayList<Effect>();
	private Effect permanentEffect;
	
	/*public Character(JsonObject j){
		super(j.get("name").getAsString(),j.get("IDNumber").getAsInt(), j.get("era").getAsInt());
		switch(j.get("type").getAsString()){
		case("NOCELLBONUS"):
			//j.get("permanentEffect").getAsJsonObject().get("presence").getAsBoolean()
    		permanentEffect = new NoCellBonusEffect();
			break;
		case("INCREMENTCARDACTIONVALUEEFFECT"):
			permanentEffect = new IncrementCardActionValueEffect();
    	}
	}*/
	public Character(String name, int IDNumber, int era) {
		super(name, IDNumber, era);
		this.setColor(Color.blue);
	}

	public ArrayList<Effect> getImmediateEffect() {
		return immediateEffect;
	}

	public void setImmediateEffect(ArrayList<Effect> immediateEffect) {
		this.immediateEffect = immediateEffect;
	}
	
	
	public void setImmediateEffect(Gson gson, JsonObject j){
    	JsonArray array = j.get("immediateEffect").getAsJsonArray();
    	for(int i = 0; i < array.size(); i++){
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
        	case("GOTOHP"):
        		System.out.println("cambio il tipo di immediate effect #" + i + " in GoToHP");
	        	v = immediateEffect.get(i);
	    		v = gson.fromJson(e.toString(), GoToHPEffect.class);
	    		immediateEffect.set(i, v);
        		System.out.println(immediateEffect.get(i).getClass());
        		break;
        	}
    		
    	}
	}

	public Effect getPermanentEffect() {
		return permanentEffect;
	}

	public void setPermanentEffect(Gson gson, JsonObject j) {
		switch(j.get("permanentEffect").getAsJsonObject().get("type").getAsString()){
		case("NOCELLBONUS"):
    		System.out.println("cambio il tipo di permanent effect della carta in NoCellBonusEffect");
        	permanentEffect = gson.fromJson(j.get("permanentEffect").toString(), NoCellBonusEffect.class);
        	System.out.println(permanentEffect.getClass());
    		//permanentEffect = new NoCellBonusEffect(j.get("permanentEffect").getAsJsonObject().get("presence").getAsBoolean());
			break;
		case("INCREMENTCARDEFFECT"):
    		System.out.println("cambio il tipo di permanent effect della carta in IncrementCardEffect");
	    	permanentEffect = gson.fromJson(j.get("permanentEffect").toString(), IncrementCardEffect.class);
	    	System.out.println(permanentEffect.getClass());
	    	break;
		case("INCREMENTHPEFFECT"):
    		System.out.println("cambio il tipo di permanent effect della carta in IncrementHPEffect");
	    	permanentEffect = gson.fromJson(j.get("permanentEffect").toString(), IncrementCardEffect.class);
	    	System.out.println(permanentEffect.getClass());
	    	break;
    	}
		
	}

	public void setPermanentEffect(Effect permanentEffect) {
		this.permanentEffect = permanentEffect;
	}
}
