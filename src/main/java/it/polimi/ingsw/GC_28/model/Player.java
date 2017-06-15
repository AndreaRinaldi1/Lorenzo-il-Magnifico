package it.polimi.ingsw.GC_28.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;



public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private PlayerColor color;
	private PlayerBoard board;
	private FamilyMember[] familyMembers = new FamilyMember[4];
	private List<ExcommunicationTile> excommunicationTile = new ArrayList<>();
	private List<LeaderCard> leaderCards = new ArrayList<>();
	
	public Player(String name, PlayerColor color){ //Used for local test. KEEP IT!
		this.name = name;
		this.color = color;
	}

	
	public String getName() {
		return name;
	}

	public PlayerBoard getBoard() {
		return this.board;
	}

	public FamilyMember[] getFamilyMembers() {
		return familyMembers;
	}
	
	public List<ExcommunicationTile> getExcommunicationTile() {
		return excommunicationTile;
	}

	public void setFamilyMembers(FamilyMember[] familyMembers) {
		this.familyMembers = familyMembers;
	}

	public void setBoard(PlayerBoard board) {
		this.board = board;
	}

	public PlayerColor getColor() {
		return color;
	}

	
	public void addResource(Resource amount){
		this.getBoard().getResources().modifyResource(amount, true);
	}
	
	
	public void reduceResources(Resource amount){
		this.getBoard().getResources().modifyResource(amount, false);
	}
	
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	
	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}


	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}


	public String displayExcommunication(){
		AsciiTable tab = new AsciiTable();
		tab.addRule();
		tab.addRow("First Excommunication", "Second Excommunication", "Third Excommunication");
		tab.addRule();
		tab.addRow(excommunicationTile.get(0).getEffect() != null ? true : false,
				   excommunicationTile.get(1).getEffect() != null ? true : false,
				   excommunicationTile.get(2).getEffect() != null ? true : false);
		tab.addRule();
		String e = tab.render();
		return e;
	}
	
	public String displayFamilyMembers(){
		StringBuilder s = new StringBuilder();
		s.append("Family Members:\n");
		for(FamilyMember f : familyMembers){
			s.append("Color: " + f.getDiceColor().name() + "  ");
			s.append("Value: " + f.getValue() + "\n");
		}
		return s.toString();
	}
	
	public String displayLeader(){
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
		return ret;
	}
	public String displayLeaderCost(){
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
		return ret;
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
	
}
