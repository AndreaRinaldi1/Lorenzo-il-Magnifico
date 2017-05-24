package it.polimi.ingsw.GC_28.boards;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.model.BoardsInitializer;
import it.polimi.ingsw.GC_28.spaces.CouncilPalace;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.TwoPrivilegesSpace;


public class GameBoard {
	private static final int N_DICE = 3;
	private static final int N_EXCOMMUNICATIONTILE = 3;
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Dice[] dices = BoardsInitializer.getDices();
	private ExcommunicationTile[] excommunications = new ExcommunicationTile[N_EXCOMMUNICATIONTILE];
	
	private ProductionAndHarvestSpace harvestSpace;
	private ProductionAndHarvestSpace productionSpace;
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private TwoPrivilegesSpace twoPrivilegesSpace;
	private Resource bonusFaithPoints;
	private CouncilPalace councilPalace;
	
	private ArrayList<FamilyMember> membersCoucilPalace = new ArrayList<>(); 
	
	
	public GameBoard(){
		/*empty for testing*/
	}
	

	public String display(){
		String ret = "GAME BOARD\n";
		
		//Towers
		ret+="----------\n";
		for(int i = 3;i >= 0 ;i--){
			ret+="|" + towers.get(CardType.TERRITORY).getCells()[i].getCard().getName() + 
				" |"+ towers.get(CardType.BUILDING).getCells()[i].getCard().getName() + 
				" |" + towers.get(CardType.CHARACTER).getCells()[i].getCard().getName() +
				" |" + towers.get(CardType.VENTURE).getCells()[i].getCard().getName()+ " |\n";
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
		ret+="Dice Values \n";
		for(int j = 0; j < N_DICE; j++){
			ret += (dices[j].getColor().toString()+": " + dices[j].getValue() + '\n');
		}
		return ret;
	}

	public Map<CardType, Tower> getTowers() {
		return towers;
	}
	
	public void setTowers(Map<CardType,Tower> towers){
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
		this.harvestSpace = harvestSpace2;
	}

	public ProductionAndHarvestSpace getProductionSpace() {
		return productionSpace;
	}

	public void setProductionSpace(ProductionAndHarvestSpace productionSpace2) {
		this.productionSpace = productionSpace2;
	}

	public MarketSpace getCoinSpace() {
		return coinSpace;
	}

	public void setCoinSpace(MarketSpace coinSpace2) {
		this.coinSpace = coinSpace2;
	}

	public MarketSpace getServantSpace() {
		return servantSpace;
	}

	public void setServantSpace(MarketSpace servantSpace2) {
		this.servantSpace = servantSpace2;
	}

	public MarketSpace getMixedSpace() {
		return mixedSpace;
	}

	public void setMixedSpace(MarketSpace mixedSpace2) {
		this.mixedSpace = mixedSpace2;
	}

	public TwoPrivilegesSpace getTwoPrivilegesSpace() {
		return twoPrivilegesSpace;
	}

	public void setTwoPrivilegesSpace(TwoPrivilegesSpace twoPrivilegesSpace2) {
		this.twoPrivilegesSpace = twoPrivilegesSpace2;
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
		this.councilPalace = councilPalace2;
	}
	
}
