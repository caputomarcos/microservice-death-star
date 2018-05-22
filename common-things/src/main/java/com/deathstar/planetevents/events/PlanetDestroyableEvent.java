package com.deathstar.planetevents.events;

public class PlanetDestroyableEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanetDestroyableEvent() {
	}

	public PlanetDestroyableEvent(String id) {
		super(id);
	}
}
