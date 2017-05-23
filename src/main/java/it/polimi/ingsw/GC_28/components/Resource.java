package it.polimi.ingsw.GC_28.components;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class Resource {
	//attributes and methods parameters in class Resource need Map and not EnumMap (fromJson's fault)
	
	private Map<ResourceType, Integer> resource; 
	
	public Resource(){}; //per le prove, al massimo dopo si toglie o private
	
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
	
	
	public boolean equals(Resource otherResource){
		Resource shorterResource;
		Resource longerResource;
		if(resource.size() > otherResource.getResource().size()){
			shorterResource = otherResource;
			longerResource = this;
		}
		else{
			shorterResource = this;
			longerResource = otherResource;
		}
		for(ResourceType resType : longerResource.getResource().keySet()){
			if(shorterResource.getResource().containsKey(resType)){
				if(!(longerResource.getResource().get(resType).equals(shorterResource.getResource().get(resType)))){
					return false;
				}
			}
			else{
				if(!(longerResource.getResource().get(resType).equals(0))){
					return false;
				}
			}
		}
		return true;
	}
}