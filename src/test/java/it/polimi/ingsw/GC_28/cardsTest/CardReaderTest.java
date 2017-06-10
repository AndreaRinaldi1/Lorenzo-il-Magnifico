package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.*;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;

public class CardReaderTest {
	
	private Gson gson = new GsonBuilder().create();
	private Deck d = new Deck();
	private CardReader cr;
	
/*	private Venture v = new Venture("Ammazzo Tutti", 1, 1);
	private EnumMap<ResourceType, Integer> resource;
	private EnumMap<ResourceType, Integer> resource1;
	private EnumMap<ResourceType, Integer> resource2;
	private ArrayList<Effect> immediateEffect = new ArrayList<>();
	private Resource cost;
	private Resource immediateBonus;
	private Resource permanentBonus;
	private ResourceEffect re = new ResourceEffect();
	private ResourceEffect re1 = new ResourceEffect();*/
	
	
	@Before
	public void cardReader() throws FileNotFoundException{
		cr = new CardReader();
		
		/*try{
        	JsonReader reader = new JsonReader(new FileReader("cards.json"));
            JsonReader reader2 = new JsonReader(new FileReader("cards.json"));
        	
        	d = gson.fromJson(reader, Deck.class);
        	
        	
        	JsonObject c = gson.fromJson(reader2, JsonObject.class);
        	JsonArray arrayVentures = c.get("ventures").getAsJsonArray();
        	
        	for(int i = 0; i < arrayVentures.size(); i++){
            	JsonObject j = arrayVentures.get(i).getAsJsonObject();
            	d.getVentures().get(i).setImmediateEffect(gson, j);
        	}

	        reader.close();
	        reader2.close();
    	}
    	catch(IOException e){
    		Logger.getAnonymousLogger().log(Level.SEVERE, "Deck file not found" + e);
    	}
    	*/
		d.equals(cr.startRead());
		
/*		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 1);
		cost = Resource.of(resource);
		v.setCost(cost);
		
		resource1 = new EnumMap<>(ResourceType.class);
		resource1.put(ResourceType.VICTORYPOINT, 2);
		immediateBonus = Resource.of(resource1);
		re.setResourceBonus(immediateBonus);
		immediateEffect.add(re);
		v.setImmediateEffect(immediateEffect);
		
		resource2 = new EnumMap<>(ResourceType.class);
		resource2.put(ResourceType.VICTORYPOINT,3);
		permanentBonus = Resource.of(resource2);
		re1.setResourceBonus(permanentBonus);
		v.setPermanentEffect(re1);
*/		        

	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStartRead() throws FileNotFoundException {
		for (int i = 0; i<d.getVentures().size(); i++){ 
			assertEquals(this.d.getVentures().get(i), this.cr.startRead().getVentures().get(i));
			//fail("Not yet implemented");
		}
		for (int i = 0; i<d.getBuildings().size(); i++){ 
			assertEquals(this.d.getBuildings().get(i), this.cr.startRead().getBuildings().get(i));
			//fail("Not yet implemented");
		}
		for (int i = 0; i<d.getCharacters().size(); i++){ 
			assertEquals(this.d.getCharacters().get(i), this.cr.startRead().getCharacters().get(i));
			//fail("Not yet implemented");
		}
		for (int i = 0; i<d.getTerritories().size(); i++){ 
			assertEquals(this.d.getTerritories().get(i), this.cr.startRead().getTerritories().get(i));
			//fail("Not yet implemented");
		}
	}

}
