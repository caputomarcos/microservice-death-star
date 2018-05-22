package com.deathstar.planetevents.events;

public class PlanetAddedEvent extends AbstractEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String climate;
	private String terrain;
	private Integer numberOfFilms;

	public PlanetAddedEvent() {
	}

	public PlanetAddedEvent(String id, String name, String climate, String terrain, Integer numberOfFilms) {
		super(id);
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.numberOfFilms = numberOfFilms;
	}

	public String getName() {
		return name;
	}

	public String getClimate() {
		return climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setNumberOfFilms(Integer numberOfFilms) {
		this.numberOfFilms = numberOfFilms;
	}
	
	public Integer getNumberOfFilms() {
		return numberOfFilms;
	}
}
