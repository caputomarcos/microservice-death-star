package com.deathstar.planetcommand.handlers;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.deathstar.planetevents.events.PlanetAddedEvent;
import com.deathstar.planetevents.events.PlanetDeletedEvent;
import com.deathstar.planetevents.events.PlanetDestroyableEvent;
import com.deathstar.planetevents.events.PlanetUndestroyableEvent;

/**
 * EventHandler's (a.k.a. EventListeners) are used to react to events and
 * perform associated actions.
 */
@Component
public class EventLoggingHandler {

	private static final Logger LOG = LoggerFactory.getLogger(EventLoggingHandler.class);
	private static final String IID = String.valueOf(Double.valueOf(Math.random() * 1000).intValue());

	@EventHandler
	public void handle(PlanetAddedEvent event) {
		LOG.debug("Instance:{} EventType:{} EventId:[{}] '{}'", IID, event.getClass().getSimpleName(), event.getId(),
				event.getName());
	}

	@EventHandler
	public void handle(PlanetDeletedEvent event) {
		LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
	}

	@EventHandler
	public void handle(PlanetDestroyableEvent event) {
		LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
	}

	@EventHandler
	public void handle(PlanetUndestroyableEvent event) {
		LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
	}
}
