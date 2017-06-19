package it.polimi.ingsw.GC_28.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable<C> {
	private List<Observer<C>> observers;
	
	
	public Observable(){
		this.observers=new ArrayList<Observer<C>>();
	}
	
	public void registerObserver(Observer<C> o){
		this.observers.add(o);
	}
	public void unregisterObserver(Observer<C> o){
		this.observers.remove(o);
	}
	
	public void notifyObserver(C c) {
		for(Observer<C> o: this.observers){
			o.update(c);
		}
	}
	
	protected void notifyObserver() {
		for(Observer<C> o: this.observers){
			o.update();
		}		
	}
}
