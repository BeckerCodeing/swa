package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Locale;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import de.shop.bestellverwaltung.domain.Position;
import de.shop.bestellverwaltung.service.PositionService;
import de.shop.util.LocaleHelper;


@Path("/position")
@Produces(APPLICATION_JSON)
@Consumes
public class PositionResource {
	
	@Context
	private HttpHeaders headers;
	
	@Inject
	private LocaleHelper localeHelper;
	
	@Inject
	private PositionService ps;
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Position findPositionById(@PathParam("id") Long id) {
		
		final Locale locale = localeHelper.getLocale(headers);
		final Position position = ps.findPositionById(id, locale);
		return position;
	}
	
}
