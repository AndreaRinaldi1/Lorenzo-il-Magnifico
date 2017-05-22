package components;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class Resource {
	//attributes and methods parameters in class Resource need Map and not EnumMap (fromJson's fault)
	
	private final Map<ResourceType, Integer> resource; 
	
	private Resource(Map<ResourceType, Integer> resource){
		this.resource = resource;
	} 
	
	public static Resource of(EnumMap<ResourceType, Integer> resource){
		return new Resource(resource);
	}
	
	public Map<ResourceType, Integer> getResource(){
		return this.resource;
	}
	
	public String toString(){
		Set<ResourceType> keySet = resource.keySet();
		StringBuilder s = new StringBuilder();
		
		for(ResourceType resType : keySet){
			s.append(resType.name() + ": " + resource.get(resType) +"\n");
		}
		return s.toString();
	}
}
