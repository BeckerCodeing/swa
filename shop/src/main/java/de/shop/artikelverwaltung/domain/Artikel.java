package de.shop.artikelverwaltung.domain;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Artikel {

	private int id;
	private String bezeichnung;
	private double preis;
	//TODO private Kategorie kategorie;
	
	@JsonIgnore
	private URI artikelUri;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}
	public URI getArtikelUri(){
		return artikelUri;
	}
	
	public void setArtikelUri(URI artikelUri)
	{
		this.artikelUri = artikelUri;
	}

		
	
}