package de.shop.artikelverwaltung.domain;

import java.io.Serializable;
import java.util.Set;

public class Kategorie implements Serializable {

	private static final long serialVersionUID = -1065751720590017636L;
	private int id;
	private Set<KategorieType> bezeichnung;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Set<KategorieType> getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(Set<KategorieType> bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result + id;
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
		final Kategorie other = (Kategorie) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} 
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Kategorie [id=" + id + ", bezeichnung=" + bezeichnung + "]";
	}
	
}
