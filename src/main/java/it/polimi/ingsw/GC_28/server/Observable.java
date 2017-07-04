package it.polimi.ingsw.GC_28.server;

import java.util.ArrayList;
import java.util.List;

/**
 * This class, along with the Observer Class, helps the implementation of the MVC pattern
 * @author andreaRinaldi
 * @version 1.0, 07/04/2017
 */
public abstract class Observable<C> {
	private List<Observer<C>> observers;
	
	public Observable(){
		this.observers=new ArrayList<Observer<C>>();
	}
	
	/**
	 * @param o the observer that want to be added to the updated observers
	 */
	public void registerObserver(Observer<C> o){
		this.observers.add(o);
	}
	
	/**
	 * @param c the object that we wanto to notify to the observers
	 */
	public void notifyObserver(C c) {
		for(Observer<C> o: this.observers){
			o.update(c);
		}
	}
	

}
