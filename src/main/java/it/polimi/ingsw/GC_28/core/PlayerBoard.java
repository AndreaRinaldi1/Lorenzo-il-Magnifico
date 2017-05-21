package it.polimi.ingsw.GC_28.core;

import java.util.EnumMap;

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
