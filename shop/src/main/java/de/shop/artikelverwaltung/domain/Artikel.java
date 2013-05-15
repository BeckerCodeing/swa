package de.shop.artikelverwaltung.domain;

import static de.shop.util.Constants.MIN_ID;
import static de.shop.util.Constants.MIN_PREIS;

import java.io.Serializable;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import javax.validation.constraints.Size;



import de.shop.util.IdGroup;



public class Artikel implements Serializable {

	private static final long serialVersionUID = 5787206691085007571L;
	private static final int BEZEICHNUNG_LENGTH_MIN = 3;
	private static final int BEZEICHNUNG_LENGTH_MAX = 32;
	
	
	@Min(value = MIN_ID, message = "{artikelverwaltung.artikel.id.min}", groups = IdGroup.class)
	private Long id;
	
	///Bezeichnung darf nicht null sein, Groﬂbuchstabe gefolgt von min. 2 Kleinbuchstaben. L‰nge = max. 20 Zeichen 
	@NotNull(message = "{artikelverwaltung.artikel.bezeichnung.notNull}")
	@Pattern(regexp = "[A-Zƒ÷‹][a-z‰ˆ¸ﬂ]+", message = "{artikelverwaltung.artikel.bezeichnung.pattern}")
	@Size(min = BEZEICHNUNG_LENGTH_MIN, max = BEZEICHNUNG_LENGTH_MAX, 
		  message = "{artikelverwaltung.artikel.bezeichnung.length}")
	private String bezeichnung;
	
	@Min(value = MIN_PREIS, message = "{artikelverwaltung.artikel.preis.min}")
	private Double preis;
	
	@NotNull(message = "{artikelverwaltung.artikel.kategorie.notNull}")
	@Valid
	private Kategorie kategorie;
	
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

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}
	
	
	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kategorie == null) ? 0 : kategorie.hashCode());
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
		final Artikel other = (Artikel) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} 
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (kategorie == null) {
			if (other.kategorie != null)
				return false;
		} 
		else if (!kategorie.equals(other.kategorie))
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
		return "Artikel [id=" + id + ", bezeichnung=" + bezeichnung
				+ ", preis=" + preis + ", kategorie=" + kategorie + "]";
	}

	
}
