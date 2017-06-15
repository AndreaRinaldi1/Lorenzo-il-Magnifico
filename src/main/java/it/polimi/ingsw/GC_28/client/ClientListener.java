package it.polimi.ingsw.GC_28.client;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;

import it.polimi.ingsw.GC_28.components.CouncilPrivilege;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.effects.GoToHPEffect;
import it.polimi.ingsw.GC_28.effects.TakeCardEffect;
import it.polimi.ingsw.GC_28.model.Player;

public class ClientListener extends Thread{
	
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
				writer.setLock();
				System.out.println(writer.lock.isLocked());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			do{
				socketLine = socketIn.readUTF();
				if(socketLine.equalsIgnoreCase("player")){
					//System.out.println("recived player");
					Object obj = socketIn.readUnshared();
					//System.out.println("received obj");
					Player p = (Player)obj;
					this.player = p;
					writer.setPlayer(p);
					//writer.setUnLock();
					System.out.println("player listener " + player.toString());
					//System.out.println(play);
					//System.out.println("player writer"+writer.player.toString());
				}
				else if(!(socketLine.equalsIgnoreCase("player"))){
					if(socketLine.equalsIgnoreCase("askAlternative")){
						String type = socketIn.readUTF();
						Resource discount1 = (Resource)socketIn.readObject();
						Resource discount2 = (Resource)socketIn.readObject();
						writer.askAlternative(discount1, discount2, type);
					}else if(socketLine.equalsIgnoreCase("askPrivilege")){
						int numberOfCouncilPrivileges = socketIn.readInt();
						boolean different = socketIn.readBoolean();
						writer.askPrivilege(numberOfCouncilPrivileges,different);
					}
					else if(socketLine.equalsIgnoreCase("takeCard")){
						Object obj = socketIn.readObject();
						TakeCardEffect effect = (TakeCardEffect) obj; 
						writer.askCard(effect);
					}
					else if(socketLine.equalsIgnoreCase("goToHP")){
						Object obj = socketIn.readObject();
						GoToHPEffect gthp = (GoToHPEffect) obj;
						writer.goToSpace(gthp);
					}
					else{
						if(socketLine.equalsIgnoreCase("it's your turn") && !first){
							writer.setUnLock();
						}
						else if(socketLine.equalsIgnoreCase("lock")){
							if(!writer.lock.isLocked()){
								writer.setLock();
							}
						}
						first = false;
						System.out.println(socketLine);
					}
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
	
	
	
	
}
