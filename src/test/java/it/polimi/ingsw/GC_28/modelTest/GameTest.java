package it.polimi.ingsw.GC_28.modelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.client.Client;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.model.Game;
import it.polimi.ingsw.GC_28.model.Player;
import it.polimi.ingsw.GC_28.model.PlayerColor;
import it.polimi.ingsw.GC_28.server.ClientHandler;
import it.polimi.ingsw.GC_28.server.Server;

public class GameTest {
	private Game game;
	private GameBoard gameBoard;

	//Scanner currentPlayer.getIn() = new Scanner(System.in);
	private Player currentPlayer;
	boolean modifiedWithServants = false;
	private int currentEra  = 1;
	private int currentPeriod = 1;
	private List<Player> players;
	private Map<Player, ClientHandler> handlers = new HashMap<>();
	private ClientHandler ch;
	private Socket socket;
	ArrayList<Character> choices;
	private int numberOfCouncilPrivileges = 2;
	private boolean different;
	
	private ServerSocket server;
	private PrintStream p;
	private Scanner scan;
	
	@Before
	public void game() throws IOException, Exception{
		game = new Game();
		gameBoard = new GameBoard();
		currentPlayer = new Player("coso", PlayerColor.BLUE);
		players = new ArrayList<>();
		handlers = new HashMap<>();
		socket = new Socket();
		ch = new ClientHandler(socket);
		choices = new ArrayList<Character>();
		/*p = new PrintStream(socket.getOutputStream());
		scan = new Scanner(socket.getInputStream());*/
	} 
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetHandlers() {
		handlers.put(currentPlayer, ch);
		game.setHandlers(handlers);	
		assertEquals(this.handlers, this.game.getHandlers());
	}

	@Test
	public void testGetCurrentPeriod() {
		game.setPeriod(currentPeriod);
		assertEquals(this.currentPeriod, this.game.getCurrentPeriod());
	}

	@Test
	public void testGetCurrentPlayer() {
		game.setCurrentPlayer(currentPlayer);
		assertEquals(this.currentPlayer, this.game.getCurrentPlayer());
	}

	@Test
	public void testGetCurrentEra() {
		game.setCurrentEra(currentEra);
		assertEquals(this.currentEra, this.game.getCurrentEra());
	}

	@Test
	public void testGetGameBoard() {
		game.setGameBoard(gameBoard);
		assertEquals(this.gameBoard, this.game.getGameBoard());
	}

	@Test
	public void testGetPlayers() {
		players.add(currentPlayer);
		game.setPlayers(players);
		assertEquals(this.players, this.game.getPlayers());
	}

	
}
