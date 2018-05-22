package com.deathstar.planetcommand.aggregates;

import com.deathstar.planetcommand.aggregates.PlanetAggregate;
import com.deathstar.planetcommand.commands.AddPlanetCommand;
import com.deathstar.planetcommand.commands.DelPlanetCommand;
import com.deathstar.planetcommand.commands.MarkPlanetAsDestroyableCommand;
import com.deathstar.planetcommand.commands.MarkPlanetAsUndestroyableCommand;
import com.deathstar.planetevents.events.AbstractEvent;
import com.deathstar.planetevents.events.PlanetAddedEvent;
import com.deathstar.planetevents.events.PlanetDeletedEvent;
import com.deathstar.planetevents.events.PlanetDestroyableEvent;
import com.deathstar.planetevents.events.PlanetUndestroyableEvent;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlanetAggregateTest {

	private FixtureConfiguration<PlanetAggregate> fixture;

	@Before
	public void setUp() throws Exception {
		fixture = Fixtures.newGivenWhenThenFixture(PlanetAggregate.class);
	}

	@Test
	public void testAddPlanet() throws Exception {
		fixture.given().when(new AddPlanetCommand("Dagobah", "Dagobah", "murky", "swamp, jungles", 3))
				.expectEvents(new PlanetAddedEvent("Dagobah", "Dagobah", "murky", "swamp, jungles", 3));
	}

	@Test
	public void testDelPlanet() throws Exception {
		List<AbstractEvent> events = new ArrayList<AbstractEvent>();
		events.add(new PlanetAddedEvent("Bespin", "Bespin", "temperate", "gas giant", 3));
		events.add(new PlanetDestroyableEvent("Bespin"));
		fixture.given(events).when(new DelPlanetCommand("Bespin")).expectVoidReturnType()
				.expectEvents(new PlanetDeletedEvent("Bespin"));
	}

	@Test
	public void testMarkPlanetItemAsDestroyable() throws Exception {
		fixture.given(new PlanetAddedEvent("Endor", "Endor", "temperate", "forests, mountains, lakes", 3))
				.when(new MarkPlanetAsDestroyableCommand("Endor")).expectVoidReturnType()
				.expectEvents(new PlanetDestroyableEvent("Endor"));
	}

	@Test
	public void testMarkPlanetItemAsUndestroyableIsAllowed() throws Exception {
		List<AbstractEvent> events = new ArrayList<AbstractEvent>();
		events.add(new PlanetAddedEvent("Naboo", "Naboo", "temperate", "grassy hills, swamps, forests, mountains", 3));
		events.add(new PlanetDestroyableEvent("Naboo"));

		fixture.given(events).when(new MarkPlanetAsUndestroyableCommand("Naboo")).expectVoidReturnType()
				.expectEvents(new PlanetUndestroyableEvent("Naboo"));
	}

	@Test
	public void testMarkPlanetItemAsUndestroyableIsPrevented() throws Exception {
		List<AbstractEvent> events = new ArrayList<AbstractEvent>();
		events.add(new PlanetAddedEvent("Naboo", "Naboo", "temperate", "grassy hills, swamps, forests, mountains", 3));

		fixture.given(events).when(new MarkPlanetAsUndestroyableCommand("Naboo"))
				.expectException(IllegalStateException.class);
	}
}
