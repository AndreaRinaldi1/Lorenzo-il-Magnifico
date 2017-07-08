package it.polimi.ingsw.GC_28.boards;

import java.util.EnumMap;
import java.util.Map;
import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.spaces.CouncilPalace;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;

/**
 * This class represents the gameboard along with all the spaces, the towers where cards are placed, the point tracks and dices reference.
 * @author andreaRinaldi, nicoloScipione
 * @version 1.0, 06/28/2017
 * @see ProdHarvSpace, MarketSpace, PrivilegesSpace, CouncilPalace, Dice, CardType, Tower, ExcommunicationTile
 */

public class GameBoard {
	private static final int N_EXCOMMUNICATIONTILE = 3;
	private ExcommunicationTile[] excommunications = new ExcommunicationTile[N_EXCOMMUNICATIONTILE];
	
	private Map<CardType, Tower> towers = new EnumMap<>(CardType.class);
	private Dice[] dices = new Dice[3];
	
	private ProdHarvSpace harvestSpace;
	private ProdHarvSpace productionSpace;
	private MarketSpace coinSpace;
	private MarketSpace servantSpace;
	private MarketSpace mixedSpace;
	private PrivilegesSpace privilegesSpace;
	private CouncilPalace councilPalace = CouncilPalace.instance();
	
	private String emptySpace = "( )\n";
	private String occSpace = "(X)\n";
	private String bigOccupiedSpace = "XXXXXXX";


	/**
	 * This method displays the gameboard. It uses several AsciiTables to represent all the spaces, towers and point tracks
	 * and appends them all as strings to a StringBuilder object that is returned as a string.
	 * @return The representation of the gameboard 
	 */
	public String display(){
		
		StringBuilder ret = new StringBuilder();
		ret.append("GAME BOARD\n");
		
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("TERRITORY","CHARACTER","BUILDING", "VENTURE");
		Map<CardType, String> cards = new EnumMap<>(CardType.class);
		for(int i = 3; i >= 0; i--){
			for(CardType ct : CardType.values()){
				Cell cell = towers.get(ct).getCells()[i];
				StringBuilder cardInfo = new StringBuilder();
				if(cell.getCard() != null){
					cardInfo.append(cell.getCard().getName());
					cards.put(ct,cardInfo.toString());
				}
				else{
					if(cell.getFamilyMember() != null){
						cards.put(ct, cell.getFamilyMember().getPlayer().getName().toUpperCase() + "{" + cell.getFamilyMember().getPlayer().getColor() + "}");
					}
					else{
						cards.put(ct, "");
					}
				}
			}
			at.addRule();
			
			at.addRow(cards.get(CardType.TERRITORY),
					cards.get(CardType.CHARACTER),
					cards.get(CardType.BUILDING), 
					cards.get(CardType.VENTURE));
		}
		at.addRule();
		String board = at.render() + "\n";
		ret.append(board);
		//Council Palace
		AsciiTable councilTable = new AsciiTable();
		councilTable.addRule();
		councilTable.addRow("Council Palace: ");
		councilTable.addRule();
		StringBuilder council = new StringBuilder();
		for (int i = 0; i < councilPalace.getPlayer().size(); i++){
			council.append("{" + councilPalace.getPlayer().get(i).getPlayer().getColor()+ "} ");
		}
		councilTable.addRow(council.toString());
		councilTable.addRule();
		String cp = councilTable.render() + "\n";
		ret.append(cp);
		
		AsciiTable spaces = new AsciiTable();
		spaces.addRule();
		spaces.addRow("Coin Space", "Servant Space", "Mixed Space", "Privileges Space");
		spaces.addRule();
		String mixSpace = emptySpace;
		String privSpace = emptySpace;
		if(!mixedSpace.isFree()){
			if(!mixedSpace.getPlayer().isEmpty()){
				mixSpace = "(" + mixedSpace.getPlayer().get(0).getPlayer().getColor().toString().toUpperCase() + ")";
			}
			else{
				mixSpace = occSpace;
			}
		}
		if(!privilegesSpace.isFree()){
			if(!privilegesSpace.getPlayer().isEmpty()){
				privSpace = "(" + privilegesSpace.getPlayer().get(0).getPlayer().getColor().toString().toUpperCase() + ")";
			}
			else{
				privSpace = occSpace;
			}
		}
		spaces.addRow(coinSpace.isFree() ? emptySpace : "(" + coinSpace.getPlayer().get(0).getPlayer().getColor().toString().toUpperCase() + ")", 
					servantSpace.isFree() ? emptySpace : "(" + servantSpace.getPlayer().get(0).getPlayer().getColor().toString().toUpperCase() + ")",
					mixedSpace.isFree() ? emptySpace : mixSpace, 
					privilegesSpace.isFree() ? emptySpace : privSpace);
		spaces.addRule();
		ret.append(spaces.render() + "\n");
		
		AsciiTable prodHarv = new AsciiTable();
		
		StringBuilder harv = new StringBuilder();
		StringBuilder prod = new StringBuilder();
		if(!harvestSpace.isSecondarySpace()){
			harv.append(bigOccupiedSpace);
		}
		else{
			for(FamilyMember fm : harvestSpace.getPlayer()){
				harv.append("{" + fm.getPlayer().getColor() + "}");
			}
		}
		if(!productionSpace.isSecondarySpace()){
			prod.append(bigOccupiedSpace);
		}
		else{
			for(FamilyMember fm : productionSpace.getPlayer()){
				prod.append("{" + fm.getPlayer().getColor() + "}");
			}
		}
		
		prodHarv.addRule();
		prodHarv.addRow("Production Space", "Harvest Space");
		prodHarv.addRule();
		prodHarv.addRow((productionSpace.isFree() ? emptySpace : "(" + productionSpace.getFirstPlayer().getPlayer().getColor().toString().toUpperCase() + ")") + "  " + ("(" + prod.toString() + ")"),
						(harvestSpace.isFree() ? emptySpace : "(" + harvestSpace.getFirstPlayer().getPlayer().getColor().toString().toUpperCase() + ")") + "  " + ("(" + harv.toString() + ")"));
		prodHarv.addRule();
		ret.append(prodHarv.render() + "\n");

	
		return ret.toString();
	}
	
	/**
	 * @return The four towers where cards are placed
	 */
	public Map<CardType, Tower> getTowers() {
		return towers;
	}
	
	/**
	 * @param towers The four towers where cards are placed
	 */
	public void setTowers(Map<CardType,Tower> towers){
		this.towers = towers;
	}
	
	/**
	 * @return The array of three excommunication tiles present on the gameboard
	 */
	public ExcommunicationTile[] getExcommunications() {
		return excommunications;
	}

	/**
	 * @param excommunications The array of three excommunication tiles present on the gameboard
	 */
	public void setExcommunications(ExcommunicationTile[] excommunications) {
		this.excommunications = excommunications;
	}

	/**
	 * @return The array of three dices present on the gameboard
	 */
	public Dice[] getDices() {
		return dices;
	}

	/**
	 * @param dices The array of three dices present on the gameboard
	 */
	public void setDices(Dice[] dices) {
		this.dices = dices;
	}
	
	/**
	 * @return The harvest space on the gameboard
	 */
	public ProdHarvSpace getHarvestSpace() {
		return harvestSpace;
	}

	/**
	 * @param harvestSpace The harvest space on the gameboard
	 */
	public void setHarvestSpace(ProdHarvSpace harvestSpace) {
		this.harvestSpace = harvestSpace;
	}

	/**
	 * @return The production space on the gameboard
	 */
	public ProdHarvSpace getProductionSpace() {
		return productionSpace;
	}

	/**
	 * @param productionSpace2 The production space on the gameboard
	 */
	public void setProductionSpace(ProdHarvSpace productionSpace) {
		this.productionSpace = productionSpace;
	}

	/**
	 * @return The Market space on the gameboard that gives coins
	 */
	public MarketSpace getCoinSpace() {
		return coinSpace;
	}

	/**
	 * @param coinSpace The Market space on the gameboard that gives coins
	 */
	public void setCoinSpace(MarketSpace coinSpace) {
		this.coinSpace = coinSpace;
	}

	/**
	 * @return The Market space on the gameboard that gives servants
	 */
	public MarketSpace getServantSpace() {
		return servantSpace;
	}

	/** 
	 * @param servantSpace The Market space on the gameboard that gives servants
	 */
	public void setServantSpace(MarketSpace servantSpace) {
		this.servantSpace = servantSpace;
	}

	/**
	 * @return The Market space on the gameboard that gives military points and coins
	 */
	public MarketSpace getMixedSpace() {
		return mixedSpace;
	}

	/**
	 * @param mixedSpace The Market space on the gameboard that gives military points and coins
	 */
	public void setMixedSpace(MarketSpace mixedSpace) {
		this.mixedSpace = mixedSpace;
	}

	/**
	 * @return The Market space on the gameboard that gives two different council privileges
	 */
	public PrivilegesSpace getPrivilegesSpace() {
		return privilegesSpace;
	}

	/** 
	 * @param privilegesSpace The Market space on the gameboard that gives two different council privileges
	 */
	public void setPrivilegesSpace(PrivilegesSpace privilegesSpace) {
		this.privilegesSpace = privilegesSpace;
	}

	/**
	 * @return The Council Palace on the gameboard 
	 */
	public CouncilPalace getCouncilPalace() {
		return councilPalace;
	}

	/**
	 * @param councilPalace The Council Palace on the gameboard
	 */
	public void setCouncilPalace(CouncilPalace councilPalace) {
		this.councilPalace = councilPalace;
	}
	
}
