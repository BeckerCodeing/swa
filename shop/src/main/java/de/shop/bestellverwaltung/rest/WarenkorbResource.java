package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Warenkorb;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;

@Path("/bestellungen")
@Produces(APPLICATION_JSON)
@Consumes
public class WarenkorbResource {
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UriHelperWarenkorb uriHelperWarenkorb;
	
	@GET
	@Path("{id:[1-9][0-9]*}/warenkorb")
	public Warenkorb getWarenkorbById(@PathParam("id") Long id) {
		final Warenkorb warenkorb = Mock.getWarenKorbById(id);
		
		if (warenkorb == null) {
			throw new NotFoundException("Kein Warenkorb für Kunde-ID " + id + " gefunden");
		}
		
		
		return warenkorb;
	}
	
}
