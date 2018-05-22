package com.deathstar.planetcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class MarkPlanetAsUndestroyableCommand {

	@TargetAggregateIdentifier
	private final String id;

	/**
	 * This constructor must set the Id field, otherwise it's unclear to Axon which
	 * aggregate this command is intended for.
	 *
	 * @param id
	 */
	public MarkPlanetAsUndestroyableCommand(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
