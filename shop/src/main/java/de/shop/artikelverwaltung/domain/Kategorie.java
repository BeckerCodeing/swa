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
	
}
