package de.shop.bestellverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;
import static de.shop.util.Constants.MIN_ID;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.net.URI;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.persistence.PostPersist;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.IdGroup;

@Cacheable
@Entity
@Table(name = "position")
public class Position implements Serializable {
	
	private static final long serialVersionUID = 2222771733641950913L;
	
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final int MENGE_MIN = 1;
	
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_ID, message = "{bestellverwaltung.position.id.min}", groups = IdGroup.class)
	private Long id = KEINE_ID;

	@Column(name = "menge", nullable = false)
	@Min(value = MENGE_MIN, message = "{bestellverwaltung.position.menge.min}")
	private short menge;

	@ManyToOne(optional = false)
    @JoinColumn(name = "artikel_fk", nullable = false)
	@NotNull(message = "{bestellverwaltung.position.artikel.notNull}")
	@JsonIgnore
	private Artikel artikel;
	
	@Transient
	private URI artikelUri;
	
	@Transient
	@JsonIgnore
	private BigDecimal preis;

	public Position() {
		super();
	}
	
	public Position(Artikel artikel) {
		super();
		this.artikel = artikel;
		this.menge = 1;
	}
	
	public Position(Artikel artikel, short menge) {
		super();
		this.artikel = artikel;
		this.menge = menge;
	}

	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Bestellposition mit ID=%d", id);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public short getMenge() {
		return menge;
	}
	public void setMenge(short menge) {
		this.menge = menge;
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
	
		
	public BigDecimal getPreis() {
		return preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
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
		result = prime * result + menge;
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
		if (menge != other.menge)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", menge=" + menge + ", artikel="
				+ artikel + ", artikelUri=" + artikelUri + "]";
	}
	

}
