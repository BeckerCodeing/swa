package de.shop.bestellverwaltung.domain;

import java.net.URI;

public class Rechnung {

	private Long id;
	private Bestellung bestellung;
	private URI bestellungUri;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Bestellung getBestellung() {
		return bestellung;
	}
	
	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}
	
	public URI getBestellungUri() {
		return bestellungUri;
	}
	
	public void setBestellungUri(URI bestellungUri) {
		this.bestellungUri = bestellungUri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bestellung == null) ? 0 : bestellung.hashCode());
		result = prime * result
				+ ((bestellungUri == null) ? 0 : bestellungUri.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rechnung other = (Rechnung) obj;
		if (bestellung == null) {
			if (other.bestellung != null)
				return false;
		} else if (!bestellung.equals(other.bestellung))
			return false;
		if (bestellungUri == null) {
			if (other.bestellungUri != null)
				return false;
		} else if (!bestellungUri.equals(other.bestellungUri))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rechnung [id=" + id + ", bestellung=" + bestellung
				+ ", bestellungUri=" + bestellungUri + "]";
	}
		
	
}
