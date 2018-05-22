package com.deathstar.planetevents.events;

public class PlanetUndestroyableEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanetUndestroyableEvent() {
	}

	public PlanetUndestroyableEvent(String id) {
		super(id);
	}
}
