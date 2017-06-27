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
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStartRead() throws FileNotFoundException{
		d = this.cr.startRead();
		CardReader.main(null);
	}

}
