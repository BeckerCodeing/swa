package de.shop.bestellverwaltung.rest;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.rest.UriHelperBestellung;

	@ApplicationScoped
	public class UriHelperRechnung {
		
		@Inject
		private UriHelperBestellung uriHelperBestellung;
		
		public void updateUriRechnung(Rechnung rechnung, UriInfo uriInfo) {
			// URL fuer Rechnung setzen
			final Bestellung bestellung = rechnung.getBestellung();
			if (bestellung != null) {
				final URI bestellungUri = uriHelperBestellung.getUriBestellung(rechnung.getBestellung(), uriInfo);
				rechnung.setBestellungUri(bestellungUri);
			}
			
		}

		public URI getUriRechnung(Rechnung rechnung, UriInfo uriInfo) {
			final UriBuilder ub = uriInfo.getBaseUriBuilder()
			                             .path(RechnungResource.class)
			                             .path(RechnungResource.class, "findRechnungById");
			final URI uri = ub.build(rechnung.getId());
			return uri;
		}
	}

