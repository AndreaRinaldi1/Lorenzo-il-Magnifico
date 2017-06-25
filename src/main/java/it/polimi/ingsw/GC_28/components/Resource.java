package it.polimi.ingsw.GC_28.components;

import java.util.Map;

public class Resource{
	private Map<ResourceType, Integer> res; 
		
	private Resource(Map<ResourceType, Integer> resource){
		this.res = resource;
	} 
	
	public static Resource of(Map<ResourceType, Integer> resource){
		return new Resource(resource);
	}
	
	public Map<ResourceType, Integer> getResource(){
		return this.res;
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
	
	
	
	
	@Override
	public boolean equals(Object obj){
		Resource otherResource = (Resource) obj;
		Resource shorterResource;
		Resource longerResource;
		if(otherResource == null){
			return false;
		}
		else{
			if(!(otherResource.getResource().isEmpty())){
				if(res.size() > otherResource.getResource().size()){
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

	public boolean greaterOrEqual(Resource otherRes) {
		for(ResourceType rt : otherRes.getResource().keySet()){
			if(res.get(rt) < otherRes.getResource().get(rt)){
				return false;
			}
		}
		return true;
	}
}
