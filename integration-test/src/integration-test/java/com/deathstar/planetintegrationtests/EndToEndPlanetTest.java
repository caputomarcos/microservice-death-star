package com.deathstar.planetintegrationtests;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deathstar.utils.Statics;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.deathstar.utils.Statics.*;
import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EndToEndPlanetTest {

	private static final Logger LOG = LoggerFactory.getLogger(EndToEndPlanetTest.class);
	private static String id;
	private static String name;
	private static String climate;
	private static String terrain;

	@BeforeClass
	public static void setupClass() {
		id = UUID.randomUUID().toString();
		name = "End2End Test Planet [" + id + "]";
		climate = "End2End Test Planet [" + id + "]";
		terrain = "End2End Test Planet [" + id + "]";
	}

	@After
	public void afterEach() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2l);
	}

	/**
	 * Send a command to the command-side to create a new Planet.
	 */
	@Test
	public void testA_PostAPlanet() {

		given().port(PORT_FOR_GATEWAY).when()
				.post(PLANETS_CMD_BASE_PATH + CMD_PLANET_ADD + "/{id}?name={name}&climate={climate}&terrain={terrain}",
						id, name, climate, terrain)
				.then().statusCode(HttpStatus.SC_CREATED);

	}

	/**
	 * Check that the new Planet created event has arrived on the query-side and
	 * been made available for clients to view.
	 */

	@Test
	public void testB_GetAPlanet() {

		given().port(Statics.PORT_FOR_GATEWAY).when().get(PLANETS_QRY_BASE_PATH + "/{id}", id).then()
				.statusCode(HttpStatus.SC_OK).body("name", Matchers.is(name));
	}

	public static Logger getLog() {
		return LOG;
	}
}
