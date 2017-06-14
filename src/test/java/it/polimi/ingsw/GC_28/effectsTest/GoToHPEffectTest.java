package it.polimi.ingsw.GC_28.effectsTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import it.polimi.ingsw.GC_28.boards.BonusTile;
import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.DiceColor;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.GameModel;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Server;
import it.polimi.ingsw.GC_28.spaces.ProductionAndHarvestSpace;

public class GoToHPEffectTest {
	private GoToHPEffect gt;
	private int actionValue = 4;
	private boolean production;
	private boolean harvest;
	
	private FamilyMember fm;
	private Player player;
	private Game g;
	private GameBoard gb;
	private ProductionAndHarvestSpace productionSpace2;
	private Resource res;
	EnumMap<ResourceType, Integer> w;
	private GameModel gameModel;
	private List<Player> players = new ArrayList<>();
	
	private Socket socket = new Socket();
	private ClientHandler clientHandler;
	private Map<Player, ClientHandler> handlers = new HashMap<>();
	
	@Before
	public void goToHPEffect() throws IOException{
		//creazione effetto
		gt = new GoToHPEffect();
		//fatti player e family member 
		player = new Player("Gino", PlayerColor.BLUE);
		fm = new FamilyMember(player, false, DiceColor.BLACK);
		//creazione playerBoard
		w = new EnumMap<ResourceType, Integer>(ResourceType.class);
		for(ResourceType resType : ResourceType.values()){
			w.put(resType, 0);
		}
		res = Resource.of(w);
		BonusTile bt = new BonusTile();
		PlayerBoard pb = new PlayerBoard(bt, res);
		player.setBoard(pb);
		
	    byte[] emptyPayload = new byte[1001];

	    // Using Mockito
        final Socket socket = mock(Socket.class);
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(emptyPayload);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
	    when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
	    

		clientHandler = new ClientHandler(socket);
		handlers.put(player, clientHandler);
		players.add(player);
		gb = new GameBoard();
		gameModel = new GameModel(gb, players);
		g = new Game(gameModel);
		g.setHandlers(handlers);
		g.setCurrentPlayer(player);
		gb = new GameBoard();
		productionSpace2 = new ProductionAndHarvestSpace(true, 1);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

/*	@Test
	public void testApply() {
		harvest = true;
		gt.setActionValue(actionValue);
		gt.setHarvest(this.harvest);
		gt.setProduction(this.production);
		//gt.apply(this.fm, this.g);
		fail("Not yet implemented");

		//assertTrue(g.goToSpace(gt));
	}
*/
	@Test
	public void testGetActionValue() {
		gt.setActionValue(actionValue);
		assertEquals(this.actionValue, this.gt.getActionValue());
	}

	@Test
	public void testIsProduction() {
		gt.setProduction(production);
		assertEquals(false, this.gt.isProduction());
	}

	@Test
	public void testIsHarvest() {
		gt.setHarvest(true);
		assertEquals(true, this.gt.isHarvest());
	}

}
