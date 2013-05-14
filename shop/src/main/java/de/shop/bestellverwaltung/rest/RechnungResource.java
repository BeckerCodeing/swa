package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.Collection;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.bestellverwaltung.service.RechnungService;
import de.shop.util.LocaleHelper;
import de.shop.util.Log;
import de.shop.util.NotFoundException;


	
@Path("/rechnung")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
@Log
public class RechnungResource {

			@Context
			private UriInfo uriInfo;

			@Context
			private HttpHeaders headers;
			
			@Inject
			private RechnungService rs;
			
			@Inject
			private LocaleHelper localeHelper;
			
			@Inject
			private UriHelperRechnung uriHelperRechnung;
			
			@GET
			@Path("{id:[1-9][0-9]*}")
			public Rechnung findRechnungById(@PathParam("id") Long id) {
				
				final Locale locale = localeHelper.getLocale(headers);
				final Rechnung rechnung = rs.findRechnungById(id, locale);
				if (rechnung == null) {
					throw new NotFoundException("Keine Rechnung mit der ID " + id + " gefunden.");
				}
				
				//URLs innerhalb der gefundenen Rechnung anpassen
				uriHelperRechnung.updateUriRechnung(rechnung, uriInfo);
				return rechnung;
			}
			
//			FindRechnungByKundeId			
			@GET
			@Path("{id:[1-9][0-9]*}/kunden")
			public Collection<Rechnung> findRechnungByKundeId(@PathParam("id") Long kundeId) {
				final Collection<Rechnung> rechnungen = rs.findRechnungByKundeId(kundeId);
				if (rechnungen.isEmpty()) {
					throw new NotFoundException("Zum Kunden " + kundeId + " wurden keine Rechnungen gefunden");
				}
			
				for (Rechnung rechnung : rechnungen) {
					uriHelperRechnung.updateUriRechnung(rechnung, uriInfo);
				}
			
			return rechnungen;
			}
			
			@POST
			@Consumes(APPLICATION_JSON)
			@Produces
			public Response createRechnung(Rechnung rechnung) {
				
				final Locale locale = localeHelper.getLocale(headers);				
				rechnung = rs.createRechnung(rechnung, locale);
				final URI rechnungUri = uriHelperRechnung.getUriRechnung(rechnung, uriInfo);
			
				return Response.created(rechnungUri).build();
			}
			
			@PUT
			@Consumes(APPLICATION_JSON)
			@Produces
			public Response updateRechnung(Rechnung rechnung) {
				final Locale locale = localeHelper.getLocale(headers);
				rs.updateRechnung(rechnung, locale);
			
			return Response.noContent().build();
			}	
		
}

	
