package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

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
	
	
	private BigDecimal preis;
	
	@NotNull(message = "{bestellverwaltung.position.artikel.notNull}")
	@Valid
	@JsonIgnore
	private Artikel artikel;
	
	private URI artikelUri;
		
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
	
	public BigDecimal getPreis() {
		return preis;
	}
	
	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public URI getArtikelUri() {
		return artikelUri;
	}
	public void setArtikelUri(URI artikelUri) {
		this.artikelUri = artikelUri;
	}
	
		
	//BigDecimal * int nicht möglich, daher in der Methode neuen BigDecimal(int Menge)
	public BigDecimal calcPreis() {
		return artikel.getPreis().multiply(new BigDecimal(this.getMenge()));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result
				+ ((artikelUri == null) ? 0 : artikelUri.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((menge == null) ? 0 : menge.hashCode());
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
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
		if (artikelUri == null) {
			if (other.artikelUri != null)
				return false;
		} 
		else if (!artikelUri.equals(other.artikelUri))
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
		if (preis == null) {
			if (other.preis != null)
				return false;
		} 
		else if (!preis.equals(other.preis))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Position [id=" + id + ", menge=" + menge + ", preis=" + preis
				+ ", artikel=" + artikel + ", artikelUri=" + artikelUri + "]";
	}
	
	
	
	
	
}
