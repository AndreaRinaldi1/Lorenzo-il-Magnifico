package spaces;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import core.GameBoard;


public class SpacesReader {
	 Gson gson = new GsonBuilder().create();

	    public static void main(String[] args) throws FileNotFoundException{
	        SpacesReader reader = new SpacesReader();
	        reader.startRead();
	    }

	    public GameBoard startRead() throws FileNotFoundException{
	    	GameBoard gameBoard = GameBoard.instance();
	        JsonReader reader = new JsonReader(new FileReader("spaces.json"));
	        try{
	        	gameBoard = gson.fromJson(reader, GameBoard.class);
	        	//d.getDeck().get(CardType.VENTURE).get(0).
	        	//System.out.println(gameBoard.getCoinSpace().getActionValue());   
	        	//System.out.println(d.getVentures().get(0).getAlternativeCostPresence());
		        reader.close();
	    	}
	    	catch(IOException e){
	    		e.printStackTrace();
	    	}
	        finally{
	        	return gameBoard;
	        }
	    }
}
