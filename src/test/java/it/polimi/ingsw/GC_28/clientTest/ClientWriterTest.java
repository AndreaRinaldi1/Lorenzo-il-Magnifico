package it.polimi.ingsw.GC_28.clientTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.client.ClientWriter;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.spaces.SpaceType;
import junit.framework.Assert;

public class ClientWriterTest {
	
	private ClientWriter writer;
	
	Scanner stdin = new Scanner(System.in);
	
	Socket socket = new Socket();
	Player p = new Player("mario", PlayerColor.RED);
	
	@Before
	public void ClientWriter(){
		
		writer = new ClientWriter(socket,p);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testAskCardName() {
		String name = "bosco";
		System.setIn(new ByteArrayInputStream(name.getBytes()));
		
		//Scanner stdin = new Scanner(System.in);
		assertEquals(name,writer.askCardName());
	}
	
	@Test
	public void testAskFamilyMember(){
		FamilyMember[] fam = new FamilyMember[4];
		int i = 0;
		for(DiceColor dc : DiceColor.values()){
			if(dc == DiceColor.NEUTRAL){
				FamilyMember fm = new FamilyMember(p, true, DiceColor.NEUTRAL);
				fm.setValue(0);
				fam[i] = fm;
			}
			else{
				FamilyMember fm = new FamilyMember(p, false, dc);
				fm.setValue(1);
				fam[i] = fm;
			}
			
			i++;
		}
		p.setFamilyMembers(fam);
		String familyMember = "orange";
		System.setIn(new ByteArrayInputStream(familyMember.getBytes()));
		
		assertEquals(fam[1], writer.askFamilyMember());
	}
	
	@Test
	public void TestAskWhichSpace(){
		String space = "coinspace";
		System.setIn(new ByteArrayInputStream(space.getBytes()));
		
		assertEquals(SpaceType.COINSPACE,writer.askWhichSpace());
		
		String space2 = "servantspace";
		System.setIn(new ByteArrayInputStream(space2.getBytes()));
		
		assertEquals(SpaceType.SERVANTSPACE,writer.askWhichSpace());
		
		String space3 = "councilpalace";
		System.setIn(new ByteArrayInputStream(space3.getBytes()));
		
		assertEquals(SpaceType.COUNCILPALACE,writer.askWhichSpace());
		
		String space4 = "mixedspace";
		System.setIn(new ByteArrayInputStream(space4.getBytes()));
		
		assertEquals(SpaceType.MIXEDSPACE,writer.askWhichSpace());
		
		String space5 = "privilegesspace";
		System.setIn(new ByteArrayInputStream(space5.getBytes()));
		
		assertEquals(SpaceType.PRIVILEGESSPACE,writer.askWhichSpace());
		
		String space6 = "productionspace";
		System.setIn(new ByteArrayInputStream(space6.getBytes()));
		
		assertEquals(SpaceType.PRODUCTIONSPACE,writer.askWhichSpace());
		
		String space7 = "harvestspace";
		System.setIn(new ByteArrayInputStream(space7.getBytes()));
		
		assertEquals(SpaceType.HARVESTSPACE,writer.askWhichSpace());
		
		String space8 = "vefds\ncoinspace";
		System.setIn(new ByteArrayInputStream(space8.getBytes()));
		
		assertEquals(SpaceType.COINSPACE,writer.askWhichSpace());
	}
	
}
