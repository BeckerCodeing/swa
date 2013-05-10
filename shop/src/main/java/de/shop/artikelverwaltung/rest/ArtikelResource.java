package de.shop.artikelverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.Collection;

import java.util.Locale;

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
import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.util.LocaleHelper;
import de.shop.util.Log;
import de.shop.util.NotFoundException;


@Path("/artikel")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
@Log
public class ArtikelResource {
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpHeaders headers;

	@Inject
	private UriHelperArtikel uriHelperArtikel;
	
	@Inject
	private ArtikelService as;
	
	@Inject
	private LocaleHelper localeHelper;

	
	@GET
	@Path("{id:[1-9][0-9]*}")	
	public Artikel findArtikelById(@PathParam("id") Long id) {
		final Locale locale = localeHelper.getLocale(headers);
		final Artikel artikel = as.findArtikelById(id, locale);
		if (artikel == null) {
			throw new NotFoundException("Kein Artikel mit der ID " + id + " gefunden");
		}
		return artikel;
		
	}
	
	@GET
	public Collection<Artikel> findArtikelByBezeichnung(@QueryParam("bezeichnung")
	@DefaultValue("") String bezeichnung) {		
		Collection<Artikel> gesuchteArtikel = null;
		if ("".equals(bezeichnung)) {
			gesuchteArtikel = as.findAllArtikel();
			if (gesuchteArtikel.isEmpty()) {
				throw new NotFoundException("Keine Artikel vorhanden.");
			}
			
		}
		else {
			final Locale locale = localeHelper.getLocale(headers);
			gesuchteArtikel = as.findArtikelByBezeichnung(bezeichnung, locale);
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
		final Locale locale = localeHelper.getLocale(headers);
		artikel = as.createArtikel(artikel, locale);		
		final URI artikelUri = uriHelperArtikel.getUriArtikel(artikel, uriInfo);
		
		return Response.created(artikelUri).build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateArtikel(Artikel artikel) {
		final Locale locale = localeHelper.getLocale(headers);
		as.updateArtikel(artikel, locale);
		
		return Response.noContent().build();
		
	}
	
	
	
}
