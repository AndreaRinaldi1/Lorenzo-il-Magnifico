package it.polimi.ingsw.GC_28.components;

import java.util.Map;
import java.util.Set;

/**
 * This class allows to create a new resource. Every resource is a Map<ResourceType, Integer>, 
 * for each ResourceType (coin, wood, stone, servant, military points, victory points, faith points) 
 * there is a number that says how many there are. 
 * @author robertoturi
 * @version 1.0, 07/08/2017
 */
public class Resource{
	private Map<ResourceType, Integer> resource; 
		
	/**
	 * This private method is used by the public method ".of" to create a new resource
	 * @param resource it is the Map<ResoruceType, Integer> that it has to be set
	 */
	private Resource(Map<ResourceType, Integer> resource){
		this.resource = resource;
	} 
	
	/**
	 * @param resource the Map<ResourceType, Integer> with the values that we want to set
	 * @return A new resource with for each resource's types the values that we want to set by the Map<ResourceType, Integer>
	 */
	public static Resource of(Map<ResourceType, Integer> resource){
		return new Resource(resource);
	}
	
	/**
	 * @return the Map<ResourceType, Integer> with the values that we want to set
	 */
	public Map<ResourceType, Integer> getResource(){
		return this.resource;
	}
	
	/**
	 * This method is used to add the amount of resources, if the boolean value "sum" is true; if "sum" is false
	 * the method will subtract the amount of resources.
	 * If the parameter "amount" is null, this method will return the starting resource. 
	 * @param amount it is the amount of resources that will be added or subtracted
	 * @param sum true if it is a sum, false if it is subtraction
	 */
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
	
	/**
	 * 
	 * @param otherResource the comparison resource 
	 * @return false if "otherResource" is null, if the value of every resource's types is different between the two resource that we analyze. True otherwise.
	 */
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

	/**
	 * This method is used to describe the resource with resource's type and the value of it.
	 */
	@Override
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
	
	/**
	 * @param otherRes it is a comparison resource
	 * @return false if the comparison resource is bigger than the one we analyze, true otherwise. 
	 */
	public boolean greaterOrEqual(Resource otherRes) {
		for(ResourceType rt : otherRes.getResource().keySet()){
			if(resource.get(rt) < otherRes.getResource().get(rt)){
				return false;
			}
		}
		return true;
	}
}
