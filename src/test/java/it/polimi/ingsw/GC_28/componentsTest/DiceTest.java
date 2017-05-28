package it.polimi.ingsw.GC_28.componentsTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.GC_28.components.Dice;
import it.polimi.ingsw.GC_28.components.DiceColor;

public class DiceTest {

	private Dice dice = new Dice(DiceColor.ORANGE);
	
	@Before
	public void dice(){
		dice.rollDice();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetColor() {
		assertEquals(DiceColor.ORANGE, dice.getColor());
	}

	@Test
	public void testGetValue() {
		for(int i = 0; i<6; i++){
			if(i == dice.getValue()){
				assertEquals(i, dice.getValue());
			}
		}
	}

	@Test
	public void testToString() {
		assertEquals("Color: " + DiceColor.ORANGE + "Value: " + dice.getValue(), dice.toString());
	}

}
