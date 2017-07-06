package it.polimi.ingsw.GC_28.server;


import it.polimi.ingsw.GC_28.core.*;

/**
 * This class is where the methods for controlling the validity of the action and its actual application are called.
 * @author andreaRinaldi 
 * @version 1.0, 07/04/2017
 */
public class Controller implements Observer<Action> {

	@Override
	public void update(Action action) {
		if(action.isApplicable()){
			action.apply();
		}
	}

}
