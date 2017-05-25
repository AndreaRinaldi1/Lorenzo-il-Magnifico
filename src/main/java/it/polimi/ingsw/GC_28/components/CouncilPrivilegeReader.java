package it.polimi.ingsw.GC_28.components;
import it.polimi.ingsw.GC_28.cards.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        try{
        	JsonReader reader = new JsonReader(new FileReader("priv.json"));
        	CouncilPrivilege cp = gson.fromJson(reader, CouncilPrivilege.class);
        	//System.out.println(x.get("a").toString());
        	//System.out.println(d.toString());    		
	        reader.close();
    	}
    	catch(IOException e){
    		Logger.getAnonymousLogger().log(Level.INFO, "input not valide" + e);
    	}
    }
}
