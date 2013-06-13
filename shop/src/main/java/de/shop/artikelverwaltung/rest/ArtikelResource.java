package de.shop.artikelverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.Collection;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.service.ArtikelService;
import de.shop.util.LocaleHelper;
import de.shop.util.Log;
import de.shop.util.NotFoundException;
import de.shop.util.Transactional;
import static de.shop.util.Constants.KEINE_ID;


@Path("/artikel")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
@Transactional
@Log
public class ArtikelResource {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
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

	@PostConstruct
	private void postConstruct() {
		LOGGER.debugf("CDI-faehiges Bean %s wurde erzeugt", this);
	}
	
	@PreDestroy
	private void preDestroy() {
		LOGGER.debugf("CDI-faehiges Bean %s wird geloescht", this);
	}
	
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
				throw new NotFoundException("Kein Artikel vorhanden.");
			}
		}
		else {
			final Locale locale = localeHelper.getLocale(headers);
			gesuchteArtikel = as.findArtikelByBezeichnungWildcard(bezeichnung, locale);
			if (gesuchteArtikel.isEmpty()) {
				throw new NotFoundException("Kein Artikel mit Bezeichnung: " + bezeichnung + " vorhanden.");
			}
		}
		
		
	return gesuchteArtikel;
	}
	
	
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createArtikel(Artikel artikel) {
		final Locale locale = localeHelper.getLocale(headers);
		
		artikel.setId(KEINE_ID);
		
		artikel = as.createArtikel(artikel, locale);	
		LOGGER.tracef("Artikel %s", artikel);
		
		final URI artikelUri = uriHelperArtikel.getUriArtikel(artikel, uriInfo);		
		return Response.created(artikelUri).build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateArtikel(Artikel artikel) {
		// Vorhandenen Artikel ermitteln
		final Locale locale = localeHelper.getLocale(headers);
		final Artikel origArtikel = as.findArtikelById(artikel.getId(), locale);
		if (origArtikel == null) {
			throw new NotFoundException("Kein Artikel mit der ID " + artikel.getId());
		}
		LOGGER.tracef("Artikel vorher: %s", origArtikel);
		
		// Daten vorhandener Artikel überschreiben
		origArtikel.setValues(artikel);
		LOGGER.tracef("Artikel nachher: %s", origArtikel);
		
		// Update durchführen
		artikel = as.updateArtikel(origArtikel, locale);
		if (artikel == null) {
			throw new NotFoundException("Kein Artikel mit der ID " + origArtikel.getId());
		}
		return Response.noContent().build();
		
	}
	
	
	
}
