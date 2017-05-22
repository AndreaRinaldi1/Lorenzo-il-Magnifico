package it.polimi.ingsw.GC_28.boards;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.*;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;

public class PlayerBoard {
	private Resource resource = Resource.of(new EnumMap<ResourceType, Integer>(ResourceType.class));

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
