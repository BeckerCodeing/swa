package de.shop.bestellverwaltung.domain;

import static de.shop.util.Constants.MIN_ID;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.IdGroup;

public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;
	
	@Min(value = MIN_ID, message = "{bestellverwaltung.bestellung.id.min}", groups = IdGroup.class)
	private Long id;
	private boolean ausgeliefert;
	
	@NotNull(message = "{bestellverwaltung.bestellung.kunde.notNull}")
	@JsonIgnore
	private Kunde kunde;
	private URI kundeUri;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		final Bestellung other = (Bestellung) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", ausgeliefert=" + ausgeliefert + ", kundeUri=" + kundeUri + "]";
	}
}
