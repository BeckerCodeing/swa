package de.shop.bestellverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;
import static de.shop.util.Constants.MIN_ID;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;

import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.IdGroup;

@Entity
@Table(name = "bestellung")
@NamedQueries({
		@NamedQuery(name = Bestellung.FIND_BESTELLUNGEN_BY_KUNDE, query = "SELECT b"
				+ " FROM   Bestellung b"
				+ " WHERE  b.kunde = :"
				+ Bestellung.PARAM_KUNDE),
//		@NamedQuery(name = Bestellung.FIND_BESTELLUNG_BY_ID_FETCH_LIEFERUNGEN, query = "SELECT DISTINCT b"
//				+ " FROM   Bestellung b LEFT JOIN FETCH b.lieferungen"
//				+ " WHERE  b.id = :" + Bestellung.PARAM_ID),
		@NamedQuery(name = Bestellung.FIND_KUNDE_BY_ID, query = "SELECT b.kunde"
				+ " FROM   Bestellung b"
				+ " WHERE  b.id = :"
				+ Bestellung.PARAM_ID) })
public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1618359234119003714L;

	private static final Logger LOGGER = Logger.getLogger(MethodHandles
			.lookup().lookupClass());

	private static final String PREFIX = "Bestellung.";
	public static final String FIND_BESTELLUNGEN_BY_KUNDE = PREFIX
			+ "findBestellungenByKunde";
	public static final String FIND_BESTELLUNG_BY_ID_FETCH_LIEFERUNGEN = PREFIX
			+ "findBestellungenByIdFetchLieferungen";
	public static final String FIND_KUNDE_BY_ID = PREFIX
			+ "findBestellungKundeById";

	public static final String PARAM_KUNDE = "kunde";
	public static final String PARAM_ID = "id";

	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_ID, message = "{bestellverwaltung.bestellung.id.min}", groups = IdGroup.class)
	// private Long id;
	private Long id = KEINE_ID;

	private boolean ausgeliefert;

	@ManyToOne(optional = false)
	@JoinColumn(name = "kunde_fk", nullable = false, insertable = false, updatable = false)
	@NotNull(message = "{bestellverwaltung.bestellung.kunde.notNull}")
	// @Valid
	@JsonIgnore
	private Kunde kunde;

	@Transient
	private URI kundeUri;

	@OneToMany(fetch = EAGER, cascade = { PERSIST, REMOVE })
	@JoinColumn(name = "bestellung_fk", nullable = false)
	@OrderColumn(name = "idx", nullable = false)
	@NotEmpty(message = "{bestellverwaltung.bestellung.positionen.notEmpty}")
	// @NotNull(message = "{bestellverwaltung.bestellung.positionen.NotNull}")
	@Size(min = 1, message = "{bestellverwaltung.bestellung.positionen.Size}")
	@Valid
	private List<Position> positionen;

	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date erzeugt;

	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date aktualisiert;

	public Bestellung() {
		super();
	}

	public Bestellung(List<Position> positionen) {
		super();
		this.positionen = positionen;
	}

	@PrePersist
	private void prePersist() {
		erzeugt = new Date();
		aktualisiert = new Date();
	}

	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Bestellung mit ID=%d", id);
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

	public boolean isAusgeliefert() {
		return ausgeliefert;
	}

	public void setAusgeliefert(boolean ausgeliefert) {
		this.ausgeliefert = ausgeliefert;
	}

	public List<Position> getPositionen() {
		if (positionen == null) {
			return null;
		}

		return Collections.unmodifiableList(positionen);
	}

	public void setPositionen(List<Position> bestellpositionen) {
		if (this.positionen == null) {
			this.positionen = bestellpositionen;
			return;
		}

		// Wiederverwendung der vorhandenen Collection
		this.positionen.clear();
		if (bestellpositionen != null) {
			this.positionen.addAll(bestellpositionen);
		}
	}

	public Bestellung addBestellposition(Position bestellposition) {
		if (positionen == null) {
			positionen = new ArrayList<>();
		}
		positionen.add(bestellposition);
		return this;
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

	// public List<Position> getPositionen() {
	// return positionen;
	// }
	// public void setPositionen(List<Position> positionen) {
	// this.positionen = positionen;
	// }

	// Preis berechnen
	public BigDecimal calcPreis() {
		BigDecimal ergebnis = new BigDecimal(0.0);
		for (Position position : positionen) {
			ergebnis.add(position.calcPreis());
		}
		return ergebnis;
	}

	@JsonProperty("datum")
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
		this.aktualisiert = aktualisiert == null ? null : (Date) aktualisiert
				.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ausgeliefert ? 1231 : 1237);
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
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
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		} 
		else if (!kunde.equals(other.kunde))
			return false;
		if (positionen == null) {
			if (other.positionen != null)
				return false;
		} 
		else if (!positionen.equals(other.positionen))
			return false;
		return true;
	}



}
