package de.shop.artikelverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;


@Path("/artikel")
@Produces(APPLICATION_JSON)
@Consumes
public class ArtikelResource {

	@Context
	private UriInfo uriInfo;
	@Inject
	private UriHelperArtikel uriHelperArtikel;
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	
	public Artikel findArtikelById(@PathParam("id") int id){
		final Artikel artikel = Mock.findArtikelById(id);
		if(artikel ==null){
			throw new NotFoundException("Kein Artikel mit der ID "+id+" gefunden");
		}
		return artikel;
		
	}
}
