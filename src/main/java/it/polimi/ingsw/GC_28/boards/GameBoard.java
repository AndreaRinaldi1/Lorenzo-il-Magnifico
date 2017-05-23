package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.cards.Character;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.spaces.CouncilPalace;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.TwoPrivilegesSpace;


public class GameBoard {
	private final int N_DICE = 3;
	private final int N_EXCOMMUNICATIONTILE = 3;
	private EnumMap<CardType, Tower> towers = new EnumMap<CardType, Tower>(CardType.class);
	private Dice[] dices = new Dice[N_DICE];
	private ExcommunicationTile[] excommunications = new ExcommunicationTile[N_EXCOMMUNICATIONTILE];
	
	private static ProductionAndHarvestSpace harvestSpace, productionSpace;
	private static MarketSpace coinSpace, servantSpace, mixedSpace;
	private static TwoPrivilegesSpace twoPrivilegesSpace;
	private Resource bonusFaithPoints;
	private static CouncilPalace councilPalace;
	
	private ArrayList<FamilyMember> membersCoucilPalace = new ArrayList<FamilyMember>(); 
	private ArrayList<Territory> territory = new ArrayList<Territory>();
	private ArrayList<Building> buildings = new ArrayList<Building>();
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Venture> ventures = new ArrayList<Venture>();
	
	
	public GameBoard(){
	}
	
	
	
	

	public String display(){
		String ret = "GAME BOARD\n";
		
		//Towers
		ret+="----------\n";
		for(int i = 4;i > 0 ;i--){
			ret+="|" + territory.get(i).getName() + " |"+ buildings.get(i).getName() + " |" + 
					characters.get(i).getName() + " |" + ventures.get(i).getName() + " |\n";
			ret+="----------\n";	
		}
		
		//Council Palace
		ret+="CP: ";
		for (int i = 0; i < membersCoucilPalace.size(); i++){		
			ret+="| " + membersCoucilPalace.get(i).getPlayer().getColor() + " |"; 
		}
		ret+="Church: | | | |\n";			//ho bisogno delle carte scomunica
		
		//hHarvest and Production Space
		if(harvestSpace.isFree()==false){
			ret+="(X)";
		}
		else{
			ret+="( )";
		}
		ret+="(    )\n";
		if(productionSpace.isFree()==false){
			ret+="(X)";
		}
		else{
			ret+="( )";
		}
		ret+="(    )\n";
		
		//Market Space
		if(coinSpace.isFree()==false){
			ret+="(X)";
		}
		else{
			ret+="( )";
		}
		if(servantSpace.isFree()==false){
			ret+="(X)";
		}
		else{
			ret+="( )";
		}
		if(mixedSpace.isFree()==false){
			ret+="(X)";
		}
		else{
			ret+="( )";
		}
		if(twoPrivilegesSpace.isFree()==false){
			ret+="(X)";
		}
		else{
			ret+="( )";
		}
		ret+="\n";
	
		//Dice Space
		ret+="Dice Values ";
		for(int i = 0; i < N_DICE; i++){
			dices[i].toString();
		}
		return ret;
	}
	
	public void rollDices(){
		for(int i = 0; i<N_DICE; i++){
			dices[i].setValue();
		}
	}

	public EnumMap<CardType, Tower> getTowers() {
		return towers;
	}
	
	public void setTowers(EnumMap<CardType,Tower> towers){
		this.towers = towers;
	}

	

	public Dice[] getDices() {
		return dices;
	}

	public void setDices(Dice[] dices) {
		this.dices = dices;
	}

	public ExcommunicationTile[] getExcommunications() {
		return excommunications;
	}

	public void setExcommunications(ExcommunicationTile[] excommunications) {
		this.excommunications = excommunications;
	}

	public ProductionAndHarvestSpace getHarvestSpace() {
		return harvestSpace;
	}

	public void setHarvestSpace(ProductionAndHarvestSpace harvestSpace2) {
		harvestSpace = harvestSpace2;
	}

	public ProductionAndHarvestSpace getProductionSpace() {
		return productionSpace;
	}

	public void setProductionSpace(ProductionAndHarvestSpace productionSpace2) {
		productionSpace = productionSpace2;
	}

	public MarketSpace getCoinSpace() {
		return coinSpace;
	}

	public void setCoinSpace(MarketSpace coinSpace2) {
		coinSpace = coinSpace2;
	}

	public MarketSpace getServantSpace() {
		return servantSpace;
	}

	public void setServantSpace(MarketSpace servantSpace2) {
		servantSpace = servantSpace2;
	}

	public MarketSpace getMixedSpace() {
		return mixedSpace;
	}

	public void setMixedSpace(MarketSpace mixedSpace2) {
		mixedSpace = mixedSpace2;
	}

	public TwoPrivilegesSpace getTwoPrivilegesSpace() {
		return twoPrivilegesSpace;
	}

	public void setTwoPrivilegesSpace(TwoPrivilegesSpace twoPrivilegesSpace2) {
		twoPrivilegesSpace = twoPrivilegesSpace2;
	}

	public Resource getBonusFaithPoints() {
		return bonusFaithPoints;
	}

	public void setBonusFaithPoints(Resource bonusFaithPoints) {
		this.bonusFaithPoints = bonusFaithPoints;
	}

	public CouncilPalace getCouncilPalace() {
		return councilPalace;
	}

	public void setCouncilPalace(CouncilPalace councilPalace2) {
		councilPalace = councilPalace2;
	}
	
}