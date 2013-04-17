package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import de.shop.bestellverwaltung.domain.Position;
import de.shop.util.Mock;


@Path("/position")
@Produces(APPLICATION_JSON)
@Consumes
public class PositionResource {
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Position findPositionById(@PathParam("id") Long id) {
		
		final Position position = Mock.findPositionById(id);
		return position;
	}
	
}
