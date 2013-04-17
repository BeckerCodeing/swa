package de.shop.bestellverwaltung.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import de.shop.bestellverwaltung.domain.Position;
import de.shop.bestellverwaltung.domain.Warenkorb;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;

@Path("/warenkorb")
@Produces(APPLICATION_JSON)
@Consumes
public class WarenkorbResource {
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Warenkorb getWarenkorbById(@PathParam("id") Long id) {
		final Warenkorb warenkorb = Mock.getWarenKorbById(id);
		
		if (warenkorb == null) {
			throw new NotFoundException("Kein Warenkorb für Kunde-ID " + id + " gefunden");
		}
		
		
		return warenkorb;
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	@Produces
	public Response updateWarenkorbPosition(Position position) {
		//TODO Anwendungskern statt Mock
		Mock.updateWarenkorbPosition(position);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public Response resetWarenkorb(@PathParam("id") Long kundeId) {
		//TODO Anwendungskern statt Mock
		Mock.resetWarenkorb(kundeId);
		return Response.noContent().build();
	}
	
	//Position aus Warenkorb löschen, QueryParameter kundeId und positionId
	@DELETE
	@Produces
	public Response deleteWarenkorbPosition(@QueryParam("kundeId") @DefaultValue("") Long kundeId, 
											@QueryParam("positionId") @DefaultValue("") Long positionId) {
		//TODO Anweundskern statt Mock
		Mock.deleteWarenkorbPosition(Mock.findKundeById(kundeId), Mock.findPositionById(positionId));
		return Response.noContent().build();
	}
}
