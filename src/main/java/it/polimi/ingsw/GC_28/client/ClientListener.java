package it.polimi.ingsw.GC_28.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Future;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Player;

public class ClientListener implements Runnable{
	
	Socket socket;
	Player player;
	ClientWriter writer;
	
	boolean first = true;
	
	public ClientListener(Socket socket, Player player, ClientWriter writer){
		this.socket = socket;
		this.player = player;
		this.writer = writer;
	}
	
	@Override
	public void run(){
		ObjectInputStream socketIn = null;
		String socketLine;
		try{
			//ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketIn = new ObjectInputStream(socket.getInputStream());
			try {
				do{
					socketLine = socketIn.readUTF();
					if(!("end").equalsIgnoreCase(socketLine)){
						System.out.println(socketLine);
					}
				}while(!socketLine.equals("end"));
				Object obj = socketIn.readObject();
				Player p = (Player)obj;
				//this.player = p;
				writer.setPlayer(p);
				System.out.println("set player");
				Object obj2 = socketIn.readObject();
				System.out.println("read councilpriv");
				CouncilPrivilege privilege = (CouncilPrivilege)obj2;
				writer.setCouncilPrivilege(privilege);
				System.out.println("set cp");
				Object obj3 = socketIn.readObject();
				GameBoard board = (GameBoard) obj3;
				writer.setGameBoard(board);
				System.out.println("set board");
				/*writer.setLock();
				System.out.println(writer.lock.isLocked());*/
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			do{
				socketLine = socketIn.readUTF();
				if(socketLine.equalsIgnoreCase("player")){
					//System.out.println("recived player");
					Object obj = socketIn.readObject();
					//System.out.println("received obj");
					Player p = (Player)obj;
					this.player = p;
					writer.setPlayer(p);
					//writer.setUnLock();
					System.out.println("player listener " + player.toString());
					//System.out.println(play);
					//System.out.println("player writer"+writer.player.toString());
				}
				else if(socketLine.equalsIgnoreCase("board")){
					Object obj = socketIn.readObject();
					GameBoard gameBoard = (GameBoard) obj;
					writer.setGameBoard(gameBoard);
					System.out.println(writer.gameBoard.display());
					System.out.println(writer.player.getBoard().display());
					//System.out.println(writer.player.displayFamilyMembers());
					for(int i = 0; i < 4; i++){
						System.out.println(writer.player.getFamilyMembers()[i].toString());
					}
					//System.out.println(writer.gameBoard.getDices()[0].getValue());
					
				}
				else if(socketLine.equalsIgnoreCase("freeFamilyMembers")){
					Object obj = socketIn.readObject();
					Dice[] dice = (Dice[]) obj;
					for(int i = 0;i < 4; i++ ){
						writer.player.getFamilyMembers()[i].setUsed(false);
						if(i != 3)
							writer.player.getFamilyMembers()[i].setValue(dice[i].getValue());
						else
							writer.player.getFamilyMembers()[i].setValue(0);
					}
					//setFamilyMember();
				}
				else{
					/*if(socketLine.equalsIgnoreCase("it's your turn") && !first){
						writer.setUnLock();
					}
					else if(socketLine.equalsIgnoreCase("lock")){
						if(!writer.lock.isLocked()){
							writer.setLock();
						}
					}
					first = false;*/
					System.out.println(socketLine);
				}
			
				if(socketLine.equals("sospeso")){
					System.out.println("entro");
					//socketOut.writeUTF("disconnect");
					//socketOut.flush();
				}
			}while(!socketLine.equals("close"));
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		catch(NoSuchElementException e){
			System.out.println("Client chiuso -listener-");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(socketIn != null){
					socketIn.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private void setFamilyMember(){
		FamilyMember[] f = writer.player.getFamilyMembers();
		for(FamilyMember fm : f){
			fm.setValue(writer.gameBoard.getDices());
			fm.setUsed(false);
		}
		f[3].setValue(0);
	}
}
