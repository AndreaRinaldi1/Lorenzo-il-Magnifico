package it.polimi.ingsw.GC_28.spaces;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


public class SpacesReader {
	 Gson gson = new GsonBuilder().create();

	    public static void main(String[] args) throws FileNotFoundException{
	        SpacesReader reader = new SpacesReader();
	        reader.startRead();
	    }

	    public EverySpace startRead() throws FileNotFoundException{
	    	EverySpace everySpace = new EverySpace();
	        try{
	        	JsonReader reader = new JsonReader(new FileReader("spaces.json"));
	        	everySpace = gson.fromJson(reader, EverySpace.class);
		        reader.close();
	    	}
	    	catch(IOException e){
	    		Logger.getAnonymousLogger().log(Level.SEVERE, "space file not found" + e);
	    	}
	        return everySpace ;
	    }
}
