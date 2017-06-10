package it.polimi.ingsw.GC_28.cardsTest;

import it.polimi.ingsw.GC_28.cards.CardType;
import it.polimi.ingsw.GC_28.cards.LeaderCard;
import it.polimi.ingsw.GC_28.components.Resource;
import it.polimi.ingsw.GC_28.components.ResourceType;
import it.polimi.ingsw.GC_28.effects.Effect;
import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.OtherEffect;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.Map;

import org.junit.*;

public class LeaderCardTest {
	
	private LeaderCard leader;
	private String name = "ciao";
	private Boolean p = true;
	private Boolean a = false;
	private Boolean permanent = true;
	private OtherEffect effect;
	private Resource resourceCost;
	private Map<CardType,Integer> cardCost = new EnumMap<>(CardType.class);
	
	@Before
	public void leaderCard(){
		this.leader = new LeaderCard();
		this.leader.setName(name);
		this.leader.setActive(a);
		this.leader.setPlayed(p);
		this.leader.setPermanent(permanent);
		EnumMap<ResourceType, Integer> resource = new EnumMap<ResourceType, Integer>(ResourceType.class);
		resource.put(ResourceType.COIN, 5);
		resourceCost = Resource.of(resource);
		this.leader.setResourceCost(resourceCost);
		cardCost.put(CardType.TERRITORY, 3);
		this.leader.setCardCost(cardCost);
		effect = new OtherEffect();
		effect.setType(EffectType.NOEXTRACOSTINTOWEREFFECT);
		this.leader.setEffect(effect);
	}
	
	@Test
	public void testGetName(){
		assertEquals(this.name, this.leader.getName());
	}
	
	@Test
	public void testGetActive(){
		assertEquals(this.a, this.leader.getActive());
	}
	
	@Test
	public void testGetPlayed(){
		assertEquals(this.p, this.leader.getPlayed());
	}
	 @Test 
	 public void testGetPermanent(){
			assertEquals(this.permanent, this.leader.getPermanent());
	 }
	 
	 @Test
	 public void testGetResourceCost(){
			assertEquals(this.resourceCost, this.leader.getResourceCost());
	 }
	 
	 @Test 
	 public void testGetCardCost(){
			assertEquals(this.cardCost, this.leader.getCardCost());
	 }
	 
	 @Test
	 public void testGetEffect(){
			assertEquals(this.effect, this.leader.getEffect());
	 }
	 
	 @Test
	 public void testToString(){
		 assertEquals("ciao played: " + p + " active: "+ a, this.leader.toString());
	 }
}











