package it.polimi.ingsw.GC_28.components;

import java.util.HashMap;
import java.util.Map;
import java.lang.Character;

public class CouncilPrivilege {
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

	public Map<Character, Resource> getOptions() {
		return options;
	}
	
	public void setOptions(Map<Character, Resource> options) {
		this.options = options;
	}
	
	public static void setCouncilPrivilege(CouncilPrivilege cp){
		if(instance == null){
			instance = cp;
		}
	}
	
}
