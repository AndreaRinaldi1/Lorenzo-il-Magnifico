package it.polimi.ingsw.GC_28.effects;

import it.polimi.ingsw.GC_28.boards.GameBoard;
import it.polimi.ingsw.GC_28.boards.PlayerBoard;
import it.polimi.ingsw.GC_28.components.FamilyMember;
import it.polimi.ingsw.GC_28.model.Game;

public class IncrementHPEffect extends Effect{
	private int increment;
	private boolean production;
	private boolean harvest;
	public final EffectType type = EffectType.INCREMENTHPEFFECT;

	public IncrementHPEffect(){
		super();
	}

	public int getIncrement() {
		return increment;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public boolean isProduction() {
		return production;
	}

	public void setProduction(boolean production) {
		this.production = production;
	}

	public boolean isHarvest() {
		return harvest;
	}

	public void setHarvest(boolean harvest) {
		this.harvest = harvest;
	}
	


	@Override
	public void apply(FamilyMember familyMember, GameBoard gameBoard, Game game) {
		System.out.println("apply di IncrementHPEffect");
		//Quando un controller, prima di effettuare l'azione di harvest o production, deve controllare se ci sono incrementhpeffects
		//chiama (per ogni IncrementHPEffect che trova) getProduction o getHarvest a seconda dell'azione scelta dal giocatore e guarda se è true o false. 
		//Se true chiama questo apply (che quindi non fa controlli sui boolean ma aumenta solo actionValue del familyMember), se no non lo chiama.
		familyMember.incrementValue(increment);
	}
	
	
}
