package it.polimi.ingsw.GC_28.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.GC_28.view.GameView;

public interface ClientHandler{
	public abstract void send(String message) ;
	public abstract String receive() ;
	
}
