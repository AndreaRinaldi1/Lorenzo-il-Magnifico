package it.polimi.ingsw.GC_28.boards;

import java.util.EnumMap;
import java.util.Map;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Dice;

import it.polimi.ingsw.GC_28.components.Resource;

import it.polimi.ingsw.GC_28.spaces.CouncilPalace;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;


public class GameBoard {
	private static final int N_DICE = 3;
	private static final int N_EXCOMMUNICATIONTILE = 3;
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Dice[] dices = new Dice[3];
	private ExcommunicationTile[] excommunications = new ExcommunicationTile[N_EXCOMMUNICATIONTILE];
	
	private ProductionAndHarvestSpace harvestSpace;
	private ProductionAndHarvestSpace productionSpace;
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private PrivilegesSpace privilegesSpace;
	private Resource bonusFaithPoints;
	private CouncilPalace councilPalace = CouncilPalace.instance();
	
	private String emptySpace = "( )\n";
	private String occSpace = "(X)\n";
	
	public GameBoard(){
		/*empty for testing*/
	}
	

	public String display(){
		StringBuilder ret = new StringBuilder();
		ret.append("GAME BOARD\n");
		
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("TERRITORY","BUILDING", "CHARACTER", "VENTURE");
		for(int i = 3; i >= 0; i--){
			at.addRule();
			at.addRow(towers.get(CardType.TERRITORY).getCells()[i].getCard().getName(),
					  towers.get(CardType.BUILDING).getCells()[i].getCard().getName(),
					  towers.get(CardType.CHARACTER).getCells()[i].getCard().getName(),
					  towers.get(CardType.VENTURE).getCells()[i].getCard().getName());
		}
		at.addRule();
		
		String board = at.render();
		board += "\n";
		
		ret.append(board);
		
		System.out.println("display 2");
		//Council Palace
		AsciiTable councilTable = new AsciiTable();
		councilTable.addRule();
		councilTable.addRow("Council Palace: ");
		councilTable.addRule();
		for (int i = 0; i < councilPalace.getPlayer().size(); i++){
			councilTable.addRow(councilPalace.getPlayer().get(i).getPlayer().getName() + "  " +
								councilPalace.getPlayer().get(i).getPlayer().getColor());
			councilTable.addRule();
		}
		String cp = councilTable.render() + "\n";
		ret.append(cp);
		/*ret.append("Council Palace: \n");
		for (int i = 0; i < councilPalace.getPlayer().size(); i++){
			ret.append(councilPalace.getPlayer().get(i).getPlayer().getColor());
		}*/
		
		ret.append("Church: | | | |\n");			//ho bisogno delle carte scomunica
		
		//hHarvest and Production Space
		ret.append("Harverst Space: \n");
		if(!(harvestSpace.isFree())){
			ret.append(occSpace);
		}
		else{
			ret.append(emptySpace);
		}
		ret.append("(    )\n");
		
		ret.append("Production Space: \n");
		if(!(productionSpace.isFree())){
			ret.append(occSpace);
		}
		else{
			ret.append(emptySpace);
		}
		ret.append("(    )\n");
		
		//Market Space
		ret.append("Coin Space: \n");
		if(!(coinSpace.isFree())){
			ret.append(occSpace);
		}
		else{
			ret.append(emptySpace);
		}
		ret.append("Servant Space: \n");
		if(!(servantSpace.isFree())){
			ret.append(occSpace);
		}
		else{
			ret.append(emptySpace);
		}
		ret.append("Mixed Space: \n");
		if(!(mixedSpace.isFree())){
			ret.append(occSpace);
		}
		else{
			ret.append(emptySpace);
		}
		
		ret.append("Two Privileges Space: \n");
		if(!(privilegesSpace.isFree())){
			ret.append(occSpace);

		}
		else{
			ret.append(emptySpace);
		}
		ret.append("\n");
	
		//Dice Space
		ret.append("Dice Values \n");
		for(int j = 0; j < N_DICE; j++){
			ret.append((dices[j].getColor().toString()+": " + dices[j].getValue() + '\n'));
		}
		return ret.toString();
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

	public PrivilegesSpace getPrivilegesSpace() {
		return privilegesSpace;
	}

	public void setPrivilegesSpace(PrivilegesSpace privilegesSpace) {
		this.privilegesSpace = privilegesSpace;
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
