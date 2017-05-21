package core;

import java.util.EnumMap;

import cards.Resource;
import cards.ResourceType;

public class PlayerBoard {
	public Resource res = Resource.of(new EnumMap<ResourceType, Integer>(ResourceType.class));
}
