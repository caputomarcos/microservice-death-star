package com.deathstar.planetquery.handlers;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.deathstar.planetevents.events.PlanetAddedEvent;
import com.deathstar.planetevents.events.PlanetDeletedEvent;
import com.deathstar.planetevents.events.PlanetDestroyableEvent;
import com.deathstar.planetevents.events.PlanetUndestroyableEvent;

/**
 * Handler's (a.k.a. Listeners) can be used to react to events and perform
 * associated actions, such as updating a 'materialised-view' for example.
 */
@Component
public class EventLoggingHandler {

	private static final Logger LOG = LoggerFactory.getLogger(EventLoggingHandler.class);

	@Value("${spring.application.index}")
	private Integer indexId;

	@EventHandler
	public void handle(PlanetAddedEvent event) {
		LOG.debug("Instance:[{}] Event:{} Id:{} Name:'{}'", indexId, event.getClass().getSimpleName(), event.getId(),
				event.getName());
	}

	@EventHandler
	public void handle(PlanetDeletedEvent event) {
		LOG.debug("Instance:[{}] Event:{} Id:{} Name:'{}'", indexId, event.getClass().getSimpleName(), event.getId());
	}

	@EventHandler
	public void handle(PlanetDestroyableEvent event) {
		LOG.debug("Instance:[{}] Event:{} Id:{}", indexId, event.getClass().getSimpleName(), event.getId());
	}

	@EventHandler
	public void handle(PlanetUndestroyableEvent event) {
		LOG.debug("Instance:[{}] Event:{} Id:{}", indexId, event.getClass().getSimpleName(), event.getId());
	}
}
