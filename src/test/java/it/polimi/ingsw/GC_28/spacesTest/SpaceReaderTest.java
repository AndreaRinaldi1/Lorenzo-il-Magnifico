package it.polimi.ingsw.GC_28.spacesTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.spaces.EverySpace;
import it.polimi.ingsw.GC_28.spaces.SpacesReader;

public class SpaceReaderTest {
	
	private SpacesReader reader;
	private EverySpace everySpace;
	private Gson gson = new GsonBuilder().create();

	@Before
	public void spaceReader() throws FileNotFoundException{
		reader = new SpacesReader();
		everySpace = new EverySpace();
		SpacesReader.main(null);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStartRead() throws FileNotFoundException {
		try{
        	JsonReader reader = new JsonReader(new FileReader("spaces.json"));
        	everySpace = gson.fromJson(reader, EverySpace.class);
        	
        	//d.getDeck().get(CardType.VENTURE).get(0).
        	//System.out.println(gameBoard.);   
        	//System.out.println(d.getVentures().get(0).getAlternativeCostPresence());
	        reader.close();
    	}
    	catch(IOException e){
    		Logger.getAnonymousLogger().log(Level.SEVERE, "space file not found" + e);
    	}
		
		assertEquals(this.everySpace.getClass().getName(), this.reader.startRead().getClass().getName());
	}

}
