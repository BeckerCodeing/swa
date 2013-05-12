package de.shop.kundenverwaltung.domain;

import static de.shop.util.Constants.MIN_ID;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.util.IdGroup;

public class Adresse implements Serializable {
	private static final long serialVersionUID = -4812303860223131704L;
	
	public static final int PLZ_LENGTH_MAX = 5;
	public static final int ORT_BEZEICHNUNG_LENGTH_MIN = 2;
	public static final int ORT_BEZEICHNUNG_LENGTH_MAX = 32;
	public static final int STRASSE_LENGTH_MIN = 2;
	public static final int STRASSE_LENGTH_MAX = 32;
	public static final int HAUSNUMMER_MIN = 1;
	
	@Min(value = MIN_ID, message = "{kundenverwaltung.adresse.id.min}", groups = IdGroup.class)
	private Long id;
	
	@NotNull(message = "{kundenverwaltung.adresse.plz.notNull}")
	@Pattern(regexp = "\\d{5}", message = "{kundenverwaltung.adresse.plz}")
	private String plz;
	
	@NotNull(message = "{kundenverwaltung.adresse.ort.notNull}")
	@Size(min = ORT_BEZEICHNUNG_LENGTH_MIN, max = ORT_BEZEICHNUNG_LENGTH_MAX, 
	message = "{kundenverwaltung.adresse.ort.length}")
	private String bezeichnung;
	
	@NotNull(message = "{kundenverwaltung.adresse.strasse.notNull}")
	@Size(min = STRASSE_LENGTH_MIN, max = STRASSE_LENGTH_MAX, message = "{kundenverwaltung.adresse.strasse.length}")
	private String strasse;
	
	@NotNull(message = "{kundenverwaltung.adresse.hausnummer.notNull}")
	@Min(value = HAUSNUMMER_MIN, message = "{kundenverwaltung.adresse.hausnummer.min")
	private int hausnummer;
	
	@NotNull(message = "{kundenverwaltung.adresse.kunde.notNull}")
	@JsonIgnore
	private Kunde kunde;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public int getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	
	public Kunde getKunde() {
		return kunde;
	}
	
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
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
		final Adresse other = (Adresse) obj;
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
		if (plz == null) {
			if (other.plz != null)
				return false;
		} 
		else if (!plz.equals(other.plz))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Adresse [id=" + id + ", plz=" + plz + ", bezeichnung="
				+ bezeichnung + ", strasse=" + strasse + ", hausnummer="
				+ hausnummer +  "]";
	}
}
