package com.deathstar.planetevents.events;

public class PlanetDeletedEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanetDeletedEvent() {
	}

	public PlanetDeletedEvent(String id) {
		super(id);
	}

}
