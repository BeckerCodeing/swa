package de.shop.bestellverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
//import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.util.IdGroup;
//import de.shop.util.IdGroup;
//import static de.shop.util.Constants.MIN_ID;
import static de.shop.util.Constants.MIN_POSITION_MENGE;

@Entity
@Table(name = "position")
@NamedQueries({
//    @NamedQuery(name  = Position.FIND_LADENHUETER,
//   	            query = "SELECT a"
//   	            	    + " FROM   Artikel a"
//   	            	    + " WHERE  a NOT IN (SELECT bp.artikel FROM Bestellposition bp)")
})

public class Position implements Serializable {

	//private static final long serialVersionUID = 2212950377169631920L;
	
	private static final long serialVersionUID = 2222771733641950913L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final String PREFIX = "Bestellposition.";
	public static final String FIND_LADENHUETER = PREFIX + "findLadenhueter";
	//private static final int ANZAHL_MIN = 1;
	
	
	//private Long id;
	//@Min(value = MIN_ID, message = "{bestellverwaltung.position.id.min}", groups = IdGroup.class)
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	@Column(name = "menge", nullable = false)
	@Min(value = MIN_POSITION_MENGE, message = "{bestellverwaltung.position.menge.min}")
	private Integer menge;
	
//	@Transient
//	private BigDecimal preis;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "artikel_fk", nullable = false)
	@NotNull(message = "{bestellverwaltung.position.artikel.notNull}")
	//@Valid
	@JsonIgnore
	private Artikel artikel;
	
	@Transient
	private URI artikelUri;
	
	//@Column(name = "anzahl", nullable = false)
	//@Min(value = ANZAHL_MIN, message = "{bestellverwaltung.bestellposition.anzahl.min}")
	//private short anzahl;
	
	public Position() {
		super();
	}
	
	public Position(Artikel artikel) {
		super();
		this.artikel = artikel;
		this.menge = 1;
	}
	
	public Position(Artikel artikel, Integer anzahl) {
		super();
		this.artikel = artikel;
		this.menge = anzahl;
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
	public Integer getMenge() {
		return menge;
	}
	public void setMenge(Integer menge) {
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
	
		
	//BigDecimal * int nicht möglich, daher in der Methode neuen BigDecimal(int Menge)
	public BigDecimal calcPreis() {
		return artikel.getPreis().multiply(new BigDecimal(this.getMenge()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
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
		return "Position [id=" + id + ", menge=" + menge + ", artikel="
				+ artikel + "]";
	}

}
