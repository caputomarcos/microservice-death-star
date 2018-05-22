package com.deathstar.planetcommand.aggregates;

import com.deathstar.planetcommand.commands.AddPlanetCommand;
import com.deathstar.planetcommand.commands.DelPlanetCommand;
import com.deathstar.planetcommand.commands.MarkPlanetAsDestroyableCommand;
import com.deathstar.planetcommand.commands.MarkPlanetAsUndestroyableCommand;
import com.deathstar.planetevents.events.PlanetAddedEvent;
import com.deathstar.planetevents.events.PlanetDeletedEvent;
import com.deathstar.planetevents.events.PlanetDestroyableEvent;
import com.deathstar.planetevents.events.PlanetUndestroyableEvent;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PlanetAggregate is essentially a DDD AggregateRoot (from the DDD concept). In
 * event-sourced systems, Aggregates are often stored and retreived using a
 * 'Repository'. In the simplest terms, Aggregates are the sum of their applied
 * 'Events'.
 * <p/>
 * The Repository stores the aggregate's Events in an 'Event Store'. When an
 * Aggregate is re-loaded by the repository, the Repository re-applies all the
 * stored events to the aggregate thereby re-creating the logical state of the
 * Aggregate.
 * <p/>
 * The PlanetAggregate Aggregate can handle and react to 'Commands', and when it
 * reacts to these com.deathstar.planet.commands it creates and 'applies' Events
 * that represent the logical changes to be made. These Events are also handled
 * by the PlanetAggregate.
 * <p/>
 * Axon takes care of much of this via the CommandBus, EventBus and Repository.
 * <p/>
 * Axon delivers com.deathstar.planet.commands placed on the bus to the
 * Aggregate. Axon supports the 'applying' of Events to the Aggregate, and the
 * handling of those events by the aggregate or any other configured
 * EventHandlers.
 */
public class PlanetAggregate extends AbstractAnnotatedAggregateRoot<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(PlanetAggregate.class);

	/**
	 * Aggregates that are managed by Axon must have a unique identifier. Strategies
	 * similar to GUID are often used. The annotation 'AggregateIdentifier'
	 * identifies the id field as such.
	 */
	@AggregateIdentifier
	private String id;
	private String name;
	private String climate;
	private String terrain;
	private boolean isDestroyable = false;

	/**
	 * This default constructor is used by the Repository to construct a prototype
	 * PlanetAggregate. Events are then used to set properties such as the
	 * PlanetAggregate's Id in order to make the Aggregate reflect it's true logical
	 * state.
	 */
	public PlanetAggregate() {
	}

	/**
	 * This constructor is marked as a 'CommandHandler' for the AddPlanetCommand.
	 * This command can be used to construct new instances of the Aggregate. If
	 * successful a new PlanetAddedEvent is 'applied' to the aggregate using the
	 * Axon 'apply' method. The apply method appears to also propagate the Event to
	 * any other registered 'Event Listeners', who may take further action.
	 *
	 * @param command
	 */
	@CommandHandler
	public PlanetAggregate(AddPlanetCommand command) {
		LOG.debug("Command: 'AddPlanetCommand' received.");
		LOG.debug("Queuing up a new PlanetAddedEvent for planet '{}'", command.getId());
		apply(new PlanetAddedEvent(command.getId(), command.getName(), command.getClimate(), command.getTerrain(),
				command.getNumberOfFilms()));
	}

	@CommandHandler
	public void PlanetWipeout(DelPlanetCommand command) {
		LOG.debug("Command: 'DelPlanetCommand' received.");
		LOG.debug("Queuing up a new PlanetAddedEvent for planet '{}'", command.getId());
		apply(new PlanetDeletedEvent(command.getId()));
	}

	@CommandHandler
	public void markDestroyable(MarkPlanetAsDestroyableCommand command) {
		LOG.debug("Command: 'MarkPlanetAsDestroyableCommand' received.");
		if (!this.isDestroyable()) {
			apply(new PlanetDestroyableEvent(id));
		} else {
			throw new IllegalStateException("This PlanetAggregate (" + this.getId() + ") is already Destroyable.");
		}
	}

	@CommandHandler
	public void markUndestroyable(MarkPlanetAsUndestroyableCommand command) {
		LOG.debug("Command: 'MarkPlanetAsUndestroyableCommand' received.");
		if (this.isDestroyable()) {
			apply(new PlanetUndestroyableEvent(id));
		} else {
			throw new IllegalStateException("This PlanetAggregate (" + this.getId() + ") is already off-sale.");
		}
	}

	/**
	 * This method is marked as an EventSourcingHandler and is therefore used by the
	 * Axon framework to handle events of the specified type (PlanetAddedEvent). The
	 * PlanetAddedEvent can be raised either by the constructor during
	 * PlanetAggregate(AddPlanetCommand) or by the Repository when 're-loading' the
	 * aggregate.
	 *
	 * @param event
	 */
	@EventSourcingHandler
	public void on(PlanetAddedEvent event) {
		this.id = event.getId();
		this.name = event.getName();
		LOG.debug("Applied: 'PlanetAddedEvent' [{}] '{}'", event.getId(), event.getName());
	}

	@EventSourcingHandler
	public void on(PlanetDeletedEvent event) {
		this.id = event.getId();
		LOG.debug("Applied: 'PlanetDeletedEvent' [{}] '{}'", event.getId());
	}

	@EventSourcingHandler
	public void on(PlanetDestroyableEvent event) {
		this.isDestroyable = true;
		LOG.debug("Applied: 'PlanetDestroyableEvent' [{}]", event.getId());
	}

	@EventSourcingHandler
	public void on(PlanetUndestroyableEvent event) {
		this.isDestroyable = false;
		LOG.debug("Applied: 'PlanetUndestroyableEvent' [{}]", event.getId());
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

	public boolean isDestroyable() {
		return isDestroyable;
	}
}
