package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.ResourceEffect;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;

public class PlayerTest {
	private Player p; 
	private Player p1; 
	private PlayerBoard pb;
	private BonusTile bt;
	private Resource resources;
	EnumMap<ResourceType, Integer> resource;
	private FamilyMember fm;
	private FamilyMember fm1;
	private FamilyMember[] familyMembers;

	private	List<LeaderCard> leaderCards = new ArrayList<>();
	private LeaderCard leader;  
	private Map<CardType,Integer> cardCost = new EnumMap<>(CardType.class);

	
	private ResourceEffect effect;
	private List<ExcommunicationTile> excommunicationTile = new ArrayList<>();
	private ExcommunicationTile et1;
	private ExcommunicationTile et2;
	private ExcommunicationTile et3;

	
	@Before
	public void playerTest() throws IOException{
		p = new Player("Rob", PlayerColor.BLUE);
		resource = new EnumMap<>(ResourceType.class);
		resource.put(ResourceType.COIN, 1);
		resources = Resource.of(resource);
		bt = new BonusTile();
		pb = new PlayerBoard(bt, resources);
		p.setBoard(pb);
		
		fm = new FamilyMember(p, false, DiceColor.BLACK);
		fm.setValue(3);
		fm1 = new FamilyMember(p, true, DiceColor.NEUTRAL);
		fm1.setValue(2);
		familyMembers = new FamilyMember[2];
		familyMembers[0] = fm;
		familyMembers[1] = fm1;
		p.setFamilyMembers(familyMembers);
		
		leader = new LeaderCard();
		leader.setResourceCost(resources);
		leader.setCardCost(cardCost);
		leaderCards.add(leader);
		cardCost.put(CardType.TERRITORY, 3);
		this.leader.setCardCost(cardCost);
		p.setLeaderCards(leaderCards);
		p1 = new Player("popopopopo", null);
		p1.setColor(PlayerColor.RED);
		
		//Excommunication Tile
		effect = new ResourceEffect();
		effect.setResourceBonus(resources);
		/*et1 = new ExcommunicationTile();
		et1.setEffect(effect);
		et2 = new ExcommunicationTile();
		et2.setEffect(effect);
		et3 = new ExcommunicationTile()*/
		for(int i = 0; i<3; i++){
			excommunicationTile.add(new ExcommunicationTile());
			excommunicationTile.get(i).setEffect(effect);
		}
		p.setExcommunicationTile(excommunicationTile);
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

	@Test
	public void testGetLeaderCards(){
		assertEquals(this.leaderCards, this.p.getLeaderCards());
	}
	
	@Test
	public void testDisplayExcommunication(){
		AsciiTable tab = new AsciiTable();
		tab.addRule();
		tab.addRow("First Excommunication", "Second Excommunication", "Third Excommunication");
		tab.addRule();
		tab.addRow(excommunicationTile.get(0).getEffect() != null ? true : false,
				   excommunicationTile.get(1).getEffect() != null ? true : false,
				   excommunicationTile.get(2).getEffect() != null ? true : false);
		tab.addRule();
		String e = tab.render();
		assertEquals(e, this.p.displayExcommunication());
	}
	
	
	@Test
	public void testDisplayLeader(){
		Boolean active = true;
		leader.setActive(active);
		String name = "Rob";
		leader.setName(name);
		Boolean played = true;
		leader.setPlayed(played);
		AsciiTable leadTab = new AsciiTable();
		leadTab.addRule();
		leadTab.addRow("Leader", "played" ,"active");//, "resource Cost", "CardCost" );
		leadTab.addRule();
		for(LeaderCard lc : leaderCards){
			leadTab.addRow(lc.getName(),lc.getPlayed(),lc.getActive());
					//lc.getResourceCost() != null ? lc.getResourceCost().getResource().toString(): "***",
					//lc.getCardCost() != null ? lc.getCardCost().values() : "***");
		}
		leadTab.addRule();
		String ret = leadTab.render();
		assertEquals(ret, this.p.displayLeader());
	}
	
	@Test
	public void displayLeaderCost(){
		AsciiTable costTab = new AsciiTable();
		costTab.addRule();
		costTab.addRow("Leader", "ResourceCost" ,"CardCost");//, "resource Cost", "CardCost" );
		costTab.addRule();
		for(LeaderCard lc : leaderCards){
			costTab.addRow(lc.getName(),
					lc.getResourceCost() != null ? resourceCost(lc): "***",
					lc.getCardCost() != null ? cardCost(lc) : "***");
		}
		costTab.addRule();
		String ret = costTab.render();
		assertEquals(ret, this.p.displayLeaderCost());
	}
	
	private String resourceCost(LeaderCard lc){
		StringBuilder r = new StringBuilder();
		for(ResourceType rt : lc.getResourceCost().getResource().keySet()){
			r.append(rt.name() + "=" + lc.getResourceCost().getResource().get(rt)+"\n");
		}
		return r.toString();
	}
	
	private String cardCost(LeaderCard lc){
		StringBuilder s = new StringBuilder();
		for(CardType ct : lc.getCardCost().keySet()){
			s.append(ct.name() + "="+ lc.getCardCost().get(ct)+ "\n");
		}
		return s.toString();
	}
	
	@Test
	public void testGetFamilyMembers() {
		assertArrayEquals(this.familyMembers, this.p.getFamilyMembers());
	}

	@Test
	public void testGetColor() {
		assertEquals(PlayerColor.BLUE, this.p.getColor());
	}
	
/*	@Test
	public void testDisplayFamilyMembers () {
		System.out.println(p.displayFamilyMembers());
	}
*/
}
