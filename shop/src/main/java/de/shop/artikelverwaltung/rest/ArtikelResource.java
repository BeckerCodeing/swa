package de.shop.artikelverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.Collection;



import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.LocaleHelper;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;


@Path("/artikel")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class ArtikelResource {

	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpHeaders headers;
	
	@Inject
	private UriHelperArtikel uriHelperArtikel;
	
	@Inject
	private LocaleHelper localeHelper;
	
	@GET
	@Path("{id:[1-9][0-9]*}")	
	public Artikel findArtikelById(@PathParam("id") Long id) {
		//TODO Anwendungskern statt Mock, Locale
		final Artikel artikel = Mock.findArtikelById(id);
		if (artikel == null) {
			throw new NotFoundException("Kein Artikel mit der ID " + id + " gefunden");
		}
		return artikel;
		
	}
	
	@GET
	public Collection<Artikel> findArtikelByBezeichnung(@QueryParam("bezeichnung") @DefaultValue("") String bezeichnung) {		
		
		//TODO Anwendungskern statt Mock, Locale
		Collection<Artikel> gesuchteArtikel = null;
		if ("".equals(bezeichnung)) {
			gesuchteArtikel = Mock.findAllArtikel();
			if (gesuchteArtikel.isEmpty()) {
				throw new NotFoundException("Keine Artikel vorhanden.");
			}
			
		}
		else {
			gesuchteArtikel = Mock.findArtikelByBezeichnung(bezeichnung);
			if (gesuchteArtikel.isEmpty()) {
				throw new NotFoundException("Kein Artikel mit Bezeichnung " + bezeichnung + " gefunden.");
			}
		}
		
		
	return gesuchteArtikel;
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createArtikel(Artikel artikel) {
		//TODO Anwendungskern statt Mock, Locale
		artikel = Mock.createArtikel(artikel);
	
		final URI artikelUri = uriHelperArtikel.getUriArtikel(artikel, uriInfo);
		return Response.created(artikelUri).build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateArtikel(Artikel artikel) {
		//TODO Anwendungskern statt Mock, Locale
		Mock.updateArtikel(artikel);
		return Response.noContent().build();
		
	}
	
	
	
}
