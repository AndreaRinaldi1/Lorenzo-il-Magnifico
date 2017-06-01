package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.util.EnumMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class PlayerTest {
	private Player p; 
	private Socket s;
	private PlayerBoard pb;
	private BonusTile bt;
	private Resource resources;
	EnumMap<ResourceType, Integer> resource;
	private FamilyMember fm;
	private FamilyMember fm1;
	private FamilyMember[] familyMembers;

	
	@Before
	public void playerTest() throws IOException{
		s = new Socket();
		p = new Player("Rob", PlayerColor.BLUE, s);
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 1);
		resources = Resource.of(resource);
		bt = new BonusTile();
		pb = new PlayerBoard(bt, resources);
		p.setBoard(pb);
		
		fm = new FamilyMember(p, false, DiceColor.BLACK);
		fm1 = new FamilyMember(p, true, DiceColor.NEUTRAL);
		familyMembers = new FamilyMember[2];
		familyMembers[0] = fm;
		familyMembers[1] = fm1;
		p.setFamilyMembers(familyMembers);
		
		/*
		in = new Scanner(s.getInputStream());
		out = new PrintWriter(s.getOutputStream());
		*/
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetName() {
		assertEquals("Rob", this.p.getName());
	}

	@Test
	public void testGetBoard() {
		assertEquals(this.pb, this.p.getBoard());
	}
/*
	@Test
	public void testGetIn() {
		assertEquals(this.in, this.p.getIn());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetOut() {
		assertEquals(this.out, this.p.getOut());
		//fail("Not yet implemented");
	}
*/
	@Test
	public void testGetFamilyMembers() {
		assertArrayEquals(this.familyMembers, this.p.getFamilyMembers());
	}

	@Test
	public void testGetColor() {
		assertEquals(PlayerColor.BLUE, this.p.getColor());
	}
	
	@Test
	public void testDisplayFamiltMembers () {
		System.out.println(p.displayFamilyMembers());
	}

}
