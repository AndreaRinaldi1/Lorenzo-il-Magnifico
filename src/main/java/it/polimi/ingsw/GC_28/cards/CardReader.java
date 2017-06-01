package it.polimi.ingsw.GC_28.cards;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.GC_28.effects.DiscountEffect;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.ExchangeEffect;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.HarvestEffect;
import it.polimi.ingsw.GC_28.effects.IncrementCardEffect;
import it.polimi.ingsw.GC_28.effects.IncrementHPEffect;
import it.polimi.ingsw.GC_28.effects.MultiplierEffect;
import it.polimi.ingsw.GC_28.effects.NoCellBonusEffect;
import it.polimi.ingsw.GC_28.effects.NoEffect;
import it.polimi.ingsw.GC_28.effects.PrivilegesEffect;
import it.polimi.ingsw.GC_28.effects.ProductionEffect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardReader{

    public static void main(String[] args) throws FileNotFoundException{
        CardReader reader = new CardReader();
        reader.startRead();
    }

    public Deck startRead() throws FileNotFoundException{
        Deck deck = new Deck();
        
        Type requestListTypeToken = new TypeToken<Deck>() {}.getType();
        
        final RuntimeTypeAdapterFactory<Effect> typeFactory = RuntimeTypeAdapterFactory
        		.of(Effect.class,"type")
        		.registerSubtype(DiscountEffect.class,"DISCOUNTEFFECT")
        		.registerSubtype(ExchangeEffect.class,"EXCHANGEEFFECT")
        		.registerSubtype(GoToHPEffect.class,"GOTOHP")
        		.registerSubtype(HarvestEffect.class,"HARVESTEFFECT")
        		.registerSubtype(IncrementCardEffect.class,"INCREMENTCARDEFFECT")
        		.registerSubtype(IncrementHPEffect.class,"INCREMENTHPEFFECT")
        		.registerSubtype(MultiplierEffect.class,"MULTIPLIEREFFECT")
        		.registerSubtype(NoCellBonusEffect.class,"NOCELLBONUS")
        		.registerSubtype(PrivilegesEffect.class,"PRIVILEGESEFFECT")
        		.registerSubtype(ProductionEffect.class,"PRODUCTIONEFFECT")
        		.registerSubtype(ResourceEffect.class,"RESOURCEEFFECT")
        		.registerSubtype(TakeCardEffect.class,"TAKECARDEFFECT")
        		.registerSubtype(NoEffect.class,"NOEFFECT");
        
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();

        try{
        	JsonReader reader = new JsonReader(new FileReader("cards.json"));     	
        	deck = gson.fromJson(reader, requestListTypeToken);
	        reader.close();
    	}
    	catch(IOException e){
    		Logger.getAnonymousLogger().log(Level.SEVERE, "Deck file not found" + e);
    	}
        return deck;
    }
        /*try{
        	JsonReader reader = new JsonReader(new FileReader("cards.json"));
            JsonReader reader2 = new JsonReader(new FileReader("cards.json"));
        	
        	deck = gson.fromJson(reader, Deck.class);
        	
        	
        	JsonObject c = gson.fromJson(reader2, JsonObject.class);
        	JsonArray arrayCharacter = c.get("characters").getAsJsonArray();
        	JsonArray arrayVentures = c.get("ventures").getAsJsonArray();
        	JsonArray arrayTerritories = c.get("territories").getAsJsonArray();
        	
        	for(int i = 0; i < arrayVentures.size(); i++){
            	JsonObject j = arrayVentures.get(i).getAsJsonObject();
            	deck.getVentures().get(i).setImmediateEffect(gson, j);
        	}

        	for(int i = 0; i < arrayCharacter.size(); i++){
            	JsonObject j = arrayCharacter.get(i).getAsJsonObject();
            	deck.getCharacters().get(i).setPermanentEffect(gson, j);
            	deck.getCharacters().get(i).setImmediateEffect(gson, j);
        	}
        	
        	for(int i = 0; i < arrayTerritories.size(); i++){
            	JsonObject j = arrayTerritories.get(i).getAsJsonObject();
            	deck.getTerritories().get(i).setImmediateEffect(gson, j);
        	}
        	
	        reader.close();
	        reader2.close();
    	}
    	catch(IOException e){
    		Logger.getAnonymousLogger().log(Level.SEVERE, "Deck file not found" + e);
    	}
        return deck;

       
    }*/
}
