package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.CardReader;
import it.polimi.ingsw.GC_28.cards.Deck;
import it.polimi.ingsw.GC_28.model.BoardSetup;
import it.polimi.ingsw.GC_28.model.Game;

public class BoardSetupTest {
	private BoardSetup bs;
	private Game g;
	private Deck deck;
	
	@Before
	public void boardSetup(){
		g = new Game(); 
		bs = new BoardSetup(g);
		deck = new Deck();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFirstSetUpCards() {
		bs.firstSetUpCards();
		try{
			CardReader cardReader = new CardReader();
			deck =  cardReader.startRead();
			}catch(FileNotFoundException e){
				Logger.getAnonymousLogger().log(Level.SEVERE, "deck not found" + e);
			}
		
	}

	@Test
	public void testSetUpBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrepareTowers() {
		fail("Not yet implemented");
	}

	@Test
	public void testFreeSpace() {
		fail("Not yet implemented");
	}

}
