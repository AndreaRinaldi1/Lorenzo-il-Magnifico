package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.cards.LeaderCardReader;

public class LeaderCardReaderTest {

	LeaderCardReader reader = new LeaderCardReader();
	private LeaderCardReader lcr;
	private LeaderCardReader lcr1;

	@Before
	public void leaderCardReader(){
		lcr = new LeaderCardReader();
		lcr1 = new LeaderCardReader();
		LeaderCardReader.main(null);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testStart() throws IOException {
		for(int i = 0; i < this.lcr1.start().size(); i++){
			for(int j = 0; j < this.lcr.start().size(); j++){
				if(this.lcr1.start().get(i).equals(this.lcr.start().get(j))){
					assertEquals(this.lcr1.start().get(i), this.lcr.start().get(j));
				}
			}
		}
	}

}
