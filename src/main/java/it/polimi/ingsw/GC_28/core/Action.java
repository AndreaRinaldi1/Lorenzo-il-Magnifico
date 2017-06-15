package it.polimi.ingsw.GC_28.core;

import it.polimi.ingsw.GC_28.client.ClientWriter;

public abstract class Action  {
	private ClientWriter writer;
	
	public ClientWriter getWriter() {
		return writer;
	}
	public void setWriter(ClientWriter writer) {
		this.writer = writer;
	}
	public abstract boolean isApplicable();
	public abstract void apply();
}
