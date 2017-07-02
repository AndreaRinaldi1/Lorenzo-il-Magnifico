package it.polimi.ingsw.GC_28.model;


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

/**
 * This class rappresent Player with its playerboard, excommunication tile, family member and leader cards.
 * @author nicoloscipioen ,andrearinaldi
 * @version 1.0, 30/06/2017
 * @see FamilyMember,LeaderCard, PlayerBoard,ExcommunicationTile
 */

public class Player {
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

	/**
	 * 
	 * @return Return the name of the player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The player's playerBoard
	 */
	public PlayerBoard getBoard() {
		return this.board;
	}
	
	/**
	 * 
	 * @return The player's family member
	 */
	public FamilyMember[] getFamilyMembers() {
		return familyMembers;
	}
	
	/**
	 * 
	 * @return The player's excommunicationTile
	 */
	public List<ExcommunicationTile> getExcommunicationTile() {
		return excommunicationTile;
	}
	
	/**
	 * 
	 * @param excommunicationTile. The list of excommunicationTile for the player
	 */
	public void setExcommunicationTile(List<ExcommunicationTile> excommunicationTile) {
		this.excommunicationTile = excommunicationTile;
	}

	/**
	 * 
	 * @param familyMembers. The player's family member.
	 */
	public void setFamilyMembers(FamilyMember[] familyMembers) {
		this.familyMembers = familyMembers;
	}
	
	/**
	 * 
	 * @param board. The player's playerboard.
	 */
	public void setBoard(PlayerBoard board) {
		this.board = board;
	}
	
	/**
	 * 
	 * @return  Player's color.
	 */
	public PlayerColor getColor() {
		return color;
	}

	/**
	 * This method add resources to player's resource.
	 * @param amount. The resource to add to player's resource.
	 */
	public void addResource(Resource amount){
		this.getBoard().getResources().modifyResource(amount, true);
	}
	
	/**
	 * This method subtract the given amount of resource from player's resource 
	 * @param amount.The amount of resource to subtract from player's resource.
	 */
	public void reduceResources(Resource amount){
		this.getBoard().getResources().modifyResource(amount, false);
	}
	
	/**
	 * This method set player's color to given color.
	 * @param color to set to the player.
	 */
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	
	/**
	 * 
	 * @return The player's list of leader cards.
	 */
	public List<LeaderCard> getLeaderCards() {
		return leaderCards;
	}

	/**
	 * 
	 * @param leaderCards. The list of leader cards to set to player.
	 */
	public void setLeaderCards(List<LeaderCard> leaderCards) {
		this.leaderCards = leaderCards;
	}

	/**
	 *  This method displays the excommunicationTile. It uses AsciiTable to represent the excommunicationTile
	 *   and appends it as strings to a StringBuilder object that is returned as a string.
	 * @return The representation of excommunicationTile
	 */
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
	
	/**
	 * This method displays the familyMember. It uses AsciiTable to represent and
	 * appends them as strings to a StringBuilder object that is returned as a string.
	 * @return The representation of familyMember
	 */
	public String displayFamilyMember(){
		StringBuilder s = new StringBuilder();
		s.append("Family Members:\n");
		AsciiTable fm = new AsciiTable();
		fm.addRule();
		fm.addRow(familyMembers[0].getDiceColor(), familyMembers[1].getDiceColor(), familyMembers[2].getDiceColor(), familyMembers[3].getDiceColor());
		fm.addRule();
		fm.addRow(familyMembers[0].isUsed() ? "used" : familyMembers[0].getValue(), 
				familyMembers[1].isUsed() ? "used" : familyMembers[1].getValue(),
				familyMembers[2].isUsed() ? "used" : familyMembers[2].getValue(),
				familyMembers[3].isUsed() ? "used" : familyMembers[3].getValue());
		fm.addRule();
		s.append(fm.render());
		return s.toString();
	}
	
	/**
	 * This method displays the Leadercards. It uses AsciiTable to represent and
	 * appends them as strings to a StringBuilder object that is returned as a string.
	 * @return The representation of leader cards.
	 */
	public String displayLeader(){
		AsciiTable leadTab = new AsciiTable();
		leadTab.addRule();
		leadTab.addRow("Leader", "played" ,"active");
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
	
	/**
	 * This method displays the Leadercard's cost. It uses AsciiTable to represent and
	 * appends them as strings to a StringBuilder object that is returned as a string.
	 * @return The Leader card's cost.
	 */
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
	
	/**
	 * This method take a leader card as input and return its Resource cost as a string.
	 * @param lc. Leader card
	 * @return The cost of the leader card as a string
	 */
	private String resourceCost(LeaderCard lc){
		StringBuilder r = new StringBuilder();
		for(ResourceType rt : lc.getResourceCost().getResource().keySet()){
			r.append(rt.name() + "=" + lc.getResourceCost().getResource().get(rt)+"\n");
		}
		return r.toString();
	}
	
	/**
	 * This method take a leader card as parameter and return its card cost as a string.
	 * @param lc. Leader card
	 * @return The Leadercard's card cost as a String.
	 */
	private String cardCost(LeaderCard lc){
		StringBuilder s = new StringBuilder();
		for(CardType ct : lc.getCardCost().keySet()){
			s.append(ct.name() + "="+ lc.getCardCost().get(ct)+ "\n");
		}
		return s.toString();
	}
	
}
