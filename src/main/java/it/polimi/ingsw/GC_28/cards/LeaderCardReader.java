package it.polimi.ingsw.GC_28.cards;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.OtherEffect;
import it.polimi.ingsw.GC_28.effects.PopeEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.effects.SetFamilyMemberValueEffect;

public class LeaderCardReader {
	
	public static void main(String[] args) {
		LeaderCardReader reader = new LeaderCardReader();
		reader.start();
	}
	
	public List<LeaderCard> start(){
		List<LeaderCard> leaders = new ArrayList<>();
		
		Type requestTypeToken = new TypeToken<ArrayList<LeaderCard>>() {}.getType();
		
		final RuntimeTypeAdapterFactory<Effect> typeFactory = RuntimeTypeAdapterFactory
				.of(Effect.class, "type")
				.registerSubtype(ResourceEffect.class,"RESOURCEEFFECT")
				.registerSubtype(PrivilegesEffect.class,"PRIVILEGESEFFECT")
				.registerSubtype(OtherEffect.class, "OTHEREFFECT")
				.registerSubtype(GoToHPEffect.class,"GOTOHP")
				.registerSubtype(SetFamilyMemberValueEffect.class,"SETFAMILYMEMBERVALUEEFFECT")
				.registerSubtype(PopeEffect.class,"POPEFFECT");
		
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
		
		try{
			JsonReader reader = new JsonReader(new FileReader("leader.json"));
			leaders = gson.fromJson(reader, requestTypeToken);
			reader.close();
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "cannot find leader.json file" + e);
		}
		return leaders;
	}
}
