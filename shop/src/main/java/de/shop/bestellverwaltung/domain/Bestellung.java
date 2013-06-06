package de.shop.bestellverwaltung.domain;

import static de.shop.util.Constants.MIN_ID;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.IdGroup;


public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;
	
	@Min(value = MIN_ID, message = "{bestellverwaltung.bestellung.id.min}", groups = IdGroup.class)
	private Long id;
	private boolean ausgeliefert;
	
	//TODO BigDecimal..
	private double gesamtpreis;
	
	@NotNull(message = "{bestellverwaltung.bestellung.kunde.notNull}")
	@Valid
	@JsonIgnore
	private Kunde kunde;
	
	private URI kundeUri;
	

	@NotNull(message = "{bestellverwaltung.bestellung.positionen.NotNull}")
	@Size(min = 1, message = "{bestellverwaltung.bestellung.positionen.Size}")
	@Valid
	private List<Position> positionen;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isAusgeliefert() {
		return ausgeliefert;
	}
	public void setAusgeliefert(boolean ausgeliefert) {
		this.ausgeliefert = ausgeliefert;
	}
	
	public Kunde getKunde() {
		return kunde;
	}
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
	
	public URI getKundeUri() {
		return kundeUri;
	}
	public void setKundeUri(URI kundeUri) {
		this.kundeUri = kundeUri;
	}
	public List<Position> getPositionen() {
		return positionen;
	}
	public void setPositionen(List<Position> positionen) {
		this.positionen = positionen;
	}
	
	public double getGesamtpreis() {
		return gesamtpreis;
	}
	
	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	
	//Preis berechnen
	public double calcPreis() {
		double ergebnis = 0;
		for (Position position : positionen) {
			ergebnis += position.calcPreis();
		}
		return ergebnis;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ausgeliefert ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(gesamtpreis);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result
				+ ((kundeUri == null) ? 0 : kundeUri.hashCode());
		result = prime * result
				+ ((positionen == null) ? 0 : positionen.hashCode());
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
		final Bestellung other = (Bestellung) obj;
		if (ausgeliefert != other.ausgeliefert)
			return false;
		if (Double.doubleToLongBits(gesamtpreis) != Double
				.doubleToLongBits(other.gesamtpreis))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		} 
		else if (!kunde.equals(other.kunde))
			return false;
		if (kundeUri == null) {
			if (other.kundeUri != null)
				return false;
		} 
		else if (!kundeUri.equals(other.kundeUri))
			return false;
		if (positionen == null) {
			if (other.positionen != null)
				return false;
		} 
		else if (!positionen.equals(other.positionen))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", ausgeliefert=" + ausgeliefert
				+ ", gesamtpreis=" + gesamtpreis + ", kunde=" + kunde
				+ ", kundeUri=" + kundeUri + "]";
	}
	

	
	
}
