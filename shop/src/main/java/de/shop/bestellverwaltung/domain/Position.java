package de.shop.bestellverwaltung.domain;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.IdGroup;
import static de.shop.util.Constants.MIN_ID;
import static de.shop.util.Constants.MIN_POSITION_MENGE;

public class Position implements Serializable {

	private static final long serialVersionUID = 2212950377169631920L;
	
	@Min(value = MIN_ID, message = "{bestellverwaltung.position.id.min}", groups = IdGroup.class)
	private Long id;
	
	@Min(value = MIN_POSITION_MENGE, message = "{bestellverwaltung.position.menge.min}")
	private Integer menge;
	
	//TODO Braucht normalerweise keine Prüfung, ist doch eh ein errechnetes Feld
	private Double gesamtpreis;
	
	@NotNull(message = "{bestellverwaltung.position.artikel.notNull}")
	@Valid
	private Artikel artikel;
	
	//TODO Macht es hier Sinn mit @Valid nochmal Bestellung prüfen zu lassen?
	@JsonIgnore
	private Bestellung bestellung;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getMenge() {
		return menge;
	}
	public void setMenge(Integer menge) {
		this.menge = menge;
	}
	public Double getGesamtpreis() {
		return gesamtpreis;
	}
	public void setGesamtpreis(Double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public Bestellung getBestellung() {
		return bestellung;
	}
	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((gesamtpreis == null) ? 0 : gesamtpreis.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menge == null) ? 0 : menge.hashCode());
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
		final Position other = (Position) obj;
		if (artikel == null) {
			if (other.artikel != null)
				return false;
		}
		else if (!artikel.equals(other.artikel))
			return false;
		if (gesamtpreis == null) {
			if (other.gesamtpreis != null)
				return false;
		}
		else if (!gesamtpreis.equals(other.gesamtpreis))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (menge == null) {
			if (other.menge != null)
				return false;
		}
		else if (!menge.equals(other.menge))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Position [id=" + id + ", menge=" + menge + ", gesamtpreis="
				+ gesamtpreis + ", artikel=" + artikel + "]";
	}
	//TODO Preis für eine Position berechnen, zum Testen --> später anpassen
	public double calcPreis() {
		return artikel.getPreis() * this.getMenge();
	}
	
	
	
	
	
}
