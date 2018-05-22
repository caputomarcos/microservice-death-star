package com.deathstar.planetintegrationtests;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.deathstar.utils.Statics.*;
import static com.jayway.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddDuplicatePlanetTest {

	private static final Logger LOG = LoggerFactory.getLogger(AddDuplicatePlanetTest.class);
	private static String id;
	private static String name;
	private static String climate;
	private static String terrain;

	@BeforeClass
	public static void setupClass() {
		id = UUID.randomUUID().toString();
		name = "Duplicate Testing Planet [" + id + "]";
		climate = "Duplicate Testing Planet [" + id + "]";
		terrain = "Duplicate Testing Planet [" + id + "]";
	}

	@After
	public void afterEach() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2l);
	}

	@Test
	public void testAddOfDuplicatesFailsPartA() {
		given().port(PORT_FOR_GATEWAY).when()
				.post(PLANETS_CMD_BASE_PATH + CMD_PLANET_ADD + "/{id}?name={name}&climate={climate}&terrain={terrain}",
						id, name, climate, terrain)
				.then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void testAddOfDuplicatesFailsPartB() {
		given().port(PORT_FOR_GATEWAY).when()
				.post(PLANETS_CMD_BASE_PATH + CMD_PLANET_ADD + "/{id}?name={name}&climate={climate}&terrain={terrain}",
						id, name, climate, terrain)
				.then().statusCode(HttpStatus.SC_CONFLICT);
	}

	public static Logger getLog() {
		return LOG;
	}
}
