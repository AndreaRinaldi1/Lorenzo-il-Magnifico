package it.polimi.ingsw.GC_28.boards;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.spaces.CouncilPalace;
import it.polimi.ingsw.GC_28.spaces.MarketSpace;
import it.polimi.ingsw.GC_28.spaces.PrivilegesSpace;
import it.polimi.ingsw.GC_28.spaces.ProdHarvSpace;


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
	private Resource bonusFaithPoints;
	private CouncilPalace councilPalace = CouncilPalace.instance();
	
	private String emptySpace = "( )\n";
	private String occSpace = "(X)\n";
	private String bigEmptySpace = "(        )\n";
	private String bigOccupiedSpace = "XXXXXXX";
	
	public GameBoard(){
		/*empty for testing*/
	}
	

	public String display(){
		
		StringBuilder ret = new StringBuilder();
		ret.append("GAME BOARD\n");
		
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow("TERRITORY","CHARACTER","BUILDING", "VENTURE");
		Map<CardType, String> cards = new HashMap<>();
		for(int i = 3; i >= 0; i--){
			for(CardType ct : CardType.values()){
				Cell cell = towers.get(ct).getCells()[i];
				StringBuilder cardInfo = new StringBuilder();
				if(cell.getCard() != null){
					cardInfo.append(cell.getCard().getName());
					/*if(ct == CardType.VENTURE){
						Venture v = (Venture) cell.getCard();
						if(v.getAlternativeCostPresence()){
							cardInfo.append(v.getMinimumRequiredMilitaryPoints()+ " - " + v.getAlternativeCost().getResource().get(ResourceType.MILITARYPOINT) + " MP\n");
						}
					}
					if(cell.getCard().getCost() != null){
						cardInfo.append(cell.getCard().getCost().toString());
					}
					cards.put(ct,cardInfo.toString());*/
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
			
						/*at.addRow( ? towers.get(CardType.TERRITORY).getCells()[i].getCard().getName() :  "***",
					  towers.get(CardType.CHARACTER).getCells()[i].getCard() != null ?  : "***",
					  towers.get(CardType.BUILDING).getCells()[i].getCard() != null ? towers.get(CardType.BUILDING).getCells()[i].getCard().getName() :  "***",
					  towers.get(CardType.VENTURE).getCells()[i].getCard() != null ? towers.get(CardType.VENTURE).getCells()[i].getCard().getName() :  "***" );
		*/
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
		/*ret.append("CHURCH\n");
		AsciiTable church = new AsciiTable();
		church.addRule();
		StringBuilder churchString = new StringBuilder();
		for(Player p : gameBoardChurch.getPlayersMarkers().get(Integer.valueOf(0))){
			System.out.println(p.getColor());
		}
		for(int i = 1 ; i < 16 ; i++){
			churchString.append(i);
			for(Player p : gameBoardChurch.getPlayersMarkers().get(Integer.valueOf(i))){
				churchString.append(" "+ p.getColor());
			}
		}
		church.addRow("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15");
		church.addRow(churchString.toString());
		church.addRule();
		ret.append(church.render() + "\n");			//ho bisogno delle carte scomunica
*/		
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

	public Map<CardType, Tower> getTowers() {
		return towers;
	}
	
	
	public ExcommunicationTile[] getExcommunications() {
		return excommunications;
	}


	public void setExcommunications(ExcommunicationTile[] excommunications) {
		this.excommunications = excommunications;
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
	
	public ProdHarvSpace getHarvestSpace() {
		return harvestSpace;
	}

	public void setHarvestSpace(ProdHarvSpace harvestSpace2) {
		this.harvestSpace = harvestSpace2;
	}

	public ProdHarvSpace getProductionSpace() {
		return productionSpace;
	}

	public void setProductionSpace(ProdHarvSpace productionSpace2) {
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
