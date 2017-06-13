package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.ExcommunicationReader;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;

public class ExcommunicationReaderTest {
	
	private ExcommunicationReader reader;
	private ExcommunicationReader reader1;

	private ArrayList<ExcommunicationTile> excomm = new ArrayList<ExcommunicationTile>();

	@Before
	public void excommunicationReader(){
		reader = new ExcommunicationReader();
		reader1 = new ExcommunicationReader();
		//excomm.equals(reader.startRead());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

/*	@Test
	public void testMain() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testStartRead() {
		try{
			for(int i = 0; i < this.reader1.startRead().size(); i++){
				for(int j = 0; j < this.reader.startRead().size(); j++){
					if(this.reader1.startRead().get(i).equals(this.reader.startRead().get(j))){
						assertEquals(this.reader1.startRead().get(i), this.reader.startRead().get(j));
					}
				}
			}
		}catch(IOException e){
			Logger.getAnonymousLogger().log(Level.SEVERE, "IOException "+ e);
		}
	}

}
