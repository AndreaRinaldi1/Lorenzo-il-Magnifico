package it.polimi.ingsw.GC_28.boards;

import java.util.EnumMap;

import components.Resource;
import components.ResourceType;
import it.polimi.ingsw.GC_28.cards.*;

public class PlayerBoard {
	private Resource resource = Resource.of(new EnumMap<ResourceType, Integer>(ResourceType.class));

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
