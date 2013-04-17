package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import com.jayway.restassured.response.Response;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.util.LocaleHelper;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;


	
	@Path("/rechnung")
	@Produces(APPLICATION_JSON)
	@Consumes
	public class RechnungResource {

			@Context
			private UriInfo uriInfo;
			
			@Context
			private HttpHeaders headers;
			
			@Inject
			private UriHelperRechnung uriHelperRechnung;
			
			@Inject
			private LocaleHelper localeHelper;
			
			@GET
			@Path("{id:[1-9][0-9]*}")
			public Rechnung findRechnungById(@PathParam("id") Long id) {
				@SuppressWarnings("unused")
				final Locale locale = localeHelper.getLocale(headers);
				
				//TODO Anwendungskern statt Mock, Verwendung von Locale
				final Rechnung rechnung = Mock.findRechnungById(id);
				if(rechnung == null) {
					throw new Exception("Keine Rechnung mit der ID " + id + " gefunden.");
				}
				
				//URLs innerhalb der gefundenen Rechnung anpassen
				uriHelperRechnung.updateUriRechnung(rechnung, uriInfo);
				return rechnung;
			}
			
			@GET
			@Path("{id:[1-9][0-9]*}/bestellungen")
			public Collection<Rechnung> findRechnungByBestellungId(@PathParam("id") Long bestellungId) {
				final Collection<Rechnung> rechnungen = Mock.findRechnungByBestellungId(bestellungId);
				if (rechnungen.isEmpty()) {
					throw new NotFoundException("Zur ID " + bestellungId + " wurden keine Rechnungen gefunden");
				}
			
				// URLs innerhalb der gefundenen Bestellungen anpassen
				for (Rechnung rechnung : rechnungen) {
					uriHelperRechnung.updateUriRechnung(rechnung, uriInfo);
				}
			
			return rechnungen;
			}
			
			@POST
			@Consumes(APPLICATION_JSON)
			@Produces
			public Response createRechnung(Rechnung rechnung) {
							
				@SuppressWarnings ("unused")
				final Locale locale = localeHelper.getLocale(headers);
				
				rechnung = Mock.createRechnung(rechnung);
				final URI rechnungUri = uriHelperRechnung.getUriRechnung(rechnung, uriInfo);
			
				return Response.created(rechnungUri).build();
			}
			
			@PUT
			@Consumes(APPLICATION_JSON)
			@Produces
			public Response updateRechnung(Rechnung rechnung) {
				@SuppressWarnings ("unused")
				final Locale locale = localeHelper.getLocale(headers);
			
				Mock.updateRechnung(rechnung);
			
			return Response.noContent().build();
			}
		
	}

	}
