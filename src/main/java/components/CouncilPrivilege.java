package components;

import java.util.HashMap;
import java.lang.Character;

public class CouncilPrivilege {
	private HashMap<Character, Resource> options = new HashMap<Character, Resource>();
	private static CouncilPrivilege instance;
	
	private CouncilPrivilege(){
	}
	
	public static CouncilPrivilege instance(){
		if(instance == null){
			instance = new CouncilPrivilege();
		}
		return instance;
	}
	
	public Resource choose(Character c){
		return options.get(c); 
	}
	
	public HashMap<Character, Resource> getOptions() {
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
