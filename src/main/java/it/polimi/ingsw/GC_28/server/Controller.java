package it.polimi.ingsw.GC_28.server;


import it.polimi.ingsw.GC_28.core.*;
import it.polimi.ingsw.GC_28.view.GameView;

public class Controller implements Observer<Action> {

	@Override
	public void update(Action action) {
		if(action.isApplicable()){
			action.apply();
		}
	}

}
