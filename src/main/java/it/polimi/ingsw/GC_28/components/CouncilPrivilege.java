package it.polimi.ingsw.GC_28.components;

import java.util.HashMap;
import java.util.Map;


/**
 * This class allows to create a new conucil's privilege. A council's privilege is a Map<Character, resource>
 * and it is used as bonus for the council palace and for some cards. 
 * When the player has to choose a conucil's privilege, he will have a list of chars and resources.
 * The player will choose the char of the resource he wants and the resource he chose will add to the player's resources.  
 * @author robertoturi
 * @version 1.0, 07/08/2017
 */
public class CouncilPrivilege {
	private Map<Character, Resource> options = new HashMap<>();
	private static CouncilPrivilege instance;
	
	private CouncilPrivilege(){
	}
	
	/**
	 * This static method will create a council's privilege if it isn't created yet, 
	 * otherwise it returns the same council's privilege.  
	 * @return A new council's privilege if it isn't created yet, or the same otherwise. 
	 */
	public static CouncilPrivilege instance(){
		if(instance == null){
			instance = new CouncilPrivilege();
		}
		return instance;
	}

	/**
	 * @return options, it is the Map<Character, Resource>
	 */
	public Map<Character, Resource> getOptions() {
		return options;
	}
	
	/**
	 * @param options, the Map<Character, Resource> 
	 */
	public void setOptions(Map<Character, Resource> options) {
		this.options = options;
	}
	
	/**
	 * If the council's privilege is null, this method sets that council's privilege with another council's privilege not null.   
	 * @param cp 
	 */
	public static void setCouncilPrivilege(CouncilPrivilege cp){
		if(instance == null){
			instance = cp;
		}
	}
	
}
