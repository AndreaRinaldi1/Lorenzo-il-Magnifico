package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class BoardsInitializerTest {

	BoardsInitializer bi = new BoardsInitializer();
	Player p1;
	Player p2;
	Player p3;
	Player p4;
	
	@Before
	public void boardInitilizer(){
		
	}
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
	}

	@Test
	public void testInitializeBoard() {
		List<Player> players = new ArrayList<>();
		p1 = new Player("jhon", PlayerColor.RED);
		p2 = new Player("karl", PlayerColor.BLUE);
		p3 = new Player("Lenny", PlayerColor.GREEN);
		p4 = new Player("Homer", PlayerColor.YELLOW);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		try {
			bi.initializeBoard(players);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		fail("Not yet implemented");
	}

	@Test
	public void testInitDices() {
//		fail("Not yet implemented");
	}

	@Test
	public void testInitFamilyMember() {
//		fail("Not yet implemented");
	}

}
