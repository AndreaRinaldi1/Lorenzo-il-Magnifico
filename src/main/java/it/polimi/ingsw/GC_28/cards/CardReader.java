package it.polimi.ingsw.GC_28.cards;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CardReader{
    Gson gson = new GsonBuilder().create();

    public static void main(String[] args) throws FileNotFoundException{
        CardReader reader = new CardReader();
        reader.startRead();
    }

    public Deck startRead() throws FileNotFoundException{
        JsonReader reader = new JsonReader(new FileReader("cards.json"));
        JsonReader reader2 = new JsonReader(new FileReader("cards.json"));
        //JsonReader reader3 = new JsonReader(new FileReader("cards.json"));

        Deck deck = new Deck();
        try{
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
            	//Character character = new Character(j);
            	//deck.getCharacters().add(character);
        	}
        	
        	for(int i = 0; i < arrayTerritories.size(); i++){
            	JsonObject j = arrayTerritories.get(i).getAsJsonObject();
            	deck.getTerritories().get(i).setImmediateEffect(gson, j);
        	}
        	//System.out.println(e.getClass());
        	
        	//System.out.println(deck.getCharacters().get(0).getPermanentEffect().getClass());
        	//System.out.println(deck.getCharacters().get(0).getImmediateEffect().get(0).getClass());
        	//System.out.println(deck.getCharacters().get(0).getImmediateEffect().get(1).getClass());

        	//System.out.println(d.getBuildings().get(0));
        	//d.getDeck().get(CardType.VENTURE).get(0).
        //	System.out.println(d.toString());   
        	//System.out.println(d.getVentures().get(0).getAlternativeCostPresence());
	        reader.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
        finally{
        	return deck;
        }
       
    }
}
