package it.polimi.ingsw.GC_28.cardsTest;

import static org.junit.Assert.*;

import org.junit.*;

import it.polimi.ingsw.GC_28.cards.ExcommunicationTile;

import it.polimi.ingsw.GC_28.effects.EffectType;
import it.polimi.ingsw.GC_28.effects.OtherEffect;


public class ExcomunicationTileTest {
	private ExcommunicationTile ex;
	private OtherEffect effect;
	private int era;
	@Before
	public void excommunication(){
		this.ex = new ExcommunicationTile();
		effect =  new OtherEffect();
		effect.setType(EffectType.NOCELLBONUS);
		this.ex.setEffect(effect);
		era = 1;
		this.ex.setEra(era);
	}
	
	@Test
	public void testGetEra(){
		assertEquals(this.era, this.ex.getEra());
	}
	
	@Test
	public void testGetEffect(){
		assertEquals(this.effect, this.ex.getEffect());
	}
}
