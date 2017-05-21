package it.polimi.ingsw.GC_28.core;

import java.util.EnumMap;

import it.polimi.ingsw.GC_28.cards.*;

public class PlayerBoard {
	public Resource res = Resource.of(new EnumMap<ResourceType, Integer>(ResourceType.class));
}
