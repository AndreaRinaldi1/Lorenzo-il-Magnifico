package it.polimi.ingsw.GC_28.components;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.lang.Character;

public class CouncilPrivilege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<Character, Resource> options = new HashMap<>();
	private static CouncilPrivilege instance;
	
	private CouncilPrivilege(){
	}
	
	public static CouncilPrivilege instance(){
		if(instance == null){
			instance = new CouncilPrivilege();
		}
		return instance;
	}
	
	public Resource choose(Character c){ //forse non necessario
		return options.get(c); 
	}
	
	public Map<Character, Resource> getOptions() {
		return options;
	}
	
	public void setOptions(HashMap<Character, Resource> options) {
		this.options = options;
	}
	
	public static void setCouncilPrivilege(CouncilPrivilege cp){
		if(instance == null){
			instance = cp;
		}
	}
	
}
