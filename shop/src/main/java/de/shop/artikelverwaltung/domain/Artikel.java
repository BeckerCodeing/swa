package de.shop.artikelverwaltung.domain;

import static de.shop.util.Constants.MIN_ID;
import static de.shop.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;


import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import javax.validation.constraints.Size;





import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.logging.Logger;

import de.shop.util.IdGroup;


@Entity
@Cacheable
@Table(name = "artikel")
@NamedQueries({
	@NamedQuery(name = Artikel.FIND_ARTIKEL_BY_ID,
				query = "SELECT 	a"
						+ " FROM 	Artikel a"
						+ " WHERE 	a.id = :id"),
	@NamedQuery(name = Artikel.FIND_ARTIKEL_ORDER_BY_ID,
				query = "SELECT		  a"
						+ " FROM	  Artikel a"
						+ " ORDER BY  a.id"),
	@NamedQuery(name = Artikel.FIND_ARTIKEL_BY_BEZ,
				query = "SELECT a"
						+ " FROM 		Artikel a"
						+ " WHERE 		a.bezeichnung LIKE :" + Artikel.PARAM_BEZEICHNUNG
						+ "				AND a.verfuegbar = TRUE"
						+ " ORDER BY	a.id ASC")

})
public class Artikel implements Serializable {

	private static final long serialVersionUID = 5787206691085007571L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private static final int BEZEICHNUNG_LENGTH_MAX = 32;
	
	private static final String PREFIX = "Artikel.";
	
	public static final String FIND_ARTIKEL_BY_ID = PREFIX + "findArtikelById";
	public static final String FIND_ARTIKEL_ORDER_BY_ID = PREFIX + "findArtikelOrderById";
	
	public static final String FIND_ARTIKEL_BY_BEZ = PREFIX + "findArtikelByBezeichnung";

	
	public static final String PARAM_BEZEICHNUNG = "bezeichnung";

	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_ID, message = "{artikelverwaltung.artikel.id.min}", groups = IdGroup.class)
	private Long id = KEINE_ID;
	
	@Column(length = BEZEICHNUNG_LENGTH_MAX, nullable = false)
	@NotNull(message = "{artikelverwaltung.artikel.bezeichnung.notNull}")
	@Size(max = BEZEICHNUNG_LENGTH_MAX, message = "{artikelverwaltung.artikel.bezeichnung.length}")
	private String bezeichnung;
	
	
	@Column(scale = 2, nullable = false)
	@DecimalMin("5.0")
	private BigDecimal preis;
	
	@Column(nullable = false)
	private boolean verfuegbar;
	
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date erzeugt;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date aktualisiert;
	
	
	public Artikel() {
		super();
		
		
	}
	public Artikel(String bezeichnung, BigDecimal preis) {
		super();
		this.bezeichnung = bezeichnung;
		this.preis = preis;
	}
	
	@PrePersist
	private void prePersist() {
		erzeugt = new Date();
		aktualisiert = new Date();
	}
	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neuer Artikel mit ID=%d", id);
	}
	
	@PreUpdate
	private void preUpdate() {
		aktualisiert = new Date();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public BigDecimal getPreis() {
		return preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}
	public void setVerfuegbar(boolean verfuegbar) {
		this.verfuegbar = verfuegbar;
	}

	public Date getErzeugt() {
		return erzeugt == null ? null : (Date) erzeugt.clone();
	}
	public void setErzeugt(Date erzeugt) {
		this.erzeugt = erzeugt == null ? null : (Date) erzeugt.clone();
	}
	public Date getAktualisiert() {
		return aktualisiert == null ? null : (Date) aktualisiert.clone();
	}
	public void setAktualisiert(Date aktualisiert) {
		this.aktualisiert = aktualisiert == null ? null : (Date) aktualisiert.clone();
	}
	
	public void setValues(Artikel a) {
		bezeichnung = a.bezeichnung;
		preis = a.preis;
		verfuegbar = a.verfuegbar;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
		result = prime * result + (verfuegbar ? 1231 : 1237);
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
		final Artikel other = (Artikel) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} 
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (preis == null) {
			if (other.preis != null)
				return false;
		} 
		else if (!preis.equals(other.preis))
			return false;
		if (verfuegbar != other.verfuegbar)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", preis=" + preis + ", verfuegbar=" + verfuegbar
				+ ", erzeugt=" + erzeugt
				+ ", aktualisiert=" + aktualisiert + "]";
	}
	
}
