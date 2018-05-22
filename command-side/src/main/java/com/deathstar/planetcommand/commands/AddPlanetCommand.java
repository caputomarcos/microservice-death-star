package com.deathstar.planetcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class AddPlanetCommand {

	@TargetAggregateIdentifier
	private final String id;
	private final String name;
	private final String climate;
	private final String terrain;
	private final Integer numberOfFilms;

	public AddPlanetCommand(String id, String name, String climate, String terrain, Integer numberOfFilms) {
		this.id = id;
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.numberOfFilms = numberOfFilms;
	}

	public String getId() {
		return id;
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

	public Integer getNumberOfFilms() {
		return numberOfFilms;
	}
}
