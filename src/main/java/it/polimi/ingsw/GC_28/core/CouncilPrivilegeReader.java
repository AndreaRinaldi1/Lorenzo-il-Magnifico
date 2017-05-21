package it.polimi.ingsw.GC_28.core;
import it.polimi.ingsw.GC_28.cards.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


public class CouncilPrivilegeReader {
	

    /*public static void main(String[] args) throws FileNotFoundException{
        CouncilPrivilegeReader reader = new CouncilPrivilegeReader();
        reader.startRead();
    }*/

    public static void startRead() throws FileNotFoundException{
    	Gson gson = new GsonBuilder().create();
        JsonReader reader = new JsonReader(new FileReader("priv.json"));
        GameBoard gameBoard = GameBoard.instance();
        try{
        	CouncilPrivilege cp = gson.fromJson(reader, CouncilPrivilege.class);
        	gameBoard.getCouncilPalace().setBonus2(cp);
        	//System.out.println(x.get("a").toString());
        	//System.out.println(d.toString());    		
	        reader.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
