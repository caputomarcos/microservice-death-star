package com.deathstar.planetcommand;

import com.deathstar.planetcommand.commands.AddPlanetCommand;
import com.deathstar.planetcommand.commands.DelPlanetCommand;
import com.deathstar.utils.Asserts;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.repository.ConcurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

@RestController
@RequestMapping("/planets")
public class PlanetRestController {

	private static final Logger LOG = LoggerFactory.getLogger(PlanetRestController.class);

	@Autowired
	CommandGateway commandGateway;

	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
	public void add(@PathVariable(value = "id") String id, @RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "climate", required = true) String climate,
			@RequestParam(value = "terrain", required = true) String terrain, HttpServletResponse response) {

		LOG.debug("Adding Planet [{}] '{}'", id, name);

		try {
			Asserts.INSTANCE.areNotEmpty(Arrays.asList(id, name, climate, terrain));
			// TODO - https://swapi.co/documentation
			AddPlanetCommand command = new AddPlanetCommand(id, name, climate, terrain, 3);
			commandGateway.sendAndWait(command);
			LOG.info("Added Planet [{}] '{}'", id, name);
			response.setStatus(HttpServletResponse.SC_CREATED);// Set up the 201 CREATED response
			return;
		} catch (AssertionError ae) {
			LOG.warn("Add Request failed - empty params?. [{}] '{}'", id, name, climate, terrain);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (CommandExecutionException cex) {
			LOG.warn("Add Command FAILED with Message: {}", cex.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			if (null != cex.getCause()) {
				LOG.warn("Caused by: {} {}", cex.getCause().getClass().getName(), cex.getCause().getMessage());
				if (cex.getCause() instanceof ConcurrencyException) {
					LOG.warn("A duplicate planet with the same ID [{}] already exists.", id);
					response.setStatus(HttpServletResponse.SC_CONFLICT);
				}
			}
		}
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	public void del(@PathVariable(value = "id") String id, HttpServletResponse response) {

		LOG.debug("Deleting Planet [{}]", id);

		try {
			Asserts.INSTANCE.areNotEmpty(Arrays.asList(id));
			DelPlanetCommand command = new DelPlanetCommand(id);
			commandGateway.sendAndWait(command);
			LOG.info("Deleted Planet [{}]", id);
			response.setStatus(HttpServletResponse.SC_CREATED);// Set up the 201 CREATED response
			return;
		} catch (AssertionError ae) {
			LOG.warn("Del Request failed - empty params?. [{}] '{}'", id);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (CommandExecutionException cex) {
			LOG.warn("Del Command FAILED with Message: {}", cex.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			if (null != cex.getCause()) {
				LOG.warn("Caused by: {} {}", cex.getCause().getClass().getName(), cex.getCause().getMessage());
				if (cex.getCause() instanceof ConcurrencyException) {
					LOG.warn("A duplicate planet with the same ID [{}] already exists.", id);
					response.setStatus(HttpServletResponse.SC_CONFLICT);
				}
			}
		}
	}

}
