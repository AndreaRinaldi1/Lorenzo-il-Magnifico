package it.polimi.ingsw.GC_28.server;

public interface Observer<C> {
	public default void update(C o){
		
	}
	
	public void update();
}
