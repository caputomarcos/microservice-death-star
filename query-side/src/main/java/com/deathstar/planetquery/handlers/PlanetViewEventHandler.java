package com.deathstar.planetquery.handlers;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.replay.ReplayAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deathstar.planetevents.events.PlanetAddedEvent;
import com.deathstar.planetevents.events.PlanetDeletedEvent;
import com.deathstar.planetevents.events.PlanetDestroyableEvent;
import com.deathstar.planetevents.events.PlanetUndestroyableEvent;
import com.deathstar.planetquery.domain.Planet;
import com.deathstar.planetquery.repository.PlanetRepository;
import com.deathstar.planetquery.util.EventModifier;
@Component
public class PlanetViewEventHandler implements ReplayAware {

	private static final Logger LOG = LoggerFactory.getLogger(PlanetViewEventHandler.class);

	@Autowired
	private PlanetRepository planetRepository;

	@EventHandler
	public void handle(PlanetAddedEvent event) {
		LOG.info("PlanetAddedEvent: [{}] '{}'", event.getId(), event.getName());
		// TODO - https://swapi.co/documentation - Anti-pattern 
		EventModifier.swapi(event);
		planetRepository.save(new Planet(event.getId(), event.getName(), event.getClimate(), event.getTerrain(),
				event.getNumberOfFilms(), false));
	}

	@EventHandler
	public void handle(PlanetDeletedEvent event) {
		LOG.info("PlanetDeletedEvent: [{}]", event.getId());
		if (planetRepository.exists(event.getId())) {
			Planet planet = planetRepository.findOne(event.getId());
			if (!planet.isDestroyable()) {
				planet.setDestroyable(true);
				planetRepository.delete(planet);
			}
		}
	}

	@EventHandler
	public void handle(PlanetDestroyableEvent event) {
		LOG.info("PlanetDestroyableEvent: [{}]", event.getId());
		if (planetRepository.exists(event.getId())) {
			Planet planet = planetRepository.findOne(event.getId());
			if (!planet.isDestroyable()) {
				planet.setDestroyable(true);
				planetRepository.save(planet);
			}
		}
	}

	@EventHandler
	public void handle(PlanetUndestroyableEvent event) {
		LOG.info("PlanetUndestroyableEvent: [{}]", event.getId());

		if (planetRepository.exists(event.getId())) {
			Planet planet = planetRepository.findOne(event.getId());
			if (planet.isDestroyable()) {
				planet.setDestroyable(false);
				planetRepository.save(planet);
			}
		}
	}

	public void beforeReplay() {
		LOG.info("Event Replay is about to START. Clearing the View...");
	}

	public void afterReplay() {
		LOG.info("Event Replay has FINISHED.");
	}

	public void onReplayFailed(Throwable cause) {
		LOG.error("Event Replay has FAILED.");
	}

}
