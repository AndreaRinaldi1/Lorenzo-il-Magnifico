package it.polimi.ingsw.GC_28.server;


import it.polimi.ingsw.GC_28.core.*;
import it.polimi.ingsw.GC_28.model.Game;

public class Controller implements Observer<Action> {

	@Override
	public void update(Action action) {
		System.out.println("controller 1");
		if(action.isApplicable()){
			System.out.println("controller 2");
			action.apply();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	
}
