package it.polimi.ingsw.GC_28.spaces;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

//import cards.Deck;


public class SpacesReader {
	 Gson gson = new GsonBuilder().create();

	    public static void main(String[] args) throws FileNotFoundException{
	        SpacesReader reader = new SpacesReader();
	        reader.startRead();
	    }

	    private void startRead() throws FileNotFoundException{
	        JsonReader reader = new JsonReader(new FileReader("spaces.json"));
	        try{
	        	EverySpace everySpace = gson.fromJson(reader, EverySpace.class);
	        	//d.getDeck().get(CardType.VENTURE).get(0).
	        	System.out.println(everySpace.getServantSpace().getActionValue());   
	        	//System.out.println(d.getVentures().get(0).getAlternativeCostPresence());
		        reader.close();
	    	}
	    	catch(IOException e){
	    		e.printStackTrace();
	    	}
	    }
}
