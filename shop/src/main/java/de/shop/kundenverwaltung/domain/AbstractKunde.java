package de.shop.kundenverwaltung.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sun.xml.internal.rngom.util.Uri;

import de.shop.bestellverwaltung.domain.Bestellung;

public abstract class AbstractKunde {

	private Long id;
	private String vorname;
	private String nachname;
	private Adresse adresse;
	private String email;
	@JsonIgnore
	private List<Bestellung> bestellungen;
	private Uri bestellungenUri;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public Adresse getAdresse() {
		return adresse;
	}
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Bestellung> getBestellungen() {
		return bestellungen;
	}
	public void setBestellungen(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen;
	}
	public Uri getBestellungenUri() {
		return bestellungenUri;
	}
	public void setBestellungenUri(Uri bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
	}
//TODO: Override Methoden
}
