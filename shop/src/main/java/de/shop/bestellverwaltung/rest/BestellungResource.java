package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;







import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;

@Path("/bestellungen")
@Produces(APPLICATION_JSON)
@Consumes
@RequestScoped
public class BestellungResource {
	@Context
	private UriInfo uriInfo;

	@Inject
	private UriHelperBestellung uriHelperBestellung;
		
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Bestellung findBestellungById(@PathParam("id") Long id) {
		
		// TODO Anwendungskern statt Mock, Verwendung von Locale
		final Bestellung bestellung = Mock.findBestellungById(id);
		if (bestellung == null) {
			throw new NotFoundException("Keine Bestellung mit der ID " + id + " gefunden.");
		}
		
		// URLs innerhalb der gefundenen Bestellung anpassen
		uriHelperBestellung.updateUriBestellung(bestellung, uriInfo);
		return bestellung;
	}
	
	//TODO Machen!
	@POST
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response createBestellung(Bestellung bestellung) {
		bestellung = Mock.createBestellung(bestellung);
		final URI bestellungUri = uriHelperBestellung.getUriBestellung(bestellung, uriInfo);
		return Response.created(bestellungUri).build();
	}

}
