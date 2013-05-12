package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;



import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;






import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;


	
@Path("/rechnung")
@Produces(APPLICATION_JSON)
@Consumes
public class RechnungResource {

			@Context
			private UriInfo uriInfo;
			
			@Inject
			private UriHelperRechnung uriHelperRechnung;
			
			@GET
			@Path("{id:[1-9][0-9]*}")
			public Rechnung findRechnungById(@PathParam("id") Long id) {
				//TODO Anwendungskern statt Mock, Verwendung von Locale
				final Rechnung rechnung = Mock.findRechnungById(id);
				if (rechnung == null) {
					throw new NotFoundException("Keine Rechnung mit der ID " + id + " gefunden.");
				}
				
				//URLs innerhalb der gefundenen Rechnung anpassen
				uriHelperRechnung.updateUriRechnung(rechnung, uriInfo);
				return rechnung;
			}
			
//			FindRechnungByKundeId			
//			@GET
//			@Path("{id:[1-9][0-9]*}/kunden")
//			public Collection<Rechnung> findRechnungByKundeId(@PathParam("id") Long kundeId) {
////				final Collection<Rechnung> rechnungen = Mock.findRechnungByKundeId(kundeId);
////				if (rechnungen.isEmpty()) {
//					throw new NotFoundException("Zum Kunden " + kundeId + " wurden keine Rechnungen gefunden");
//				}
//			
//				for (Rechnung rechnung : rechnungen) {
//					uriHelperRechnung.updateUriRechnung(rechnung, uriInfo);
//				}
//			
//			return rechnungen;
//			}
			
			@POST
			@Consumes(APPLICATION_JSON)
			@Produces
			public Response createRechnung(Rechnung rechnung) {
				
								
//				rechnung = Mock.createRechnung(rechnung);
				final URI rechnungUri = uriHelperRechnung.getUriRechnung(rechnung, uriInfo);
			
				return Response.created(rechnungUri).build();
			}
			
			@PUT
			@Consumes(APPLICATION_JSON)
			@Produces
			public Response updateRechnung(Rechnung rechnung) {
				Mock.updateRechnung(rechnung);
			
			return Response.noContent().build();
			}	
		
}

	
