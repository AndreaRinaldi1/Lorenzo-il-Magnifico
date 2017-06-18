package it.polimi.ingsw.GC_28.components;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class Resource{
	//attributes and methods parameters in class Resource need Map and not EnumMap (fromJson's fault)
	
	private Map<ResourceType, Integer> resource; 
	
	//public Resource(){}; //per le prove, al massimo dopo si toglie o private
	
	private Resource(Map<ResourceType, Integer> resource){
		this.resource = resource;
	} 
	
	public static Resource of(EnumMap<ResourceType, Integer> resource){
		return new Resource(resource);
	}
	
	public Map<ResourceType, Integer> getResource(){
		return this.resource;
	}
	
	public void modifyResource(Resource amount, boolean sum){
		int i;
		if(amount == null){
			return;
		}
		if(sum){
			i = 1;
		}
		else{
			i = -1;
		}
		for(ResourceType resourceType : amount.getResource().keySet()){
			Integer x = 0;
			if(this.getResource().containsKey(resourceType)){
				x = this.getResource().get(resourceType);
			}
			x = x + i*amount.getResource().get(resourceType);
			this.getResource().put(resourceType, x);
		}
	}
	
	
	
	public String toString(){
		Set<ResourceType> keySet = resource.keySet();
		StringBuilder s = new StringBuilder();
		for(ResourceType resType : keySet){
			if(resource.get(resType) != 0){
				s.append(resType.name() + ": " + resource.get(resType) +"\n");
			}
		}
		return s.toString();
	}
	
	
	public boolean equals(Resource otherResource){
		Resource shorterResource;
		Resource longerResource;
		if(otherResource == null){
			return false;
		}
		else{
			if(!(otherResource.getResource().isEmpty())){
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
			}
			return true;
		}
	}

	public boolean greaterOrEqual(Resource res) {
		for(ResourceType rt : res.getResource().keySet()){
			if(resource.get(rt) < res.getResource().get(rt)){
				return false;
			}
		}
		return true;
	}
}
