package com.deathstar.planetcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DelPlanetCommand {

	@TargetAggregateIdentifier
	private final String id;

	public DelPlanetCommand(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
